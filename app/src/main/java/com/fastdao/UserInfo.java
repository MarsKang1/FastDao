package com.fastdao;

import com.models.Column;

/**
 * Created by Administrator on 2017/12/29.
 */

public class UserInfo {
    @Column(name = "TEXT_VIEW")
    private String userName;
    @Column(name = "PASS_WORD")
    private String passWprd;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassWprd() {
        return passWprd;
    }

    public void setPassWprd(String passWprd) {
        this.passWprd = passWprd;
    }
}
