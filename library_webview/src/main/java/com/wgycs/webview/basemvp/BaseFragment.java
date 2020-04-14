package com.wgycs.webview.basemvp;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.LayoutRes;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


//androidx.fragment.app.Fragment; 实现了
//LifecycleOwner  LifecycleObserver  监听 和 发送生命周期
public abstract class BaseFragment<P extends BasePresenter> extends Fragment implements IBaseView {

    private P presenter;
    protected Context attachedContext;

    /**
     * 获取资源ID
     * */
    protected abstract @LayoutRes
    int getLayoutResId();

    protected abstract P injectPresenter();

    protected abstract void initViews();

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        this.attachedContext = context;
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        try {
            return inflater.inflate(getLayoutResId(), null);
        } catch (Exception exception) {
            exception.printStackTrace();
            return null;
        }
    }

    /**
     * 当前 fragment的Activity 和 fragment的View层级实例被创建完成，
     * 通常被用来进行最后的初始化，如检索视图或恢复状态。这个方法会在 {@link #onCreateView} 调用之后，
     * 但会在 {@link #onViewStateRestored(Bundle)} 之前。
     * */
    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        presenter = injectPresenter();
        if (presenter != null) {
            presenter.attachView(this);
        }
        initViews();
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (presenter != null) {
            presenter.detachView();
        }
        presenter = null;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        this.attachedContext = null;
    }


}
