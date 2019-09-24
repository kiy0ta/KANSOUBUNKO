package com.kansoubunko.kiyota.kansoubunko.fragment;


import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.dao.KansouDao;
import com.kansoubunko.kiyota.kansoubunko.dto.BookInfoEntity;
import com.kansoubunko.kiyota.kansoubunko.dto.UserInfoEntity;

import java.util.List;

import static android.view.View.GONE;

public class SettingFragment extends Fragment {


    public KansouDao mDao;
    private List<UserInfoEntity> userInfoList;
    private List<BookInfoEntity> kansou2;
    private Uri m_uri;
    private static final int REQUEST_CHOOSER = 1000;
    private String username;
    private String userName;
    private String userBirthday;
    private String follow;
    private String followers;
    private String userImage;
    private String profile;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // 画面初期化処理
        final View inflate = inflater.inflate(R.layout.fragment_setting, container, false);

        //ユーザー情報の取得
        Bundle bundle = getArguments();
        username = bundle.getString("userName");
        mDao = new KansouDao(getActivity());
        userInfoList = mDao.findUserInfo(username);
        for (UserInfoEntity entity : userInfoList) {
            userName = entity.getUserName();
            userBirthday = entity.getUserBirthday();
            follow = entity.getFollow();
            followers = entity.getFollowers();
            userImage = entity.getUserImage();
            profile = entity.getProfile();
        }
        //名前
        TextView userNameText = inflate.findViewById(R.id.setting_name);
        userNameText.setText(userName);
        //誕生日
        TextView birthdayText = inflate.findViewById(R.id.setting_birthday);
        birthdayText.setText(userBirthday);
        //フォロー
        TextView followText = inflate.findViewById(R.id.setting_follow);
        followText.setText(follow);
        //フォロワー
        TextView followersText = inflate.findViewById(R.id.setting_followers);
        followersText.setText(followers);
        //プロフィール
        TextView profileText = inflate.findViewById(R.id.setting_profile);
        profileText.setText(profile);
        //ユーザーアイコン
        ImageView userImage = inflate.findViewById(R.id.setting_user_image);
        userImage.setImageResource(R.drawable.ic_sample_user_image);
        //他人のプロフィールだったら、編集マークをGONEにする
        Button editButton = inflate.findViewById(R.id.setting_edit_button);
        TextView editText = inflate.findViewById(R.id.setting_user_image_gray_cover);
        ImageView editImage = inflate.findViewById(R.id.setting_user_image_button);
        if (false) {
            editButton.setVisibility(GONE);
            editText.setVisibility(GONE);
            editImage.setVisibility(GONE);
        }
        //ユーザーアイコン編集ボタン
        ImageView userImageEdit = inflate.findViewById(R.id.setting_user_image_button);
        userImageEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGallery();
            }
        });
        return inflate;
    }


    //画像をカメラとギャラリーの両方から参照できる
    private void showGallery() {
//        //カメラの起動Intentの用意
//        String photoName = System.currentTimeMillis() + ".jpg";
//        ContentValues contentValues = new ContentValues();
//        contentValues.put(MediaStore.Images.Media.TITLE, photoName);
//        contentValues.put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg");
//        m_uri = getContentResolver()
//                .insert(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, contentValues);
//
//        Intent intentCamera = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        intentCamera.putExtra(MediaStore.EXTRA_OUTPUT, m_uri);
//
//        // ギャラリー用のIntent作成
//        Intent intentGallery;
//        if (Build.VERSION.SDK_INT < 19) {
//            intentGallery = new Intent(Intent.ACTION_GET_CONTENT);
//            intentGallery.setType("image/*");
//        } else {
//            intentGallery = new Intent(Intent.ACTION_OPEN_DOCUMENT);
//            intentGallery.addCategory(Intent.CATEGORY_OPENABLE);
//            intentGallery.setType("image/jpeg");
//        }
//        Intent intent = Intent.createChooser(intentCamera, "画像の選択");
//        intent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Intent[]{intentGallery});
//        startActivityForResult(intent, REQUEST_CHOOSER);
    }
}
