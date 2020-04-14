package com.wgycs.webview.webinterface;

/**
 * @ProjectName: FM_android
 * @Package: com.wgycs.library_webview.webinterface
 * @ClassName: IWebRegister
 * @Description: 声明 注册流程中  ， WebView 相关的交互接口
 * @Create: By wangy / 2020/4/2 16:16
 * @Version: 1.0
 */
public interface IWebRegister extends IBaseWebInterface {
    /**
     * 初始化 H5 -> Native  通知Native进行初始化
     * Native 将初始化的值 传给H5。
     * //收到的Json格式
     * {
     * "identity":"initPage",
     * "type":1,
     *//*0:Native to H5; 1:H5 to Native*//*
                "data":null
        }
        // 需要设置的Json格式
        {
            "identity":"initPage",
                "type":0,*//*0:Native to H5; 1:H5 to Native*//*
                "data":{
                    "clientVersion":"客户端版本号",
                    "clientSource":"客户端来源",
                    "agentCode":"渠道码"
       *         }
       *   }
       */
    void initPage(String clientVersion, String clientSource, String agentCode);


    interface CallBack {

        /*
         * 回调
         * */
        void initPage();
        /*
         * H5 -> Native
         * 注册成功  H5 返回成功 ， 应用层处理
         */

        /*{
          "identity":"registSuccess",
          "type":1,/*0:Native to H5; 1:H5 to Native
          "data":{
              "userName":"用户名",
               "passWord":"密码"
              }
          }
      */
        void registSuccess(String userName, String password);
    }

}
