package anhtu.bkhn.sensor1_stp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.app.Activity;
import android.hardware.Sensor;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.widget.TextView;

import java.util.List;

public class MainActivity extends Activity {
    private SensorManager sMgr;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        TextView sensorsData = (TextView)findViewById(R.id.textView1);
        sMgr = (SensorManager)this.getSystemService(SENSOR_SERVICE);
        List<Sensor> list = sMgr.getSensorList(Sensor.TYPE_ALL);
        StringBuilder data = new StringBuilder();
        for(Sensor sensor: list){
            data.append(sensor.getName() + "\n");
            data.append(sensor.getVendor() + "\n");
            data.append(sensor.getVersion() + "\n");
        }
        sensorsData.setText(data);
    }

}