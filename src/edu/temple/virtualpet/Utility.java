package edu.temple.virtualpet;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class Utility {
	
	public static boolean isNetworkAvailable(Activity act) {
		try {
		     ConnectivityManager cm = (ConnectivityManager) act.getSystemService(Context.CONNECTIVITY_SERVICE);
		     NetworkInfo ni = cm.getNetworkInfo(0);
		     if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED)
		    	 return true;
		     ni = cm.getNetworkInfo(1);
		     if (ni != null && ni.getState() == NetworkInfo.State.CONNECTED)
		    	 return true;
		}
		catch(Exception e) {
			e.printStackTrace();
			return false;
		}
		return false;
	}
}
