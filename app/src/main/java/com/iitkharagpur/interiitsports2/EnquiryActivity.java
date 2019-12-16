package com.iitkharagpur.interiitsports2;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iitkharagpur.interiitsports2.R;
import com.iitkharagpur.interiitsports2.adaptars.EnquiryViewAdapter;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.Arrays;
import java.util.Objects;

public class EnquiryActivity extends AppCompatActivity {
	
	private String complaintText;
	private String[] complaintTypes = {"Select Category ▼", "Accomodation", "Sports Event", "Transportation", "Mess", "Miscellaneous"};
	private String typeComplaint;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		
		setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
		Objects.requireNonNull(getSupportActionBar()).setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
		getSupportActionBar().setDisplayShowCustomEnabled(true);
		getSupportActionBar().setCustomView(R.layout.custom_action_bar);
		Toolbar toolbar=(Toolbar)getSupportActionBar().getCustomView().getParent();
		toolbar.setContentInsetsAbsolute(0,0);
		getWindow().getDecorView().setSystemUiVisibility( View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		getWindow().setStatusBarColor(Color.WHITE);
		
		setContentView(R.layout.activity_enquiry);
		
		RecyclerView recyclerView = findViewById(R.id.enquiry_list);
		recyclerView.setLayoutManager(new LinearLayoutManager(this));
		recyclerView.setAdapter(new EnquiryViewAdapter(this));
		
		final Spinner spinner = findViewById(R.id.complaintType);
		ArrayAdapter arrayAdapter = new ArrayAdapter<>(this,R.layout.prompt_comments_text, Arrays.asList(complaintTypes));
		spinner.setAdapter(arrayAdapter);
		((Button) findViewById(R.id.complaintButton)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				complaintText = ((TextView)EnquiryActivity.this.findViewById(R.id.complaintText)).getText().toString();
				typeComplaint = String.valueOf(spinner.getSelectedItem());
				if(spinner.getSelectedItemPosition()==0){
					Toast.makeText(EnquiryActivity.this, "Please Select a category", Toast.LENGTH_SHORT).show();
					return;
				}
				IntentIntegrator intentIntegrator = new IntentIntegrator(EnquiryActivity.this);
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
			}
			else {
				try {
					Uri uri = new Uri.Builder()
						.scheme("https")
						.authority("interiit.com")
						.appendPath("fileInquiry")
						.appendPath(Result.getContents())
						.appendPath(typeComplaint)
						.appendPath(complaintText)
						.build();
					RequestQueue queue = Volley.newRequestQueue(EnquiryActivity.this);
					StringRequest stringRequest = new StringRequest(Request.Method.GET, uri.toString(),
						new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {
								Toast.makeText(EnquiryActivity.this, "You complaint is successfully accepted", Toast.LENGTH_SHORT).show();
							}
						}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							try {
								Toast.makeText(EnquiryActivity.this, "Sorry, we cannot accept complaints now", Toast.LENGTH_SHORT).show();
								Log.d("ERROR", Objects.requireNonNull(error.getMessage()));
							} catch (Exception ignored) {
							}
						}
					});
					queue.add(stringRequest);
				} catch (Exception e){
					Toast.makeText(this, "Sorry, we cannot scan", Toast.LENGTH_SHORT).show();
				}
			}
		}
		else {
			super.onActivityResult(requestCode , resultCode , data);
		}
	}
}
