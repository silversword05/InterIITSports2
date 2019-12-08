package com.example.interiitsports2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.Objects;

public class SplashActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Objects.requireNonNull(getSupportActionBar()).hide();
		getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(Color.WHITE);
		
		setContentView(R.layout.activity_splash);
		
		ImageView imageView = findViewById(R.id.splash_screen);
		Animation animation = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.fade_in);
		animation.setAnimationListener(new Animation.AnimationListener() {
			@Override
			public void onAnimationStart(Animation animation) { }
			
			@Override
			public void onAnimationEnd(Animation animation) {
				startActivity(new Intent(SplashActivity.this, MainActivity.class));
			}
			
			@Override
			public void onAnimationRepeat(Animation animation) { }
		});
		imageView.startAnimation(animation);
	}
}
