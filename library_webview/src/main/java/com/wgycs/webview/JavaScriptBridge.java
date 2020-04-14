package com.wgycs.webview;

import android.os.RemoteException;

import com.wgycs.webview.webinterface.IBaseWebInterface;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;

/**
 * @ProjectName: FM_android
 * @Package: com.wgycs.library_webview
 * @ClassName: JavaScriptBridge
 * @Description: 完成JavaScript 和 WebView的消息发送和 回调解析
 * @Create: By wangy / 2020/4/3 11:10
 * @Version: 1.0
 */

public class JavaScriptBridge implements IBaseWebInterface {

    private Object callBackListener;
    private static final String JavaScript_InterFace = "PageMessageHandle";
    private static JavaScriptBridge instance;

    private IWebViewBinder binder;

    /**
     * 获取单例实例
     * */
    public static JavaScriptBridge getInstance() {
        if (instance == null) {
            synchronized (JavaScriptBridge.class) {
                if (instance == null) {
                    instance = new JavaScriptBridge();
                }
            }
        }
        return instance;
    }

    public void setMainProcessBinder(IWebViewBinder binder) {
        this.binder = binder;
    }

    public void unsetMainProcessBinder() {
        this.binder = null;
    }

    /**
     * 将解析文件
     * @param clazz 协议 解析文件
     * */
    public void registerJavaScriptBridge(Class clazz) {
        try {
            this.callBackListener = clazz.newInstance();
        } catch (IllegalAccessException | InstantiationException except) {
            except.printStackTrace();
        }
    }

    /**
     * 供用户调用  向 JS 发送数据
     */
    public void callJavaScript(final String json) {
        try {
            binder.callJavaScriptByUser(json);
        } catch (RemoteException except) {
            except.printStackTrace();
        }
    }


    /**
     * 处理Json 发过来的回调信息
     *
     * @param json JavaScript传递过来的数据
     */
    public void parseJson(String json) {
        /**
         * 若回调为空  则无法发送到 应用层  将不处理解析
         * */
        if (callBackListener == null) {
            return;
        }

        String identity;
        int type;
        JSONObject params = null;

        try {
            JSONObject jsonObject = new JSONObject(json);
            type = jsonObject.getInt("type");
            // 错误的类型  不进行继续解析
            if (type == NATIVE_2_H5) {
                return;
            }

            identity = jsonObject.getString("identity");
            if (jsonObject.optJSONObject("data") != null) {
                params = jsonObject.getJSONObject("data");
            }

            Class<?> callbackClass = callBackListener.getClass();
            Method[] methods = callbackClass.getMethods();

            for (Method method : methods) {
                if (identity.equals(method.getName())) {
                    Class<?>[] identityParams = method.getParameterTypes();
                    if (identityParams.length == 0) {
                        method.invoke(callBackListener);
                        break;
                    }

                    if (params != null && identityParams.length == params.length()) {
                        //将 data 数据转换为 object对象  作为参数反射调用
                        JSONArray paramArr = params.names();
                        if (paramArr == null) {
                            return;
                        }
                        ArrayList<Object> args = new ArrayList<>();
                        for (int i = 0; i < params.length(); i++) {
                            String obj = (String) paramArr.get(i);
                            args.add(params.get(obj));
                        }
                        method.invoke(callBackListener, args.toArray());
                        break;
                    }
                }
            }
        } catch (JSONException | IllegalAccessException | InvocationTargetException except) {
            except.printStackTrace();
        }
    }

}
