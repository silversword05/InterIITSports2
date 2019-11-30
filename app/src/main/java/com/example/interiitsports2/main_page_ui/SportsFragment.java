package com.example.interiitsports2.main_page_ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interiitsports2.R;
import com.example.interiitsports2.adaptars.SportsViewAdapter;

public class SportsFragment extends Fragment {
	
	private static final int no_of_columns = 2;
	
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_sports, null);
		RecyclerView recyclerView = view.findViewById(R.id.sports_list);
		recyclerView.setLayoutManager(new GridLayoutManager(getContext(), no_of_columns));
		recyclerView.setAdapter(new SportsViewAdapter(getContext()));
		return view;
	}
}