package com.dciets.androidutils.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.RemoteInput;
import android.util.Log;

import com.dciets.androidutils.utils.NotificationUtils;

public class ReplyVoiceReceiver extends BroadcastReceiver {
    public static final String EXTRA_VOICE_REPLY = "extra_voice_reply";

    public void onReceive(Context context, Intent intent) {
        Log.i("ReplyVoiceActivity", "intent=" + intent);
        if (intent == null)
            return;

        String relevantInformation = intent.getStringExtra("relevantInformation");
        CharSequence text = getMessageText(intent);

        if(text != null) {
            //Do something useful with the text
        }
        NotificationUtils.cancelAll(context);
    }

    private CharSequence getMessageText(Intent intent) {
        Bundle remoteInput = RemoteInput.getResultsFromIntent(intent);
        if (remoteInput != null) {
            return remoteInput.getCharSequence(EXTRA_VOICE_REPLY);
        }
        return null;
    }
}