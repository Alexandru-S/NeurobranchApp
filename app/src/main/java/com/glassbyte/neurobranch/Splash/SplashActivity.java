package com.glassbyte.neurobranch.Splash;

import android.content.Intent;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.glassbyte.neurobranch.Authentication.AuthenticationActivity;
import com.glassbyte.neurobranch.R;
import com.glassbyte.neurobranch.Services.Globals;

/**
 * Created by ed on 10/06/16.
 */
public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        //hide navigation bar and status bar for immersive mode
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);

        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash_layout);

        Thread splashThread = new Thread() {
            public void run() {
                try {
                    int timer = 0;
                    while (timer < Globals.SPLASH_DURATION) {
                        sleep(100);
                        timer = timer + 100;
                    }
                    startActivity(new Intent(getApplicationContext(), AuthenticationActivity.class));
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    startActivity(new Intent(SplashActivity.this, AuthenticationActivity.class));
                    finish();
                }
            }
        };
        splashThread.start();
    }
}
