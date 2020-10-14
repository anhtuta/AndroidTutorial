package vn.edu.hust.tung.myradio;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.DatePickerDialog;
import android.app.PendingIntent;
import android.app.TimePickerDialog;
import android.content.ContentValues;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.DatePicker;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class ScheduleActivity extends AppCompatActivity {

    TextView tvDay, tvTime, tvScheduledChannel;
    ListView lvRadioChannel;

    Calendar cal=Calendar.getInstance();    //Gets a calendar using the default time zone and locale. The Calendar returned is based on the current time in the default time zone with the default locale.
    SimpleDateFormat formatDate=new SimpleDateFormat("dd/MM/yyyy");
    SimpleDateFormat formatTime=new SimpleDateFormat("HH:mm");

    final String NO_SCHEDULED_CHANNEL = "there's no channel has been scheduled";

    private String [] channelName = {"Magic 80s Florida",
            "BBC Radio 1",
            "BBC Radio 2",
            "Box UK Radio danceradiouk",
            "Smash! - 90s & 00s Hits Non Stop!",
            "Coast TWO - Tenerife - Non Stop Dance",
            "1FM - Music From The 1960s - Today!",
            "Stoke Mandeville Hospital Radio",
            "First Step Radio Live",
            "Magic 60s Florida",
            "Magic 70s Florida",
            "Play 105",
            "Classic Rock Florida - SHE Radio",
            "1POWER",
            "108.fm The Office"};

    Spinner spinnerChannel;
    ArrayList<String> listChannel;
    ArrayAdapter<String> adapterChannel;

    int alarm_ID = 511;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        if(RadioBroadcaseReciever.isTimeUp) {       //nếu ko có kênh nào đang đc đặt lịch thì xóa database để cho ko phải hiển thị kênh nào lên textview nữa
            RadioBroadcaseReciever.isTimeUp = false;        //dùng xong biến isTimeUp phải đặt lại giá trị mặc định cho nó :)
            ScheduleDatabase.deleteAllRecord(this);
        }
        addComponents();
        if(tvScheduledChannel.getText().equals("")) tvScheduledChannel.setText(NO_SCHEDULED_CHANNEL);
    }

    private void addComponents() {
        tvDay= (TextView) findViewById(R.id.tvDay);
        tvTime= (TextView) findViewById(R.id.tvTime);
        tvScheduledChannel= (TextView) findViewById(R.id.tv_scheduled_channel);

        spinnerChannel= (Spinner) findViewById(R.id.spinnerChannel);
        listChannel = new ArrayList<>();
        for(int i=0; i<channelName.length; i++) listChannel.add(channelName[i]);
        adapterChannel = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listChannel);
        adapterChannel.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerChannel.setAdapter(adapterChannel);

        Cursor cursor = ScheduleDatabase.readDatabase(this);
        while (cursor.moveToNext()) {   //thực ra table này có 1 bản ghi thôi :D
            int id = -1;
            id = cursor.getInt(0);
            String date = cursor.getString(1);
            String time = cursor.getString(2);

            if(id != -1) {
                tvScheduledChannel.append(spinnerChannel.getItemAtPosition(id) + "\n" + date + "\n" + time);
            }
        }
    }

    public void btChooseDayEvent(View view) {
        final DatePickerDialog.OnDateSetListener callBack= new DatePickerDialog.OnDateSetListener() { //chú ý OnDateSetListener là 1 interface trong class DatePickerDialog
            @Override
            public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) { //lắng nghe sự thay đổi mà user thay đổi ngày tháng năm trên giao diện (thay đổi sau khi show() ở dưới)
                cal.set(Calendar.YEAR, year);
                cal.set(Calendar.MONTH, month);
                cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);     //lấy thời gian khi người dùng chọn ở DatePicker

                //hiển thị sự thay đổi: hiển thị thời gian vừa chọn lên tvDay
                tvDay.setText(formatDate.format(cal.getTime()));
            }
        };

        DatePickerDialog dateDialog=new DatePickerDialog(
                ScheduleActivity.this,
                callBack,
                cal.get(Calendar.YEAR),
                cal.get(Calendar.MONTH),
                cal.get(Calendar.DAY_OF_MONTH));     //thiết lập giá trị cho datePicker là giá trị ngày hiện tại

        dateDialog.show();  //Hiển thị cửa số DatePickerDialog để ta chọn ngày tháng năm trên đó
    }

    public void btChooseTimeEvent(View view) {
        TimePickerDialog.OnTimeSetListener callback=new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                cal.set(Calendar.HOUR_OF_DAY, hourOfDay);
                cal.set(Calendar.MINUTE, minute);
                cal.set(Calendar.SECOND, 0);

                //hiển thị sự thay đổi:
                tvTime.setText(formatTime.format(cal.getTime()));
            }
        };

        TimePickerDialog timeDialog=new TimePickerDialog(
                ScheduleActivity.this,
                callback,
                cal.get(Calendar.HOUR_OF_DAY),
                cal.get(Calendar.MINUTE),
                true);   //thiết lập giá trị cho TimePicker là giá trị giờ và phút hiện tại

        timeDialog.show();
    }

    public void btOk_PickerEvent(View view) {
        if(tvDay.getText().toString().equals(""))  Toast.makeText(this, "Please choose a day", Toast.LENGTH_SHORT).show();
        else if(tvTime.getText().toString().equals("")) {
            Toast.makeText(this, "please choose a time", Toast.LENGTH_SHORT).show();
        } else {
            Integer position = spinnerChannel.getSelectedItemPosition();
            MainActivity.radioChannel = MainActivity.listRadio.get(position);
            //MainActivity.changeRadio();

            long timer = cal.getTimeInMillis();
            //System.out.println("cal = "+new Date(timer));

            if (timer > System.currentTimeMillis()) {
                //timer = System.currentTimeMillis() + 5000;      // để 5 giây để test thôi!, sau khi test xong thì comment dòng này lại là đc!

                Intent intent = new Intent(this, RadioBroadcaseReciever.class);
                PendingIntent pendingIntent = PendingIntent.getBroadcast(this.getApplicationContext(), alarm_ID, intent, 0);
                AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.set(AlarmManager.RTC_WAKEUP, timer, pendingIntent);

                Toast.makeText(this, "Alarm set in: " + formatDate.format(cal.getTime()) + " at " + formatTime.format(cal.getTime()), Toast.LENGTH_LONG).show();
                tvScheduledChannel.setText(spinnerChannel.getSelectedItem().toString() + "\n" + tvDay.getText() + "\n" + tvTime.getText());

                //delete the whole database first: we just want to store one record, because we want to set timer for just one channel!
                ScheduleDatabase.deleteAllRecord(this);

                //insert this channel into database:
                ContentValues contentValues = new ContentValues();
                contentValues.put("channel_id", position);
                contentValues.put("date", tvDay.getText().toString());
                contentValues.put("time", tvTime.getText().toString());

            } else
                Toast.makeText(this, "Please choose a day in the future, not in the past!", Toast.LENGTH_LONG).show();
        }
    }

    public void btDeleteScheduleEvent(View view) {
        if(tvScheduledChannel.getText().toString().equals(NO_SCHEDULED_CHANNEL) || tvScheduledChannel.getText().toString().equals(""))
            Toast.makeText(this, "there's no schedule to delete!", Toast.LENGTH_SHORT).show();
        else {
            AlertDialog.Builder aBuilder = new AlertDialog.Builder(ScheduleActivity.this);
            aBuilder.setTitle("Delete")
                    .setMessage("Do you want to delete this schedule?")
                    .setIcon(R.drawable.ic_delete)
                    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(ScheduleActivity.this, RadioBroadcaseReciever.class);
                            PendingIntent pendingIntent = PendingIntent.getBroadcast(ScheduleActivity.this.getApplicationContext(), alarm_ID, intent, 0);
                            AlarmManager alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                            alarmManager.cancel(pendingIntent);

                            Toast.makeText(ScheduleActivity.this, "Canceled your schedule", Toast.LENGTH_SHORT).show();
                            tvScheduledChannel.setText("");
                            ScheduleDatabase.deleteAllRecord(ScheduleActivity.this);
                        }
                    })
                    .setNegativeButton("No, continue timer", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            AlertDialog alertDialog = aBuilder.create();
            alertDialog.show();
        }
    }

}
