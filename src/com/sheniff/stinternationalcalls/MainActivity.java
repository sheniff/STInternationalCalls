package com.sheniff.stinternationalcalls;

import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.EditTextPreference;
import android.preference.PreferenceActivity;

public class MainActivity extends PreferenceActivity implements OnSharedPreferenceChangeListener {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.pref_general);
    }
    
    @Override
    protected void onResume() {
        super.onResume();
        getPreferenceScreen().getSharedPreferences()
                .registerOnSharedPreferenceChangeListener(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        getPreferenceScreen().getSharedPreferences()
                .unregisterOnSharedPreferenceChangeListener(this);
    }
    
    public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
        if (key.equals("preset_call_center")) {
            EditTextPreference callCenterNumberPref = (EditTextPreference) findPreference("call_center_number");
            callCenterNumberPref.setText(sharedPreferences.getString(key, null));
        } else if (key.equals("enable_service")) {
    		boolean enabled = sharedPreferences.getBoolean(key, true); 
        	findPreference("call_center_number").setEnabled(enabled);
        	findPreference("preset_call_center").setEnabled(enabled);
        	findPreference("target_phones").setEnabled(enabled);
        	findPreference("dial_delay").setEnabled(enabled);
        }
    }
}
