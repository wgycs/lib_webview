package com.wgycs.webview.webinterface;

/**
 * @ProjectName: FM_android
 * @Package: com.wgycs.library_webview.webinterface
 * @ClassName: IWebPersionI
 * @Description: 个人信息 ， 选中我的  打开个人信息， 可修改昵称、手机号、邮箱等
 * @Create: By wangy / 2020/4/2 17:45
 * @Version: 1.0
 */
public interface IWebPersonal {

    void initPage(String token);

    interface CallBack {
        void initPage();

        /**
         * 更新用户信息
         */
        void updateUserInfo(String nickName);

        /**
         * 退出登录  以及  信息修改（密码、手机号）后需要重新登录
         */
        void logout();
    }

}
