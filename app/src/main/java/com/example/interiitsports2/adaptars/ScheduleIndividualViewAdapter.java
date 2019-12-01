package com.example.interiitsports2.adaptars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interiitsports2.R;
import com.example.interiitsports2.datas.ScheduleIndividualData;

import java.util.ArrayList;

public class ScheduleIndividualViewAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<ScheduleIndividualViewAdapter.ScheduleIndividualViewHolder> {
	
	private ArrayList<ScheduleIndividualData> scheduleIndividualDataArrayList = new ArrayList<>();
	private String game;
	private Context context;
	
	public ScheduleIndividualViewAdapter(Context context, String game){
		this.context = context;
		scheduleIndividualDataArrayList.add(ScheduleIndividualData.processJson(context));
	}
	
	@NonNull
	@Override
	public ScheduleIndividualViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View listEditableView = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_schedule, parent, false);
		return new ScheduleIndividualViewAdapter.ScheduleIndividualViewHolder(listEditableView);
	}
	
	@Override
	public void onBindViewHolder(@NonNull ScheduleIndividualViewHolder holder, int position) {
		holder.match_name.setText(scheduleIndividualDataArrayList.get(position).getMatch_name());
		holder.match_type.setText(scheduleIndividualDataArrayList.get(position).getMatch_type());
		holder.match_venue.setText(scheduleIndividualDataArrayList.get(position).getMatch_venue());
	}
	
	@Override
	public int getItemCount() {
		return scheduleIndividualDataArrayList.size();
	}
	
	class ScheduleIndividualViewHolder extends RecyclerView.ViewHolder{
		TextView match_type, match_name, match_venue;
		ScheduleIndividualViewHolder(@NonNull View itemView) {
			super(itemView);
			match_type = itemView.findViewById(R.id.match_type_schedulei);
			match_name = itemView.findViewById(R.id.match_name_schedulei);
			match_venue = itemView.findViewById(R.id.match_venue_schedulei);
		}
	}
}
