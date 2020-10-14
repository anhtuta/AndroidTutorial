package com.dhbkhn.anhtu.listviewbasic2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    ListView lvName;
    ArrayList<String> arrName;
    ArrayAdapter<String> adapterName;
    String [] stringName = {"Vegeta", "Son Goku", "Son Go Han", "trunks", "Ma bu", "Krilin", "Picollo"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addComponets();
        addEvents();
    }

    private void addComponets() {
        arrName=new ArrayList<String>();
//        arrName.add("Vegeta");
//        arrName.add("Son Goku");
//        arrName.add("Son Go Han");
//        arrName.add("trunks");
//        arrName.add("Ma bu");
//        arrName.add("Krilin");
//        arrName.add("Picollo");

        for(int i=0; i<stringName.length; i++) arrName.add(stringName[i]);

        adapterName = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrName);

        lvName=(ListView) findViewById(R.id.lvName);
        lvName.setAdapter(adapterName);
    }

    private void addEvents() {
        lvName.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this, "Your name is: "+stringName[position], Toast.LENGTH_SHORT).show();
            }
        });
    }

}
