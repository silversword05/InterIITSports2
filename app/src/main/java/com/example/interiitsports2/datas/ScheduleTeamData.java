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
	
	private ScheduleTeamData(String team1_name, String team2_name, String match_venue, String match_type, Context context) {
		this.team1_name = team1_name;
		this.team2_name = team2_name;
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
	
	public static ScheduleTeamData processJson(Context context){
		JsonObject jsonObject = JsonParser.parseString("{\"team1\":\"IIT_Bhilai\",\"team2\":\"IIT_BHU\", \"venue\":\"Gymkhana at 4:50PM\", \"type\":\"Final\"}").getAsJsonObject();
		return new ScheduleTeamData(jsonObject.get("team1").getAsString(), jsonObject.get("team2").getAsString(), jsonObject.get("venue").getAsString(), jsonObject.get("type").getAsString(), context);
		
	}
	
}


