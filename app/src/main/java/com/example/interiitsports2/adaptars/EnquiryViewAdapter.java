package com.example.interiitsports2.adaptars;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interiitsports2.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class EnquiryViewAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<EnquiryViewAdapter.EnquiryViewHolder>  {
	
	private ArrayList<Map<String, Object>> phone_list = new ArrayList<>();
	private Context context;
	
	public EnquiryViewAdapter(Context context){
		this.context = context;
		fetchData();
	}
	
	private void fetchData(){
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		db.collection("Call").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
			@Override
			public void onComplete(@NonNull Task<QuerySnapshot> task) {
				if (task.isSuccessful()) {
					for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
						Log.d("FIREBASE", document.getId() + " => " + document.getData());
						phone_list.add( document.getData());
					}
					Log.d("PRIORITY", phone_list.get(0).toString());
					Collections.sort(phone_list, new Comparator<Map<String, Object>>() {
						@Override
						public int compare(Map<String, Object> o1, Map<String, Object> o2) {
							return ((String)Objects.requireNonNull(o1.get("Priority"))).compareTo((String) Objects.requireNonNull(o2.get("Priority")));
						}
					});
					notifyDataSetChanged();
				} else {
					Log.d("FIREBASE", "Error getting documents: ", task.getException());
				}
			}
		});
	}
	
	@NonNull
	@Override
	public EnquiryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		Log.d("DATA", "ON CREATE" );
		View listEditableView = LayoutInflater.from(parent.getContext()).inflate(R.layout.prompt_enquiry_text, parent, false);
		return new EnquiryViewAdapter.EnquiryViewHolder(listEditableView);
	}
	
	@Override
	public void onBindViewHolder(@NonNull EnquiryViewHolder holder,final int position) {
		Log.d("DATA", (String)Objects.requireNonNull(phone_list.get(position).get("Name")) );
		holder.phoneText.setText((String)Objects.requireNonNull(phone_list.get(position).get("Name")));
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(Intent.ACTION_DIAL, Uri.fromParts("tel", (String) Objects.requireNonNull(phone_list.get(position).get("Phone")), null));
				context.startActivity(intent);
			}
		});
	}
	
	@Override
	public int getItemCount() {
		return phone_list.size();
	}
	
	class EnquiryViewHolder extends RecyclerView.ViewHolder{
		TextView phoneText;
		EnquiryViewHolder(@NonNull View itemView) {
			super(itemView);
			phoneText = itemView.findViewById(R.id.phoneText);
		}
	}
}
