package com.bkhn.anhtu.sqlitedemo;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.ButtonBarLayout;
import android.support.v7.widget.ListViewCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    String DATABASE_NAME = "db_contact.sqlite";
    String TABLE_NAME = "table_contacts"; //ten table trong csdl db_contact
    String DB_PATH_SUFFIX = "/database/";
    SQLiteDatabase database = null;

    ListView lvContact;
    ArrayList<String> listContact;
    ArrayAdapter<String> adapterContact;

    Button btAdd, btUpdate;
    EditText txtId, txtName, txtPhone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        copyDBFromAssetsToMobileSystem();

        addComponents();
        addEvents();

        try {
            showAllContactsOnListView();
        } catch (Exception e) {
            Toast.makeText(this, "Error: "+e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void showAllContactsOnListView() {
        //buoc 1: open database:
        database = openOrCreateDatabase(DATABASE_NAME, MODE_PRIVATE, null);
        //Cursor cursor = database.query(TABLE_NAME, null, null, null,null,null,null); //truy van
        //cach 2:
        Cursor cursor = database.rawQuery("select * from "+TABLE_NAME, null);
        listContact.clear(); //xoa cai cu di
        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            String phone = cursor.getString(2);

            listContact.add(id+" - "+name+" - "+phone);
        }
        adapterContact.notifyDataSetChanged();
        cursor.close();
    }

    private void addComponents() {
        lvContact = (ListView)findViewById(R.id.lvContact);
        listContact = new ArrayList<String>();
        adapterContact = new ArrayAdapter<String>(MainActivity.this, android.R.layout.simple_list_item_1, listContact);
        lvContact.setAdapter(adapterContact);

        btAdd = (Button)findViewById(R.id.btAdd);
        btUpdate = (Button)findViewById(R.id.btUpdate);
        txtId = (EditText)findViewById(R.id.txtId);
        txtName = (EditText)findViewById(R.id.txtName);
        txtPhone = (EditText)findViewById(R.id.txtPhone);

    }

    private void addEvents() {
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {  //add an object into table in database
                //readMode();
                ContentValues row = new ContentValues();
                row.put("id", Integer.valueOf(txtId.getText().toString()));
                row.put("name", txtName.getText().toString());
                row.put("phone", txtPhone.getText().toString());  //chu y: ten cot phai giong trong database

                long l = database.insert(TABLE_NAME, null, row);
                if(l>0)
                    Toast.makeText(MainActivity.this, "Add successful", Toast.LENGTH_SHORT).show();
                else Toast.makeText(MainActivity.this, "Add failed!", Toast.LENGTH_SHORT).show();

            }
        });

        btUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ContentValues row = new ContentValues();
                row.put("name", "Tu toc xu");
                database.update(TABLE_NAME, row, "id=?", new String[]{"1001"});

                showAllContactsOnListView();
            }
        });

        lvContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
    }

    private void copyDBFromAssetsToMobileSystem() {
        File dbFile = getDatabasePath(DATABASE_NAME);
        if(!dbFile.exists()) {
            try {
                InputStream input = getAssets().open(DATABASE_NAME);

                String outFileName = getStoredPath();
                File f = new File(getApplicationInfo().dataDir + DB_PATH_SUFFIX);  //lay duong dan thu muc goc + DB_PATH_SUFFIX
                if(!f.exists()) {
                    f.mkdir();
                }

                OutputStream output = new FileOutputStream(outFileName);

                byte[] buffer = new byte[1024];
                int length;
                while ((length = input.read(buffer)) > 0) {
                    output.write(buffer, 0, length);
                }

                output.flush();
                output.close();
                input.close();

                Toast.makeText(this, "sao chep csdl thanh cong", Toast.LENGTH_LONG).show();

            } catch (Exception e) {
                Toast.makeText(this, "ko the sao chep csdl. Loi: "+e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        else Toast.makeText(this, "this database already exist!", Toast.LENGTH_SHORT).show();
    }

    private String getStoredPath() {  //lay duong dan phai luu tru
        return getApplicationInfo().dataDir + DB_PATH_SUFFIX+ DATABASE_NAME;
    }

    public void readMode() {
        txtId.setVisibility(View.INVISIBLE);
        txtName.setVisibility(View.INVISIBLE);
        txtPhone.setVisibility(View.INVISIBLE);
    }
}
