package com.lm.sample.broadcast;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lm.log.LmLog;

public class SampleBroadcastReceiver extends BroadcastReceiver {
    public static final String action = "com.lm.sample.broadcast.SampleBroadcastReceiver";
    private static int count = 0;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (action.equals(intent.getAction())) {
            LmLog.info("接收到广播" + (count++));
        }
    }
}
