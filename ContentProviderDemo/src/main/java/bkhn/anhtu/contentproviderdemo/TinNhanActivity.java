package bkhn.anhtu.contentproviderdemo;

import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class TinNhanActivity extends AppCompatActivity {

    ListView lvTinNhan;
    ArrayList<String> listTinNhan;
    ArrayAdapter<String> adapterTinNhan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tin_nhan);

        addComponents();
        addEvents();

        readAllMessagesFromDevice();
    }

    private void addComponents() {
        lvTinNhan = (ListView) findViewById(R.id.lvTinNhan);
        listTinNhan = new ArrayList<>();
        adapterTinNhan = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listTinNhan);
        lvTinNhan.setAdapter(adapterTinNhan);
    }

    private void addEvents() {

    }

    private void readAllMessagesFromDevice() {
        Uri uri = Uri.parse("content://sms/inbox");
        Cursor cursor = getContentResolver().query(uri, null,null,null, null);
        listTinNhan.clear();
        while(cursor.moveToNext()) {
            //B1: lay index:
            int indexPhoneNumber = cursor.getColumnIndex("address");
            int indexTimeStamp = cursor.getColumnIndex("date");
            int indexBody = cursor.getColumnIndex("body");

            //B2: lay dl tu index:
            String phonenumber=cursor.getString( indexPhoneNumber );
            String timeStamp=cursor.getString(indexTimeStamp);
            String body= cursor.getString( indexBody );

            listTinNhan.add("Phone Number: "+phonenumber+"\n"+"Time Stamp: "+timeStamp+"\n"+"SMS's body: "+body);
        }
        cursor.close();
        adapterTinNhan.notifyDataSetChanged();

    }
}
