package com.iitkharagpur.interiitsports2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Objects;

import io.paperdb.Paper;

public class QrScanActivity extends AppCompatActivity {
	
	private Button button;
	
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
		
		setContentView(R.layout.activity_qr_scan);
		
		button = findViewById(R.id.scanQRMess);
		button.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				button.setEnabled(false);
				button.setText(R.string.please_wait);
				IntentIntegrator intentIntegrator = new IntentIntegrator(QrScanActivity.this);
				intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.QR_CODE_TYPES);
				intentIntegrator.setBeepEnabled(true);
				intentIntegrator.setCameraId(MainActivity.cameraId);
				intentIntegrator.setPrompt(" ");
				intentIntegrator.setBarcodeImageEnabled(false);
				intentIntegrator.initiateScan();
			}
		});
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		IntentResult Result = IntentIntegrator.parseActivityResult(requestCode , resultCode ,data);
		if(Result != null){
			if(Result.getContents() == null){
				Toast.makeText(this, "Sorry, we cannot scan", Toast.LENGTH_SHORT).show();
				button.setEnabled(true);
				button.setText(R.string.scan_qr_for_mess);
			}
			else {
				try {
					String id = Paper.book().read("MessId");
					String password = Paper.book().read("MessPassword");
					Uri uri = new Uri.Builder()
						.scheme("https")
						.authority("interiit.com")
						.appendPath("addQR")
						.appendPath(Result.getContents())
						.appendPath(id)
						.appendPath(password)
						.build();
					Log.d("MESS", uri.toString());
					RequestQueue queue = Volley.newRequestQueue(QrScanActivity.this);
					StringRequest stringRequest = new StringRequest(Request.Method.GET, uri.toString(),
						new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {
								response = response.substring(1, response.length()-1);
								if(response.equals("Success"))
									Toast.makeText(QrScanActivity.this, "QR is successfully scanned", Toast.LENGTH_SHORT).show();
								else Toast.makeText(QrScanActivity.this, "Error : "+response, Toast.LENGTH_SHORT).show();
								button.setEnabled(true);
								button.setText(R.string.scan_qr_for_mess);
							}
						}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							try {
								Toast.makeText(QrScanActivity.this, "Sorry, we cannot scan QRs now", Toast.LENGTH_SHORT).show();
								Log.d("ERROR", Objects.requireNonNull(error.getMessage()));
								button.setEnabled(true);
								button.setText(R.string.scan_qr_for_mess);
							} catch (Exception ignored) {
							}
						}
					});
					queue.add(stringRequest);
				} catch (Exception e){
					Toast.makeText(this, "Sorry, we cannot scan", Toast.LENGTH_SHORT).show();
					button.setEnabled(true);
					button.setText(R.string.scan_qr_for_mess);
				}
			}
		}
		else {
			super.onActivityResult(requestCode , resultCode , data);
		}
	}
}
