package com.dhbk.anhtu.tabhostdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TabHost;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    Button btSave;
    EditText txtName, txtAddr;
    TabHost tabHost;
    ListView lvStudent;
    StudentAdapter adapter;
    List<Student> arrStudent=new ArrayList<Student>();  //chú ý ko có dòng này thì chạy app sẽ bị stop, ko chạy đc (lỗi)

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addComponents();
        addEvents();
    }

    private void addComponents() {
        ///tab host:
        tabHost=(TabHost)findViewById(android.R.id.tabhost);
        tabHost.setup();

        TabHost.TabSpec tab1= tabHost.newTabSpec("t1");
        tab1.setIndicator("", getResources().getDrawable(R.drawable.add));
        tab1.setContent(R.id.tab1);
        tabHost.addTab(tab1);// add tab to tabHost

        TabHost.TabSpec tab2= tabHost.newTabSpec("t2");
        tab2.setIndicator("", getResources().getDrawable(R.drawable.history));
        tab2.setContent(R.id.tab2);
        tabHost.addTab(tab2);// add tab to tabHost

        txtName=(EditText)findViewById(R.id.txtName);
        txtAddr=(EditText) findViewById(R.id.txtAddr);
        btSave=(Button)findViewById(R.id.btSave);
        lvStudent=(ListView)findViewById(R.id.lvList);

        ///list view:
        adapter=new StudentAdapter(MainActivity.this, R.layout.row, arrStudent);
        lvStudent.setAdapter(adapter);
    }

    private void addEvents() {
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String ten=txtName.getText().toString().trim();
                String diachi=txtAddr.getText().toString().trim();

                Student s=new Student();
                s.setName(ten);
                s.setAddr(diachi);

                adapter.add(s);

                txtName.setText("");
                txtAddr.setText("");

                Toast.makeText(MainActivity.this, "Saved", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
