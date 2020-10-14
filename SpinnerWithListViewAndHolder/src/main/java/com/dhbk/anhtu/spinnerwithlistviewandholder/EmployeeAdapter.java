package com.dhbk.anhtu.spinnerwithlistviewandholder;

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
        //Employee em=getItem(position);  //lấy employee thứ position trong ArrayAdapter
        View row=convertView;
        EmployeeHolder holder=null;

        if(row==null) {
            LayoutInflater li=LayoutInflater.from(getContext());
            row=li.inflate(R.layout.row, parent, false);

            holder=new EmployeeHolder(row);
            row.setTag(holder);
        }
        else {
            holder= (EmployeeHolder) row.getTag();
        }

//        if(em!=null) { //hiển thị em đó lên row
//            TextView tvName=(TextView)row.findViewById(R.id.tvName);
//            tvName.setText(em.getName());
//            TextView tvDate=(TextView)row.findViewById(R.id.tvDate);
//            tvDate.setText(em.getDieDate());
//            TextView tvSick=(TextView)row.findViewById(R.id.tvSick);
//            tvSick.setText(String.valueOf(em.getSickNumber()));  //CHÚ Ý DÒNG NÀY KO CÓ PHẦN String.valueOf() THÌ LỖI, VÌ em.getSickNumber() CÓ KIỂU int
//        }

        holder.insertToRow(getItem(position));

        return row;
    }
}

class EmployeeHolder {
    TextView name=null;
    TextView dieDate=null;
    TextView sickNum=null;

    public EmployeeHolder(View row) {
        name=(TextView)row.findViewById(R.id.tvName);
        dieDate=(TextView)row.findViewById(R.id.tvDate);
        sickNum=(TextView)row.findViewById(R.id.tvSick);
    }

    public void insertToRow(Employee em) {
        name.setText(em.getName());
        dieDate.setText(em.getDieDate());
        sickNum.setText(String.valueOf(em.getSickNumber()));
    }
}
