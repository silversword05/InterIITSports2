package com.iitkharagpur.interiitsports2.adaptars;

import android.content.Context;
import android.graphics.Typeface;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.iitkharagpur.interiitsports2.R;
import com.iitkharagpur.interiitsports2.datas.PointsData;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class PointsDataAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<PointsDataAdapter.PointsDataViewHolder>{
	
	private List<PointsData> pointsData = new ArrayList<>();
	private Context context;
	private int gameType; // 0 for no group games
	private ProgressBar progressBar;
	
	public PointsDataAdapter(String sheet, String id, Context context, int gameType, ProgressBar progressBar){
		this.context = context;
		this.gameType = gameType;
		this.progressBar = progressBar;
		fetchData(sheet, id);
	}
	
	private void fetchData(String sheet, String id){
		Uri uri = new Uri.Builder()
			.scheme("https")
			.authority("sheets.googleapis.com")
			.appendPath("v4")
			.appendPath("spreadsheets")
			.appendPath(id)
			.appendPath("values")
			.appendPath(sheet)
			.appendQueryParameter("key", "AIzaSyAz5e5iG2MnVSQVZP6ozzCS5hZ5gjVwb_Y")
			.build();
		RequestQueue queue = Volley.newRequestQueue(context);
		StringRequest stringRequest = new StringRequest(Request.Method.GET, uri.toString(),
			new Response.Listener<String>() {
				@Override
				public void onResponse(String response) {
					JsonObject jsonObject = (JsonObject) JsonParser.parseString(response);
					Log.d("DATA", jsonObject.get("values").getAsJsonArray().toString());
					JsonArray jsonArray = jsonObject.get("values").getAsJsonArray();
					for(int i=0; i<jsonArray.size(); i++)
						pointsData.add(new PointsData(jsonArray.get(i).getAsJsonArray(), gameType));
					notifyDataSetChanged();
					progressBar.setVisibility(View.GONE);
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
	public PointsDataViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View listEditableView = LayoutInflater.from(parent.getContext()).inflate(R.layout.points_team, parent, false);
		return new PointsDataAdapter.PointsDataViewHolder(listEditableView);
	}
	
	@Override
	public void onBindViewHolder(@NonNull PointsDataViewHolder holder, int position) {
		holder.teamName_points.setText(pointsData.get(position).getTeamName());
		holder.points_points.setText(pointsData.get(position).getPoints_points());
		if(gameType==1){
			holder.for_points.setText(pointsData.get(position).getFor_points());
			holder.against_points.setText(pointsData.get(position).getAgainst_points());
			holder.lost_points.setText(pointsData.get(position).getLost_points());
			holder.won_points.setText(pointsData.get(position).getWon_points());
			holder.played_points.setText(pointsData.get(position).getPlayed_points());
		} else {
			holder.for_points.setVisibility(View.GONE);
			holder.against_points.setVisibility(View.GONE);
			holder.lost_points.setVisibility(View.GONE);
			holder.won_points.setVisibility(View.GONE);
			holder.played_points.setVisibility(View.GONE);
		}
		if (position==0){
			holder.teamName_points.setTypeface(null, Typeface.BOLD);
			holder.played_points.setTypeface(null, Typeface.BOLD);
			holder.won_points.setTypeface(null, Typeface.BOLD);
			holder.lost_points.setTypeface(null, Typeface.BOLD);
			holder.for_points.setTypeface(null, Typeface.BOLD);
			holder.against_points.setTypeface(null, Typeface.BOLD);
			holder.points_points.setTypeface(null, Typeface.BOLD);
		}
	}
	
	@Override
	public int getItemCount() {
		return pointsData.size();
	}
	
	class PointsDataViewHolder extends RecyclerView.ViewHolder {
		TextView teamName_points, played_points, won_points, lost_points, for_points, against_points, points_points;
		
		PointsDataViewHolder(@NonNull View itemView) {
			super(itemView);
			teamName_points = itemView.findViewById(R.id.teamName_points);
			played_points = itemView.findViewById(R.id.played_points);
			won_points = itemView.findViewById(R.id.won_points);
			lost_points = itemView.findViewById(R.id.lost_points);
			for_points = itemView.findViewById(R.id.for_points);
			against_points = itemView.findViewById(R.id.against_points);
			points_points = itemView.findViewById(R.id.points_points);
		}
	}
}
