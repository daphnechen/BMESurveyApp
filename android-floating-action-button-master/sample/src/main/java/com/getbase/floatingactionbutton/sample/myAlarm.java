package com.getbase.floatingactionbutton.sample;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by Yeshy on 4/13/2016.
 */
public class myAlarm extends BroadcastReceiver
{

    @Override
    public void onReceive(Context context, Intent intent) {
        Log.d("Alarm Recieved!", "YAAAY");
        Intent i = new Intent(context, InviteService.class);
        context.startService(i);
    }
}