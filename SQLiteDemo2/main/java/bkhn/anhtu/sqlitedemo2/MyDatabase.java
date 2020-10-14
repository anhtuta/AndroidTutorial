package bkhn.anhtu.sqlitedemo2;

/**
 * Created by anhtu on 29/03/2017.
 */


        import android.app.Activity;
        import android.content.ContentValues;
        import android.content.Context;
        import android.database.Cursor;
        import android.database.SQLException;
        import android.database.sqlite.SQLiteDatabase;
        import android.widget.Toast;

        import java.io.File;
        import java.io.FileOutputStream;
        import java.io.IOException;
        import java.io.InputStream;

/**
 * Created by anhtu on 27/03/2017.
 */

public class MyDatabase {
    public static final String DATABASE_NAME = "BaiHat.sqlite";
    public static final String TABLE_NAME = "BaiHat_table";

    public static SQLiteDatabase initDatabase(Activity activity) {  //khởi tạo database cho 1 activity, kết quả trả về 1 database
        try {
            String outFileName = activity.getApplicationInfo().dataDir + "/databases/" + DATABASE_NAME;
            File f = new File(outFileName);
            if (!f.exists()) {
                InputStream e = activity.getAssets().open(DATABASE_NAME);
                File folder = new File(activity.getApplicationInfo().dataDir + "/databases/");
                if (!folder.exists()) {
                    folder.mkdir();
                }
                FileOutputStream myOutput = new FileOutputStream(outFileName);
                byte[] buffer = new byte[1024];

                int length;
                while ((length = e.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                e.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return activity.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }

    public static Cursor readDatabase(Activity activity) {
        SQLiteDatabase database = MyDatabase.initDatabase(activity); //chọn database:
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME, null); //chọn bảng trong database

        return cursor;
    }

    public static void updateARecord(Activity activity, int id, ContentValues contentValues) {
        SQLiteDatabase database = initDatabase(activity);    //lấy database EmployeeDB
        database.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id + ""});  //update database
    }

    public static void updateARecord(Activity activity, int id, Song_update s) {
        SQLiteDatabase database = initDatabase(activity);    //lấy database EmployeeDB

        ContentValues contentValues = new ContentValues();   //create contentValues
        contentValues.put("name", s.name);
        contentValues.put("singer", s.singer);
        contentValues.put("favorite", s.isFavorite==true ? 1:0);

        database.update(TABLE_NAME, contentValues, "ID = ?", new String[]{id + ""});  //update database using contentValues
    }

    public static void insertARecord(Activity activity, ContentValues contentValues) {
        SQLiteDatabase database = initDatabase(activity);    //lấy database EmployeeDB
        try {
            database.insert(TABLE_NAME, null, contentValues);   //insert new record into database
        } catch (SQLException e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    public static void insertARecord(Activity activity, Song s) {
        SQLiteDatabase database = initDatabase(activity);    //lấy database EmployeeDB

        ContentValues contentValues = new ContentValues();
        contentValues.put("id", s.id);
        contentValues.put("name", s.name);
        contentValues.put("singer", s.singer);
        contentValues.put("favorite", s.isFavorite==true ? 1:0);

        long kq = database.insert(TABLE_NAME, null, contentValues);   //insert new record into database
        if(kq > 0) Toast.makeText(activity, "add a song with id = "+kq+" successfull", Toast.LENGTH_SHORT).show();
        else Toast.makeText(activity, "the id already exist!", Toast.LENGTH_SHORT).show();

    }

    public static void deleteARecord(Activity activity, int idNv) {
        SQLiteDatabase database = initDatabase(activity);
        database.delete(TABLE_NAME, "ID = ?", new String[]{idNv + ""});
    }
}