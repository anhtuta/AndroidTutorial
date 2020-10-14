package com.dhbk.anhtu.bai27autocomplete;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btSave;

    AutoCompleteTextView autoTP; //giống ListView
    String[]arrTP;
    ArrayAdapter<String>adapterTP;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addComponents();
        addEvents();
    }

    private void addComponents() {
        autoTP=(AutoCompleteTextView)findViewById(R.id.autotxtTP);
        btSave=(Button)findViewById(R.id.btSave);

        arrTP=getResources().getStringArray(R.array.arrTP);
        adapterTP=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrTP);
        autoTP.setAdapter(adapterTP);
    }

    private void addEvents() {
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String s=autoTP.getText().toString();
                Toast.makeText(MainActivity.this, "Bạn vừa chọn thành phố: "+s, Toast.LENGTH_SHORT).show();
            }
        });
    }
}
