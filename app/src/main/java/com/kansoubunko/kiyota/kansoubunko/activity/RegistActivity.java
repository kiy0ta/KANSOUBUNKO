package com.kansoubunko.kiyota.kansoubunko.activity;


import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.kansoubunko.kiyota.kansoubunko.R;

public class RegistActivity extends AppCompatActivity {
    public static Intent getStartIntent(MainActivity mainActivity) {
        return new Intent(mainActivity, RegistActivity.class);
    }

    public static Intent getStartIntent(ListActivity listActivity) {
        return new Intent(listActivity, RegistActivity.class);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);

        TextView backTextView = findViewById(R.id.back_arrow);
        backTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
}
