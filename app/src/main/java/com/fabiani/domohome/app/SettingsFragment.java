package com.fabiani.domohome.app;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.Preference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Patterns;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class SettingsFragment extends PreferenceFragment {
	public static final String TAG = "SettingsFragment";
	public static final String IP_KEY="ip";
	public static final String EXTRA_IP_SUBMITTED="com.homeautomation.smarthome_extra_ip_submitted";
    public static final String EXTRA_IP_IS_VALID ="com.homeautomation.smarthome.isvalid";
	public static String mAddressInput;
	public static boolean isIpValid=false;
	private EditTextPreference mIpEditTextPreference;
	private Pattern mIpPattern = Patterns.IP_ADDRESS;
	private Matcher mIpMatcher;
	
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		addPreferencesFromResource(R.xml.preferences);
		new Connector();
		isIpValid=getArguments().getBoolean(EXTRA_IP_SUBMITTED);
		mIpEditTextPreference = (EditTextPreference) findPreference("IP_KEY");
		mIpEditTextPreference.setOnPreferenceChangeListener(new Preference.OnPreferenceChangeListener() {
			@Override
			public boolean onPreferenceChange(Preference preference, Object newValue) {
				mAddressInput = newValue.toString();
				Connector.sIp = mAddressInput;
                SharedPreferences.Editor editor =PreferenceManager.
                        getDefaultSharedPreferences(getActivity()).edit();
				editor.putString(IP_KEY, mAddressInput);
				editor.apply();
				if (isIpValid()) {
					isIpValid = true;
				} else{
					Toast.makeText(getActivity(), R.string.connector_ip_error, Toast.LENGTH_SHORT).show();
					isIpValid = false;
			}
                editor.putBoolean(EXTRA_IP_IS_VALID,isIpValid);
                editor.apply();
				return true;
			}
		});
		mIpEditTextPreference.setText(Connector.sIp);
	}
	public  boolean isIpValid() {
		mIpMatcher = mIpPattern.matcher(mAddressInput);
        return mIpMatcher.matches() ? true : false;
	}

	public static SettingsFragment newInstance(){
		Bundle args=new Bundle();
		args.putBoolean(EXTRA_IP_SUBMITTED, isIpValid);
		SettingsFragment fragment=new SettingsFragment();
		fragment.setArguments(args);
		return fragment;
	}
}
