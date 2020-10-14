package com.dhbkhn.anhtu.labai;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Random;

public class MainActivity extends AppCompatActivity {

    ImageView ivLabai;
    Button btLabai;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivLabai = (ImageView) findViewById(R.id.imageViewLaBai);

        btLabai = (Button) findViewById(R.id.buttonLaBai);
        btLabai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> arrLabai = new ArrayList<Integer>();
                arrLabai.add(R.drawable.c1);
                arrLabai.add(R.drawable.c2);
                arrLabai.add(R.drawable.c3);
                arrLabai.add(R.drawable.c4);
                arrLabai.add(R.drawable.c5);

                Random rd = new Random();
                int a = rd.nextInt(5);

                ivLabai.setImageResource(arrLabai.get(a));
            }
        });
    }
}
