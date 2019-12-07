package com.example.interiitsports2.adaptars;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.interiitsports2.PointsTableActivity;
import com.example.interiitsports2.R;
import com.example.interiitsports2.datas.GoogleSheetIds;

import java.util.List;
import java.util.Objects;

public class PointsGridAdapter extends androidx.recyclerview.widget.RecyclerView.Adapter<PointsGridAdapter.PointsViewHolder> {
	
	private List<String> arrayList;
	private Context context;
	private int gender;
	private String gameName;
	
	public PointsGridAdapter(Context context, List<String> options, int gender, String gameName) {
		this.context = context;
		arrayList = options;
		this.gender = gender;
		this.gameName = gameName;
	}
	
	@NonNull
	@Override
	public PointsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
		View dayEditableView = LayoutInflater.from(parent.getContext()).inflate(R.layout.day_grid, parent, false);
		GridLayoutManager.LayoutParams params = (GridLayoutManager.LayoutParams) dayEditableView.getLayoutParams();
		params.height = 120;
		dayEditableView.setLayoutParams(params);
		return new PointsGridAdapter.PointsViewHolder(dayEditableView);
	}
	
	@Override
	public void onBindViewHolder(@NonNull PointsViewHolder holder, final int position) {
		holder.dayName.setText(String.valueOf(arrayList.get(position)));
		holder.dayName.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Log.d("CLICK", "CLICK");
				if (gender == 0) {
					String sheet = arrayList.get(position).trim();
					String id = GoogleSheetIds.getTeamGame(gameName).get("KEY");
					Log.d("ID", Objects.requireNonNull(id) + " " + sheet);
					Intent intent = new Intent(context, PointsTableActivity.class);
					intent.putExtra("SHEET", sheet);
					intent.putExtra("ID", id);
					intent.putExtra("TYPE", 1);
					context.startActivity(intent);
				} else {
					String sheet = arrayList.get(position).substring(0, arrayList.get(position).indexOf('(')).trim();
					char g = arrayList.get(position).charAt(arrayList.get(position).indexOf("(") + 1);
					String id = GoogleSheetIds.getTeamGame(gameName, g).get("KEY");
					Log.d("ID", Objects.requireNonNull(id) + " " + sheet);
					Intent intent = new Intent(context, PointsTableActivity.class);
					intent.putExtra("SHEET", sheet);
					intent.putExtra("ID", id);
					intent.putExtra("TYPE", 1);
					context.startActivity(intent);
				}
			}
		});
	}
	
	@Override
	public int getItemCount() {
		return arrayList.size();
	}
	
	class PointsViewHolder extends RecyclerView.ViewHolder {
		Button dayName;
		
		PointsViewHolder(@NonNull View itemView) {
			super(itemView);
			dayName = itemView.findViewById(R.id.dayName);
		}
	}
}
