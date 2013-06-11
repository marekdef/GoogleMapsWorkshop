package com.mobica.workshop.Preferences;

import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.mobica.workshop.MapsActivity;

public class Preferences {

	private static SharedPreferences preferences = null;
	private static SharedPreferences.Editor preferencesEditor = null;
	
	private static void init() {
		if(preferences ==  null) preferences = PreferenceManager.getDefaultSharedPreferences(MapsActivity.getInstance());
		if(preferencesEditor == null) preferencesEditor = preferences.edit();
	}
	
	public static boolean putBoolean(String key, boolean value) {
		init();
		preferencesEditor.putBoolean(key, value);
		return preferencesEditor.commit();
	}
	
	public static boolean putFloat(String key, float value) {
		init();
		preferencesEditor.putFloat(key, value);
		return preferencesEditor.commit();
	}
	
	public static boolean putString(String key, String value) {
		init();
		preferencesEditor.putString(key, value);
		return preferencesEditor.commit();
	}
	
	public static boolean putInt(String key, int value) {
		init();
		preferencesEditor.putInt(key, value);
		return preferencesEditor.commit();
	}
	
	public static boolean putLong(String key, long value) {
		init();
		preferencesEditor.putLong(key, value);
		return preferencesEditor.commit();
	}
	
	public static boolean remove(String key) {
		init();
		preferencesEditor.remove(key);
		return preferencesEditor.commit();
	}

	public static boolean getBoolean(String key) {
		init();
		return preferences.getBoolean(key, false);
	}
	
	public static float getFloat(String key, float defValue) {
		init();
		return preferences.getFloat(key, defValue);
	}
	
	public static String getString(String key, String defValue) {
		init();
		return preferences.getString(key, defValue);
	}
	
	public static int getInt(String key, int defValue) {
		init();
		return preferences.getInt(key, defValue);
	}
	
	public static long getLong(String key, long defValue) {
		init();
		return preferences.getLong(key, defValue);
	}
}
