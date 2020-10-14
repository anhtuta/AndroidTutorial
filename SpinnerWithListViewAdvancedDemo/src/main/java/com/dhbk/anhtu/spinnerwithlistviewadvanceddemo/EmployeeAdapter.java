package com.dhbk.anhtu.spinnerwithlistviewadvanceddemo;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by AnhTu on 07/03/2017.
 */

public class EmployeeAdapter extends ArrayAdapter<Employee> {
    public EmployeeAdapter(Context context, int resource, List<Employee> objects) {
        super(context, resource, objects);
    }


    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View row=convertView;
        Employee em=getItem(position);  //lấy employee thứ position trong ArrayAdapter

        if(row==null) {
            LayoutInflater li=LayoutInflater.from(getContext());
            row=li.inflate(R.layout.row, null);
        }

        if(em!=null) { //hiển thị em đó lên row
            TextView tvName=(TextView)row.findViewById(R.id.tvName);
            tvName.setText(em.getName());
            TextView tvDate=(TextView)row.findViewById(R.id.tvDate);
            tvDate.setText(em.getDieDate());
            TextView tvSick=(TextView)row.findViewById(R.id.tvSick);
            tvSick.setText(String.valueOf(em.getSickNumber()));  //CHÚ Ý DÒNG NÀY KO CÓ PHẦN String.valueOf() THÌ LỖI, VÌ em.getSickNumber() CÓ KIỂU int
        }

        return row;
    }
}
