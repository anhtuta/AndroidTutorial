package com.dhbk.anhtu.sharedpreferences;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtUser, txtPass;
    Button btOk;
    CheckBox cbRem;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor; //editor để chỉnh sửa, đưa dữ liệu vào sharedPreferences
    String USERNAME_KEY = "user";
    String PASSWORD_KEY = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addComponents();

        sharedPreferences=getSharedPreferences("loginPrefs",MODE_PRIVATE); //riêng tư so với các app khác
        txtUser.setText(sharedPreferences.getString(USERNAME_KEY, ""));  //lấy biến dữ liệu đã lưu trước đó và settext cho cái txtUser, nếu ko tìm thấy, tức là trước đó người dùng chưa lưu lại thì ta để cái edittext đó rỗng
        txtPass.setText(sharedPreferences.getString(PASSWORD_KEY, ""));

        addEvents();
    }

    private void addComponents() {
        txtUser=(EditText)findViewById(R.id.txtUser);
        txtPass=(EditText)findViewById(R.id.txtPass);
        btOk=(Button) findViewById(R.id.btOk);
        cbRem=(CheckBox) findViewById(R.id.cbRemember);

    }

    private void addEvents() {
        btOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user = txtUser.getText().toString().trim();
                String pass = txtPass.getText().toString().trim();

                if(user.equals("anhtu") && pass.equals("5555")) {  //Nếu đăng nhập đúng user và pass
                    Toast.makeText(MainActivity.this, "Login successful", Toast.LENGTH_SHORT).show();

                    if(cbRem.isChecked()) {//lưu user và pass :
                        editor = sharedPreferences.edit(); //cấp quyền có thể chỉnh sửa
                        editor.putString(USERNAME_KEY, txtUser.getText().toString().trim());  //đưa data vào sharedPreferences thông qua biến editor
                        editor.putString(PASSWORD_KEY, txtPass.getText().toString().trim());  //tham số 1 là key, tham số 2 là value
                        editor.commit();
                    }
                }
                else Toast.makeText(MainActivity.this, "Login failed", Toast.LENGTH_SHORT).show();
            }
        });
    }
}
