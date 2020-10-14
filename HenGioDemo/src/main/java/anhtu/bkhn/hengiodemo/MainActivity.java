package anhtu.bkhn.hengiodemo;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtTime;
    TextView tvProgress;
    int time = 0;
    Button btStart;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTime= (EditText) findViewById(R.id.txtTime);
        tvProgress= (TextView) findViewById(R.id.tvProgress);
        btStart= (Button) findViewById(R.id.btStart);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Toast.makeText(this, "onDestroy", Toast.LENGTH_SHORT).show();
    }

    public void btStartEvent(View view) {
        int timeSecond = Integer.valueOf(txtTime.getText().toString().trim());
        TimeTask timeTask = new TimeTask();
        timeTask.execute(timeSecond);
    }

    class TimeTask extends AsyncTask<Integer, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            time = 0;
            btStart.setEnabled(false);
            Toast.makeText(MainActivity.this, "Starting timer", Toast.LENGTH_SHORT).show();
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            super.onPostExecute(aBoolean);
            if(aBoolean == true) {
                Toast.makeText(MainActivity.this, "time's up. exiting app...", Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            tvProgress.setText("current timer is: "+values[0]+" (s)");
        }

        @Override
        protected Boolean doInBackground(Integer... params) {
            for(int i =0; i< params[0]; i++) {
                time++;
                SystemClock.sleep(1000);
                publishProgress(time);
            }
            return true;
        }
    }
}
