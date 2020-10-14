package anhtu.bkhn.bai57broadcastreciever;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    TextView txtHello;

    BroadcastReceiver wifiReciever = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            if(connectivityManager.getActiveNetworkInfo()!=null) {
                txtHello.setVisibility(View.VISIBLE);
                Toast.makeText(context, "internet is avalable", Toast.LENGTH_SHORT).show();
            } else {
                txtHello.setVisibility(View.INVISIBLE);
                Toast.makeText(context, "internet is unavalable! sorry...", Toast.LENGTH_SHORT).show();
            }

        }
    };

    @Override
    protected void onResume() {
        super.onResume();
        IntentFilter filter = new IntentFilter(ConnectivityManager.CONNECTIVITY_ACTION);
        //more action: using: filter.addAction("put name action you want in here...");
        registerReceiver(wifiReciever, filter);
    }

    @Override
    protected void onPause() {
        super.onPause();
        //hủy sự kiện lắng nghe wifi ở đây:
        if(wifiReciever!=null) unregisterReceiver(wifiReciever);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtHello= (TextView) findViewById(R.id.txtHello);
        txtHello.setVisibility(View.INVISIBLE);
    }
}
