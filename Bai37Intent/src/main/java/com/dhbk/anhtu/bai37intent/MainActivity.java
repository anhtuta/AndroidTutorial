package com.dhbk.anhtu.bai37intent;

import android.Manifest;
import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtPhone, txtSMS;
    Button btQuaySo, btCall, btSendSMS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtPhone = (EditText) findViewById(R.id.txtPhone);
        txtSMS = (EditText) findViewById(R.id.txtSms);
        btQuaySo = (Button) findViewById(R.id.btQuaySo);
        btCall = (Button) findViewById(R.id.btCall);
        btSendSMS = (Button) findViewById(R.id.btSend);

        btQuaySo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + txtPhone.toString().trim()); //chú ý phải đúng cú pháp dòng này
                Intent intent = new Intent(Intent.ACTION_DIAL); //đây là implicit intent
                intent.setData(uri);
                startActivity(intent);
            }
        });
        btCall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:" + txtPhone.toString().trim());
                Intent it = new Intent(Intent.ACTION_CALL);
                it.setData(uri);
                if (ActivityCompat.checkSelfPermission(MainActivity.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    // TODO: Consider calling
                    //    ActivityCompat#requestPermissions
                    // here to request the missing permissions, and then overriding
                    //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                    //                                          int[] grantResults)
                    // to handle the case where the user grants the permission. See the documentation
                    // for ActivityCompat#requestPermissions for more details.
                    return;
                }
                startActivity(it);
            }
        });
        btSendSMS.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final SmsManager smsManager=SmsManager.getDefault();
                Intent smsIntent=new Intent("ACTION_MSG_SENT");

                //Khai báo pendingintent để kiểm tra kết quả
                final PendingIntent pendingMsgSent = PendingIntent.getBroadcast(MainActivity.this, 0, smsIntent, 0);
                registerReceiver(new BroadcastReceiver() {
                    public void onReceive(Context context, Intent intent) {
                        int result = getResultCode();
                        String msg="Đã gửi tin nhắn thành công";
                        if (result != Activity.RESULT_OK) {
                            msg="Gửi tin nhắn thất bại";
                        }
                        Toast.makeText(MainActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                }, new IntentFilter("ACTION_MSG_SENT"));

                //Gọi hàm gửi tin nhắn đi
                smsManager.sendTextMessage(txtPhone.getText().toString().trim(), null, txtSMS.getText().toString(), pendingMsgSent, null);  //(txtPhone.getText().toString(), null, txtSMS.getText()+"", pendingMsgSent, null);
                finish();
            }
        });
    }

    public void sendSms(String destinationPhone, String sourcePhone) {
        //lấy mặc định SmsManager
        final SmsManager sms = SmsManager.getDefault();
        Intent msgSent = new Intent("ACTION_MSG_SENT");
        //Khai báo pendingintent để kiểm tra kết quả
        final PendingIntent pendingMsgSent = PendingIntent.getBroadcast(this, 0, msgSent, 0);
        registerReceiver(new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                int result = getResultCode();
                String msg="Send OK";
                if (result != Activity.RESULT_OK) {
                    msg="Send failed";
                }
                Toast.makeText(MainActivity.this, msg, Toast.LENGTH_LONG).show();
            }
        }, new IntentFilter("ACTION_MSG_SENT"));
        //Gọi hàm gửi tin nhắn đi
        sms.sendTextMessage(destinationPhone, null, sourcePhone+"", pendingMsgSent, null);
        finish();
    }
}
