package com.dennismwebia.angar.activities;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;

import com.daimajia.androidanimations.library.Techniques;
import com.daimajia.androidanimations.library.YoYo;

import com.dennismwebia.angar.R;
import com.dennismwebia.angar.views.Btn;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initUI();
    }

    private void initUI() {
        Btn btnGetStarted = (Btn) findViewById(R.id.btnGetStarted);

        btnGetStarted.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnGetStarted:
                Intent intentBluetoothSetup = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intentBluetoothSetup);
                break;
            default:
                break;
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        LinearLayout layoutLogo = (LinearLayout) findViewById(R.id.layoutCustomLogo);
        YoYo.with(Techniques.BounceIn).duration(2800).repeat(0).playOn(layoutLogo);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent i = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(i);
            }
        }, 3000);
    }
}
