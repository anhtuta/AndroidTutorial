package anhtu.bkhn.service2_o7planning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private boolean binded=false;
    private WeatherService weatherService;

    private TextView weatherText;
    private EditText locationText;

    ServiceConnection weatherServiceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            //Toast.makeText(weatherService, "onServiceConnected", Toast.LENGTH_SHORT).show();
            WeatherService.LocalWeatherBinder binder = (WeatherService.LocalWeatherBinder) service;
            weatherService = binder.getService();
            binded = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            Toast.makeText(weatherService, "onServiceDisconnected", Toast.LENGTH_SHORT).show();
            binded = false;
        }
    };

    // Khi Activity tạo giao diện của nó.
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        weatherText = (TextView) this.findViewById(R.id.text_weather);
        locationText = (EditText)this.findViewById(R.id.text_input_location);
    }

    // Khi Activity start.
    @Override
    protected void onStart() {
        super.onStart();

        // Tạo đối tượng Intent cho WeatherService.
        Intent intent = new Intent(this, WeatherService.class);

        // Gọi method bindService(..) để giàng buộc dịch vụ với giao diện.
        this.bindService(intent, weatherServiceConnection, Context.BIND_AUTO_CREATE);
    }

    // Khi Activity ngừng hoạt động.
    @Override
    protected void onStop() {
        super.onStop();
        if (binded) {
            // Hủy giàng buộc kết nối với dịch vụ.
            this.unbindService(weatherServiceConnection);
            binded = false;
        }
    }

    // Khi click vào Button xem thời tiết.
    public void showWeatherEvent(View view)  {
        String location = locationText.getText().toString().trim();
        String weather= this.weatherService.getWeatherToday(location);

        weatherText.setText(weather);
    }

    public void stopBoundServiceEvent(View view) {
        if(binded) {
            this.unbindService(weatherServiceConnection);
            binded=false;
            Toast.makeText(weatherService, "Stopped", Toast.LENGTH_SHORT).show();
        }
    }
}
