package com.bkhn.anhtu.servicedemo;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class ServiceDemo extends Service implements Runnable {

    private int counter=0;
    @Override
    public void onCreate() {
        super.onCreate();

    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Log.i("service1", "service1 firing : # " + counter++);
                Thread.sleep(10000); //this is where the heavy-duty computing occurs
            } catch (Exception ee) {
                Log.e("service1", ee.getMessage());
            }
        }
    }
}
