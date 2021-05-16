package com.utkarsh.shiftalarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class MyReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        Bundle bundle=intent.getExtras();
        if(intent.getAction().equalsIgnoreCase("com.utkarsh.alarm"))
        {
            Toast.makeText(context.getApplicationContext(), "Success "+bundle.getString("My message"),
                    Toast.LENGTH_LONG).show();
        }
    }
}