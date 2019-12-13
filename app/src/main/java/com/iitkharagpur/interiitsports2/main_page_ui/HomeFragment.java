package com.iitkharagpur.interiitsports2.main_page_ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iitkharagpur.interiitsports2.EnquiryActivity;
import com.iitkharagpur.interiitsports2.R;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnPausedListener;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import io.paperdb.Paper;

public class HomeFragment extends Fragment {
	
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		signInAnonymously();
		View view = inflater.inflate(R.layout.fragment_home, null);
		((Button)view.findViewById(R.id.facebook)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					Objects.requireNonNull(getContext()).getPackageManager().getPackageInfo("com.facebook.katana", 0);
					startActivity( new Intent(Intent.ACTION_VIEW, Uri.parse("fb://facewebmodal/f?href=https://www.facebook.com/interiit19")));
				} catch (Exception e) {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://www.facebook.com/interiit19")));
				}
			}
		});
		((Button)view.findViewById(R.id.web)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://interiit.com")));
				} catch (Exception ignored){}
			}
		});
		((Button)view.findViewById(R.id.youtube)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					startActivity(new Intent(Intent.ACTION_VIEW,   Uri.parse("http://www.youtube.com/channel/UCmlqubFEoVdj8Izqs5KO63g")));
				} catch (Exception ignored) {}
			}
		});
		((Button)view.findViewById(R.id.instagram)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					startActivity(new Intent(Intent.ACTION_VIEW,   Uri.parse("https://www.instagram.com/inter_iit/")));
				} catch (Exception ignored) {}
			}
		});
		((Button)view.findViewById(R.id.moments)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				IntentIntegrator intentIntegrator = IntentIntegrator.forSupportFragment(HomeFragment.this);
				intentIntegrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
				intentIntegrator.setBeepEnabled(true);
				intentIntegrator.setCameraId(0);
				intentIntegrator.setPrompt(" ");
				intentIntegrator.setBarcodeImageEnabled(false);
				intentIntegrator.initiateScan();
				//catchMoments();
			}
		});
		((Button)view.findViewById(R.id.enquiry)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				setEnquiryView();
				
			}
		});
		((Button)view.findViewById(R.id.team)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					startActivity(new Intent(Intent.ACTION_VIEW,   Uri.parse("https://interiit.com/team")));
				} catch (Exception ignored) {}
			}
		});
		((Button)view.findViewById(R.id.sponsors)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				try {
					startActivity(new Intent(Intent.ACTION_VIEW,   Uri.parse("https://interiit.com/sponsors")));
				} catch (Exception ignored) {}
			}
		});
		return view;
	}
	
	private void signInAnonymously() {
		FirebaseAuth mAuth = FirebaseAuth.getInstance();
		mAuth.signInAnonymously().addOnSuccessListener(Objects.requireNonNull(getActivity()), new OnSuccessListener<AuthResult>() {
			@Override
			public void onSuccess(AuthResult authResult) {
			
			}
		});
	}
	
	private void catchMoments(){
		final AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
		LayoutInflater li = LayoutInflater.from(getContext());
		final View promptsView = li.inflate(R.layout.prompt_moments, null, false);
		builder.setView(promptsView);
		builder.setTitle("Moments");
		final EditText name = promptsView.findViewById(R.id.getMomentName);
		final EditText moment = promptsView.findViewById(R.id.getCandimoment);
		builder.setPositiveButton("Add Moment Image", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Paper.book().write("momentName", String.valueOf(name.getText()));
				Paper.book().write("moment", String.valueOf(moment.getText()));
				Log.d("DETAILS", String.valueOf(name.getText()));
				dialog.cancel();
				CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(Objects.requireNonNull(getContext()), HomeFragment.this);
			}
		}).show();
	}
	
	@Override
	public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
			CropImage.ActivityResult cropimage = CropImage.getActivityResult(data);
			assert cropimage != null;
			Uri imageUri = cropimage.getUri();
			Log.d("IMAGE URI", imageUri.toString());
			final StorageReference mStorageRef = FirebaseStorage.getInstance().getReference();
			final String filename = RingtoneManager.getRingtone(getContext(), imageUri).getTitle(getContext());
			Toast.makeText(getContext(), "Upload in progress in background!", Toast.LENGTH_SHORT).show();
			mStorageRef.child(Paper.book().read("email")+"/"+filename).putFile(imageUri)
				.addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
					@Override
					public void onProgress(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
						double progress = (100.0 * taskSnapshot.getBytesTransferred()) / taskSnapshot.getTotalByteCount();
						Log.d("Upload","Upload is " + progress + "% done");
					}
				}).addOnPausedListener(new OnPausedListener<UploadTask.TaskSnapshot>() {
				@Override
				public void onPaused(@NonNull UploadTask.TaskSnapshot taskSnapshot) {
					Log.d("UPLOAD", "paused");
				}
			}).addOnFailureListener(new OnFailureListener() {
				@Override
				public void onFailure(@NonNull Exception exception) {
					Log.d("UPLOAD", "failed");
				}
			}).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
				@Override
				public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
					Log.d("UPLOAD", "success");
					mStorageRef.child(Paper.book().read("email")+"/"+filename).getDownloadUrl()
						.addOnSuccessListener(new OnSuccessListener<Uri>() {
							@Override
							public void onSuccess(Uri uri) {
								addMomentToFirestore(uri);
							}
						});
				}
			});
		}
		if(requestCode == IntentIntegrator.REQUEST_CODE && resultCode == Activity.RESULT_OK){
			IntentResult Result = IntentIntegrator.parseActivityResult(requestCode , resultCode ,data);
			if(Result != null){
				if(Result.getContents() == null){
					Log.d("QR SCAN" , "cancelled scan");
					Toast.makeText(getContext(), "Sorry we cannot make a scan now", Toast.LENGTH_SHORT).show();
				}
				else {
					String email = Result.getContents().substring(0, Result.getContents().indexOf('^'));
					Paper.init(Objects.requireNonNull(getContext()));
					Paper.book().write("email", email);
					Uri uri = new Uri.Builder()
						.scheme("https")
						.authority("interiit.com")
						.appendPath("profile_req_with_qr")
						.appendPath(Result.getContents())
						.build();
					RequestQueue queue = Volley.newRequestQueue(getContext());
					StringRequest stringRequest = new StringRequest(Request.Method.GET, uri.toString(),
						new Response.Listener<String>() {
							@Override
							public void onResponse(String response) {
								Log.d("RESPONSE", response);
								JsonObject jsonObject = ((JsonArray) JsonParser.parseString(response)).get(0).getAsJsonObject();
								Paper.book().write("name", jsonObject.get("name").getAsString());
								Paper.book().write("iit", jsonObject.get("iit").getAsString());
								Paper.book().write("sports", jsonObject.get("selected_sports").getAsString());
								Paper.book().write("phone", jsonObject.get("phone").getAsString());
							}
						}, new Response.ErrorListener() {
						@Override
						public void onErrorResponse(VolleyError error) {
							try {
								Log.d("DATA", Objects.requireNonNull(error.getMessage()));
							} catch (Exception ignored) {}
						}
					});
					queue.add(stringRequest);
					catchMoments();
				}
			}
		}
	}
	
	private void addMomentToFirestore(Uri uri){
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		Map<String, Object> moment = new HashMap<>();
		moment.put("name", Paper.book().read("name"));
		moment.put("email", Paper.book().read("email"));
		moment.put("iit", Paper.book().read("iit"));
		moment.put("moment", Paper.book().read("moment"));
		moment.put("momentName", Paper.book().read("momentName"));
		moment.put("sports", Paper.book().read("sports"));
		moment.put("phone", Paper.book().read("phone"));
		moment.put("Link", uri.toString());
		db.collection("Moments").document()
			.set(moment)
			.addOnSuccessListener(new OnSuccessListener<Void>() {
				@Override
				public void onSuccess(Void aVoid) {
					Log.d("Upload", "DocumentSnapshot successfully written!");
					
						notifyThis();
					
				}
			});
	}
	
	private void setEnquiryView(){
		Objects.requireNonNull(getContext()).startActivity(new Intent(getActivity(), EnquiryActivity.class));
	}
	
	private void notifyThis() {
		NotificationChannel mChannel = new NotificationChannel("MY_ID", "InterIITSports2019", NotificationManager.IMPORTANCE_HIGH);
		
		Notification notification =
			new NotificationCompat.Builder(Objects.requireNonNull(getContext()), "MY_ID")
				.setSmallIcon(R.mipmap.ic_launcher_foreground)
				.setContentTitle("Moments")
				.setContentText("Your moment is successfully uploaded.")
				.setChannelId("MY_ID").build();
		
		NotificationManager mNotificationManager =
			(NotificationManager) getContext().getSystemService(Context.NOTIFICATION_SERVICE);
		Objects.requireNonNull(mNotificationManager).createNotificationChannel(mChannel);
		mNotificationManager.notify(0 , notification);
	}
}