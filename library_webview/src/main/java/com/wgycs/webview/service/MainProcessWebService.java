package com.wgycs.webview.service;

import android.app.Service;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.IBinder;
import android.os.Message;
import android.os.RemoteException;
import android.util.Log;

import androidx.annotation.NonNull;

import com.wgycs.webview.ICallJsHolder;
import com.wgycs.webview.IWebViewBinder;
import com.wgycs.webview.JavaScriptBridge;

public class MainProcessWebService extends Service {
    private static final String TAG = "MainProcessWebService";

    private static final int NATIVE_2_H5 = 0;
    private static final int H5_2_NATIVE = 1;

    private ICallJsHolder callJsHolder;

    private HandlerThread handlerThread;
    private Handler workHandler;

    @Override
    public void onCreate() {
        super.onCreate();
        handlerThread = new HandlerThread(TAG);
        handlerThread.start();

        //handler Thread start后 才可获取 Looper
        workHandler = new Handler(handlerThread.getLooper()) {
            @Override
            public void dispatchMessage(@NonNull Message msg) {
                super.dispatchMessage(msg);
                String json = (String) msg.obj;
                switch (msg.what) {
                    case NATIVE_2_H5:
                        try {
                            callJsHolder.callToJavaScript(json);
                        } catch (RemoteException except) {
                            except.printStackTrace();
                        }
                        break;
                    case H5_2_NATIVE:
                        Log.d(TAG, "dispatchMessage: " + msg.obj);
                        //Json 数据 异常的时候 需要捕获
                        try {
                            JavaScriptBridge.getInstance().parseJson(json);
                        } catch (Exception except) {
                            except.printStackTrace();
                        }
                        break;

                    default:
                        // no need to do .only for check style
                        break;
                }


            }
        };
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handlerThread.quit();
    }

    private IWebViewBinder.Stub serviceBinder = new IWebViewBinder.Stub() {
        @Override
        public void handleJavaScriptCall(String json) throws RemoteException {
            Log.d(TAG, "handleJavaScriptCall: " + json);
            Message message = Message.obtain();
            message.what = H5_2_NATIVE;
            message.obj = json;
            workHandler.sendMessage(message);
        }

        @Override
        public void callJavaScriptByUser(String json) throws RemoteException {
            Message message = Message.obtain();
            message.what = NATIVE_2_H5;
            message.obj = json;
            workHandler.sendMessage(message);
        }

        @Override
        public void registerHolder(ICallJsHolder holder) throws RemoteException {
            callJsHolder = holder;
        }

        @Override
        public void unRegister() throws RemoteException {
            callJsHolder = null;
        }
    };

    @Override
    public IBinder onBind(Intent intent) {
        Log.d(TAG, "onBind: ");
        JavaScriptBridge.getInstance().setMainProcessBinder(serviceBinder);
        return serviceBinder;
    }

    @Override
    public void unbindService(ServiceConnection conn) {
        Log.d(TAG, "unbindService: ");
        JavaScriptBridge.getInstance().unsetMainProcessBinder();
        super.unbindService(conn);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        return super.onUnbind(intent);
    }
}
