package com.example.interiitsports2.sports_page_ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interiitsports2.R;
import com.example.interiitsports2.adaptars.DayGridAdapter;
import com.example.interiitsports2.adaptars.ScheduleIndividualViewAdapter;
import com.example.interiitsports2.adaptars.ScheduleTeamViewAdapter;

import java.util.Arrays;

import io.paperdb.Paper;

public class ScheduleFragment extends Fragment {
	
	private String[] individualGames = {"Weightlifting", "Athletics"};
	private static final int no_of_columns = 2;
	
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_schedule, null);
		if(!Paper.book().contains("Game")) return view;
		
		String gameName = Paper.book().read("Game");
		assert gameName != null;
		
		RecyclerView recyclerView = view.findViewById(R.id.schedule_list);
		
		
		if(Arrays.asList(individualGames).contains(gameName)){
			recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
			recyclerView.setAdapter(new ScheduleIndividualViewAdapter(getContext(), gameName));
		} else {
			recyclerView.setPadding(15, 10, 30, 20);
			recyclerView.setLayoutManager(new GridLayoutManager(getContext() ,no_of_columns));
			recyclerView.setAdapter(new DayGridAdapter(getContext()));
		}
		
		return view;
	}
}