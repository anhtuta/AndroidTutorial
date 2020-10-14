package com.dhbk.anhtu.bai35activitytransmitrecieve;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main3Activity extends AppCompatActivity {

    TextView txtKq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);

        txtKq=(TextView)findViewById(R.id.txtKq);

        Intent intent=getIntent(); //lấy thông tin intent mở nó

        boolean kieuBoolean=intent.getBooleanExtra("KIEU_BOOLEAN", false); //
        char kieuChar=intent.getCharExtra("KIEU_CHAR", (char) 0); //nếu ko tìm thấy thì trả về 0
        int kieuInt=intent.getIntExtra("KIEU_INT", 0);
        double kieuDouble=intent.getDoubleExtra("KIEU_DOUBLE", 0.0);
        String kieuString=intent.getStringExtra("KIEU_STRING");

        Student st= (Student) intent.getSerializableExtra("SINHVIEN");
        txtKq.setText(kieuBoolean+"\n"+kieuChar+"\n"+kieuInt+"\n"+kieuDouble+"\n"+kieuString+"\n"+st.toString());

    }
}
