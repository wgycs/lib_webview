package com.wgycs.webview.basemvp;

import android.os.Bundle;

import androidx.annotation.IdRes;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity <P extends IBasePresenter> extends AppCompatActivity implements IBaseView {

    //IBasePresenter 对象
    private P presenter;

    protected abstract void initLayout(@Nullable Bundle savedInstanceState);

    protected abstract P injectPresenter();

    protected abstract void initViews();

    protected abstract void initData();

    /**
     * 存在Fragment 需实现该方法制定Fragment的位置
     * */
    @IdRes
    protected int FragmentContainer(){
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initLayout(savedInstanceState);

        /**
         * 获取 实例对象并  和View 绑定
         * */
        presenter = injectPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }

        initData();
        initViews();
    }

    public P getPresenter() {
        return presenter;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 销毁时 释放对象
        if (presenter != null) {
            this.presenter.detachView();
        }

        this.presenter = null;
    }
}
