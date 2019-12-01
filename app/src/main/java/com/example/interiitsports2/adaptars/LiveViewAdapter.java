package com.example.interiitsports2.adaptars;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interiitsports2.R;
import com.example.interiitsports2.datas.LiveMatch_data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class LiveViewAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<LiveViewAdapter.LiveViewHolder> {
	
	private ArrayList<LiveMatch_data> liveMatchList = new ArrayList<>();
	private Context context;
	private String game;
	
	public LiveViewAdapter(Context context, String game){
		this.context = context;
		liveMatchList.add(LiveMatch_data.processJson(context));
	}
	
	@NonNull
	@Override
	public LiveViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View listEditableView = LayoutInflater.from(parent.getContext()).inflate(R.layout.team_match_card, parent, false);
		return new LiveViewAdapter.LiveViewHolder(listEditableView);
	}
	
	@Override
	public void onBindViewHolder(@NonNull LiveViewHolder holder, final int position) {
		holder.team1_name.setText(liveMatchList.get(position).getTeam1_name());
		holder.team2_name.setText(liveMatchList.get(position).getTeam2_name());
		holder.team1_score.setText(liveMatchList.get(position).getTeam1_score());
		holder.team2_score.setText(liveMatchList.get(position).getTeam2_score());
		holder.match_venue.setText(liveMatchList.get(position).getMatch_venue());
		holder.match_type.setText(liveMatchList.get(position).getMatch_type());
		holder.team1_logo.setImageBitmap(liveMatchList.get(position).getTeam1_logo());
		holder.team2_logo.setImageBitmap(liveMatchList.get(position).getTeam2_logo());
		holder.itemView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				final AlertDialog.Builder builder = new AlertDialog.Builder(context);
				LayoutInflater li = LayoutInflater.from(context);
				final View promptsView = li.inflate(R.layout.prompt1, null, false);
				builder.setView(promptsView);
				builder.setTitle("Comments");
				final ListView listView = promptsView.findViewById(R.id.comments_list);
				String comments = liveMatchList.get(position).getCommentary();
				List<String> comment_list = Arrays.asList(comments.split("\n"));
				final ArrayAdapter<String> adapter = new ArrayAdapter<>(context, R.layout.prompt1_text, comment_list);
				listView.setAdapter(adapter);
				builder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
					@Override
					public void onClick(DialogInterface dialog, int which) {
						dialog.cancel();
					}
				}).show();
			}
		});
	}
	
	@Override
	public int getItemCount() {
		return liveMatchList.size();
	}
	
	class LiveViewHolder extends RecyclerView.ViewHolder{
		TextView match_type, team1_score, team2_score, team1_name, team2_name, match_venue;
		ImageView team1_logo, team2_logo;
		LiveViewHolder(@NonNull View itemView) {
			super(itemView);
			match_type = itemView.findViewById(R.id.match_type);
			team1_score = itemView.findViewById(R.id.team1_score);
			team2_score = itemView.findViewById(R.id.team2_score);
			team1_name = itemView.findViewById(R.id.team1_name);
			team2_name = itemView.findViewById(R.id.team2_name);
			match_venue = itemView.findViewById(R.id.match_venue);
			team1_logo = itemView.findViewById(R.id.team1_logo);
			team2_logo = itemView.findViewById(R.id.team2_logo);
		}
	}
}
