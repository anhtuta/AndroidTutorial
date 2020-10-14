package com.dhbk.anhtu.bai36resultbetweenactivitiesmanagement;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtA, txtB;
    Button btSend;
    TextView txtKq;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addComponents();
        addEvents();
    }

    private void addComponents() {
        txtA=(EditText) findViewById(R.id.txtA);
        txtB=(EditText) findViewById(R.id.txtB);
        btSend=(Button) findViewById(R.id.btSend);
        txtKq=(TextView)findViewById(R.id.txtKq);
    }

    private void addEvents() {
        btSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    Intent intent = new Intent(MainActivity.this, HandlingActivity.class);
                    intent.putExtra("a", Integer.parseInt(txtA.getText().toString().trim()));
                    intent.putExtra("b", Integer.parseInt(txtB.getText().toString().trim()));

                    //step1:
                    startActivityForResult(intent, 95);  //tham số 1 là intent muốn gọi, tham số 2 là mã yêu cầu, mã thì ta tùy chọn
                }catch (Exception e) {
                    Toast.makeText(MainActivity.this, "Invalid input. Please enter an integer number!", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //requestCode=mã yêu cầu ta gửi đi = 95
        //resultCode = mã trả về = 33, chú ý ta phải chọn 2 mã này khác nhau
        super.onActivityResult(requestCode, resultCode, data);

        //step6:
        if(requestCode==95 && resultCode==33) {  //phải dùng lệnh if để kiểm ra xem đúng mã ko, vì có thể có nhiều mã gửi đi và gửi về
            int kq=data.getIntExtra("USCLN", -1); //nếu ko tìm thấy biến nào tên "UCLN" thì kq=-1
            txtKq.setText("Ket qua = "+kq);
        }
    }
}
