package com.android.jamal.laporandanamasjid.login;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.android.jamal.laporandanamasjid.MainActivity;
import com.android.jamal.laporandanamasjid.R;

import java.util.logging.Handler;

public class SplashActivity extends AppCompatActivity {

    private static int timeout = 1500;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        new android.os.Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(SplashActivity.this, Login.class);
                startActivity(intent);
                finish();
            }
        },timeout);
    }
}