package vn.edu.hust.tung.myradio;

/**
 * Created by anhtu on 18/04/2017.
 */

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.widget.Toast;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public class ScheduleDatabase {
    public static final String DATABASE_NAME = "schedule_db.sqlite";
    public static final String SCHEDULE_TABLE = "schedule_tb";


    public static SQLiteDatabase initDatabase(Activity activity) {
        String fileOutput = activity.getApplicationInfo().dataDir + "/databases/" + DATABASE_NAME;
        File f = new File(fileOutput);
        if (!f.exists()) {   //nếu file students_db.sqlite chưa tồn tại thì ta tạo mới 1 file có tên là students_db.sqlite, sau đó lấy file students_db.sqlite từ thư mục assets và copy nội dung sang file students_db.sqlite vừa tạo
            Toast.makeText(activity, "This is the first time running this app, so database doesn't exist. now we have to create new database", Toast.LENGTH_SHORT).show();  //khi chạy ứng dụng lần đầu tiên thì f sẽ ko tồn tại. chỉ có lần đầu tiên thôi!
            try {
                InputStream inputStream = activity.getAssets().open(DATABASE_NAME);
                File folder = new File(activity.getApplicationInfo().dataDir + "/databases/");

                if (!folder.exists()) folder.mkdir();

                FileOutputStream myOutput = new FileOutputStream(fileOutput);
                byte[] buffer = new byte[1024];

                int length;
                while ((length = inputStream.read(buffer)) > 0) {
                    myOutput.write(buffer, 0, length);
                }

                myOutput.flush();
                myOutput.close();
                inputStream.close();
            } catch (IOException e) {
                Toast.makeText(activity, e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
        return activity.openOrCreateDatabase(DATABASE_NAME, Context.MODE_PRIVATE, null);
    }


    public static Cursor readDatabase(Activity activity) {
        SQLiteDatabase database = ScheduleDatabase.initDatabase(activity); //chọn database:
        Cursor cursor = database.rawQuery("SELECT * FROM "+SCHEDULE_TABLE, null); //chọn bảng trong database

        return cursor;
    }

    public static int updateARecord(Activity activity, int id, ContentValues contentValues) {
        SQLiteDatabase database = initDatabase(activity);    //lấy database EmployeeDB
        int record = database.update(SCHEDULE_TABLE, contentValues, "channel_id = ?", new String[]{id + ""});  //update database
        return record;
    }

//    public static int updateARecord(Activity activity, int id, Student s) {
//        SQLiteDatabase database = initDatabase(activity);    //lấy database EmployeeDB
//
//        ContentValues contentValues = new ContentValues();   //create contentValues
//        contentValues.put("name", s.name);
//        contentValues.put("faculty", s.faculty);
//        contentValues.put("photo", s.photo);
//
//        int record = database.update(STUDENTS_TABLE, contentValues, "channel_id = ?", new String[]{id + ""});  //update database using contentValues
//        return record;
//    }

    public static long insertARecord(Activity activity, ContentValues contentValues) {
        SQLiteDatabase database = initDatabase(activity);    //lấy database EmployeeDB
        try {
            long recordID = database.insert(SCHEDULE_TABLE, null, contentValues);   //insert new record into database. Returns: the row ID of the newly inserted row, or -1 if an error occurred
            return recordID;
        } catch (SQLException e) {
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
        return -1;   //if an error occurred
    }

//    public static long insertARecord(Activity activity, Student s) {
//        SQLiteDatabase database = initDatabase(activity);    //lấy database EmployeeDB
//
//        ContentValues contentValues = new ContentValues();
//        contentValues.put("id", s.id);
//        contentValues.put("name", s.name);
//        contentValues.put("faculty", s.faculty);
//        contentValues.put("photo", s.photo);
//
//        long kq = database.insert(STUDENTS_TABLE, null, contentValues);   //insert new record into database
//        if(kq > 0) Toast.makeText(activity, "add a song with id = "+kq+" successfull", Toast.LENGTH_SHORT).show();
//        else Toast.makeText(activity, "the id already exist!", Toast.LENGTH_SHORT).show();
//
//        return kq;
//    }

    public static int deleteARecord(Activity activity, int idNv) {
        SQLiteDatabase database = initDatabase(activity);
        return database.delete(SCHEDULE_TABLE, "channel_id = ?", new String[]{idNv + ""});
    }

    public static void deleteAllRecord(Activity activity) {
        SQLiteDatabase database = initDatabase(activity);
        database.execSQL("DELETE FROM "+SCHEDULE_TABLE);
        database.close();
    }

}
