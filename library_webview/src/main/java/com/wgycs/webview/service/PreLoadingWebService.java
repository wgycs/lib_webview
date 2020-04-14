package com.wgycs.webview.service;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

/**
 * H5 web 预加载
 * */
public class PreLoadingWebService extends Service {

    public PreLoadingWebService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }
}
