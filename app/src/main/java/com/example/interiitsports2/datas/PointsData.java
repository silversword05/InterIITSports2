package com.example.interiitsports2.datas;

import com.google.gson.JsonArray;

public class PointsData {
	
	private String teamName, played_points, won_points, lost_points, for_points, against_points, points_points;
	
	public PointsData(JsonArray jsonArray, int gameType) {
		if(gameType==0) {
			this.teamName = jsonArray.get(0).getAsString();
			this.points_points = jsonArray.get(1).getAsString();
		}else{
			this.teamName = jsonArray.get(0).getAsString();
			this.played_points = jsonArray.get(1).getAsString();
			this.won_points = jsonArray.get(2).getAsString();
			this.lost_points = jsonArray.get(3).getAsString();
			this.for_points = jsonArray.get(4).getAsString();
			this.against_points = jsonArray.get(5).getAsString();
			this.points_points = jsonArray.get(6).getAsString();
		}
	}
	
	public String getTeamName() {
		return teamName;
	}
	
	public String getPlayed_points() {
		return played_points;
	}
	
	public String getWon_points() {
		return won_points;
	}
	
	public String getLost_points() {
		return lost_points;
	}
	
	public String getFor_points() {
		return for_points;
	}
	
	public String getAgainst_points() {
		return against_points;
	}
	
	public String getPoints_points() {
		return points_points;
	}
}
