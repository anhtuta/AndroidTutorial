package vn.edu.hust.tung.myradio;

import android.media.MediaPlayer;
import android.media.MediaRecorder;
import android.os.Environment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;

public class RecordActivity extends AppCompatActivity {

    Button btRecord, btStopRecord, btPlay, btStopPlaying;
    String audioSavePathInDevice = null;
    MediaRecorder mediaRecorder ;       //dùng để record âm thanh
    public static final int RequestPermissionCode = 1;
    MediaPlayer mediaPlayer ;       //dùng để chạy âm thanh


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_record);
        setTitle("Record radio");

        btRecord = (Button) findViewById(R.id.btRecord);
        btStopRecord = (Button) findViewById(R.id.btStopRecord);

        btStopRecord.setEnabled(false);

        addEvents();
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(mediaRecorder != null) stopRecord();
    }


    private void addEvents() {
        btRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                File file = new File(Environment.getExternalStorageDirectory(), "MyRadio/recordAudio");
                if(!file.exists()) file.mkdirs();
                String pathFolder = Environment.getExternalStorageDirectory()+"/MyRadio/recordAudio/";
                audioSavePathInDevice = pathFolder+"recordRadio_"+getCurrentStringTime()+".mp3";

                mediaRecorderReady();

                try {
                    mediaRecorder.prepare();
                    mediaRecorder.start();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (IllegalStateException e) {
                    Log.e("Illegal......", e.getMessage());
                }

                btRecord.setEnabled(false);
                btStopRecord.setEnabled(true);

                Toast.makeText(RecordActivity.this, "Recording started", Toast.LENGTH_SHORT).show();
                Toast.makeText(RecordActivity.this, "Touch outside of press \"Stop record\" to save", Toast.LENGTH_LONG).show();
            }
        });

        btStopRecord.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                stopRecord();
            }
        });
    }

    private void stopRecord() {
        mediaRecorder.stop();
        mediaRecorder = null;
        btRecord.setEnabled(true);
        btStopRecord.setEnabled(false);

        Toast.makeText(RecordActivity.this, "Recording completed\nFile saved in "+audioSavePathInDevice, Toast.LENGTH_LONG).show();
    }

    private void mediaRecorderReady() {
        mediaRecorder = new MediaRecorder();
        mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
        mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.DEFAULT);
        mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.DEFAULT);
        mediaRecorder.setOutputFile(audioSavePathInDevice);
    }

    private String getCurrentStringTime() {
        Calendar calendar = Calendar.getInstance();
        int second = calendar.get(Calendar.SECOND);
        int minute = calendar.get(Calendar.MINUTE);
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH)+1; //CHú ý: tháng 1 bắt đầu = 0: The first month of the year in the Gregorian and Julian calendars is JANUARY which is 0
        int year = calendar.get(Calendar.YEAR);

        String secondStr;
        if(second < 10) secondStr = "0"+second;
        else secondStr = second+"";

        String minuteStr;
        if(minute < 10) minuteStr = "0"+minute;
        else minuteStr = minute+"";

        String hourStr;
        if(hour < 10) hourStr = "0"+hour;
        else hourStr = hour+"";

        String dayStr;
        if(day < 10) dayStr = "0"+day;
        else dayStr = day+"";

        String monthStr;
        if(month < 10) monthStr = "0"+month;
        else monthStr = month+"";


        String currentTime = year+monthStr+dayStr+"_"+hourStr+minuteStr+secondStr;

        return currentTime;
    }
}
