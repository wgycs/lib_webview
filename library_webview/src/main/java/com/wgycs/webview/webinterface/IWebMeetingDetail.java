package com.wgycs.webview.webinterface;

/**
 * @ProjectName: FM_android
 * @Package: com.wgycs.library_webview.webinterface
 * @ClassName: IWebMeetingDetail
 * @Description: 会议详情 ：  操作路径 ： 选中会议页面 打开详情
 * @Create: By wangy / 2020/4/2 17:11
 * @Version: 1.0
 */
public interface IWebMeetingDetail {

    void initPage(String token, Long roomId, int openAudio, int openCamera);

    interface CallBack {
        /*参考其他说明*/
        void initPage();

        void back();

        /**
         * 启动会议
         */
        void startMeeting(Long roomId);

        /**
         * 复制会议邀请信息
         */
        void copyInvitation(String invitation);

        /*
         *  弹出会议邀请界面   暂定H5实现
         * */
        //void popMeetingShare(String url);
    }

}
