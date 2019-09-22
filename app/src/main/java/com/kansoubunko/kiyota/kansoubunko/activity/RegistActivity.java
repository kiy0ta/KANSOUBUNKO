package com.kansoubunko.kiyota.kansoubunko.activity;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.util.TimeUtils;
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
import com.kansoubunko.kiyota.kansoubunko.util.ConfigPropUtil;
import com.kansoubunko.kiyota.kansoubunko.util.KansouTimeUtils;

import java.io.File;
import java.time.LocalDate;
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
    private static Boolean editBoolean = false;
    public static int GALLERY_CODE = 102;

    public static Intent getStartIntent(MainActivity mainActivity) {
        editBoolean = false;
        return new Intent(mainActivity, RegistActivity.class);
    }

    public static Intent getStartIntent(ListActivity listActivity) {
        editBoolean = true;
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
        //true:編集モード
        if (editBoolean) {
            mSharedPreferences = getSharedPreferences("bookInfo", MODE_PRIVATE);
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

        //新規登録の場合は、空文字を100文字文つめておく
        //false:新規登録
        int i = 0;
        if (!editBoolean) {
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
                LocalDate today = LocalDate.now();
                String strToday = String.valueOf(today);
                //TODO:日付のフォーマット処理が必要

                dao.registBookInfo("kiyota", newTitle, "no_book_img", bookReview, strToday);
                finish();
            }
        });
    }

    //画像をカメラとギャラリーの両方から参照できる
    private void showGallery() {
        //カメラの起動Intentの用意
//        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intentCamera.addCategory(Intent.CATEGORY_DEFAULT);
//        String image_name = KansouTimeUtils.getCurrentTimeInLong() + ".png";
//        File file = new File(getSaveFilePath(), image_name);
//        if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
//        //这里进行替换uri的获得方式
//        Uri imageUri = FileProvider.getUriForFile(this, "jp.co.wrb.wearablechallet.fileprovider", file);
//        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);
//        intentCamera.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);//这里加入flag
//        //ギャラリーの起動Intentの用意
//        Intent galleryIntent = new Intent(Intent.ACTION_PICK, null);
//        galleryIntent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
//        this.startActivityForResult(galleryIntent, GALLERY_CODE);


        String photoName = System.currentTimeMillis() + ".jpg";
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, photoName);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        String image_name = System.currentTimeMillis() + ".jpg";
        File file = new File(getSaveFilePath(), image_name);
//            m_uri = getContentResolver()
//                    .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
            m_uri = FileProvider.getUriForFile(this, "jp.co.wrb.wearablechallet.fileprovider", file);

        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, m_uri);

         //ギャラリー用のIntent作成
            Intent intentGallery;
            if (Build.VERSION.SDK_INT < 19) {
                intentGallery = new Intent(Intent.ACTION_GET_CONTENT);
                intentGallery.setType("image/*");
            } else {
                intentGallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
                intentGallery.addCategory(Intent.CATEGORY_OPENABLE);
                intentGallery.setType("image/jpeg");
            }
        Intent chooserIntent = Intent.createChooser(intentCamera, "画像の選択");
        chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intentGallery});
        startActivityForResult(chooserIntent, REQUEST_CHOOSER);
    }

    public static String getSaveFilePath() {
        String url = ConfigPropUtil.getInstance().get("photo_util_file_url");
        String path = getRootFilePath() + url;
        File file = new File(path);
        if (!file.exists()) {
            file.mkdir();
        }
        return path;
    }

    public static String getRootFilePath() {
        if (hasSDCard()) {
            return Environment.getExternalStorageDirectory().getAbsolutePath() + "/";// filePath:/sdcard/
        } else {
            return Environment.getDataDirectory().getAbsolutePath() + "/data/"; // filePath:/data/data/
        }
    }

    /**
     * 判断内置存储状态
     */
    public static boolean hasSDCard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
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
            Log.d("loglog", "imageNAME:" + bookImageView.getResources());
            adapter = new BookReviewGridAdapter(this, R.layout.item_book_review, word);
            bookReviewGridView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }

}