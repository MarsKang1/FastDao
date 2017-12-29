package com.fastdao;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.models.Column;

public class MainActivity extends AppCompatActivity {
    @Column(name = "TEXT_VIEW")
    TextView mTvTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
//        ViewBinding.bind(this);
        UserInfo userInfo = new UserInfo();
    }
}
