package com.bkhn.anhtu.intentdemo;

import android.Manifest;
import android.app.SearchManager;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.StringTokenizer;

public class MainActivity extends AppCompatActivity {

    TextView label1;
    EditText txtPhoneNumber, txtWords, txtUrl;
    Button btDial, btCall;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        try {
            label1 = (TextView) findViewById(R.id.label1);
            txtPhoneNumber = (EditText) findViewById(R.id.txtPhoneNumber);

            btDial = (Button) findViewById(R.id.btnCallActivity2);
            btDial.setOnClickListener(new OpenDialEvent());

            btCall = (Button) findViewById(R.id.btCall);
            btCall.setOnClickListener(new CallNowEvent());

            txtWords = (EditText)findViewById(R.id.txtWords);
            txtUrl = (EditText)findViewById(R.id.txtUrl);
        } catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

    public void btOpenWebEvent(View view) {
        Intent intent = new Intent (Intent.ACTION_WEB_SEARCH );
        intent.putExtra(SearchManager.QUERY, txtWords.getText().toString());
        startActivity(intent);
    }

    public void btDisplayImageEvent(View view) {
        Intent myIntent = new Intent();
        myIntent.setType("image/pictures/*");
        myIntent.setAction(Intent.ACTION_GET_CONTENT);
        startActivity(myIntent);
    }

    public void btViewAWebPageEvent(View view) {
        try {

            String myUrl = txtUrl.getText().toString();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(myUrl));
            startActivity(intent);
        }catch (Exception e) {
            String myUrl = "http://"+txtUrl.getText().toString();
            Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(myUrl));
            startActivity(intent);
        }
    }

    public void btGeoCodeEvent(View view) {
        String geoCode = "geo:0,0?q=nha hat lon ha noi";//"geo:0,0?q=nha+hat+lon+ha+noi";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoCode));
        startActivity(intent);
    }

    public void btOpenMapEvent(View view) {
        EditText txtAddr = (EditText)findViewById(R.id.txtAdress);
        String addr = txtAddr.getText().toString();
        String geoCode = "geo:0,0?q="+addr.trim();//"geo:0,0?q=nha+hat+lon+ha+noi";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(geoCode));
        startActivity(intent);
    }

    public void btOpenMusicPlayerEvent(View view) {
        Intent intent = new Intent("android.intent.action.MUSIC_PLAYER");
        startActivity(intent);
    }

    public void btPlayASongEvent(View view) {
//        Intent intent = new Intent(android.content.Intent.ACTION_VIEW);
//        Uri data = Uri.parse("file:///sdcard0/1.Nhac_Like/Xin Anh Dung - LK Emily JustaTee.mp3");
//        String type = "audio/mp3";
//        intent.setDataAndType(data, type);
//        startActivity(intent);

        Intent it = new Intent();
        it.setType("audio/mp3");
        it.setAction(Intent.ACTION_VIEW);
        startActivity(it);
    }

    public void btPickContactEvent(View view) {
        try {
            String phoneNumber = "content://contacts/people/";
            Intent myActivity2 = new Intent(Intent.ACTION_PICK, Uri.parse(phoneNumber));
            startActivityForResult(myActivity2, 222);
        }
        catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            switch (requestCode){
                case (222): {
                    if (resultCode == MainActivity.RESULT_OK) {
                        String selectedContact = data.getDataString();
                        label1.setText(selectedContact.toString());
                        Intent myAct3 = new Intent (Intent.ACTION_VIEW,
                                Uri.parse(selectedContact));
                        startActivity(myAct3);
                    }
                    else {
                        label1.setText("Selection CANCELLED "
                                + requestCode + " " + resultCode);
                    }
                    break;
                }
            }//switch
        }
        catch (Exception e) {
            Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }// onActivityResult

    private class OpenDialEvent implements View.OnClickListener {
        @Override
        public void onClick(View v) {
            try {
                String myData = txtPhoneNumber.getText().toString();
                Intent myActivity2 = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + myData));  //example: tel:0975233700, note. the parameters are: action:Intent.ACTION_DIAL, and data: Uri.parse("tel:" + myData)
                startActivity(myActivity2);
            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }

    private class CallNowEvent implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            try {
                String phoneNum = txtPhoneNumber.getText().toString().trim();
                Intent intentCallNow = new Intent(Intent.ACTION_CALL, Uri.parse("tel:" + phoneNum));

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
                startActivity(intentCallNow);

            } catch (Exception e) {
                Toast.makeText(getBaseContext(), e.getMessage(), Toast.LENGTH_LONG).show();
            }
        }
    }
}

