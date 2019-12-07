package com.example.interiitsports2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.interiitsports2.adaptars.ScheduleTeamViewAdapter;

import java.util.Objects;

import io.paperdb.Paper;

public class PreviousTeamSchedule extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		String gameName = Paper.book().read("Game");
		int day = Paper.book().read("Day");
		
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.custom_action_bar);
		Toolbar toolbar=(Toolbar)getSupportActionBar().getCustomView().getParent();
		toolbar.setContentInsetsAbsolute(0,0);
		((TextView)getSupportActionBar().getCustomView().findViewById(R.id.heading)).setText(gameName);
		getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(Color.WHITE);
		
		setContentView(R.layout.activity_previous_team_schedule);
		
		RecyclerView recyclerView = findViewById(R.id.schedule_list_team);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(new ScheduleTeamViewAdapter(this, gameName, day));
	}
}
