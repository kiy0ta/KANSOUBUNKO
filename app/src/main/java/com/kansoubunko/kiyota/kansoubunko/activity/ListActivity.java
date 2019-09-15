package com.kansoubunko.kiyota.kansoubunko.activity;


import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.adapter.BookListGridAdapter;
import com.kansoubunko.kiyota.kansoubunko.dao.KansouDao;
import com.kansoubunko.kiyota.kansoubunko.dto.BookInfoEntity;
import com.kansoubunko.kiyota.kansoubunko.util.PhoneInfo;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {

    public KansouDao mDao;
    List<BookInfoEntity> bookInfoList = new ArrayList<>();
    List<BookInfoEntity> bookReviewList = new ArrayList<>();
    private int reviewListCount = 0;
    private String members[];

    /**
     * ゲージビューの最大幅
     */
    private double maxGaugeWidth;
    /**
     * ゲージビューの高さ
     */
    private int height;
    /**
     * ゲージビューの横幅
     */
    private int width;
    /**
     * ゲージビューのディスプレイ横幅に対する割合
     */
    private static final double RATE_OF_WIDTH_DISPLAY = 0.72;
    /**
     * ゲージビューのディスプレイ高さに対する割合
     */
    private static final double RATE_OF_HEIGHT_DISPLAY = 0.05;

    public static Intent getStartIntent(MainActivity mainActivity) {
        return new Intent(mainActivity, ListActivity.class);
    }

    // 表示する画像の名前（拡張子無し）
    private String members2[] = {
            "no_book_img", "no_book_img", "no_book_img", "no_book_img", "no_book_img",
            "no_book_img", "no_book_img", "no_book_img", "no_book_img", "no_book_img"
    };

    // Resource IDを格納するarray
    private List<Integer> imgList = new ArrayList<>();

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        final Resources res = getResources();

        //本のすべてのデータを取得する
        mDao = new KansouDao(getApplicationContext());
        bookInfoList = mDao.selectBookInfoAll();
//        Log.d("loglog","AllListSize:" + bookInfoList.size());
        //感想が記入されている本の件数を取得する
        int i = 0;
        for (BookInfoEntity entity : bookInfoList) {
            if (bookInfoList.get(i).getBookReview().length() > 1) {
                entity.setBookId(bookInfoList.get(i).getBookId());
                i++;
            }
            bookReviewList.add(entity);
            reviewListCount = bookReviewList.size();
        }

        // for-each member名をR.drawable.名前としてintに変換してarrayに登録
        int position = 0;
        String image = null;
        String title = null;
        for (BookInfoEntity entity : bookInfoList) {
            entity.setBookImage(bookInfoList.get(position).getBookImage());
            image = entity.getBookImage();
            int imageId = getResources().getIdentifier(
                    image, "drawable", getPackageName());
            imgList.add(imageId);
            //string[]の処理
            //TODO String[]の修正
            entity.setBookTitle(bookInfoList.get(position).getBookTitle());
            title = entity.getBookTitle();
            members = new String[]{title};
            position++;
        }
//        for (String member : members) {
//            int imageId = getResources().getIdentifier(
//                    member, "drawable", getPackageName());
//            imgList.add(imageId);
//        }

        //ゲージViewをインスタンス化
        TextView gaugeMaxTextView = findViewById(R.id.max_gauge);
        TextView gaugeTextView = findViewById(R.id.count_gauge);

        //ゲージViewの大きさを設定
        int width = (int) (PhoneInfo.getPhoneWidth(this) * this.RATE_OF_WIDTH_DISPLAY);
        int height = (int) (PhoneInfo.getPhoneWidth(this) * this.RATE_OF_HEIGHT_DISPLAY);
        gaugeMaxTextView.setWidth(width);
        gaugeMaxTextView.setHeight(height);
        int bookAllCount = bookInfoList.size();
        currentReviewCountGauge(reviewListCount, bookAllCount, width, height, gaugeTextView);

        //本の画像一覧を表示するGridViewを生成
        GridView bookListGridView = findViewById(R.id.list_book);
        BookListGridAdapter adapter = new BookListGridAdapter(this, R.layout.item_book_list, imgList, members);
        bookListGridView.setAdapter(adapter);

        Button bookRegistButton = findViewById(R.id.list_regist);
        bookRegistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(RegistActivity.getStartIntent(ListActivity.this));
            }
        });
        //画像が押下されたときの処理
        bookListGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(), RegistActivity.class);
                // clickされたpositionのtextとphotoのID
                long viewId = id;
                int selectedImage = imgList.get(position);
                String selectedTitle = members[position];

                // インテントにセット
                intent.putExtra("Image", selectedImage);
                intent.putExtra("Title", selectedTitle);
                // Activity をスイッチする
                startActivity(intent);
            }
        });

        //「◁」が押下されたときの処理
        TextView backTextView = findViewById(R.id.back_arrow);
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public void currentReviewCountGauge(double finishBookCount, double maxBookCount, double maxWidth, int height, TextView gaugeView) {
        this.maxGaugeWidth = maxWidth;
        //現在の達成率を算出
        final double currentAchievement = finishBookCount / maxBookCount;
        // ゲージの横幅を算出
        final int currentGaugeWidth = (int) (this.maxGaugeWidth * (currentAchievement));
        // ゲージ描画
        gaugeView.setWidth(currentGaugeWidth);
        gaugeView.setHeight(height);
    }
}
