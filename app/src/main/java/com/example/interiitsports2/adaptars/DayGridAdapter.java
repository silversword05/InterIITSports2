package com.example.interiitsports2.adaptars;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interiitsports2.PreviousTeamSchedule;
import com.example.interiitsports2.R;

import java.util.ArrayList;

import io.paperdb.Paper;

public class DayGridAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<DayGridAdapter.DayViewHolder> {
	
	private ArrayList<Integer> days;
	private Context context;
	
	public DayGridAdapter(Context context){
		this.context = context;
		days = new ArrayList<>();
		for(int i=0; i<=7; i++){
			days.add(i);
		}
	}
	
	@NonNull
	@Override
	public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View dayEditableView = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_grid, parent, false);
		GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) dayEditableView.getLayoutParams();
		params.height = 150;
		dayEditableView.setLayoutParams(params);
		return new DayGridAdapter.DayViewHolder(dayEditableView);
	}
	
	@Override
	public void onBindViewHolder(@NonNull DayViewHolder holder, final int position) {
		holder.dayName.setText(String.valueOf(days.get(position)));
		holder.dayName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				//Toast.makeText(context, "Day "+position, Toast.LENGTH_SHORT).show();
				Paper.book().write("Day", position);
				Intent intent = new Intent(context, PreviousTeamSchedule.class);
				context.startActivity(intent);
			}
		});
	}
	
	@Override
	public int getItemCount() {
		return days.size();
	}
	
	class DayViewHolder extends RecyclerView.ViewHolder{
		Button dayName;
		DayViewHolder(@NonNull View itemView) {
			super(itemView);
			dayName = itemView.findViewById(R.id.dayName);
		}
	}
}
