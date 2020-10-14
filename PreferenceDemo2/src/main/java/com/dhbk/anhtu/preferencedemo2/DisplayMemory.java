package com.dhbk.anhtu.preferencedemo2;

/**
 * Created by AnhTu on 15/03/2017.
 */


import android.os.Bundle;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Menu;
import android.widget.TextView;

public class DisplayMemory extends Activity {
    SharedPreferences prefs;
    String SavedNameKey="com.dhbk.anhtu.preferencedemo2.savedName";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//setContentView(R.layout.activity_display_memory);
        Intent intent = getIntent();
        prefs = this.getSharedPreferences("com.dhbk.anhtu.preferencedemo2", Context.MODE_PRIVATE);
// Create the text view
        TextView textView = new TextView(this);
        textView.setTextSize(40);
        textView.setText("Data Retrieved from Shared Preferences: "+prefs.getString(SavedNameKey, ""));
// Set the text view as the activity layout
        setContentView(textView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
// Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.display_memory, menu);
        return true;
    }
}