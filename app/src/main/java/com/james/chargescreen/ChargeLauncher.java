package com.james.chargescreen;


import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

public class ChargeLauncher extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        final SharedPreferences settings = context.getSharedPreferences("com.james.chargescreen", 0);
        if(settings.getBoolean("isEnabled", false)) {
            //start the app
            new ChargeActivity().finish();
            Intent i = new Intent(Intent.ACTION_MAIN);
            i.addCategory(Intent.CATEGORY_HOME);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }
 }
