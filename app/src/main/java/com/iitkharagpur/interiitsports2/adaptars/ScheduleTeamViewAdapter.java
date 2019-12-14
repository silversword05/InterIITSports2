package com.iitkharagpur.interiitsports2.adaptars;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.iitkharagpur.interiitsports2.R;
import com.iitkharagpur.interiitsports2.datas.ScheduleTeamData;

import java.util.ArrayList;
import java.util.Objects;

public class ScheduleTeamViewAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<ScheduleTeamViewAdapter.ScheduleTeamViewHolder> {
	
	private ArrayList<ScheduleTeamData> scheduleTeamDataArrayList = new ArrayList<>();
	private String game;
	private Context context;
	private int day;
	private RecyclerView recyclerView;
	private ProgressBar progressBar;
	
	public ScheduleTeamViewAdapter(Context context, String game, int day, RecyclerView recyclerView, ProgressBar progressBar) {
		this.context = context;
		this.day = day;
		this.game = game;
		this.recyclerView = recyclerView;
		this.progressBar = progressBar;
		fetchData();
	}
	
	private void fetchData() {
		Uri uri = new Uri.Builder()
			.scheme("https")
			.authority("interiit.com")
			.appendPath("getSchedule_Team_Matches_Deatils_Android")
			.appendPath(game)
			.appendPath(day + "")
			.build();
		RequestQueue queue = Volley.newRequestQueue(context);
		StringRequest stringRequest = new StringRequest(Request.Method.GET, uri.toString(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					JsonArray jsonArray = (JsonArray) JsonParser.parseString(response);
					for (int i = 0; i < jsonArray.size(); i++) {
						Log.d("DATA LIVE", jsonArray.get(i).getAsJsonObject().toString());
						scheduleTeamDataArrayList.add(ScheduleTeamData.processJson(context, jsonArray.get(i).getAsJsonObject()));
					}
					notifyDataSetChanged();
					progressBar.setVisibility(View.GONE);
					if (scheduleTeamDataArrayList.isEmpty())
						Toast.makeText(context, "No matches till now", Toast.LENGTH_SHORT).show();
					recyclerView.scheduleLayoutAnimation();
				}
			}, new Response.ErrorListener() {
			@Override
			public void onErrorResponse(VolleyError error) {
				Log.d("DATA", Objects.requireNonNull(error.getMessage()));
			}
		});
		queue.add(stringRequest);
	}
	
	@NonNull
	@Override
	public ScheduleTeamViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View listEditableView = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_schedule, parent, false);
		return new ScheduleTeamViewAdapter.ScheduleTeamViewHolder(listEditableView);
	}
	
	@Override
	public void onBindViewHolder(@NonNull ScheduleTeamViewHolder holder, final int position) {
		holder.team1_name.setText(scheduleTeamDataArrayList.get(position).getTeam1_name());
		holder.team2_name.setText(scheduleTeamDataArrayList.get(position).getTeam2_name());
		holder.match_venue.setText(scheduleTeamDataArrayList.get(position).getMatch_venue());
		holder.match_type.setText(scheduleTeamDataArrayList.get(position).getMatch_type());
		holder.team1_logo.setImageBitmap(scheduleTeamDataArrayList.get(position).getTeam1_logo());
		holder.team2_logo.setImageBitmap(scheduleTeamDataArrayList.get(position).getTeam2_logo());
		if (!scheduleTeamDataArrayList.get(position).getWinner().equals("None"))
			holder.match_venue.setTextColor(Color.BLUE);
		else holder.match_venue.setTextColor(Color.BLACK);
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (!scheduleTeamDataArrayList.get(position).getWinner().contains("None"))
					createAlert(position);
			}
		});
		
	}
	
	private void createAlert(int position) {
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater li = LayoutInflater.from(context);
		final View promptsView = li.inflate(R.layout.prompts_match_result_team, null, false);
		builder.setView(promptsView);
		builder.setTitle("Results");
		SpannableString winner = new SpannableString("Winner : " + scheduleTeamDataArrayList.get(position).getWinner());
		winner.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		SpannableString runner = new SpannableString("Runner : " + scheduleTeamDataArrayList.get(position).getRunner());
		runner.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		SpannableString status = new SpannableString("Status : " + scheduleTeamDataArrayList.get(position).getStatus());
		status.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		((TextView) promptsView.findViewById(R.id.winner)).setText(winner);
		((TextView) promptsView.findViewById(R.id.runner)).setText(runner);
		((TextView) promptsView.findViewById(R.id.status)).setText(status);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();
		
	}
	
	@Override
	public int getItemCount() {
		return scheduleTeamDataArrayList.size();
	}
	
	class ScheduleTeamViewHolder extends RecyclerView.ViewHolder {
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
