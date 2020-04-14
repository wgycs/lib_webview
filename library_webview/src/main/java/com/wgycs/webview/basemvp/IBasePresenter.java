package com.wgycs.webview.basemvp;


public interface IBasePresenter {
    /**
     *  Presenter 和 View 进行绑定
     * */
    void attachView(IBaseView view);

    /**
     * Presenter 和 View 解绑
     * */
    void detachView();
}
