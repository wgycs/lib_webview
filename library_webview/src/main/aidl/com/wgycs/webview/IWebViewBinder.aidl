// IWebViewBinder.aidl
package com.wgycs.webview;

import com.wgycs.webview.ICallJsHolder;
// Declare any non-default types here with import statements

interface IWebViewBinder {


    /**
    *  处理 JS 的回调信息 服务端处理 来自 webview 的 js 调用
    */
    void handleJavaScriptCall(String json);

    /**
    * 用户调用 主动下向 JS 发送消息
    */
    void callJavaScriptByUser(String json);

    /**
    *  向服务端注册  操作 句柄
    */
    void registerHolder(ICallJsHolder holder);
    /**
    * 清除服务端 句柄
    */
    void unRegister();
}
