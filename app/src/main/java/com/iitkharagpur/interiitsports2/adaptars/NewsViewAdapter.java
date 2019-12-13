package com.iitkharagpur.interiitsports2.adaptars;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.iitkharagpur.interiitsports2.R;
import com.iitkharagpur.interiitsports2.datas.Newsletter_data;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class NewsViewAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<NewsViewAdapter.NewsViewHolder> {
	
	private Context context;
	private ArrayList<Newsletter_data> newsletter_data = new ArrayList<>();
	private RecyclerView recyclerView;
	private ProgressBar progressBar;
	
	public NewsViewAdapter(Context context, RecyclerView recyclerView, ProgressBar progressBar){
		this.context = context;
		this.recyclerView = recyclerView;
		this.progressBar = progressBar;
		fetchData();
	}
	
	private void fetchData(){
		FirebaseFirestore db = FirebaseFirestore.getInstance();
		db.collection("Newsletter").get().addOnCompleteListener(new OnCompleteListener<QuerySnapshot>() {
			@Override
			public void onComplete(@NonNull Task<QuerySnapshot> task) {
				if (task.isSuccessful()) {
					for (QueryDocumentSnapshot document : Objects.requireNonNull(task.getResult())) {
						Log.d("FIREBASE", document.getId() + " => " + document.getData());
						newsletter_data.add(new Newsletter_data(document.getData()));
					}
					Collections.sort(newsletter_data);
					if (newsletter_data.isEmpty()) Toast.makeText(context, "No newletters till now", Toast.LENGTH_SHORT).show();
					notifyDataSetChanged();
					progressBar.setVisibility(View.GONE);
					recyclerView.scheduleLayoutAnimation();
				} else {
					Log.d("FIREBASE", "Error getting documents: ", task.getException());
				}
			}
		});
	}
	
	private void setData(final int position, final NewsViewHolder newsViewHolder){
		Glide.with(context)
			.asBitmap()
			.load(newsletter_data.get(position).getLink())
			.into(new CustomTarget<Bitmap>() {
				@Override
				public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
					newsViewHolder.newsImage.setImageBitmap(resource);
					float density = context.getResources().getDisplayMetrics().density;
					ViewGroup.LayoutParams params = newsViewHolder.newsImage.getLayoutParams();
					params.height = 200;
					newsViewHolder.newsImage.setLayoutParams(params);
					newsViewHolder.itemView.setOnClickListener(new View.OnClickListener() {
						@Override
						public void onClick(View v) {
							Intent browserIntent = new Intent(Intent.ACTION_VIEW, Uri.parse(newsletter_data.get(position).getDirect()));
							context.startActivity(browserIntent);
						}
					});
				}
				
				@Override
				public void onLoadCleared(@Nullable Drawable placeholder) { }
			});
	}
	
	@NonNull
	@Override
	public NewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View listEditableView = LayoutInflater.from(parent.getContext()).inflate(R.layout.news_grid, parent, false);
		return new NewsViewHolder(listEditableView);
	}
	
	@Override
	public void onBindViewHolder(@NonNull NewsViewHolder holder, int position) {
		Log.d("DATA","DATA is setting");
		holder.newsHeading.setText(newsletter_data.get(position).getHeading());
		holder.news_body.setText(newsletter_data.get(position).getBody());
		setData(position, holder);
	}
	
	@Override
	public int getItemCount() {
		return newsletter_data.size();
	}
	
	class NewsViewHolder extends RecyclerView.ViewHolder{
		ImageView newsImage;
		TextView newsHeading;
		TextView news_body;
		NewsViewHolder(@NonNull View itemView) {
			super(itemView);
			newsImage = itemView.findViewById(R.id.news_image);
			newsHeading = itemView.findViewById(R.id.news_heading);
			news_body = itemView.findViewById(R.id.news_body);
		}
	}
}
