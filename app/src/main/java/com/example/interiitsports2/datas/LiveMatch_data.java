package com.example.interiitsports2.datas;

import android.content.Context;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import java.io.IOException;
import java.util.Objects;

public class LiveMatch_data {
	private String team1_name;
	private String team2_name;
	private String team1_score;
	private String team2_score;
	private String match_commentary;
	private String match_venue;
	private String match_type;
	private Context context;
	
	private LiveMatch_data(String team1_name, String team2_name, String team1_score, String team2_score, String commentary, String match_venue, String match_type, Context context) {
		this.team1_name = team1_name;
		this.team2_name = team2_name;
		this.team1_score = team1_score;
		this.team2_score = team2_score;
		this.match_commentary = commentary;
		this.match_venue = match_venue;
		this.match_type = match_type;
		this.context = context;
	}
	
	public String getTeam1_name() {
		return team1_name.replace('_',' ');
	}
	
	public String getTeam2_name() {
		return team2_name.replace('_',' ');
	}
	
	public String getTeam1_score() {
		return team1_score;
	}
	
	public String getTeam2_score() {
		return team2_score;
	}
	
	public String getCommentary() {
		return match_commentary;
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
	
	public static LiveMatch_data processJson(Context context, JsonObject jsonObject){
		Log.d("NAME SPORT", jsonObject.get("sport_name").toString());
		String score1 = (jsonObject.has("score1"))?jsonObject.get("score1").toString():"";
		Log.d("SCORE 1", score1);
		String score2 = (jsonObject.has("score2"))?jsonObject.get("score2").toString():"";
		Log.d("SCORE 2", score2);
		String clg1 = jsonObject.get("clg1").getAsString().trim().replace(' ','_');
		String clg2 = jsonObject.get("clg2").getAsString().trim().replace(' ','_');
		return new LiveMatch_data(clg1, clg2, score1, score2,
			jsonObject.get("commentry").getAsString(), jsonObject.get("venue_time").getAsString(), jsonObject.get("level").getAsString(), context);
		
	}
	
}
