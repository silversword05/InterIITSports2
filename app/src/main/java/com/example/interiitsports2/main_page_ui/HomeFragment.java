package com.example.interiitsports2.main_page_ui;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.example.interiitsports2.R;
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
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.IOException;
import java.util.ArrayList;
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
				startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://interiit.com")));
				
			}
		});
		((Button)view.findViewById(R.id.gmail)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent=new Intent(Intent.ACTION_SEND);
				String[] recipients={"interiit2019@gmail.com"};
				intent.putExtra(Intent.EXTRA_EMAIL, recipients);
				intent.putExtra(Intent.EXTRA_SUBJECT,"Mail from the app:");
				intent.setType("text/html");
				intent.setPackage("com.google.android.gm");
				startActivity(Intent.createChooser(intent, "Send mail"));
			}
		});
		((Button)view.findViewById(R.id.moments)).setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				catchMoments();
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
		final EditText name = promptsView.findViewById(R.id.getCandiName);
		final EditText email = promptsView.findViewById(R.id.getCandiEmail);
		final EditText moment = promptsView.findViewById(R.id.getCandimoment);
		final Spinner spinner = promptsView.findViewById(R.id.getCandiIIT);
		ArrayList<String> arrayList = new ArrayList<>();
		try {
			for(String f : Objects.requireNonNull(Objects.requireNonNull(getContext()).getAssets().list("")))
				if(f.startsWith("IIT")) arrayList.add(f.substring(0, f.lastIndexOf(".")).replace("_"," "));
		} catch (IOException e) {
			e.printStackTrace();
		}
		ArrayAdapter arrayAdapter = new ArrayAdapter<>(getContext(),android.R.layout.simple_spinner_item, arrayList);
		arrayAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
		spinner.setAdapter(arrayAdapter);
		builder.setPositiveButton("Add your Moment", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Paper.init(Objects.requireNonNull(getContext()));
				Paper.book().write("name", String.valueOf(name.getText()));
				Paper.book().write("email", String.valueOf(email.getText()));
				Paper.book().write("moment", String.valueOf(moment.getText()));
				Paper.book().write("iit", String.valueOf(spinner.getSelectedItem()));
				Log.d("DETAILS", String.valueOf(name.getText()));
				dialog.cancel();
				CropImage.activity().setGuidelines(CropImageView.Guidelines.ON).start(getContext(), HomeFragment.this);
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
	}
	
	private void addMomentToFirestore(Uri uri){
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		Map<String, Object> moment = new HashMap<>();
		moment.put("name", Paper.book().read("name"));
		moment.put("email", Paper.book().read("email"));
		moment.put("iit", Paper.book().read("iit"));
		moment.put("moment", Paper.book().read("moment"));
		moment.put("Link", uri.toString());
		db.collection("Moments").document()
			.set(moment)
			.addOnSuccessListener(new OnSuccessListener<Void>() {
				@Override
				public void onSuccess(Void aVoid) {
					Log.d("Upload", "DocumentSnapshot successfully written!");
					AlertDialog alertDialog = new AlertDialog.Builder(HomeFragment.this.getContext()).create();
					alertDialog.setTitle("Moments Added");
					alertDialog.setMessage("Your moment is successfully added");
					alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
						new DialogInterface.OnClickListener() {
							public void onClick(DialogInterface dialog, int which) {
								dialog.dismiss();
							}
						});
					alertDialog.show();
				}
			});
	}
}