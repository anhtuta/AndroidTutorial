package bkhn.anhtu.contentproviderdemo;

import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.sql.DatabaseMetaData;
import java.util.ArrayList;

public class DanhBaActivity extends AppCompatActivity {

    ListView lvDanhBa;
    ArrayList<DanhBa> listDanhBa;
    ArrayAdapter<DanhBa> adapterDanhBa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_danh_ba);

        addComponents();
        addEvents();

        showAllContactsFromDecive();
    }

    private void addComponents() {
        lvDanhBa = (ListView) findViewById(R.id.lvDanhBa);
        listDanhBa = new ArrayList<>();
        adapterDanhBa = new ArrayAdapter<DanhBa>(this, android.R.layout.simple_list_item_1, listDanhBa);
        lvDanhBa.setAdapter(adapterDanhBa);
    }

    private void addEvents() {

    }

    private void showAllContactsFromDecive() {
        Uri uri = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        Cursor cursor = getContentResolver().query(uri, null,null,null,null);
        listDanhBa.clear();  //xoa dl cũ đi
        //B1: lấy tên cột
        //B2: lấy chỉ số của cột
        //B3: lấy dl từ chỉ số của cột đó
        while(cursor.moveToNext()) {
            //lấy tên của 1 người trong danh bạ:
            String columnName = ContactsContract.Contacts.DISPLAY_NAME;  //B1
            int columnNameIndex = cursor.getColumnIndex(columnName);  //B2
            String name = cursor.getString(columnNameIndex);  //B3

            //lấy số điện thoại của người đó trong danh bạ:
            String columnPhone = ContactsContract.CommonDataKinds.Phone.NUMBER;  //B1
            int columnPhoneIndex = cursor.getColumnIndex(columnPhone);  //B2
            String phone = cursor.getString(columnPhoneIndex);  //B3

            listDanhBa.add(new DanhBa(name, phone));
        }
        cursor.close();
        adapterDanhBa.notifyDataSetChanged();
    }
}
