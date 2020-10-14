package com.bkhn.anhtu.assetsandsharedpreference;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    TextView txtFont;
    ListView lvFont;
    ArrayList<String> listFont;
    ArrayAdapter<String> adapterFont;

    String storedName = "FontsState"; //we will get data via this name
    String FONT_KEY = "thisIsTheFontKey";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addComponents();
        addEvents();

        ///interact with assets:
        AssetManager assetManager = getAssets(); //get the whole resources in assets folder
        try {
            String [] arrayFontName = assetManager.list("fonts");  //trả về 1 mảng gồm tên các file, tài nguyên lấy từ thư mục fonts
            listFont.addAll(Arrays.asList(arrayFontName));  //transmit an array into a list
            adapterFont.notifyDataSetChanged();

            //get data that saved before the previous openning:
            SharedPreferences sharedPreferences = getSharedPreferences(storedName, MODE_PRIVATE);
            String font = sharedPreferences.getString(FONT_KEY, "");
            if(!font.equals("")) {
                Typeface typeface = Typeface.createFromAsset(getAssets(), font);
                txtFont.setTypeface(typeface);
            }
        } catch (IOException e) {
            //e.printStackTrace();  //if folder doesn't exist
            Log.e("LỖI FONT", e.toString());
        }


    }

    private void addComponents() {
        txtFont = (TextView)findViewById(R.id.txtFont);
        lvFont = (ListView) findViewById(R.id.lvFont);
        listFont = new ArrayList<>();
        adapterFont = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listFont);
        lvFont.setAdapter(adapterFont);
    }

    private void addEvents() {
        lvFont.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Typeface typeface = Typeface.createFromAsset(getAssets(), "fonts/"+listFont.get(position));
                txtFont.setTypeface(typeface);

                //save font after quit app:
                SharedPreferences sharedPreferences = getSharedPreferences(storedName, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                String font_value = "fonts/"+listFont.get(position);

                editor.putString(FONT_KEY, font_value);
                editor.commit(); //xac nhan save xuong file xml
            }
        });

    }

    public void btSwitchEvent(View view) {
        Intent intent = new Intent(MainActivity.this, LoginActivity.class);
        startActivity(intent);
    }
}
