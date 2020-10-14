package com.bkhn.anhtu.assetsandsharedpreference;

import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

public class LoginActivity extends AppCompatActivity {

    EditText txtUser, txtPass;
    CheckBox cbRem;
    Button btSave;

    String loginName = "thisIsJustAName";
    String USER_KEY = "keyForUser", PASS_KEY = "keyForPass", REMEMBER_KEY = "keyForCheckBok";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        addComponents();
    }

    private void addComponents() {
        txtPass = (EditText) findViewById(R.id.txtPass);
        txtUser = (EditText) findViewById(R.id.txtUser);
        cbRem = (CheckBox)findViewById(R.id.cbRemember);
    }

    @Override
    protected void onPause() {
        super.onPause();

        SharedPreferences sharedPreferences = getSharedPreferences(loginName, MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString(USER_KEY, txtUser.getText().toString().trim());
        editor.putString(PASS_KEY, txtPass.getText().toString().trim());
        editor.putBoolean(REMEMBER_KEY, cbRem.isChecked());
        editor.commit();

        Toast.makeText(this, "calling onPause, saved Information", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();

        SharedPreferences sharedPreferences = getSharedPreferences(loginName, MODE_PRIVATE);
        String user = sharedPreferences.getString(USER_KEY, "");
        String pass = sharedPreferences.getString(PASS_KEY, "");
        Boolean isRemember = sharedPreferences.getBoolean(REMEMBER_KEY, false);

        if(isRemember) {
            txtUser.setText(user);
            txtPass.setText(pass);
            cbRem.setChecked(true);
        }

        Toast.makeText(this, "calling onResume", Toast.LENGTH_SHORT).show();
    }

    public void btQuitEvent(View view) {
        finish();
    }
}
