package com.kansoubunko.kiyota.kansoubunko.dto;

import java.io.Serializable;

public class UserInfoEntity implements Serializable {

    //ユーザーID
    private String userId;
    //ユーザー名
    private String userName;
    //ユーザーパスワード
    private String userPassword;
    //誕生日
    private String userBirthday;
    //フォロー数
    private String follow;
    //フォロワー数
    private String followers;
    //ユーザー画像
    private String userImage;
    //自己紹介
    private String profile;

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

    public String getUserBirthday() {
        return userBirthday;
    }

    public void setUserBirthday(String userBirthday) {
        this.userBirthday = userBirthday;
    }

    public String getFollow() {
        return follow;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getFollowers() {
        return followers;
    }

    public void setFollowers(String followers) {
        this.followers = followers;
    }

    public String getUserImage() {
        return userImage;
    }

    public void setUserImage(String userImage) {
        this.userImage = userImage;
    }

    public String getProfile() {
        return profile;
    }

    public void setProfile(String profile) {
        this.profile = profile;
    }

    @Override
    public String toString() {
        return "UserInfoEntity{" +
                "userId= " + userId +
                ", userName=" + userName +
                ", userPassword=" + userPassword +
                ", userBirthday=" + userBirthday +
                ", follow=" + follow +
                ", followers=" + followers +
                ", userImage=" + userImage +
                ", profile=" + profile +
                '}';
    }
}
