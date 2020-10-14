package com.bkhn.anhtu.servicedemo;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by anhtu on 15/03/2017.
 */

public class ServiceDriver extends Activity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //invoking the service:
        Intent serviceIntent=new Intent(this, ServiceDemo.class);
        startService(serviceIntent);

        Toast.makeText(this, "Service is starting", Toast.LENGTH_SHORT).show();
    }
}
