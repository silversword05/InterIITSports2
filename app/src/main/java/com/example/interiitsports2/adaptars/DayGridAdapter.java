package com.example.interiitsports2.adaptars;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
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
	private int[] daysid = {R.drawable.day0, R.drawable.day1, R.drawable.day2, R.drawable.day3, R.drawable.day4, R.drawable.day5, R.drawable.day6, R.drawable.day7, R.drawable.day8};
	
	public DayGridAdapter(Context context){
		this.context = context;
		days = new ArrayList<>();
		for(int i=1; i<=9; i++){
			days.add(i);
		}
	}
	
	@NonNull
	@Override
	public DayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View dayEditableView = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_grid, parent, false);
		GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) dayEditableView.getLayoutParams();
		params.height = 200;
		dayEditableView.setLayoutParams(params);
		return new DayGridAdapter.DayViewHolder(dayEditableView);
	}
	
	@Override
	public void onBindViewHolder(@NonNull DayViewHolder holder, final int position) {
		holder.itemView.setBackground(context.getDrawable(daysid[position]));
		holder.dayName.setVisibility(View.GONE);
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
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
