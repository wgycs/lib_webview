// ICallJSHolder.aidl
package com.wgycs.webview;

// Declare any non-default types here with import statements

interface ICallJsHolder {

    /**
    * 调用  js 的方法函数  传递Json 数据
    */
    void callToJavaScript(String json);
}
