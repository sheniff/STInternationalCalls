package com.sheniff.stinternationalcalls;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;

public class OutgoingCallIntentReceiver extends BroadcastReceiver {
	
    @Override
    public void onReceive(Context context, Intent intent) {
		// Try to read the phone number from previous receivers.
		String phoneNumber = getResultData();
		
		if (phoneNumber == null) {
			// We could not find any previous data. Use the original phone number in this case.
			phoneNumber = intent.getStringExtra(Intent.EXTRA_PHONE_NUMBER);
		}
		
		if(useService(phoneNumber, context)){
			if( phoneNumber.indexOf(',') == -1 ){
				// Showing context panel
				setResultData(null);
				Bundle b = new Bundle();
				b.putString("number", phoneNumber);
				Intent i = new Intent(context, ContextActivity.class);
				i.putExtras(b);
				i.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				context.startActivity(i);			
			} else if( phoneNumber.indexOf(',') == phoneNumber.length()-1 ) {
				// Selecting common call
				setResultData(phoneNumber.substring(0, phoneNumber.length()-1));
			}
		}
    }

    private boolean useService(String number, Context context){
		SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(context);
    	boolean ret = true;

    	if(!prefs.getBoolean("enable_service", true))
    		return false;
    	
    	switch ( Integer.parseInt(prefs.getString("target_phones", "1")) ) {
			case 0:
				ret = true;
			break;
	
			case 1:
				ret = !number.matches("\\A((\\+|00)?1)?\\d{10}\\Z");
			break;
	
			case 2:
				ret = (number.indexOf('+') == 0 || number.indexOf("00") == 0);
			break;
	
			default:
			break;
		}
    	
    	return ret;
    }
}
