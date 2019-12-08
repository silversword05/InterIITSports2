package com.example.interiitsports2.datas;

import java.util.Arrays;
import java.util.HashMap;

public class GoogleSheetIds {
	private static String[] gameNames = {"Basketball(M)", "Basketball(W)", "Football", "Cricket", "Tennis(M)", "Tennis(W)", "Hockey", "Chess", "Weightlifting", "Athletics"};
	private static String[] ids = {"1J7tic1U9rS9IVaJhzuYXAqyJEd7KXRTNanD4z7C7ItQ",
		"1HcUH-sP8xor9t2R09U89S3eAHtGRLL2ccbfxLQUthbU", "18DWtmoup92TvRJkYQm6IZiCnh1IA7KsPeIRfTol31zQ",
		"11Tc_EY0_hiyQjPmwa9RXiihGkg5heJuqPpogwvyqBRQ", "19jp36DB8JBxWV06czg2GevsWyV8CsTePYxvO8s-Wimo",
		"1b4hAxcIdZD57TMZWnuTMRDuuftHEYDQKnztU4CQkidk",
		"1hE6ipDW_mNSDrERz0v-1R4AMgZrNs88lJ8YhyCXEi_c", "1_HKM3hlRH15JGtDXLj4xy1Tn44y6oELf1Xsb3Ucu2CQ",
		"1csJo0RnS83pKtrar8OgrccFwQWNBOqVBhF239xwC7So", "1eLInqTtnXUzkfxvjmCElyqzWQ9ZwkUqpdYjRbFOFuWU"};
	private static int[] groups = {8, 4, 8, 8, 4, 4, 4, 1, 1, 1};
	
	public static HashMap<String, String> getTeamGame(String game, char gender) {
		String text = game + "(" + gender + ")";
		int pos = Arrays.asList(gameNames).indexOf(text);
		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("KEY", ids[pos]);
		hashMap.put("GROUP", ids[pos]);
		return hashMap;
	}
	
	public static HashMap<String, String> getTeamGame(String game) {
		int pos = Arrays.asList(gameNames).indexOf(game);
		HashMap<String, String> hashMap = new HashMap<>();
		hashMap.put("KEY", ids[pos]);
		hashMap.put("GROUP", ids[pos]);
		return hashMap;
	}
	
	public static String getIndividualGame(String game){
		int pos = Arrays.asList(gameNames).indexOf(game);
		return ids[pos];
	}
	
	public static int getGroupNos(String game) {
		for (String x : gameNames)
			if (x.contains(game)) return groups[Arrays.asList(gameNames).indexOf(x)];
		return -1;
	}
	
	public static int getGroupNos(String game, char gender) {
		game = game.trim() + "(" + gender + ")";
		for (String x : gameNames)
			if (x.contains(game)) return groups[Arrays.asList(gameNames).indexOf(x)];
		return -1;
	}
}
