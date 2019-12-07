package com.example.interiitsports2.sports_page_ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interiitsports2.R;
import com.example.interiitsports2.adaptars.DayGridAdapter;
import com.example.interiitsports2.adaptars.PointsGridAdapter;
import com.example.interiitsports2.datas.GoogleSheetIds;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.paperdb.Paper;

public class PointsFragment extends Fragment {
	
	private int gender = 0;
	private List<String> groups = new ArrayList<>();
	
	private String[] noGroupGames = {"Weightlifting", "Athletics", "Chess"};
	private String[] men_women_games = {"Basketball", "Tennis"};
	private String[] teamGames = {"Basketball", "Soccer", "Cricket", "Tennis", "Hockey"};
	private RecyclerView recyclerView;
	private static final int no_of_columns = 2;
	
	private List<String> options = new ArrayList<>();
	
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_points, null);
		if(!Paper.book().contains("Game")) return view;
		
		String gameName = Paper.book().read("Game");
		assert gameName != null;
		
		if(Arrays.asList(men_women_games).contains(gameName))
			gender = 1;
		
		recyclerView = view.findViewById(R.id.points_list);
		
		if(Arrays.asList(noGroupGames).contains(gameName)){
		} else populateRecyclerView(gameName);
		
		return view;
	}
	
	private void populateRecyclerView(String gameName){
		Log.d("GAME", gameName + " "+GoogleSheetIds.getGroupNos(gameName));
		for(int i=1; i<=GoogleSheetIds.getGroupNos(gameName); i++){
			if(gender==0) options.add("Group "+i);
			else {
				options.add("Group "+i+" (M)");
				options.add("Group "+i+" (W)");
			}
		}
		recyclerView.setPadding(15, 30, 30, 40);
		recyclerView.setLayoutManager(new GridLayoutManager(getContext() ,no_of_columns));
		recyclerView.setAdapter(new PointsGridAdapter(getContext(), options, gender, gameName));
	}
}