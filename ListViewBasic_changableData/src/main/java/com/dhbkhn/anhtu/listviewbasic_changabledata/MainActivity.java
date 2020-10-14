package com.dhbkhn.anhtu.listviewbasic_changabledata;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.StreamHandler;

public class MainActivity extends AppCompatActivity {

    //dữ liệu thay đổi thì phải dùng arraylist, còn cố định thì dùng array thôi
    ArrayList<String> arrName;
    ArrayAdapter<String> adapterName;
    ListView lvName;
    EditText txtName;
    Button btSave;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addComponets();
        addEvents();
    }

    private void addComponets() {
        arrName=new ArrayList<String>(); //nguồn của adapter
        adapterName=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, arrName); //tạo adapter từ nguồn

        lvName= (ListView) findViewById(R.id.lvName);
        lvName.setAdapter(adapterName); //tạo listview từ adapter

        txtName=(EditText)findViewById(R.id.txtName);

        btSave=(Button)findViewById(R.id.btSave);
    }

    private void addEvents() {
        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String name=txtName.getText().toString();
                arrName.add(name);
                adapterName.notifyDataSetChanged(); //yêu cầu refresh dữ liệu : ra lệnh cho listview cập nhập dữ liệu
                txtName.setText("");
                txtName.requestFocus();
            }
        });
    }
}
