package com.wgycs.webview;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import android.webkit.ValueCallback;


import androidx.annotation.Nullable;

import com.wgycs.webview.basemvp.BaseActivitySample;
import com.wgycs.webview.service.MainProcessWebService;


/**
 * @ProjectName: FM_android
 * @Package: com.wgycs.library_webview
 * @ClassName: WebViewActivity
 * @Description: java类作用描述
 * @Create: By wangy / 2020/4/2 17:50
 * @Version: 1.0
 */
public class WebViewActivity extends BaseActivitySample implements JSInterface.JsFuncCallback {
    private static final String TAG = "WebViewActivity";

    /**
     * 注册给 JS 的接口名称
     */
    private static final String JS_INTERFACE_NAME = "Android";
    private static final String JavaScript_InterFace = "PageMessageHandle";

    /**
     * 初始化了的  web view
     */
    BridgeWebView webView;

    /**
     * 主进程 webserver binder 对象
     */
    private IWebViewBinder webViewBinder;

    /**
     * 注册给webview 的 JSInterface
     */
    private JSInterface jsInterface;


    @Override
    protected void initLayout(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.activity_library_webview);
    }

    @Override
    protected void initViews() {
        webView = findViewById(R.id.library_webview_container);
        //TODO  加入 Loading
        Intent intent = getIntent();
        String url = intent.getStringExtra("url");
        webView.loadUrl(url);
        initBinder();
        initJSInterface();
    }

    /**
     * 初始化 JS interface 并注册给 webview
     */
    private void initJSInterface() {
        jsInterface = new JSInterface();
        jsInterface.setCallback(this);
        webView.addJavascriptInterface(jsInterface, JS_INTERFACE_NAME);
    }

    /**
     * 初始化binder对象
     */
    private void initBinder() {
        Intent serviceIntent = new Intent(this, MainProcessWebService.class);
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE);
    }


    private ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            webViewBinder = IWebViewBinder.Stub.asInterface(service);
            Log.d(TAG, "onServiceConnected: " + name);
            try {
                webViewBinder.registerHolder(callJsHolder);
            } catch (RemoteException except) {
                except.printStackTrace();
            }
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Log.d(TAG, "onServiceDisconnected: " + name);
            try {
                webViewBinder.unRegister();
                JavaScriptBridge.getInstance().unsetMainProcessBinder();
            } catch (RemoteException except) {
                except.printStackTrace();
            }
        }
    };

    private ICallJsHolder.Stub callJsHolder = new ICallJsHolder.Stub() {
        @Override
        public void callToJavaScript(final String json) throws RemoteException {
            webView.post(new Runnable() {
                @Override
                public void run() {
                    String call = "javascript:" + JavaScript_InterFace + "(\'" + json + "\')";
                    Log.d(TAG, "run: Call js" + call);
                    webView.evaluateJavascript(call, new ValueCallback<String>() {
                        @Override
                        public void onReceiveValue(String value) {
                            Log.d(TAG, "onReceiveValue: " + value);
                        }
                    });
                }
            });
        }
    };

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unbindService(connection);
    }


    @Override
    public void execute(String json) {
        Log.d(TAG, "execute: js called" + json);
        try {
            webViewBinder.handleJavaScriptCall(json);
        } catch (RemoteException except) {
            except.printStackTrace();
        }
    }
}
