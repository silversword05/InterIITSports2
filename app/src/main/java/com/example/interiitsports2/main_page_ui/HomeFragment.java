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
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interiitsports2.EnquiryActivity;
import com.example.interiitsports2.R;
import com.example.interiitsports2.adaptars.EnquiryViewAdapter;
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
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
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
				intentIntegrator.setBeepEnabled(false);
				intentIntegrator.setCameraId(0);
				intentIntegrator.setPrompt("SCAN QR");
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
		final EditText name = promptsView.findViewById(R.id.getCandiName);
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
				Paper.book().write("name", String.valueOf(name.getText()));
				Paper.book().write("moment", String.valueOf(moment.getText()));
				Paper.book().write("iit", String.valueOf(spinner.getSelectedItem()));
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
			Toast.makeText(getContext(), "Please wait until we process your request", Toast.LENGTH_SHORT).show();
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
		moment.put("Link", uri.toString());
		db.collection("Moments").document()
			.set(moment)
			.addOnSuccessListener(new OnSuccessListener<Void>() {
				@Override
				public void onSuccess(Void aVoid) {
					Log.d("Upload", "DocumentSnapshot successfully written!");
					Toast.makeText(getContext(), "Your moment is successfully added", Toast.LENGTH_SHORT).show();
				}
			});
	}
	
	private void setEnquiryView(){
		Objects.requireNonNull(getContext()).startActivity(new Intent(getActivity(), EnquiryActivity.class));
	}
}