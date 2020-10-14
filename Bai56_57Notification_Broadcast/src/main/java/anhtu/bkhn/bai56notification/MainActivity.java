package anhtu.bkhn.bai56notification;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.Uri;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btOn, btOff;
    int notificationID;
    BroadcastReceiver broadcastReceiver;
    TextView tv, tvState;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addComponents();
        addEvents();
    }

    @Override
    protected void onResume() {
        super.onResume();
        //check xem có internet hay ko trong hàm này:
        IntentFilter intentFilter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        registerReceiver(broadcastReceiver, intentFilter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(broadcastReceiver != null) unregisterReceiver(broadcastReceiver);
    }

    private void addComponents() {
        tv = (TextView) findViewById(R.id.tv);
        tv.setVisibility(View.INVISIBLE);
        tvState= (TextView) findViewById(R.id.tvNetworkState);
        btOff= (Button) findViewById(R.id.btoff);
        btOn= (Button) findViewById(R.id.btOn);

        broadcastReceiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(CONNECTIVITY_SERVICE);
                if(connectivityManager.getActiveNetworkInfo() != null) {
                    tv.setVisibility(View.VISIBLE);
                    tvState.setText("internet is connected!");
                    Toast.makeText(context, "internet is connected!", Toast.LENGTH_SHORT).show();
                } else {
                    tv.setVisibility(View.INVISIBLE);
                    tvState.setText("no internet connection!");
                    Toast.makeText(context, "no internet connection!", Toast.LENGTH_SHORT).show();
                }
            }
        };
    }

    private void addEvents() {
        btOn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNotification();
            }
        });
        btOff.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                turnOffNotification();
            }
        });
    }

    private void turnOffNotification() {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancel(notificationID);
    }

    private void createNotification() {
        NotificationCompat.Builder nBuilder = new NotificationCompat.Builder(this)
                .setSmallIcon(R.drawable.like)
                .setContentTitle("you have new notification")
                .setContentText("this is an example of notification");

        Intent resultIntent = new Intent(this, UpdateActivity.class);   //khi click vào notification thì mở activity UpdateActivity
        PendingIntent resultPendingIntent = PendingIntent.getActivity(MainActivity.this, 0, resultIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        nBuilder.setContentIntent(resultPendingIntent);
//        Uri uri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
//        nBuilder.setSound(uri);
        Uri uri2 = Uri.parse("android.resource://"+getPackageName()+"/"+R.raw.one_call_away);
        nBuilder.setSound(uri2);
        notificationID = 113;
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(113, nBuilder.build());
    }

    public void btOffAll(View view) {
        NotificationManager notificationManager = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.cancelAll();
    }
}
