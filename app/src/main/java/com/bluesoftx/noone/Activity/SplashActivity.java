package com.bluesoftx.noone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.viewpager2.widget.ViewPager2;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.bluesoftx.noone.Adapter.MainViewPagerAdapter;
import com.bluesoftx.noone.R;
import com.bluesoftx.noone.Utilities.FirebaseParameters;
import com.bluesoftx.noone.Utilities.Parameters;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class SplashActivity extends AppCompatActivity {

    Context context = this;
    public static MainViewPagerAdapter splashViewPagerAdapter;
    public static ViewPager2 splashViewpager2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        /**
         * setSystemUiVisibility Hide navigation
         * 全螢幕畫面
         */
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
            getWindow().setStatusBarColor(ContextCompat.getColor(this, R.color.yellow));
            getWindow().setNavigationBarColor(ContextCompat.getColor(this, R.color.yellow));
        }

        splashViewpager2 = findViewById(R.id.splash_viewpager2);
        splashViewPagerAdapter = new MainViewPagerAdapter(this);
        splashViewPagerAdapter.showSplashImage();
        splashViewpager2.setAdapter(splashViewPagerAdapter);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (FirebaseParameters.mAuth.getCurrentUser() == null) {
                    splashViewPagerAdapter.removeFragment();
                    splashViewPagerAdapter.showSignUp();
                    splashViewpager2.setAdapter(splashViewPagerAdapter);
                } else {
                    Intent intent1 = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent1);
                    finish();
                }
            }
        }, 2500);




    }

    /**
     * Checking whether user granted the permission or not.
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == Parameters.MY_PERMISSIONS_REQUEST_PICTURES) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                Intent intent1 = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent1);
                finish();
            }else{
                Toast.makeText(this, Parameters.PERMISSIONS_RESULT_NOT_MESSAGE, Toast.LENGTH_SHORT).show();
            }
        }
    }
}