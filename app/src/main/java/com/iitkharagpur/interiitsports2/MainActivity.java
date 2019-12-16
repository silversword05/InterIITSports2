package com.iitkharagpur.interiitsports2;

import android.Manifest;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.hardware.Camera;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.appindexing.Action;
import com.google.firebase.appindexing.FirebaseAppIndex;
import com.google.firebase.appindexing.FirebaseUserActions;
import com.google.firebase.appindexing.Indexable;
import com.google.firebase.appindexing.builders.Actions;
import com.iitkharagpur.interiitsports2.main_page_ui.HomeFragment;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.Objects;

import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {
	public static int cameraId = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.custom_action_bar);
		Toolbar toolbar = (Toolbar) getSupportActionBar().getCustomView().getParent();
		toolbar.setContentInsetsAbsolute(0, 0);
		getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(Color.WHITE);
		
		setContentView(R.layout.activity_main);
		BottomNavigationView navView = findViewById(R.id.nav_view);
		fetchpermission();
		
		getSupportActionBar().getCustomView().findViewById(R.id.kgp_logo).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				createLoginDialog();
			}
		});
		
		getCameraId();
		// Passing each menu ID as a set of Ids because each
		// menu should be considered as top level destinations.
		AppBarConfiguration appBarConfiguration = new AppBarConfiguration.Builder(
			R.id.navigation_home, R.id.navigation_sports, R.id.navigation_newsletter)
			.build();
		NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment);
		NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
		NavigationUI.setupWithNavController(navView, navController);
		// ATTENTION: This was auto-generated to handle app links.
		Intent appLinkIntent = getIntent();
		String appLinkAction = appLinkIntent.getAction();
		Uri appLinkData = appLinkIntent.getData();
	}
	
	private void createLoginDialog(){
		final AlertDialog.Builder builder = new AlertDialog.Builder(this);
		LayoutInflater li = LayoutInflater.from(this);
		final View promptsView = li.inflate(R.layout.prompt_login, null, false);
		builder.setView(promptsView);
		builder.setTitle("Login for KGP CTMs only");
		final EditText username = promptsView.findViewById(R.id.getloginUsername);
		final EditText password = promptsView.findViewById(R.id.getloginPassword);
		Paper.init(this);
		builder.setPositiveButton("Login", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Toast.makeText(MainActivity.this, "Please wait until we log you in !!!", Toast.LENGTH_SHORT).show();
				try{
					Uri uri = new Uri.Builder()
						.scheme("https")
						.authority("interiit.com")
						.appendPath("verifyPass")
						.appendPath(String.valueOf(username.getText()))
						.appendPath(String.valueOf(password.getText()))
						.build();
					RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
					StringRequest stringRequest = new StringRequest(Request.Method.GET, uri.toString(),
						new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {
								Log.d("MESS", response);
								response = response.substring(1, response.length()-1);
								if(response.equals("-1"))
									Toast.makeText(MainActivity.this, "Invalid credentials", Toast.LENGTH_SHORT).show();
								else {
									Log.d("MESS", response);
									Paper.book().write("MessId", response);
									Paper.book().write("MessPassword", String.valueOf(password.getText()));
									startActivity(new Intent(MainActivity.this, QrScanActivity.class));
								}
							}
						}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							try {
								Toast.makeText(MainActivity.this, "Sorry, login not available now", Toast.LENGTH_SHORT).show();
								Log.d("ERROR", Objects.requireNonNull(error.getMessage()));
							} catch (Exception ignored) {
							}
						}
					});
					queue.add(stringRequest);
				} catch (Exception e){
					Toast.makeText(MainActivity.this, "Sorry, login not available now", Toast.LENGTH_SHORT).show();
					Log.e("MESS", Objects.requireNonNull(e.getLocalizedMessage()));
				}
				Log.d("DETAILS", String.valueOf(username.getText()));
				dialog.cancel();
			}
		}).show();
	}
	
	private void fetchpermission() {
		Log.d("ACTIVITY", Objects.requireNonNull(MainActivity.this).toString());
		if (ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
			ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED ||
			ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED ||
			ContextCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
			Log.d("AADI", "fd");
			ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.CAMERA,
				Manifest.permission.CALL_PHONE}, 1);
		} else {
			Log.d("PERMISSION", "granted");
		}
	}
	
	@Override
	public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
		super.onRequestPermissionsResult(requestCode, permissions, grantResults);
		if (requestCode == 1) {
			if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
				Log.d("PERMISSION", "granted");
			} else finish();
		}
	}
	
	private void getCameraId() {
		Camera.CameraInfo cameraInfo = new Camera.CameraInfo();
		for (int camIdx = 0; camIdx < Camera.getNumberOfCameras(); camIdx++) {
			Camera.getCameraInfo(camIdx, cameraInfo);
			if (cameraInfo.facing == Camera.CameraInfo.CAMERA_FACING_BACK) {
				cameraId = camIdx;
				break;
			}
		}
	}
	
	/**
	 * ATTENTION: This was auto-generated to implement the App Indexing API.
	 * See https://g.co/AppIndexing/AndroidStudio for more information.
	 */
	public Action getIndexApiAction() {
		return Actions.newView("Main", "https://interiit.com");
	}
	
	@Override
	public void onStart() {
		super.onStart();
		
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		FirebaseAppIndex.getInstance().update(new Indexable.Builder().setName("Main").setUrl("https://interiit.com").build());
		FirebaseUserActions.getInstance().start(getIndexApiAction());
	}
	
	@Override
	public void onStop() {
		
		// ATTENTION: This was auto-generated to implement the App Indexing API.
		// See https://g.co/AppIndexing/AndroidStudio for more information.
		FirebaseUserActions.getInstance().end(getIndexApiAction());
		super.onStop();
	}
}
