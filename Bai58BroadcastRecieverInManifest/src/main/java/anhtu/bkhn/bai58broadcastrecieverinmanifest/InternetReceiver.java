package anhtu.bkhn.bai58broadcastrecieverinmanifest;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

/**
 * Created by AnhTu on 12/04/2017.
 */

public class InternetReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if(connectivityManager.getActiveNetworkInfo() != null) {  //nếu có internet thì làm j đó...
            doSomethingsWhenInternetIsAvalable(context);
            Toast.makeText(context, "internet is available", Toast.LENGTH_SHORT).show();
        }

    }

    private void doSomethingsWhenInternetIsAvalable(Context context) {
        //step1:
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(context)
                .setSmallIcon(android.R.drawable.ic_dialog_alert)
                .setContentText("this is the context text")
                .setContentTitle("this is the context title");
        //step2:
        Intent resultIntent = new Intent(context, MainActivity.class);
        //step3:
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        nBuilder.setContentIntent(pendingIntent);
        //step4:
        Uri uri = Uri.parse("android.resource://"+context.getPackageName()+"/"+R.raw.one_call_away);
        nBuilder.setSound(uri);

        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(1995, nBuilder.build());
    }
}
