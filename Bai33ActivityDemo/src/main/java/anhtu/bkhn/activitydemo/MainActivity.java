package anhtu.bkhn.activitydemo;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

//đầu tiên mở app lên là thì chạy hàm onStart -> onResume
//Bắt đầu từ sau onResume chính là Foreground lifetime
//ấn btCheKhuatToanBoManHinh thì chuyển sang MainActivity2, và gọi hàm onPause -> onStop
//onStop là chờ chết
//khi ấn nút quay lại (nút back của máy chứ ko phải btBack) thì trở về MainActivity và gọi hàm onRestart -> onStart -> onResume

//Lưu trạng thái trong onPause, phục hồi trạng thái trong onResume, 2 sự kiện này tự động xảy ra mà ko cần gọi hay làm j cả

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    public void eventBtnCheToanBo(View view) {
        Intent intent=new Intent(MainActivity.this, MainActivity2.class);
        startActivity(intent);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Toast.makeText(MainActivity.this, "onStop is calling", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(MainActivity.this, "onDestroy is calling", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onPause() {
        super.onPause();
        Toast.makeText(MainActivity.this, "onPause is calling", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onResume() {
        super.onResume();
        Toast.makeText(MainActivity.this, "onResume is calling", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        Toast.makeText(MainActivity.this, "onStart is calling", Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        Toast.makeText(MainActivity.this, "onRestart is calling", Toast.LENGTH_SHORT).show();
    }

    public void eventBtnChe1Phan(View view) {
        Intent intent=new Intent(MainActivity.this, MainActivity3.class);
        startActivity(intent);
    }
}
