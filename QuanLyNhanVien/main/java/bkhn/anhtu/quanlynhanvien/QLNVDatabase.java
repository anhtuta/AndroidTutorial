package bkhn.anhtu.quanlynhanvien;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * Created by anhtu on 27/03/2017.
 */

public class QLNVDatabase {
    public static final String DATABASE_NAME = "EmployeeDB.sqlite";
    public static final String TABLE_NAME = "NhanVien";

    public static SQLiteDatabase initDatabase(Activity activity, String databaseName) {  //khởi tạo database cho 1 activity
        try {
            String outFileName = activity.getApplicationInfo().dataDir + "/databases/" + databaseName;
            File f = new File(outFileName);
            if(!f.exists()) {
                InputStream e = activity.getAssets().open(databaseName);
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
        return activity.openOrCreateDatabase(databaseName, Context.MODE_PRIVATE, null);
    }

    public static void updateDatabase(Activity activity, int id, ContentValues contentValues) {
        SQLiteDatabase database = initDatabase(activity, DATABASE_NAME);    //lấy database EmployeeDB
        database.update(TABLE_NAME, contentValues, "ID = ?", new String[] {id+""});  //update database
    }

    public static void insertDatabase(Activity activity, ContentValues contentValues) {
        SQLiteDatabase database = initDatabase(activity, DATABASE_NAME);    //lấy database EmployeeDB
        database.insert(TABLE_NAME, null, contentValues);   //insert new record into database
    }

    public static void deleteDatabasesRecord(Activity activity, int idNv) {
        SQLiteDatabase database = initDatabase(activity, DATABASE_NAME);
        database.delete(TABLE_NAME, "ID = ?", new String[] {idNv+""});
    }
}
