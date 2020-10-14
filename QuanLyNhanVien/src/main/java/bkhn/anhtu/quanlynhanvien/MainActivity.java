package bkhn.anhtu.quanlynhanvien;

import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    final String DATABASE_NAME = "EmployeeDB.sqlite";
    final String TABLE_NAME = "NhanVien";
    ListView lvNv;
    ArrayList<NhanVien> listNv;
    AdapterNhanVien adapterNv;

    SQLiteDatabase database;

    Button btAdd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addComponents();
        readData();
    }

    private void addComponents() {
        lvNv = (ListView)findViewById(R.id.lvNhanVien);
        listNv = new ArrayList<>();
        adapterNv = new AdapterNhanVien(this, listNv);
        lvNv.setAdapter(adapterNv);

        btAdd = (Button)findViewById(R.id.btAdd);
        btAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, AddActivity.class);
                startActivity(intent);
            }
        });
    }

    private void readData() {
        database = MyDatabase.initDatabase(this, DATABASE_NAME); //chọn database:
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME, null); //chọn bảng trong database

        //hiển thị tất cả record trong bảng lên listview:
        listNv.clear();  //nếu ko xóa dl cũ đi thì có thể đọc 2 lần dẫn tới trùng dl
        int id; String ten; String sdt; byte[] anh;
        while(cursor.moveToNext()) {
            id = cursor.getInt(0);
            ten = cursor.getString(1);
            sdt = cursor.getString(2);
            anh = cursor.getBlob(3);

            listNv.add(new NhanVien(id, ten, sdt, anh));
        }
        adapterNv.notifyDataSetChanged();
    }
 }
