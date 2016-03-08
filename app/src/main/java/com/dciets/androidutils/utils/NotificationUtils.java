package com.dciets.androidutils.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.provider.Settings;
import android.support.v4.app.NotificationCompat;
import android.support.v4.app.RemoteInput;
import android.text.Html;

import com.dciets.androidutils.R;
import com.dciets.androidutils.receiver.ReplyVoiceReceiver;

public class NotificationUtils {

    public static void notify(final Context context){
        Intent resultVoiceReplyIntent = new Intent(context, ReplyVoiceReceiver.class);
        resultVoiceReplyIntent.putExtra("extra", "extra");
        PendingIntent resultVoiceReplyPendingIntent =
                PendingIntent.getBroadcast(
                        context,
                        0,
                        resultVoiceReplyIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        RemoteInput remoteInput = new RemoteInput.Builder(ReplyVoiceReceiver.EXTRA_VOICE_REPLY)
                .setLabel("Reply")
                .build();

        // Create the reply action and add the remote input
        NotificationCompat.Action action =
                new NotificationCompat.Action.Builder(android.R.drawable.ic_btn_speak_now,
                        "Reply", resultVoiceReplyPendingIntent)
                        .addRemoteInput(remoteInput)
                        .build();


        String history = "";

        history += "<br/><br/>...";

        Notification secondPageNotification =
                new NotificationCompat.Builder(context)
                        .setContentText(Html.fromHtml(history))
                        .build();

        String content = "This is the content of my notification";

        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(context)
                        .setLargeIcon(BitmapFactory.decodeResource(context.getResources(), android.R.drawable.ic_dialog_email))
                        .setSmallIcon(android.R.drawable.btn_minus)
                        .setContentTitle("My title")
                        .setContentText(content)
                        .setColor(Color.DKGRAY)
                        .setVibrate(new long[] { 0, 500})
                        .setSound(Settings.System.DEFAULT_NOTIFICATION_URI)
                        .extend(new NotificationCompat.WearableExtender().addAction(action).addPage(secondPageNotification))
                        .setLights(context.getResources().getColor(R.color.main_color), 1000, 3000)
                        .setAutoCancel(true);

        mBuilder.setContentInfo("Extra text");

        Intent resultIntent = new Intent(context, Settings.class); //Change Settings.class for an actual activity
        resultIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        resultIntent.putExtra("extra", "extra");
        PendingIntent resultPendingIntent =
                PendingIntent.getActivity(
                        context,
                        0,
                        resultIntent,
                        PendingIntent.FLAG_UPDATE_CURRENT
                );

        mBuilder.setContentIntent(resultPendingIntent);

        // Sets an ID for the notification
        int mNotificationId = 001;
        // Gets an instance of the NotificationManager service
        NotificationManager mNotifyMgr =
                (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        // Builds the notification and issues it.
        mNotifyMgr.notify(mNotificationId, mBuilder.build());
    }

    public static void cancelAll(final Context context){
        NotificationManager notifManager= (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notifManager.cancelAll();
    }
}