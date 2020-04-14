package com.wgycs.webviewdemo;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.wgycs.webview.JavaScriptBridge;
import com.wgycs.webview.WebViewActivity;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    /**
     * action define in activity_main.xml
     * */
    public void onButton(View view) {
        Intent webIntent = new Intent(this, WebViewActivity.class);
        webIntent.putExtra("url", "file:///android_asset/Webdemo/index.html");
        startActivity(webIntent);

        //注册回调处理方法
        JavaScriptBridge.getInstance().registerJavaScriptBridge(WebRegister.class);

        //调用Js方法
        JavaScriptBridge.getInstance().callJavaScript("jsonString");
    }
}
