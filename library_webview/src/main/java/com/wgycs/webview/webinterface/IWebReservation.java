package com.wgycs.webview.webinterface;

/**
 * @ProjectName: FM_android
 * @Package: com.wgycs.library_webview.webinterface
 * @ClassName: IWebReservation
 * @Description: 声明 预约会议流程中的  ， WebView 相关的交互接口
 * @Create: By wangy / 2020/4/2 16:16
 * @Version: 1.0
 */
public interface IWebReservation {
    /**
     *  初始化 H5 -> Native  通知Native进行初始化
     *  Native 将初始化的值 传给H5。
         {
         "identity":"initPage",
         "type":1,//0:Native to H5; 1:H5 to Native
        "data":null
        }
    //------------------------------------------------------
        {
        "identity":"initPage",
        "type":0, //0:Native to H5; 1:H5 to Native
        "data":{
        "token":"xxxxx",
        "nickName":"昵称"
        }
        }
     */
    void initPage(String token, String nickName);


    interface CallBack {
        /**
         * 需要初始化的回调信息
         * */
        void initPage();

        /**
         * H5 - > Native   弹出返回事件
         * */
        void back();

        /**
         * 复制邀请信息成功
         * */
        void copyInvitation(String invitation);

    }







}
