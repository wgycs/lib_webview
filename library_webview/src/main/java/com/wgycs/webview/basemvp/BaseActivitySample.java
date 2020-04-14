package com.wgycs.webview.basemvp;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;

/**
 * @ProjectName: FM_android
 * @Package: com.inpor.library_base.basemvp
 * @ClassName: BaseActivitySample
 * @Description: 不使用MVP 框架的 功能可继承此Base类
 * @Create: By wangy / 2020/4/2 17:51
 * @Version: 1.0
 */
public class BaseActivitySample extends BaseActivity {
    @Override
    protected void initLayout(@Nullable Bundle savedInstanceState) {

    }

    @Override
    protected IBasePresenter injectPresenter() {
        return null;
    }

    @Override
    protected void initViews() {

    }

    @Override
    protected void initData() {

    }

    @Override
    public Context getContext() {
        return null;
    }
}
