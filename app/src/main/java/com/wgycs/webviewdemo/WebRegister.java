package com.wgycs.webviewdemo;

import android.util.Log;

import com.wgycs.webview.JavaScriptBridge;
import com.wgycs.webview.webinterface.BaseProtocol;
import com.wgycs.webview.webinterface.IWebRegister;

/**
 * @ProjectName: FM_android
 * @Package: com.inpor.library_webview.webinterface
 * @ClassName: WebRegister
 * @Description: java类作用描述
 * @Create: By wangy / 2020/4/8 11:05
 * @Version: 1.0
 */
public class WebRegister extends BaseProtocol implements IWebRegister.CallBack {

    private static final String TAG = "WebRegister";

    @Override
    public void initPage() {
        Log.d(TAG, "initPage ");
        //JavaScriptBridge.getInstance().callJavaScript("aaa");
    }

    @Override
    public void registSuccess(String userName, String password) {
        Log.d(TAG, "registSuccess: " + userName);
    }
}
