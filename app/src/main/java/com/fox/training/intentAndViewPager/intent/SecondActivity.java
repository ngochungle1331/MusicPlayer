package com.fox.training.intentAndViewPager.intent;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import com.fox.training.R;

public class SecondActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        ActionBar actionBar = getSupportActionBar();
        assert actionBar != null;
        actionBar.setTitle(R.string.second_activity_action_bar_title);

        EditText mNameEt = findViewById(R.id.nameEt);
        EditText mEmailEt = findViewById(R.id.emailEt);
        EditText mPhoneEt = findViewById(R.id.phoneEt);

        Button mSaveBtn = findViewById(R.id.saveBtn);

        mSaveBtn.setOnClickListener(v -> {
            String name = mNameEt.getText().toString();
            String email = mEmailEt.getText().toString();
            String phone = mPhoneEt.getText().toString();

            Intent intent = new Intent(SecondActivity.this, ThirdActivity.class);
            intent.putExtra(getString(R.string.second_activity_put_extra_name_key), name);
            intent.putExtra(getString(R.string.second_activity_put_extra_email_key), email);
            intent.putExtra(getString(R.string.second_activity_put_extra_phone_key), phone);
            startActivity(intent);
        });


    }
}