package com.dhbkhn.anhtu.listviewadvanced;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AnhTu on 03/03/2017.
 */

public class StudentAdapter extends ArrayAdapter<Student> {

    public StudentAdapter(Context context, int resource, List<Student> objects) { //3 tham số lần lượt là: màn hình đang đứng, layout nào, mảng
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View v=convertView;
        Student st=getItem(position);

        if(v==null) {  //nếu view dó null thì tạo view đó là 1 row từ file row.xml
            LayoutInflater li=LayoutInflater.from(getContext());
            v=li.inflate(R.layout.row, null);
        }

        if(st!=null) { //nếu st khác null thì hiển thị thông tin sv đó lên row
            TextView tvName=(TextView)v.findViewById(R.id.tvName);
            TextView tvPhone=(TextView)v.findViewById(R.id.tvPhone);

            tvName.setText(st.getName());
            tvPhone.setText(st.getPhone());
        }

        return v;
    }
}
