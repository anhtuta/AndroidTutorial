package com.dhbk.anhtu.bai36resultbetweenactivitiesmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class HandlingActivity extends AppCompatActivity {

    TextView txtGotResult;
    Button btTinhUC;
    Intent intent;
    int a,b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_handling);

        txtGotResult=(TextView)findViewById(R.id.txtGotResult);
        btTinhUC=(Button)findViewById(R.id.btTinhUSCLN);
        intent=getIntent(); //step2

        a=intent.getIntExtra("a", -1); //=-1 nếu ko tìm thấy số có tên là "a"
        b=intent.getIntExtra("b", -1);
        txtGotResult.setText("a = "+a+"\n"+"b = "+b);

        btTinhUC.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int ucln=USCLN(a,b);
                intent.putExtra("USCLN", ucln);  //step3: change infomation, we should reuse the old intent, don't need to create the new one
                setResult(33, intent); //step4: đánh dấu kq trả về, tham số 1 là mã kq, tham số 2 là intent
                finish(); //step5: close this screen, để MainActivity trở thành foreground lifetime, vì nó chỉ nhận đc kq tại  foreground lifetime
            }
        });
    }

    public int USCLN(int a, int b) {
            while(a!=b) {
                if(a>b) a=a-b;
                else if(b>a) b=b-a;
            }
            return a;

    }
}
