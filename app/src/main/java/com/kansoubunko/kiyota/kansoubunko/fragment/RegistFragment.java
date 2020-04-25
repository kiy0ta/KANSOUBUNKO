package com.kansoubunko.kiyota.kansoubunko.fragment;

import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.TextView;

import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.adapter.BookReviewGridAdapter;
import com.kansoubunko.kiyota.kansoubunko.constants.DataConstants;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static android.app.Activity.RESULT_OK;
import static android.view.View.GONE;

public class RegistFragment extends Fragment {

    public String input = "";
    public List<String> word = new ArrayList<>();
    private SharedPreferences mSharedPreferences;
    private Button bookReviewButton;
    private Button bookReviewUpdateButton;
    private String newTitle;
    private BookReviewGridAdapter adapter;
    private GridView bookReviewGridView;
    private TextView bookTitleTextView;
    private ImageView bookImageView;
    private Uri m_uri;
    private static final int REQUEST_CHOOSER = 1000;
    private static Boolean editBoolean = false;
    public static int GALLERY_CODE = 102;
    private Resources res;
    private String image_name;
    private File file;
    private Intent intentCamera;
    private String username;

    private static final Map<Integer, String> BOOK_REVIEW_MAP = new HashMap<>();

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 画面初期化処理
        View inflate = inflater.inflate(R.layout.fragment_regist, container, false);
        //ユーザー情報の取得
        Bundle bundle = getArguments();
        username = bundle.getString("userName");
        //本のデータをすべて取得する

        //一覧画面から遷移しているとき(本の画像を押下したとき＝編集モード)
        //true:編集モード
        if (editBoolean) {
//            mSharedPreferences = getSharedPreferences("bookInfo", MODE_PRIVATE);
            String bookId = mSharedPreferences.getString("bookId", "");
            String bookTitle = mSharedPreferences.getString("bookTitle", "");
            String bookImage = mSharedPreferences.getString("bookImage", "");
            String bookReview = mSharedPreferences.getString("bookReview", "");
        }
        //設定画面から遷移しているとき(閲覧モード)
        if (editBoolean) {
//            mSharedPreferences = getSharedPreferences("bookInfo", MODE_PRIVATE);
            String bookId = mSharedPreferences.getString("bookId", "");
            String bookTitle = mSharedPreferences.getString("bookTitle", "");
            String bookImage = mSharedPreferences.getString("bookImage", "");
            String bookReview = mSharedPreferences.getString("bookReview", "");
        }

        //100マスを生成
        bookReviewGridView = inflate.findViewById(R.id.regist_book_review);
        adapter = new BookReviewGridAdapter(getActivity(), R.layout.item_book_review, word);
        bookReviewGridView.setAdapter(adapter);

        //画像表示用ビュー
        bookImageView = inflate.findViewById(R.id.regist_book_img);
        bookImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGallery();
            }
        });

        //タイトル入力用ビュー
        bookTitleTextView = inflate.findViewById(R.id.regist_book_title);
        bookTitleTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ダイアログを表示する
                BookTitleDialogFragment dialog = new BookTitleDialogFragment();
                // 表示  getFragmentManager()は固定、sampleは識別タグ
                dialog.show(getFragmentManager(), "sample");
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
        bookReviewButton = inflate.findViewById(R.id.regist_book_review_button);
        bookReviewUpdateButton = inflate.findViewById(R.id.regist_book_review_update_button);
        bookReviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //ダイアログを表示する
                BookReviewDialogFragment dialog = new BookReviewDialogFragment();
                // 表示  getFragmentManager()は固定、sampleは識別タグ
                dialog.show(getFragmentManager(), "sample");
            }
        });
        return inflate;
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
        adapter = new BookReviewGridAdapter(getActivity(), R.layout.item_book_review, word);
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

                image_name = bookImageView.getResources().toString();
                //TODO:初期画面表示処理

            }
        });
    }

    //閲覧モード
    public void initView() {

    }

    //画像をカメラとギャラリーの両方から参照できる
    private void showGallery() {
        //カメラの起動Intentの用意
        String photoName = System.currentTimeMillis() + ".jpg";
        ContentValues contentValues = new ContentValues();
        contentValues.put(MediaStore.Images.Media.TITLE, photoName);
        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
        m_uri = getActivity().getContentResolver()
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
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
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
                    getActivity(),
                    new String[]{resultUri.getPath()},
                    new String[]{"image/jpeg"},
                    null
            );
            // 画像を設定
            bookImageView.setImageURI(resultUri);
            adapter = new BookReviewGridAdapter(getActivity(), R.layout.item_book_review, word);
            bookReviewGridView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
    }
}


