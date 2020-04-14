package com.wgycs.webview;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.webkit.JavascriptInterface;
import android.webkit.WebSettings;
import android.webkit.WebView;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @ProjectName: FM_android
 * @Package: com.wgycs.library_webview
 * @ClassName: BridgeWebView
 * @Description: webView 桥接JS 和  android 的对象
 * @Create: By wangy / 2020/4/2 15:21
 * @Version: 1.0
 */
public class BridgeWebView extends WebView {

    private static final String TAG = "Webdemo";
    //  注册给 JS 的接口名称
    private static final String JS_INTERFACE_NAME = "Android";

    public BridgeWebView(Context context) {
        super(context);
        initWebViewSettings();
    }

    public BridgeWebView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initWebViewSettings();
    }

    public BridgeWebView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initWebViewSettings();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initWebViewSettings() {
        WebSettings webSettings = getSettings();
        // 设置支持 JavaScript 脚本运行

        //5.0以上开启混合模式加载
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        //允许缓存，设置缓存位置
        webSettings.setAppCacheEnabled(true);
        //设置UA
        webSettings.setUserAgentString("Android");
        webSettings.setJavaScriptEnabled(true);
        // js 可以自动打开windows
        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);
    }

    /**
     * 像webview中添加 JavaScript对象， 更加返回的协议内容调用 对应的接口
     */
    @Deprecated
    public void addBridgeJavaScriptInterface(Object webInterface) {
        addJavascriptInterface(new JavaScriptWrap(webInterface), JS_INTERFACE_NAME);
    }

    /**
     * 设置调用策略, 解析被调用接口，并前往调用
     */
    @Deprecated
    public static class JavaScriptWrap {

        private Object target;

        JavaScriptWrap(Object object) {
            target = object;
        }

        /**
         * 注册给js 调用的方法
         * */
        @JavascriptInterface
        public void nativeMessageHandle(String json) {
            //调用 多个参数 的方法时 可查找
            //Class<?>[] params = new Class[]{String[].class};
            try {
                Class<?> methodClass = this.target.getClass();
                Class<?> superClass = methodClass.getSuperclass();
                if (superClass != null) {
                    Method targetMethod = superClass.getDeclaredMethod("parseJson", String.class);
                    targetMethod.invoke(target, json);
                }
            } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException except) {
                except.printStackTrace();
            }

        }

    }
}
