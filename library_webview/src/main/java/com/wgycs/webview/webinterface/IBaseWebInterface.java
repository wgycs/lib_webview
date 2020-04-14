package com.wgycs.webview.webinterface;

/**
 * @ProjectName: FM_android
 * @Package: com.wgycs.library_webview.webinterface
 * @ClassName: IBaseWebInterface
 * @Description: IWeb Interface 基类
 * @Create: By wangy / 2020/4/2 20:30
 * @Version: 1.0
 */
public interface IBaseWebInterface {

    static final int NATIVE_2_H5 = 0;
    static final int H5_2_NATIVE = 1;
    // This method should not be changed.
    // The JavaScript will invoke it. {@link BridgeWebView}

    void parseJson(String json);
}
