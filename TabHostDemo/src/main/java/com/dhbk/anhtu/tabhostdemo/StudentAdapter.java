package com.dhbk.anhtu.tabhostdemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AnhTu on 09/03/2017.
 */

public class StudentAdapter extends ArrayAdapter<Student> {

    public StudentAdapter(Context context, int resource, List<Student> objects) {
        super(context, resource, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        Student st=getItem(position); //lấy student thứ position trong ArrayAdapter

        if(row==null) {
            LayoutInflater li=LayoutInflater.from(getContext());
            row=li.inflate(R.layout.row, null);
        }

        if(st!=null) { //hiển thị st đó lên row
            TextView tvName=(TextView)row.findViewById(R.id.tvName);
            tvName.setText(st.getName());
            TextView tvAddr=(TextView)row.findViewById(R.id.tvAddr);
            tvAddr.setText(st.getAddr());
        }

        return row;
    }
}
