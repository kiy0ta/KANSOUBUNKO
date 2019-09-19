package com.kansoubunko.kiyota.kansoubunko.activity;


import android.content.ContentValues;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;


import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.dao.KansouDao;
import com.kansoubunko.kiyota.kansoubunko.dto.BookInfoEntity;
import com.kansoubunko.kiyota.kansoubunko.dto.UserInfoEntity;

import java.util.List;

public class SettingActivity extends AppCompatActivity {

    private SharedPreferences mSharedPreferences;
    public KansouDao mDao;
    private List<UserInfoEntity> kansou;
    private List<BookInfoEntity> kansou2;
    private Uri m_uri;
    private static final int REQUEST_CHOOSER = 1000;

    public static Intent getStartIntent(MainActivity mainActivity) {
        return new Intent(mainActivity, SettingActivity.class);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        mSharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        String userName = mSharedPreferences.getString("userName", "");
        //名前
        TextView userNameText = findViewById(R.id.setting_name);
        userNameText.setText(userName);
        //誕生日
        TextView birthdayText = findViewById(R.id.setting_birthday);
        birthdayText.setText("1994/12/19");
        //フォロー
        TextView followText = findViewById(R.id.setting_follow);
        followText.setText("100");
        //フォロワー
        TextView followersText = findViewById(R.id.setting_followers);
        followersText.setText("100");
        //プロフィール
        TextView profileText = findViewById(R.id.setting_profile);
        profileText.setText("村上春樹が好きです。");
        //ユーザーアイコン
        ImageView userImage = findViewById(R.id.setting_user_image);
        userImage.setImageResource(R.drawable.ic_sample_user_image);
        //ユーザーアイコン編集ボタン
        ImageView userImageEdit = findViewById(R.id.setting_user_image_button);
        userImageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGallery();
            }
        });

        //編集ボタン
        Button editButton = findViewById(R.id.setting_edit_button);
        editButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        //左矢印で戻る
        TextView backTextView = findViewById(R.id.back_arrow);
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    //画像をカメラとギャラリーの両方から参照できる
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
}
