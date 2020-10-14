package com.dhbk.anhtu.spinnerdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    EditText txtName, txtSickNumber;
    Spinner spDate;
    Button btSave;
    ListView lvEmployee;

    ArrayList<String> listDate;
    ArrayAdapter<String> adapterDate;

    List<Employee> listEmployee=new ArrayList<Employee>();
    ArrayAdapter<Employee> adapterLv=null;

    String []date={"Thứ 2", "Thứ 3", "Thứ 4", "Thứ 5", "Thứ 6", "Thứ 7", "Chủ Nhật"};
    int lastPosition=-1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addComponents();
        addEvents();
    }

    private void addComponents() {
        txtName=(EditText)findViewById(R.id.txtName);
        txtSickNumber=(EditText) findViewById(R.id.txtSickNumber);
        spDate=(Spinner)findViewById(R.id.spDate);
        btSave=(Button)findViewById(R.id.btSave);
        lvEmployee=(ListView)findViewById(R.id.lvEmployee);

        //cho spinner:
        listDate=new ArrayList<String>();
        for(int i=0; i<date.length; i++) listDate.add(date[i]); //tạo tài nguyên cho nguồn DL

        adapterDate=new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_spinner_item, listDate);
        adapterDate.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);  //tạo tài nguyên cho adapter

        spDate.setAdapter(adapterDate);

        //cho listview:
        //ko tạo tài nguyên cho nguồn của adapter của listview vì khi ấn save thì mới thêm 1 đối tượng vào nguồn đó đc
        adapterLv=new ArrayAdapter<Employee>(MainActivity.this, android.R.layout.simple_list_item_1, listEmployee);
        lvEmployee.setAdapter(adapterLv);
    }

    private void addEvents() {
        spDate.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                lastPosition=position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                lastPosition=-1;
            }
        });

        btSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(lastPosition==-1)
                    Toast.makeText(MainActivity.this, "Bạn phải chọn ngày trước", Toast.LENGTH_SHORT).show();
                else {
                    String ten=txtName.getText().toString().trim();
                    String thu=txtSickNumber.getText().toString();
                    if((ten.equals("")) || (thu.equals(""))) Toast.makeText(MainActivity.this, "Bạn chưa điền đủ thông tin", Toast.LENGTH_SHORT).show();
                    else {
                        Employee em = new Employee();
                        em.setName(ten);
                        em.setDieDate(date[lastPosition]);
                        em.setSickNumber(Integer.parseInt(thu));

                        Toast.makeText(MainActivity.this, em.toString(), Toast.LENGTH_SHORT).show();

                        adapterLv.add(em);
                    }
                }
            }
        });
    }
}
