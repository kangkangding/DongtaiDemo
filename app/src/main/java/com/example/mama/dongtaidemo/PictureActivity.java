package com.example.mama.dongtaidemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

public class PictureActivity extends AppCompatActivity {

    private ImageView iv_picture;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_picture);
        initView();
        initData();
    }

    private void initData() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        String url = bundle.getString("url");


        SharedPreferenceUtil sputil = new SharedPreferenceUtil(this);
        int width = Integer.parseInt(sputil.getSharePre("screenwidth","0"));
        ViewGroup.LayoutParams params = iv_picture.getLayoutParams();
        params.width = Math.round(width);
        iv_picture.setLayoutParams(params);
        Glide.with(this).load(url).into(iv_picture);
    }

    private void initView() {
        iv_picture = (ImageView) findViewById(R.id.iv_picture);
        iv_picture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
