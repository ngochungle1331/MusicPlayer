package com.fox.training.intentAndViewPager.intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.fox.training.R;

public class ThirdActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.third_activity_action_bar_title);

        Intent intent = getIntent();
        String name = intent.getStringExtra(getString(R.string.second_activity_put_extra_name_key));
        String email = intent.getStringExtra(getString(R.string.second_activity_put_extra_email_key));
        String phone = intent.getStringExtra(getString(R.string.second_activity_put_extra_phone_key));

        TextView mResultTv = findViewById(R.id.resultTv);
        mResultTv.setText(name + "    " + email + "    " + phone);
    }
}