package vn.edu.hust.tung.myradio.Modules;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by tung on 3/14/17.
 */


public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String db_name = "my_radio";
    private static final int db_version = 2;

    public DatabaseHelper(Context context) {
        super(context, db_name, null, db_version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.execSQL("CREATE TABLE if not exists favorite " +
                    "(_id integer primary key autoincrement, id_radio integer);");
            db.execSQL("create table if not exists last_state " +
                    "(_id integer primary key autoincrement, id_radio integer, volume integer)");
        } catch (Exception e) {
            Log.i("main", e.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }

}
