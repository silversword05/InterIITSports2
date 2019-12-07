package com.example.interiitsports2;

import android.content.DialogInterface;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.widget.TextView;
import androidx.appcompat.widget.Toolbar;

import com.google.android.material.bottomnavigation.BottomNavigationView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.navigation.NavController;
import androidx.navigation.NavDestination;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Objects;

import io.paperdb.Paper;

public class SpecificSportsActivity extends AppCompatActivity {
	
	private String[] individualGames = {"Weightlifting", "Athletics"};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.custom_action_bar);
		Toolbar toolbar=(Toolbar)getSupportActionBar().getCustomView().getParent();
		toolbar.setContentInsetsAbsolute(0,0);
		((TextView)getSupportActionBar().getCustomView().findViewById(R.id.heading)).setText(getIntent().getStringExtra("Game"));
		
		Paper.init(this);
		Paper.book().write("Game", Objects.requireNonNull(getIntent().getStringExtra("Game")));
		
		setContentView(R.layout.activity_specific_sports);
		BottomNavigationView navView = findViewById(R.id.nav_view2);
		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
			R.id.navigation_live, R.id.navigation_schedule, R.id.navigation_points)
			.build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment2);
		
		if(Arrays.asList(individualGames).contains(SpecificSportsActivity.this.getIntent().getStringExtra("Game"))) {
			navController.popBackStack();
			navController.navigate(R.id.navigation_schedule);
		}
		navController.addOnDestinationChangedListener(new NavController.OnDestinationChangedListener() {
			@Override
			public void onDestinationChanged(@NonNull final NavController controller, @NonNull NavDestination destination, @Nullable Bundle arguments) {
				if(Arrays.asList(individualGames).contains(SpecificSportsActivity.this.getIntent().getStringExtra("Game")) ){
					if(destination.getId() == R.id.navigation_live) {
						new AlertDialog.Builder(SpecificSportsActivity.this)
							.setTitle("Invalid Tab")
							.setMessage("Live Tab is only available for team games")
							.setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
								public void onClick(DialogInterface dialog, int which) {
									controller.popBackStack();
									controller.navigate(R.id.navigation_schedule);
								}
							})
							.setIcon(R.drawable.alert)
							.show();
					}
				}
				
			}
		});
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(navView, navController);
	}
	
	@Override
	public void onBackPressed() {
		FragmentManager mFragmentManager = getSupportFragmentManager();
		if (mFragmentManager.getBackStackEntryCount() > 0)
			mFragmentManager.popBackStackImmediate();
		else super.onBackPressed();
	}
	
}
