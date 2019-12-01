package com.example.interiitsports2.datas;

import android.content.Context;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

public class ScheduleIndividualData {
	private String match_name;
	private String match_venue;
	private String match_type;
	
	private ScheduleIndividualData(String match_name, String match_venue, String match_type, Context context) {
		this.match_name = match_name;
		this.match_venue = match_venue;
		this.match_type = match_type;
	}
	
	public String getMatch_venue() { return match_venue; }
	
	public String getMatch_type() { return match_type; }
	
	public static ScheduleIndividualData processJson(Context context){
		JsonObject jsonObject = JsonParser.parseString("{\"name\":\"100m Long race\", \"venue\":\"Gymkhana at 4:50PM\", \"type\":\"Final\"}").getAsJsonObject();
		return new ScheduleIndividualData(jsonObject.get("name").getAsString(), jsonObject.get("venue").getAsString(), jsonObject.get("type").getAsString(), context);
		
	}
	
	public String getMatch_name() {
		return match_name;
	}
}
