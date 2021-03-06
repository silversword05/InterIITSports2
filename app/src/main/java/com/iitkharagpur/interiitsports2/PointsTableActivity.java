package com.iitkharagpur.interiitsports2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.iitkharagpur.interiitsports2.R;
import com.iitkharagpur.interiitsports2.adaptars.PointsDataAdapter;

import java.util.Objects;

public class PointsTableActivity extends AppCompatActivity {
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.custom_action_bar);
		Toolbar toolbar=(Toolbar)getSupportActionBar().getCustomView().getParent();
		toolbar.setContentInsetsAbsolute(0,0);
		((TextView)getSupportActionBar().getCustomView().findViewById(R.id.heading)).setText(getIntent().getStringExtra("GAME"));
		getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(Color.WHITE);
		
		setContentView(R.layout.activity_points_table);
		
		String sheet = getIntent().getStringExtra("SHEET");
		String id = getIntent().getStringExtra("ID");
		int type = getIntent().getIntExtra("TYPE", 0);
		
		Log.d("DATA POINTS", sheet+" "+id+" "+type);
		
		ProgressBar progressBar = findViewById(R.id.progressBar);
		progressBar.setVisibility(View.VISIBLE);
		
		RecyclerView recyclerView = findViewById(R.id.points_data_list);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.addItemDecoration(new DividerItemDecoration(recyclerView.getContext(), DividerItemDecoration.VERTICAL));
		recyclerView.setAdapter(new PointsDataAdapter(sheet, id, this, type, progressBar));
	}
}
