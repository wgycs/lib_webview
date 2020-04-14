# lib_webview



该库是跨进程调用的webView库，其中包括了，webview和Js通信流程，以及ADIL跨进程通信机制。同时组件库内实现了对JS回调函数的解析。



## 使用方式

```java
Intent webIntent = new Intent(this, WebViewActivity.class);
        webIntent.putExtra("url", "file:///android_asset/Webdemo/index.html");
        startActivity(webIntent);

//注册回调处理方法
JavaScriptBridge.getInstance().registerJavaScriptBridge(WebRegister.class);

  //调用Js方法
JavaScriptBridge.getInstance().callJavaScript("jsonString");
```



