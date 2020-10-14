package bkhn.anhtu.contentproviderdemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void btDocDanhBaEvent(View view) {
        Intent intent = new Intent(MainActivity.this, DanhBaActivity.class);
        startActivity(intent);
    }

    public void btDocTinNhanEvent(View view) {
        Intent intent = new Intent(MainActivity.this, TinNhanActivity.class);
        startActivity(intent);
    }
}
