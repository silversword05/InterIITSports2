package com.example.interiitsports2.adaptars;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.StyleSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import com.example.interiitsports2.R;
import com.example.interiitsports2.datas.ScheduleIndividualData;
import com.example.interiitsports2.datas.ScheduleTeamData;
import com.google.gson.JsonArray;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.Objects;

public class ScheduleIndividualViewAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<ScheduleIndividualViewAdapter.ScheduleIndividualViewHolder> {
	
	private ArrayList<ScheduleIndividualData> scheduleIndividualDataArrayList = new ArrayList<>();
	private String game;
	private Context context;
	private RecyclerView recyclerView;
	
	public ScheduleIndividualViewAdapter(Context context, String game, RecyclerView recyclerView){
		this.context = context;
		this.recyclerView = recyclerView;
		this.game = game;
		fetchData();
	}
	
	private void fetchData(){
		Uri uri = new Uri.Builder()
			.scheme("https")
			.authority("interiit.com")
			.appendPath("getSchedule_Individual_Matches_Deatils_Android")
			.appendPath(game)
			.build();
		RequestQueue queue = Volley.newRequestQueue(context);
		StringRequest stringRequest = new StringRequest(Request.Method.GET, uri.toString(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					JsonArray jsonArray = (JsonArray) JsonParser.parseString(response);
					for(int i=0; i<jsonArray.size(); i++) {
						Log.d("DATA INDIVIDUAL", jsonArray.get(i).getAsJsonObject().toString());
						scheduleIndividualDataArrayList.add(ScheduleIndividualData.processJson(context, jsonArray.get(i).getAsJsonObject()));
					}
					notifyDataSetChanged();
					if(scheduleIndividualDataArrayList.isEmpty()) Toast.makeText(context, "No matches till now", Toast.LENGTH_SHORT).show();
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
	public ScheduleIndividualViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View listEditableView = LayoutInflater.from(parent.getContext()).inflate(R.layout.individual_schedule, parent, false);
		return new ScheduleIndividualViewAdapter.ScheduleIndividualViewHolder(listEditableView);
	}
	
	@Override
	public void onBindViewHolder(@NonNull ScheduleIndividualViewHolder holder, final int position) {
		holder.match_name.setText(scheduleIndividualDataArrayList.get(position).getMatch_name());
		holder.match_type.setText(scheduleIndividualDataArrayList.get(position).getMatch_type());
		holder.match_venue.setText(scheduleIndividualDataArrayList.get(position).getMatch_venue());
		holder.itemView.setOnClickListener(new View.OnClickListener(){
			@Override
			public void onClick(View v) {
				createAlert(position);
			}
		});
	}
	
	private void createAlert(int position){
		final AlertDialog.Builder builder = new AlertDialog.Builder(context);
		LayoutInflater li = LayoutInflater.from(context);
		final View promptsView = li.inflate(R.layout.prompts_match_result_individual, null, false);
		builder.setView(promptsView);
		builder.setTitle("Results");
		Log.d("INDIVIDUAL", scheduleIndividualDataArrayList.get(position).getWin1());
		SpannableString win1 = new SpannableString("Rank 1 \n "+scheduleIndividualDataArrayList.get(position).getWin1()+"\n");
		win1.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		SpannableString win2 = new SpannableString("Rank 2 \n "+scheduleIndividualDataArrayList.get(position).getWin2()+"\n");
		win2.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		SpannableString win3 = new SpannableString("Rank 3 \n "+scheduleIndividualDataArrayList.get(position).getWin3()+"\n");
		win3.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		SpannableString win4 = new SpannableString("Rank 4 \n "+scheduleIndividualDataArrayList.get(position).getWin4()+"\n");
		win4.setSpan(new StyleSpan(Typeface.BOLD), 0, 6, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
		((TextView)promptsView.findViewById(R.id.win1)).setText(win1);
		((TextView)promptsView.findViewById(R.id.win2)).setText(win2);
		((TextView)promptsView.findViewById(R.id.win3)).setText(win3);
		((TextView)promptsView.findViewById(R.id.win4)).setText(win4);
		builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				dialog.dismiss();
			}
		}).show();
		
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
