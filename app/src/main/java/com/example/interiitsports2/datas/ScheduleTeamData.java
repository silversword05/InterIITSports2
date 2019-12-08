package com.example.interiitsports2.datas;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Objects;

public class ScheduleTeamData {
	
	private String team1_name;
	private String team2_name;
	private String match_venue;
	private String match_type;
	private Context context;
	private String status;
	private String winner, runner;
	
	private ScheduleTeamData(String team1_name, String team2_name, String match_venue, String match_type, String winner, String runner, String status, Context context) {
		this.team1_name = team1_name;
		this.team2_name = team2_name;
		this.match_venue = match_venue;
		this.match_type = match_type;
		this.winner = winner;
		this.runner = runner;
		this.status = status;
		this.context = context;
	}
	
	public String getTeam1_name() {
		return team1_name.replace('_',' ');
	}
	
	public String getTeam2_name() {
		return team2_name.replace('_',' ');
	}
	
	public String getMatch_venue() { return match_venue; }
	
	public String getMatch_type() { return match_type; }
	
	public Bitmap getTeam1_logo() {
		AssetManager assetManager = context.getAssets();
		Log.d("ASSETS", "ASSETS");
		try {
			for (String file : Objects.requireNonNull(assetManager.list(""))) {
				if (file.contains(team1_name)) {
					return BitmapFactory.decodeStream(assetManager.open(file));
				}
			}
			return BitmapFactory.decodeStream(assetManager.open("vs.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public Bitmap getTeam2_logo() {
		AssetManager assetManager = context.getAssets();
		try {
			Log.d("ASSETS", "ASSETS");
			for (String file : Objects.requireNonNull(assetManager.list(""))) {
				if (file.contains(team2_name)) {
					return BitmapFactory.decodeStream(assetManager.open(file));
				}
			}
			return BitmapFactory.decodeStream(assetManager.open("vs.jpg"));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	public String getWinner() {
		return winner;
	}
	
	public String getRunner() {
		return runner;
	}
	
	public String getStatus() { return status; }
	
	
	public static ScheduleTeamData processJson(Context context, JsonObject jsonObject){
		Log.d("NAME SPORT", jsonObject.get("sport_name").toString());
		String clg1 = jsonObject.get("clg1").getAsString().trim().replace(' ','_');
		String clg2 = jsonObject.get("clg2").getAsString().trim().replace(' ','_');
		return new ScheduleTeamData(clg1, clg2, jsonObject.get("venue_time").getAsString(), jsonObject.get("level").getAsString(),
			jsonObject.get("winner").getAsString(), jsonObject.get("runner").getAsString(), jsonObject.get("status").getAsString(), context);
	}
	
}


