package com.dhbk.anhtu.weather;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu mn) {  //hàm này tạo ra menu
        getMenuInflater().inflate(R.menu.menu, mn);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {  //tạo sự kiện cho menu

        switch (item.getItemId()) {
            case R.id.about:
                //do st related to about:
                Intent about=new Intent("anhtu.weather.ABOUT");
                startActivity(about);
                break;
            case R.id.setting:
                //do st related to setting:
                Intent setting=new Intent("anhtu.weather.SETTING");
                startActivity(setting);
                break;
        }
        return false;
    }
}
