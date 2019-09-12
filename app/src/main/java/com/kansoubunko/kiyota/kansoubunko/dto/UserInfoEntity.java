package com.kansoubunko.kiyota.kansoubunko.dto;

import java.io.Serializable;

public class UserInfoEntity implements Serializable {

    //ユーザーID
    private String userId;
    //ユーザー名
    private String userName;
    //ユーザーパスワード
    private String userPassword;

    public UserInfoEntity() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String id) {
        this.userId = id;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }

    @Override
    public String toString() {
        return "KansouEntity{" +
                "userId= " + userId +
                ", userName=" + userName +
                ", userPassword='" + userPassword +
                '}';
    }
}
