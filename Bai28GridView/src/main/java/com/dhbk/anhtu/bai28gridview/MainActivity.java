package com.dhbk.anhtu.bai28gridview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    GridView gvImage;
    ArrayList<Integer> listImage;
    ImageAdapter adapterImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addComponents();
        addEvents();
    }

    private void addComponents() {
        gvImage=(GridView)findViewById(R.id.gvImage);

        //create source for adapter:
        listImage=new ArrayList<>();
        listImage.add(R.drawable.d1);
        listImage.add(R.drawable.d2);
        listImage.add(R.drawable.d3);
        listImage.add(R.drawable.d4);
        listImage.add(R.drawable.d5);
        listImage.add(R.drawable.d6);
        listImage.add(R.drawable.d7);
        listImage.add(R.drawable.d8);
        listImage.add(R.drawable.d9);
        listImage.add(R.drawable.d10);
        listImage.add(R.drawable.dj);
        listImage.add(R.drawable.dq);
        listImage.add(R.drawable.dk);

        listImage.add(R.drawable.h1);
        listImage.add(R.drawable.h2);
        listImage.add(R.drawable.h3);
        listImage.add(R.drawable.h4);
        listImage.add(R.drawable.h5);
        listImage.add(R.drawable.h6);
        listImage.add(R.drawable.h7);
        listImage.add(R.drawable.h8);
        listImage.add(R.drawable.h9);
        listImage.add(R.drawable.h10);
        listImage.add(R.drawable.hj);
        listImage.add(R.drawable.hq);
        listImage.add(R.drawable.hk);

        listImage.add(R.drawable.s1);
        listImage.add(R.drawable.s2);
        listImage.add(R.drawable.s3);
        listImage.add(R.drawable.s4);
        listImage.add(R.drawable.s5);
        listImage.add(R.drawable.s6);
        listImage.add(R.drawable.s7);
        listImage.add(R.drawable.s8);
        listImage.add(R.drawable.s9);
        listImage.add(R.drawable.s10);
        listImage.add(R.drawable.sj);
        listImage.add(R.drawable.sq);
        listImage.add(R.drawable.sk);

        //create adapter:
        adapterImage=new ImageAdapter(MainActivity.this, R.layout.cell, listImage);
        //set adapter:
        gvImage.setAdapter(adapterImage);
    }

    private void addEvents() {

    }
}
