package com.dhbk.anhtu.lifecycle;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

//GOAL: show the following life-cycle events in action

//protected void onCreate(Bundle savedInstanceState);
//protected void onStart();
//protected void onRestart();
//protected void onResume();
//protected void onPause();
//protected void onStop();
//protected void onDestroy();

public class MainActivity extends AppCompatActivity {

    private LinearLayout myScreen;
    private TextView txtToDo;
    private EditText txtColorSelect;
    private Button btnFinish;
    private static String MYPREFSID; //used in part 6
    private static int actMode; //used in part 6

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        myScreen=(LinearLayout)findViewById(R.id.myScreen);
        txtToDo=(TextView)findViewById(R.id.txtToDo);
        txtColorSelect=(EditText)findViewById(R.id.txtColorSelect);
        btnFinish=(Button)findViewById(R.id.btnFinish);

        String msg = "Instructions: \n "
                + "0. New instance (onCreate, onStart, onResume) \n "
                + "1. Back Arrow (onPause, onStop, onDestroy) \n "
                + "2. Finish (onPause, onStop, onDestroy) \n "
                + "3. Home (onPause, onStop) \n "
                + "4. After 3 > App Tab > re-execute current app \n "
                + " (onRestart, onStart, onResume) \n "
                + "5. Run DDMS > Receive a phone call or SMS \n "
                + " (onRestart, onStart, onResume) \n "
                + "6. Enter some data - repeat steps 1-5 \n ";
        txtToDo.setText(msg);

        txtColorSelect.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                //Toast.makeText(MainActivity.this, "beforeTextChanged", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                //Toast.makeText(MainActivity.this, "onTextChanged", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void afterTextChanged(Editable s) {
                changeBackgroundColor(s.toString());
            }
        });

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        Toast.makeText(getApplicationContext(), "onCreate", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveDataFromCurrentState();
        Toast.makeText(this, "onPause", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(this, "onRestart", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(this, "onResume", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        updateFromSavedState();
        Toast.makeText(this, "onStart", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }
    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(this, "onStop", Toast.LENGTH_SHORT).show();
    }

    protected void saveDataFromCurrentState() {
        SharedPreferences myPrefs = getSharedPreferences(MYPREFSID, actMode);
        SharedPreferences.Editor myEditor = myPrefs.edit();
        myEditor.putString("myBkColor", txtColorSelect.getText().toString());
        myEditor.commit();
    } // saveDataFromCurrentState

    protected void updateFromSavedState() {
        SharedPreferences myPrefs = getSharedPreferences(MYPREFSID, actMode);
        if ((myPrefs != null) && (myPrefs.contains("myBkColor"))) {
            String theChosenColor = myPrefs.getString("myBkColor","");
            txtColorSelect.setText(theChosenColor);
            changeBackgroundColor(theChosenColor);
        }
    } // updateFromSavedState
    protected void clearMyPreferences() {
        SharedPreferences myPrefs = getSharedPreferences(MYPREFSID, actMode);
        SharedPreferences.Editor myEditor = myPrefs.edit();
        myEditor.clear();
        myEditor.commit();
    } // clearMyPreferences


    private void changeBackgroundColor (String theChosenColor){
        if (theChosenColor.contains("red"))
            myScreen.setBackgroundColor(0xffff0000);
        else if (theChosenColor.contains("green"))
            myScreen.setBackgroundColor(0xff00ff00);
        else if (theChosenColor.contains("blue"))
            myScreen.setBackgroundColor(0xff0000ff);
        else {
            //reseting user preferences
            clearMyPreferences();
            myScreen.setBackgroundColor(0x00000000);
        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(getBaseContext(),
                "onRestoreInstanceState ...BUNDLING",
                Toast.LENGTH_LONG).show();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        Toast.makeText(getBaseContext(),
                "onSaveInstanceState ...BUNDLING",
                Toast.LENGTH_LONG).show();
    } // onSaveInstanceState
}
