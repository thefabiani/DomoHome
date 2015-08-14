package com.fabiani.domohome.app;

import android.content.Context;
import android.preference.PreferenceManager;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

//singleton class

public class Dashboard {
	private static final String TAG = "Dashboard";
	private static final String JSON_FILENAME = "commands.json";
	private static Dashboard sDashboard;
	private Context mAppContext;
	private JSONSerializer mJSONSerializer;
	private ArrayList<Command> mCommands;


	private Dashboard(Context appContext) {
		mAppContext = appContext;
		mJSONSerializer = new JSONSerializer(mAppContext, JSON_FILENAME);
		try {
			mCommands = mJSONSerializer.loadCommands();
			Log.i(TAG, "Commands loaded  ");
		} catch (Exception e) {
			mCommands = new ArrayList<>();
			Log.i(TAG, "commands not  loaded  ");
		}
		Connector.sIp = PreferenceManager.getDefaultSharedPreferences(mAppContext)
				.getString(SettingsFragment.IP_KEY, SettingsFragment.mAddressInput);
		SettingsFragment.isIpValid = PreferenceManager.getDefaultSharedPreferences(mAppContext)
				.getBoolean(SettingsFragment.EXTRA_IP_IS_VALID, SettingsFragment.isIpValid);
	}

	public static Dashboard get(Context c) {
		if (sDashboard == null)
			sDashboard = new Dashboard(c.getApplicationContext());
		return sDashboard;
	}
	public static ArrayList<Command>filter (List<Command> commands, CommandPredicate p){
		ArrayList<Command>result=new ArrayList<>();
		for (Command c:commands)
			if(p.test(c))
				result.add(c);
		return result;
	}

	public ArrayList<Command> getCommands() {
		return mCommands;
	}

	public Command getCommand(UUID id) {
		for (Command c : mCommands) {
			if (c.getId().equals(id))
				return c;
		}
		return null;
	}

	public void addCommand(Command c) {
		mCommands.add(c);
	}

	public void deleteCommand(Command c) {
		mCommands.remove(c);
	}

	public void saveCommands() {
		try {
			mJSONSerializer.saveCommands(mCommands);
			Log.i(TAG, "Commands saved");
		} catch (Exception e) {
			Log.i(TAG, "Error saving commands");

		}
	}

}
