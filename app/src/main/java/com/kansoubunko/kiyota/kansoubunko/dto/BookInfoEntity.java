package com.kansoubunko.kiyota.kansoubunko.dto;

import java.io.Serializable;

public class BookInfoEntity implements Serializable {

    //本のID
    private String bookId;
    //ユーザーのID
    private String bookUserName;
    //本のタイトル
    private String bookTitle;
    //本の画像
    private String bookImage;
    //ほんの感想文
    private String bookReview;

    public BookInfoEntity() {
    }
    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getBookUserName() {
        return bookUserName;
    }

    public void setBookUserName(String bookUserName) {
        this.bookUserName = bookUserName;
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
                "bookId=" + bookId +
                ", bookUserName=" + bookUserName +
                ", bookTitle=" + bookTitle +
                ", bookImage=" + bookImage +
                ", bookReview=" + bookReview +
                '}';
    }
}
