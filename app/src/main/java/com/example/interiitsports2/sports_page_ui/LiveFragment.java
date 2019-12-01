package com.example.interiitsports2.sports_page_ui;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interiitsports2.R;
import com.example.interiitsports2.adaptars.LiveViewAdapter;

import java.util.Arrays;

import io.paperdb.Paper;

public class LiveFragment extends Fragment {
	
	private String[] individualGames = {"Weightlifting", "Athletics"};
	
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_live, null);
		if(!Paper.book().contains("Game")) return view;
		
		String gameName = Paper.book().read("Game");
		assert gameName != null;
		
		if(Arrays.asList(individualGames).contains(gameName)) return view;
		
		RecyclerView recyclerView = view.findViewById(R.id.live_list);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.setAdapter(new LiveViewAdapter(getContext(), gameName));
		
		Log.d("Game NAme", gameName);
		return view;
	}
}