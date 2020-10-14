package bkhn.anhtu.quanlynhanvien;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by anhtu on 27/03/2017.
 */

public class AdapterNhanVien extends BaseAdapter {

    Activity activity;
    ArrayList<NhanVien> listNV;

    public AdapterNhanVien(Activity activity, ArrayList<NhanVien> listNV) {
        this.activity = activity;
        this.listNV = listNV;
    }

    @Override
    public int getCount() {
        return listNV.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View row = inflater.inflate(R.layout.row, null);  //tham so thu 2 la viewGroup, ko can nen ta de = null

        ImageView ivAnh = (ImageView)row.findViewById(R.id.ivAnh);
        TextView txtId = (TextView) row.findViewById(R.id.txtId);
        TextView txtTen = (TextView) row.findViewById(R.id.txtTen);
        TextView txtSdt = (TextView) row.findViewById(R.id.txtSdt);
        Button btSua = (Button)row.findViewById(R.id.btSua);
        Button btXoa = (Button)row.findViewById(R.id.btXoa);

        final NhanVien nv = listNV.get(position);

        txtId.setText(String.valueOf(nv.id));
        txtTen.setText(nv.ten);
        txtSdt.setText(nv.sdt);
        Bitmap bmAnh = BitmapFactory.decodeByteArray(nv.anh, 0, nv.anh.length);  //tham so 1 = mảng data, là ảnh của nv, tham số 2 = vị trí bắt đầu của mảng data đó, tham số 3 là chiều dài của data
        ivAnh.setImageBitmap(bmAnh);

        btSua.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, UpdateActivity.class);
                intent.putExtra("ID", nv.id);
                activity.startActivity(intent);

            }
        });
        btXoa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                builder.setIcon(android.R.drawable.ic_delete);
                builder.setTitle("Xác nhận xóa");
                builder.setMessage("Bạn có chắc muốn xóa?");
                builder.setPositiveButton("Có", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteNv(nv.id);
                    }
                });
                builder.setNegativeButton("Không", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //đếch làm j cả
                    }
                });
                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        return row;
    }

    private void deleteNv(int idNv) {
        SQLiteDatabase database = MyDatabase.initDatabase(activity, "EmployeeDB.sqlite");
        database.delete("NhanVien", "ID = ?", new String[] {idNv+""});

        //cập nhập lại listview: cập nhập lại toàn bộ dl cho adapter:

        //xóa listNv của adapter trước:
        listNV.clear();
        //thêm từng record vào listNv của adapter:
        Cursor cursor = database.rawQuery("SELECT * FROM NhanVien", null);
        while(cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String ten = cursor.getString(1);
            String sdt = cursor.getString(2);
            byte[] anh = cursor.getBlob(3);

            listNV.add(new NhanVien(id, ten, sdt, anh));
        }
        notifyDataSetChanged();
    }
}
