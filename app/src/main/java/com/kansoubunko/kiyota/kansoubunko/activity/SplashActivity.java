package com.kansoubunko.kiyota.kansoubunko.activity;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.widget.ImageView;

import com.kansoubunko.kiyota.kansoubunko.R;
import com.kansoubunko.kiyota.kansoubunko.constants.AppConstants;

public class SplashActivity extends AppCompatActivity {

    private Handler mHandler;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
        View decorView = getWindow().getDecorView();
        decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
        setContentView(R.layout.activity_splash);
        ImageView imageView = findViewById(R.id.splash_img);
        AlphaAnimation alpha = new AlphaAnimation(0.1f, 1); // 透明度を0.1から1に変化させる
        alpha.setDuration(3000); // 3000msかけてアニメーションする
        imageView.startAnimation(alpha); // アニメーション適用
    }

    @Override
    protected void onResume() {
        super.onResume();
        mHandler = new Handler();
        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {

                startActivity(LoginActivity.getStartIntent(SplashActivity.this));
                finish();
            }
        }, AppConstants.SPLASH_ANIMATION_INTERVAL);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if (mHandler != null) {
            mHandler = null;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public void onBackPressed() {
        // バックボタンeventは握りつぶす
    }
}
