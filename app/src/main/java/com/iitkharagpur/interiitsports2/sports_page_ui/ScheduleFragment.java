package com.iitkharagpur.interiitsports2.sports_page_ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.view.animation.LayoutAnimationController;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.iitkharagpur.interiitsports2.R;
import com.iitkharagpur.interiitsports2.adaptars.DayGridAdapter;
import com.iitkharagpur.interiitsports2.adaptars.ScheduleIndividualViewAdapter;

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
		ProgressBar progressBar = view.findViewById(R.id.progressBar);
		progressBar.setVisibility(View.VISIBLE);
		
		if(Arrays.asList(individualGames).contains(gameName)){
			recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
			LayoutAnimationController animation = AnimationUtils.loadLayoutAnimation(getContext(), R.anim.layout_animation_fall_down);
			recyclerView.setLayoutAnimation(animation);
			recyclerView.setAdapter(new ScheduleIndividualViewAdapter(getContext(), gameName, recyclerView, progressBar));
		} else {
			progressBar.setVisibility(View.GONE);
			recyclerView.setPadding(15, 10, 30, 100);
			recyclerView.setLayoutManager(new GridLayoutManager(getContext() ,no_of_columns));
			recyclerView.setAdapter(new DayGridAdapter(getContext()));
		}
		
		return view;
	}
}