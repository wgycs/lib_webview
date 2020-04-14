package com.wgycs.webviewdemo;

import android.app.Application;
import android.content.Intent;

import com.wgycs.webview.service.PreLoadingWebService;

/**
 * @ProjectName: WebviewSample
 * @Package: com.wgycs.webviewdemo
 * @ClassName: CSApplication
 * @Description: java类作用描述
 * @Create: By wangy / 2020/4/14 20:52
 * @Version: 1.0
 */
public class CSApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //进程预加载
        startService(new Intent(this, PreLoadingWebService.class));
    }
}
