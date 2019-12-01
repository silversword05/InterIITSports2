package com.example.interiitsports2.adaptars;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interiitsports2.R;
import com.example.interiitsports2.SpecificSportsActivity;

public class SportsViewAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<SportsViewAdapter.SportsViewHolder> {
	
	private String[] sports_list = {"Basketball", "Soccer", "Cricket", "Tennis", "Hockey", "Chess", "Weightlifting", "Athletics"};
	private int[] icons = {R.drawable.ic_basketball_icon, R.drawable.ic_soccer_icon, R.drawable.ic_cricket_icon, R.drawable.ic_tennis_icon, R.drawable.ic_hockey_icon, R.drawable.ic_chess_icon, R.drawable.ic_weightlifting_icon, R.drawable.ic_athletics_icon};
	private Context context;
	
	public SportsViewAdapter(Context context){
		this.context = context;
	}
	
	@NonNull
	@Override
	public SportsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View listEditableView = LayoutInflater.from(parent.getContext()).inflate(R.layout.sports_grid, parent, false);
		GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) listEditableView.getLayoutParams();
		params.height = GridLayoutManager.LayoutParams.WRAP_CONTENT;
		listEditableView.setLayoutParams(params);
		return new SportsViewAdapter.SportsViewHolder(listEditableView);
	}
	
	@Override
	public void onBindViewHolder(@NonNull SportsViewHolder holder, int position) {
		final String sport = sports_list[position];
		holder.gridImage.setImageResource(icons[position]);
		holder.gridText.setText(sport);
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(context, SpecificSportsActivity.class);
				intent.putExtra("Game", sport);
				context.startActivity(intent);
			}
		});
	}
	
	@Override
	public int getItemCount() {
		return sports_list.length;
	}
	
	class SportsViewHolder extends RecyclerView.ViewHolder{
		ImageView gridImage;
		TextView gridText;
		SportsViewHolder(@NonNull View itemView) {
			super(itemView);
			gridImage = itemView.findViewById(R.id.gridImage);
			gridText = itemView.findViewById(R.id.gridText);
		}
	}
}
