package com.kansoubunko.kiyota.kansoubunko.dto;

import java.io.Serializable;

public class KansouEntity implements Serializable {

    //    //ID
//    private int id;
    //ユーザー名
    private String userName;
    //ユーザーパスワード
    private String userPassword;
    //本のID
    private int bookId;
    //本のタイトル
    private String bookTitle;
    //本の画像
    private String bookImage;
    //ほんの感想文
    private String bookReview;

    public KansouEntity() {
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

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

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public String getBookTitle() {
        return bookTitle;
    }

    public void setBookTitle(String bookTitle) {
        this.bookTitle = bookTitle;
    }

    public String getBookImage() {
        return bookImage;
    }

    public void setBookImage(String bookImage) {
        this.bookImage = bookImage;
    }

    public String getBookReview() {
        return bookReview;
    }

    public void setBookReview(String bookReview) {
        this.bookReview = bookReview;
    }

    @Override
    public String toString() {
        return "KansouEntity{" +
                "userName=" + userName +
                ", userPassword='" + userPassword + '\'' +
                ", bookId=" + bookId +
                ", bookTitle=" + bookTitle +
                ", bookImage=" + bookImage +
                ", bookReview=" + bookReview +
                '}';
    }
}
