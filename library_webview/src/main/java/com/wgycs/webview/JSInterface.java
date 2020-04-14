package com.wgycs.webview;

import android.util.Log;
import android.webkit.JavascriptInterface;

import org.json.JSONObject;

/**
 * @ProjectName: FM_android
 * @Package: com.wgycs.library_webview
 * @ClassName: JavaScriptInterface
 * @Description: webview js 接口处理类
 * @Create: By wangy / 2020/4/7 19:49
 * @Version: 1.0
 */
public final class JSInterface {

    private JsFuncCallback callback;

    public void setCallback(JsFuncCallback callback) {
        this.callback = callback;
    }

    public interface JsFuncCallback {
        void execute(String json);
    }

    /**
     * 注册给 js 调用的方法
     * */
    @JavascriptInterface
    public void nativeMessageHandle(final String json) {
        if (callback != null) {
            callback.execute(json);
        }
    }

    /**
     * js callback
     * */
    @JavascriptInterface
    public void nativeMessageHandle(final JSONObject json) {
        if (callback != null) {
            callback.execute(json.toString());
            Log.d("JSONObject", "nativeMessageHandle: " + json.toString());
        }
    }

}
