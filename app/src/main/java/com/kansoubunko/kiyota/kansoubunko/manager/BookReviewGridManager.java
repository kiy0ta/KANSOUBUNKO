package com.kansoubunko.kiyota.kansoubunko.manager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class BookReviewGridManager {

    private static List<String> list = new ArrayList<>();
    //カレンダー(GridView)のマスの総数を計算する
    public static final int COUNT_DAYS = 8 * 13;

    public BookReviewGridManager(List<String> reviewList) {
        list = reviewList;
    }

    public String reviewText() {
        for (int i = 0; i < COUNT_DAYS; i++) {
            list.add("あ");
        }
        return null;
    }
}
