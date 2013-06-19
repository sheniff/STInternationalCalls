package com.sheniff.stinternationalcalls;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class ContextActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        final String phoneNumber = (String) getIntent().getSerializableExtra("number");
        
        AlertDialog LDialog = new AlertDialog.Builder(this)
	        .setTitle(R.string.app_name)
	        .setMessage(getResources().getString(R.string.dialog_info_number) + " " + phoneNumber +". " + getResources().getString(R.string.dialog_question))
	        .setPositiveButton(R.string.dialog_action_ok, new DialogInterface.OnClickListener() {
	        	@Override
	        	public void onClick(DialogInterface dialog, int id) {
	        		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(ContextActivity.this);
					
	        		String centerNumber = prefs.getString("call_center_number","");
					String dtmfTones = phoneNumber+"#";
					int delayPref = Integer.parseInt( prefs.getString("dial_delay", "4") );
					String delay = "";
					for(int i = 0; i < delayPref; i++)
						delay += ",";
					
					Intent i = new Intent("android.intent.action.CALL", Uri.parse("tel://" + centerNumber + delay + dtmfTones));
					startActivity(i);
					finish();
       			}
	        })
	        .setNegativeButton(R.string.dialog_action_cancel, new DialogInterface.OnClickListener() {
	        	@Override
	        	public void onClick(DialogInterface dialog, int id) {
					Intent i = new Intent("android.intent.action.CALL", Uri.parse("tel://" + phoneNumber + ","));
					startActivity(i);
					finish();
       			}
	        })
	        .create();
        LDialog.show();
    }
	
}
