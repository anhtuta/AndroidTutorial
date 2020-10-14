package com.dhbk.anhtu.bai29datetime;

import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.DatePicker;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    TextView tvDate, tvTime;
    ImageButton ibDate, ibTime;
    Calendar cal=Calendar.getInstance();
    SimpleDateFormat formatDate=new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatTime=new SimpleDateFormat("HH:mm");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addComponents();
        addEvents();
    }

    private void addComponents() {
        tvDate=(TextView)findViewById(R.id.tvDate);
        tvTime=(TextView)findViewById(R.id.tvTime);
        ibDate=(ImageButton) findViewById(R.id.ibDate);
        ibTime=(ImageButton)findViewById(R.id.ibTime);

        cal=Calendar.getInstance();
        tvDate.setText(formatDate.format(cal.getTime()));
        tvTime.setText(formatTime.format(cal.getTime()));
    }

    private void addEvents() {
        ibDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DatePickerDialog.OnDateSetListener callBack= new DatePickerDialog.OnDateSetListener() { //chú ý OnDateSetListener là 1 interface trong class DatePickerDialog
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) { //lắng nghe sự thay đổi mà user thay đổi ngày tháng năm trên giao diện (thay đổi sau khi show() ở dưới)
                        cal.set(Calendar.YEAR, year);
                        cal.set(Calendar.MONTH, month);
                        cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);

                        //hiển thị sự thay đổi:
                        tvDate.setText(formatDate.format(cal.getTime()));
                    }
                };

                DatePickerDialog dateDialog=new DatePickerDialog(
                        MainActivity.this,
                        callBack,
                        cal.get(Calendar.YEAR),
                        cal.get(Calendar.MONTH),
                        cal.get(Calendar.DAY_OF_MONTH));
                dateDialog.show();  //Hiển thị cửa số DatePickerDialog để ta chọn ngày tháng năm trên đó
            }
        });

        ibTime.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TimePickerDialog.OnTimeSetListener callback=new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                        cal.set(Calendar.MINUTE, minute);

                        //hiển thị sự thay đổi:
                        tvTime.setText(formatTime.format(cal.getTime()));
                    }
                };

                TimePickerDialog timeDialog=new TimePickerDialog(
                        MainActivity.this,
                        callback,
                        cal.get(Calendar.HOUR),
                        cal.get(Calendar.MINUTE),
                        true
                );
                timeDialog.show();
            }
        });
    }
}
