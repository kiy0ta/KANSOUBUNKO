package com.kansoubunko.kiyota.kansoubunko.fragment;


import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.TextView;

import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.activity.MainActivity;
import com.kansoubunko.kiyota.kansoubunko.adapter.BookListGridAdapter;
import com.kansoubunko.kiyota.kansoubunko.dao.KansouDao;
import com.kansoubunko.kiyota.kansoubunko.dto.BookInfoEntity;
import com.kansoubunko.kiyota.kansoubunko.util.PhoneInfo;

import java.util.ArrayList;
import java.util.List;

public class ListFragment extends Fragment {

    public KansouDao mDao;
    private List<BookInfoEntity> bookInfoList = new ArrayList<>();
    private List<BookInfoEntity> bookReviewList = new ArrayList<>();
    //bookTitleを格納するarray
    private List<String> bookTitleList = new ArrayList<>();
    // Resource IDを格納するarray
    private List<Integer> bookImgList = new ArrayList<>();
    private int reviewListCount = 0;
    private SharedPreferences mSharedPreferences;
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
        return new Intent(mainActivity, ListFragment.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 画面初期化処理
        final Resources res = getResources();
        final View inflate = inflater.inflate(R.layout.fragment_list, container, false);

        //特定のユーザーの本のすべてのデータを取得する
//        mDao = new KansouDao(getApplicationContext());
//        mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
//        String s = mSharedPreferences.getString("userName", "");
//        bookInfoList = mDao.selectBookInfo(s);

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
            //imageを格納する
            entity.setBookImage(bookInfoList.get(position).getBookImage());
            image = entity.getBookImage();
//            int imageId = getResources().getIdentifier(
//                    image, "drawable", getPackageName());
//            bookImgList.add(imageId);
            //titleを格納する
            entity.setBookTitle(bookInfoList.get(position).getBookTitle());
            title = entity.getBookTitle();
            bookTitleList.add(title);
            position++;
        }

        //ゲージViewをインスタンス化
        TextView gaugeMaxTextView = inflate.findViewById(R.id.max_gauge);
        TextView gaugeTextView = inflate.findViewById(R.id.count_gauge);

        //ゲージViewの大きさを設定
//        int width = (int) (PhoneInfo.getPhoneWidth(this) * this.RATE_OF_WIDTH_DISPLAY);
//        int height = (int) (PhoneInfo.getPhoneWidth(this) * this.RATE_OF_HEIGHT_DISPLAY);
        gaugeMaxTextView.setWidth(width);
        gaugeMaxTextView.setHeight(height);
        int bookAllCount = bookInfoList.size();
        currentReviewCountGauge(reviewListCount, bookAllCount, width, height, gaugeTextView);

        //本の画像一覧を表示するGridViewを生成
        GridView bookListGridView = inflate.findViewById(R.id.list_book);
//        BookListGridAdapter adapter = new BookListGridAdapter(this, R.layout.item_book_list, bookImgList, bookTitleList);
//        bookListGridView.setAdapter(adapter);

        Button bookRegistButton = inflate.findViewById(R.id.list_regist);
        bookRegistButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(RegistFragment.getStartIntent(ListFragment.this));
            }
        });
        //画像が押下されたときの処理
        bookListGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // clickされたpositionのtextとphotoのID
//                long viewId = id;
//                int selectedImage = bookImgList.get(position);
//                String selectedTitle = bookTitleList.get(position);
//                // インテントにセット
//                startActivity(RegistFragment.getStartIntent(ListFragment.this));
            }
        });

        return inflate;
    }

//        テストコード
//        int sa = bookInfoList.size();
//        Log.d("loglog", "size:" + sa);
//        for (BookInfoEntity entity : bookInfoList) {
//            entity.getBookTitle();
//            entity.getBookImage();
//            entity.getBookReview();
//            Log.d("loglog", "bookTitle:"+entity.getBookTitle());
//            Log.d("loglog", "bookImage"+entity.getBookImage());
//            Log.d("loglog", "bookReview"+entity.getBookReview());
//        }

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
