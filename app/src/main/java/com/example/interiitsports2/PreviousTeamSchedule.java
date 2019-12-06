package com.example.interiitsports2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
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
		((TextView)getSupportActionBar().getCustomView().findViewById(R.id.heading)).setText(gameName);
		
		setContentView(R.layout.activity_previous_team_schedule);
		
		RecyclerView recyclerView = findViewById(R.id.schedule_list_team);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(new ScheduleTeamViewAdapter(this, gameName, day));
	}
}