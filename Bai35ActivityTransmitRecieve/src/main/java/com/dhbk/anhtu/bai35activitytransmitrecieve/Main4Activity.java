package com.dhbk.anhtu.bai35activitytransmitrecieve;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class Main4Activity extends AppCompatActivity {

    TextView txtKq3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        txtKq3=(TextView)findViewById(R.id.txtKq3);
        Intent intent=getIntent();
        Bundle bundle=intent.getBundleExtra("MY_BUNDLE");
        txtKq3.setText(bundle.getInt("A")+"\n"+bundle.getDouble("T")+"\n"+(Product)bundle.getSerializable("SANPHAM"));
    }
}
