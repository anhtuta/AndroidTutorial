package bkhn.anhtu.quanlynhanvien;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

public class UpdateActivity extends AppCompatActivity {

    private static final String DATABASE_NAME = "EmployeeDB.sqlite";
    private static final String TABLE_NAME = "NhanVien";
    final int REQUEST_TAKE_PHOTO = 123;
    final int REQUEST_CHOOSE_PHOTO = 456;
    int id = -1;

    Button btChup, btChon, btLuu, btHuy;
    EditText txtTen, txtSdt;
    ImageView ivAnh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update);

        addComponents();
        addEvents();

        try {
            getNVInfo();
        }catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    private void addEvents() {
        btChon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                choosePicture();
            }
        });
        btChup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                takePicture();
            }
        });
        btLuu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateInfo();
            }
        });
        btHuy.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cancelUpdate();
            }
        });
    }

    private void getNVInfo() {  //lấy thông tin của nv hiển thị lên activity_update
        Intent intent = getIntent();
        id = intent.getIntExtra("ID", -1);

        SQLiteDatabase database = MyDatabase.initDatabase(this, DATABASE_NAME); //chọn database:
        Cursor cursor = database.rawQuery("SELECT * FROM "+TABLE_NAME + " WHERE ID = ?", new String[] {id+""}); //chọn bảng trong database
        cursor.moveToFirst();
        String ten = cursor.getString(1);
        String sdt = cursor.getString(2);
        byte [] anh = cursor.getBlob(3);
        Bitmap bitmap = BitmapFactory.decodeByteArray(anh, 0, anh.length);

        ivAnh.setImageBitmap(bitmap);
        txtTen.setText(ten);
        txtSdt.setText(sdt);
    }

    private void addComponents() {
        btChon = (Button)findViewById(R.id.btChon);
        btChup = (Button)findViewById(R.id.btChup);
        btLuu = (Button)findViewById(R.id.btLuu);
        btHuy = (Button)findViewById(R.id.btHuy);
        txtTen = (EditText)findViewById(R.id.txtTen_Update);
        txtSdt = (EditText)findViewById(R.id.txtSdt_Update);
        ivAnh = (ImageView)findViewById(R.id.ivAnh_Update);
    }

    private void takePicture() {  //chụp hình
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(intent, REQUEST_TAKE_PHOTO);
    }

    private void choosePicture() {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, REQUEST_CHOOSE_PHOTO);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(resultCode == RESULT_OK) {
            if(requestCode == REQUEST_CHOOSE_PHOTO) {  //chọn hình
                try {
                    Uri imageUri = data.getData();
                    InputStream inputStream = getContentResolver().openInputStream(imageUri);
                    Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                    ivAnh.setImageBitmap(bitmap);
                } catch (FileNotFoundException e) {
                    Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
                }

            } else if(requestCode == REQUEST_TAKE_PHOTO) {  //chụp hình
                Bitmap bitmap = (Bitmap) data.getExtras().get("data");  //getExtras trả về bundle, sau đó dùng key = "data" để lấy bitmap
                ivAnh.setImageBitmap(bitmap);
            }
        }
    }

    private void updateInfo() {
        //lấy thông tin ở màn hình trước khi update
        String ten = txtTen.getText().toString();
        String sdt = txtSdt.getText().toString();
        byte[] anh = getByteArrayFromImageView(ivAnh);

        //update:
        ContentValues contentValues = new ContentValues();
        contentValues.put("Ten", ten);  //tham số 1 là tên cột trong bảng trong database
        contentValues.put("SDT", sdt);
        contentValues.put("Anh", anh);

        //lấy database:
        SQLiteDatabase database = MyDatabase.initDatabase(this, "EmployeeDB.sqlite");
        //update database trên:
        database.update("NhanVien", contentValues, "id = ?", new String[] {id+""});  //"id = ?" là điều kiện update, id là tên cột trong bảng, có thể viết hoa hay ko cũng đc, dù trong CSDL thì cột ý là ID
        //quay ve MainActivity:
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    private void cancelUpdate() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public byte[] getByteArrayFromImageView(ImageView imgv){

        BitmapDrawable drawable = (BitmapDrawable) imgv.getDrawable();
        Bitmap bmp = drawable.getBitmap();

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
