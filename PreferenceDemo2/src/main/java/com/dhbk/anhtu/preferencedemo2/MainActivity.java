package com.dhbk.anhtu.preferencedemo2;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {
    SharedPreferences prefs;
    String SavedNameKey="com.dhbk.anhtu.preferencedemo2";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        prefs = this.getSharedPreferences("com.dhbk.anhtu.preferencedemo2", Context.MODE_PRIVATE);
    }

    public void SaveData(View v)
    {
        EditText txtName= (EditText) findViewById(R.id.editName);
        prefs.edit().putString(SavedNameKey, txtName.getText().toString()).commit();
        TextView lblName=(TextView) findViewById(R.id.lblName);
        lblName.setText("Name :"+txtName.getText().toString());
        final Intent intent=new Intent(this,DisplayMemory.class);
        new AlertDialog.Builder(this)
                .setTitle("Data Saved")
                .setMessage("Are you sure you want to move to next Page to Test Sahred Preference approach?")
                                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
                                        startActivity(intent);
                                    }
                                })
                                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                                    public void onClick(DialogInterface dialog, int which) {
// do nothing
                                    }
                                })
                                .show();
    } }