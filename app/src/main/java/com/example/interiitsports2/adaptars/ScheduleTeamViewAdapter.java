package com.example.interiitsports2.adaptars;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interiitsports2.R;
import com.example.interiitsports2.datas.LiveMatch_data;
import com.example.interiitsports2.datas.ScheduleTeamData;

import java.util.ArrayList;

public class ScheduleTeamViewAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<ScheduleTeamViewAdapter.ScheduleTeamViewHolder>{
	
	private ArrayList<ScheduleTeamData> scheduleTeamDataArrayList = new ArrayList<>();
	private String game;
	private Context context;
	
	public ScheduleTeamViewAdapter(Context context, String game){
		this.context = context;
		scheduleTeamDataArrayList.add(ScheduleTeamData.processJson(context));
	}
	
	@NonNull
	@Override
	public ScheduleTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View listEditableView = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_schedule, parent, false);
		return new ScheduleTeamViewAdapter.ScheduleTeamViewHolder(listEditableView);
	}
	
	@Override
	public void onBindViewHolder(@NonNull ScheduleTeamViewHolder holder, int position) {
		holder.team1_name.setText(scheduleTeamDataArrayList.get(position).getTeam1_name());
		holder.team2_name.setText(scheduleTeamDataArrayList.get(position).getTeam2_name());
		holder.match_venue.setText(scheduleTeamDataArrayList.get(position).getMatch_venue());
		holder.match_type.setText(scheduleTeamDataArrayList.get(position).getMatch_type());
		holder.team1_logo.setImageBitmap(scheduleTeamDataArrayList.get(position).getTeam1_logo());
		holder.team2_logo.setImageBitmap(scheduleTeamDataArrayList.get(position).getTeam2_logo());
	}
	
	@Override
	public int getItemCount() {
		return scheduleTeamDataArrayList.size();
	}
	
	class ScheduleTeamViewHolder extends RecyclerView.ViewHolder{
		TextView match_type, team1_name, team2_name, match_venue;
		ImageView team1_logo, team2_logo;
		ScheduleTeamViewHolder(@NonNull View itemView) {
			super(itemView);
			match_type = itemView.findViewById(R.id.match_type_schedule);
			team1_name = itemView.findViewById(R.id.team1_name_schedule);
			team2_name = itemView.findViewById(R.id.team2_name_schedule);
			match_venue = itemView.findViewById(R.id.match_venue_schedule);
			team1_logo = itemView.findViewById(R.id.team1_logo_schedule);
			team2_logo = itemView.findViewById(R.id.team2_logo_schedule);
		}
	}
}
