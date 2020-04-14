package com.wgycs.webview.basemvp;

import java.lang.ref.SoftReference;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Proxy;
import java.lang.reflect.Type;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;


public class BasePresenter<M extends  BaseModel , V extends  IBaseView> implements IBasePresenter {

    // 持有 View 的软引用  避免内存泄漏
    private SoftReference<IBaseView> softReferenceView;
    // 使用View的代理对象， 增加判断View的状态
    private V proxyView;
    // Model 对象
    private M model;

    private CompositeDisposable compositeDisposable;


    @SuppressWarnings("unchecked")
    @Override
    public void attachView(IBaseView view) {
        softReferenceView = new SoftReference<>(view);

        // Object to V 转换 unchecked
        //通过动态代理 增强 空判断
        proxyView = (V) Proxy.newProxyInstance(view.getClass().getClassLoader(), view.getClass().getInterfaces(), new InvocationHandler() {
            @Override
            public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
                if (softReferenceView == null || softReferenceView.get() == null) {
                    return null;
                }
                return method.invoke(softReferenceView.get(), args);
            }
        });

        //反射获取 Model 对象
        ParameterizedType parameterizedType = (ParameterizedType) this.getClass().getGenericSuperclass();

        if (parameterizedType != null) {
            // 获取实现类 列表
            Type[] types = parameterizedType.getActualTypeArguments();
            try {
                //创建 对象实例
                model = (M) ((Class<?>)types[0]).newInstance();
            } catch (InstantiationException | IllegalAccessException instantExcept) {
                instantExcept.printStackTrace();
            }

        }
    }

    @Override
    public void detachView() {
        if (softReferenceView != null) {
            softReferenceView.clear();
        }
        softReferenceView = null;
        model = null;
        unSubscribe();
    }

    /**
     * Rxjava 订阅对象 集中管理    detachView 时 集中销毁
     * */
    protected void addSubscribe(Disposable subscription) {
        if (subscription == null) {
            return;
        }

        if (compositeDisposable == null) {
            compositeDisposable = new CompositeDisposable();
        }

        compositeDisposable.add(subscription);
    }

    private void unSubscribe(){
        if (compositeDisposable != null) {
            compositeDisposable.dispose();
        }
    }

    @SuppressWarnings("unchecked")
    public V getView() {
        return proxyView;
    }

    protected M getModel() {
        return model;
    }
}
