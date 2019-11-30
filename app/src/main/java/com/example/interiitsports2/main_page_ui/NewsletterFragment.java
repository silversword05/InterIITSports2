package com.example.interiitsports2.main_page_ui;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interiitsports2.R;
import com.example.interiitsports2.adaptars.NewsViewAdapter;

public class NewsletterFragment extends Fragment {
	
	public View onCreateView(@NonNull LayoutInflater inflater,
							 ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_newsletter, null);
		RecyclerView recyclerView = view.findViewById(R.id.news_list);
		recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
		recyclerView.setAdapter(new NewsViewAdapter(getContext()));
		return view;
	}
}