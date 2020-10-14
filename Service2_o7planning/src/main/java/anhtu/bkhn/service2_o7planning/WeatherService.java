package anhtu.bkhn.service2_o7planning;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;
import android.widget.Toast;


import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;


public class WeatherService extends Service {

    // Lưu trữ dữ liệu thời tiết.
    private final Map<String, String> weatherDataMap = new HashMap<String, String>();
    private final IBinder binder = new LocalWeatherBinder();

    public class LocalWeatherBinder extends Binder {

        public WeatherService getService() {
            return WeatherService.this;
        }
    }

    public WeatherService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        Toast.makeText(this, "onBind", Toast.LENGTH_SHORT).show();
        return this.binder;
    }

    @Override
    public void onRebind(Intent intent) {
        Toast.makeText(this, "onReBind", Toast.LENGTH_SHORT).show();
        super.onRebind(intent);
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Toast.makeText(this, "onUnBind", Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }

    // Trả về thông tin thời tiết ứng với địa điểm của ngày hiện tại.
    public String getWeatherToday(String location) {
        Date now = new Date();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");

        String dayString = df.format(now);
        String keyLocAndDay = location + "$" + dayString;

        String weather = weatherDataMap.get(keyLocAndDay);
        //
        if (weather != null) {
            return weather;
        }

        //
        String[] weathers = new String[]{"Rainy", "Hot", "Cool", "Warm", "Snowy", "Sweltering", "scorching", "Boiling", "Chilly", "frosty", "freezing", "numbing"};

        // Giá trị ngẫu nhiên từ 0 tới weathers.size
        int i = new Random().nextInt(weathers.length);

        weather = weathers[i];
        weatherDataMap.put(keyLocAndDay, weather);     //ví dụ: put(hanoi22102017, Rainy);
        
        return weather;
    }


}