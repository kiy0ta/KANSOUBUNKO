package com.kansoubunko.kiyota.kansoubunko.activity;


import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.adapter.BookReviewGridAdapter;
import com.kansoubunko.kiyota.kansoubunko.dao.KansouDao;
import com.kansoubunko.kiyota.kansoubunko.fragment.BookReviewDialogFragment;
import com.kansoubunko.kiyota.kansoubunko.fragment.BookTitleDialogFragment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.view.View.GONE;

public class RegistActivity extends AppCompatActivity {

    public String input = "";
    public List<String> word = new ArrayList<>();
    private SharedPreferences mSharedPreferences;
    private Button bookReviewButton;
    private Button bookReviewUpdateButton;
    private Resources res;
    private KansouDao dao;
    private String newTitle;
    private BookReviewGridAdapter adapter;
    private GridView bookReviewGridView;
    private TextView bookTitleTextView;
    private ImageView bookImageView;
    private Uri m_uri;
    private static final int REQUEST_CHOOSER = 1000;

    public static Intent getStartIntent(MainActivity mainActivity) {
        return new Intent(mainActivity, RegistActivity.class);
    }

    public static Intent getStartIntent(ListActivity listActivity) {
        return new Intent(listActivity, RegistActivity.class);
    }

    private static final Map<Integer, String> BOOK_REVIEW_MAP = new HashMap<>();

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_regist);
        res = getResources();
        dao = new KansouDao(this);

        //List画面から遷移しているとき(本の画像を押下したとき＝編集モード)
        mSharedPreferences = getSharedPreferences("bookInfo", MODE_PRIVATE);
        Boolean bln = false;
        //一時的に退避
        if (bln) {
            String bookId = mSharedPreferences.getString("bookId", "");
            String bookTitle = mSharedPreferences.getString("bookTitle", "");
            String bookImage = mSharedPreferences.getString("bookImage", "");
            String bookReview = mSharedPreferences.getString("bookReview", "");
        }

        //100マスを生成
        bookReviewGridView = findViewById(R.id.regist_book_review);
        adapter = new BookReviewGridAdapter(this, R.layout.item_book_review, word);
        bookReviewGridView.setAdapter(adapter);

        //画像表示用ビュー
        bookImageView = findViewById(R.id.regist_book_img);
        bookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGallery();
            }
        });


        //タイトル入力用ビュー
        bookTitleTextView = findViewById(R.id.regist_book_title);
        bookTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ダイアログを表示する
                BookTitleDialogFragment dialog = new BookTitleDialogFragment();
                // 表示  getFragmentManager()は固定、sampleは識別タグ
                dialog.show(getSupportFragmentManager(), "sample");
            }
        });

        //空文字を100文字文つめておく
        int i = 0;
        if (!bln) {
            for (i = 0; i < 100; i++) {
                word.add("");
            }
        }

        //登録ボタン押下でダイアログを表示する処理
        bookReviewButton = findViewById(R.id.regist_book_review_button);
        bookReviewUpdateButton = findViewById(R.id.regist_book_review_update_button);
        bookReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ダイアログを表示する
                BookReviewDialogFragment dialog = new BookReviewDialogFragment();
                // 表示  getFragmentManager()は固定、sampleは識別タグ
                dialog.show(getSupportFragmentManager(), "sample");
            }
        });

        //左矢印押下で前の画面に戻る処理
        TextView backTextView = findViewById(R.id.back_arrow);
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    // ダイアログで入力した値をtextViewに入れる - ダイアログから呼び出される
    public void setReviewTextView(String value) {
        input = value;
        word = Arrays.asList(input.split(""));
    }

    // ダイアログで入力した値をtextViewに入れる - ダイアログから呼び出される
    public void setTitleTextView(String value) {
        input = value;
        bookTitleTextView.setText(input);
    }

    //ボタンのテキストを「記入」から「登録」に変更する
    public void changeButtonText(final String bookReview) {
        res = getResources();
        bookReviewButton.setVisibility(GONE);
        bookReviewUpdateButton.setVisibility(View.VISIBLE);
        setReviewTextView(bookReview);
        adapter = new BookReviewGridAdapter(this, R.layout.item_book_review, word);
        bookReviewGridView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        bookReviewUpdateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //登録処理
                if (bookTitleTextView.getText().length() != 0 || bookTitleTextView.getText() != null) {
                    newTitle = (String) bookTitleTextView.getText();
                }
                dao.registBookInfo(newTitle, "no_book_img", bookReview);
                finish();
            }
        });

    }

    private void showGallery() {

        //カメラの起動Intentの用意
        String photoName = System.currentTimeMillis() + ".jpg";
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, photoName);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        m_uri = getContentResolver()
                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);

        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, m_uri);

        // ギャラリー用のIntent作成
        Intent intentGallery;
        if (Build.VERSION.SDK_INT < 19) {
            intentGallery = new Intent(Intent.ACTION_GET_CONTENT);
            intentGallery.setType("image/*");
        } else {
            intentGallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
            intentGallery.addCategory(Intent.CATEGORY_OPENABLE);
            intentGallery.setType("image/jpeg");
        }
        Intent intent = Intent.createChooser(intentCamera, "画像の選択");
        intent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intentGallery});
        startActivityForResult(intent, REQUEST_CHOOSER);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == REQUEST_CHOOSER) {

            if (resultCode != RESULT_OK) {
                // キャンセル時
                return;
            }

            Uri resultUri = (data != null ? data.getData() : m_uri);

            if (resultUri == null) {
                // 取得失敗
                return;
            }

            // ギャラリーへスキャンを促す
            MediaScannerConnection.scanFile(
                    this,
                    new String[]{resultUri.getPath()},
                    new String[]{"image/jpeg"},
                    null
            );

            // 画像を設定
            bookImageView.setImageURI(resultUri);
            adapter = new BookReviewGridAdapter(this, R.layout.item_book_review, word);
            bookReviewGridView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

}


//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        Resources res = getResources();
//
//        setContentView(R.layout.activity_regist);
//
////        Intent intent = getIntent();
////        int selectedText = intent.getIntExtra("Image", 0);
////        String selectedPhoto = intent.getStringExtra("Title");
//
//
//        EditText text = findViewById(R.id.text);
//
//        TextView bookTitle = findViewById(R.id.regist_book_title);
//        bookTitle.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
//
//        input = "こんにちは。今日の天気は曇りです。最高気温は24度です。";
//
////        if (text.length() == 0 || text == null) {
////            String input = "";
////        }
//
//        word = Arrays.asList(input.split(""));
////        if(word.size() <= 100){
////            int count = 100 - word.size();
////            for(int i = 0; i < count; i++){
////                word.add("あ");
////            }
////        }
//
//        //左矢印押下で前の画面に戻る処理
//        GridView bookReviewGridView = findViewById(R.id.regist_book_review);
//        BookReviewGridAdapter adapter = new BookReviewGridAdapter(this, R.layout.item_book_review, word);
//        bookReviewGridView.setAdapter(adapter);
//
//        //登録ボタン押下でダイアログを表示する処理
//        Button bookReviewButton = findViewById(R.id.regist_book_review_button);
//        bookReviewButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                //ダイアログを表示する
//                BookReviewDialogFragment dialog = new BookReviewDialogFragment();
//                // 表示  getFragmentManager()は固定、sampleは識別タグ
//                dialog.show(getSupportFragmentManager(), "sample");
//            }
//        });
//
//        TextView backTextView = findViewById(R.id.back_arrow);
//        backTextView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                finish();
//            }
//        });
//
//    }
//
//    // ダイアログで入力した値をtextViewに入れる - ダイアログから呼び出される
//    public void setReviewTextView(String value) {
//        input = value;
//        word = Arrays.asList(input.split(""));
