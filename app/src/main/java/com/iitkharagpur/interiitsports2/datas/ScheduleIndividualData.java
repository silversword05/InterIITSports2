package com.iitkharagpur.interiitsports2.datas;

import android.content.Context;
import android.util.Log;

import com.google.gson.JsonObject;

public class ScheduleIndividualData {
	private String match_name;
	private String match_venue;
	
	private String match_type;
	private String win1, win2, win3, win4;
	
	private ScheduleIndividualData(String match_name, String match_venue, String match_type, String win1, String  win2, String  win3, String  win4, Context context) {
		this.match_name = match_name;
		this.match_venue = match_venue;
		this.match_type = match_type;
		this.win1 = win1;
		this.win2 = win2;
		this.win3 = win3;
		this.win4 = win4;
	}
	
	public String getMatch_venue() { return match_venue; }
	
	public String getMatch_type() { return match_type; }
	
	public String getWin1() { return win1; }
	
	public String getWin2() { return win2; }
	
	public String getWin3() { return win3; }
	
	public String getWin4() { return win4; }
	
	public static ScheduleIndividualData processJson(Context context, JsonObject jsonObject){
		Log.d("NAME SPORT", jsonObject.get("sport_name").toString());
		String sport_name = jsonObject.get("sport_name").getAsString();
		String venue = jsonObject.get("venue").getAsString();
		return new ScheduleIndividualData(sport_name, venue, "Final", jsonObject.get("win1").getAsString(), jsonObject.get("win2").getAsString(),
			jsonObject.get("win3").getAsString(), jsonObject.get("win4").getAsString(), context);
		
	}
	
	public String getMatch_name() {
		return match_name;
	}
}
