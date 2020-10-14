package bkhn.anhtu.sqlitedemo2;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    public static final String DATABASE_NAME = "BaiHat.sqlite";
    public static final String TABLE_NAME = "BaiHat_table";

    ListView lvSong;
    ArrayList<String> listSong;
    ArrayAdapter<String> adapterSong;
    SQLiteDatabase database;

    EditText txtId, txtName, txtSinger, txtFavorite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addComponents();

        try {
            lvSong = (ListView) findViewById(R.id.lvSong);
            listSong = new ArrayList<>();
            adapterSong = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listSong);
            readData();  //chú ý phải gọi hàm này sau lệnh khởi tạo adapter, vì hàm này có lệnh adapterSong.notifyDataSetChanged();, nếu adapter chưa đc khởi tạo nghĩa là adapter = null và ko thực hiện đc lệnh adapterSong.notifyDataSetChanged();
            lvSong.setAdapter(adapterSong);
        }catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    private void addComponents() {
        txtId = (EditText) findViewById(R.id.txtId);
        txtName = (EditText) findViewById(R.id.txtName);
        txtSinger = (EditText) findViewById(R.id.txtSinger);
        txtFavorite = (EditText) findViewById(R.id.txtFavorite);
    }

    private void readData() {
        //cach 1:
//        database = MyDatabase.initDatabase(MainActivity.this); //chọn database:
//        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME, null); //chọn bảng trong database

        //cach 2: cho 2 lenh tren vao 1 ham cho de quan ly:
        Cursor cursor = MyDatabase.readDatabase(this);

        //hiển thị tất cả record trong bảng lên listview:
        listSong.clear();  //nếu ko xóa dl cũ đi thì có thể đọc 2 lần dẫn tới trùng dl
        String id; String ten; String casi; String favorite;
        while(cursor.moveToNext()) {
            id = cursor.getString(0);
            ten = cursor.getString(1);
            casi = cursor.getString(2);
            favorite = cursor.getInt(3) == 1 ? "favorite" : "dislike";

            listSong.add(new String(id+" - "+ten+" - "+casi+" - "+favorite));
        }
        adapterSong.notifyDataSetChanged();
    }

    public void btAddEvent(View view) {
        try {
            int favorite = 0;
            if(txtFavorite.getText().equals("")) favorite = 0;
            else if(txtFavorite.getText().equals("1")) favorite = 1;
            Song s = new Song(Integer.valueOf(txtId.getText().toString()), txtName.getText().toString(), txtSinger.getText().toString(), favorite == 1);
            listSong.add(s.toString());
            adapterSong.notifyDataSetChanged();
            MyDatabase.insertARecord(this, s);
        } catch (Exception e) {
            Toast.makeText(this, "please enter all fields. Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        //readData();
    }
}
