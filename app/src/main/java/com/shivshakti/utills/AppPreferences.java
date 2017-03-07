package com.shivshakti.utills;

import android.content.Context;
import android.content.SharedPreferences;

public class AppPreferences {
	private static final String PREFERENCES_NAME = "Shivshakti";
	private static SharedPreferences sPreferences;

	public static void init(Context context) {
		sPreferences = context.getSharedPreferences(PREFERENCES_NAME, Context.MODE_PRIVATE);
	}

	public static SharedPreferences getPrefs() {
		return sPreferences;
	}
}