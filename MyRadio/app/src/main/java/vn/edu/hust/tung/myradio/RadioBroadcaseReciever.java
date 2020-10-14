package vn.edu.hust.tung.myradio;

import android.app.Activity;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.media.RingtoneManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by anhtu on 16/04/2017.
 */

public class RadioBroadcaseReciever extends BroadcastReceiver {

    MediaPlayer mp;
    int notificationID;
    static boolean isTimeUp = false;

    @Override
    public void onReceive(Context context, Intent intent) {
        Toast.makeText(context, "It's time for listening the scheduled radio channel!", Toast.LENGTH_SHORT).show();

        createNotification(context);
        isTimeUp = true;
    }

    private void createNotification(Context context) {
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(R.drawable.like)
                .setContentTitle("Hey you. It's time to listen to radio")
                .setContentText("Click here to open MyRadio and listen");

        Intent resultIntent = new Intent(context, MainActivity.class);   //khi click vào notification thì mở activity UpdateActivity
        PendingIntent resultPendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        nBuilder.setContentIntent(resultPendingIntent);
        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
        nBuilder.setSound(uri);
        //Neu muon cai nhac thong bao bang bai hat:
//        Uri uri2 = Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.one_call_away);
//        nBuilder.setSound(uri2);
        notificationID = 113;
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(113, nBuilder.build());
    }
}
