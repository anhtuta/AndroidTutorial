package com.dhbk.anhtu.bai30tab;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    EditText txtA, txtB;
    Button btAdd;
    ListView lvHistory;
    ArrayList<String> listAdd=new ArrayList<>();
    ArrayAdapter<String> adapterAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addComponents();
        addEvents();
    }

    private void addComponents() {
        //create tabHost firstly:
        TabHost tabHost=(TabHost)findViewById(R.id.tabHost);
        tabHost.setup();

        //then create 2 tabs:
        TabHost.TabSpec tab1= tabHost.newTabSpec("t1");
        tab1.setIndicator("", getResources().getDrawable(R.drawable.add));
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);// add tab to tabHost:

        TabHost.TabSpec tab2= tabHost.newTabSpec("t2");
        tab2.setIndicator("", getResources().getDrawable(R.drawable.history));
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);

        /////add other components:
        txtA=(EditText)findViewById(R.id.txtA);
        txtB=(EditText)findViewById(R.id.txtB);
        btAdd=(Button) findViewById(R.id.btAdd);

        lvHistory=(ListView) findViewById(R.id.lvHistory);
        adapterAdd=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listAdd);
        lvHistory.setAdapter(adapterAdd);
    }

    private void addEvents() {
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int a=Integer.parseInt(txtA.getText().toString().trim());
                int b=Integer.parseInt(txtB.getText().toString().trim());

                String kq=a+" + "+ b +" = "+(a+b);
                listAdd.add(kq);
                adapterAdd.notifyDataSetChanged();
                txtA.setText("");
                txtB.setText("");
            }
        });
    }
}
