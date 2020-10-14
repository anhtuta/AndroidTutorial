package anhtu.bkhn.service3_o7planning;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    private Button startButton;
    private Button stopButton;
    private TextView percelText;
    private ProgressBar progressBar;

    private Intent serviceIntent;
    private ResponseReceiver receiver = new ResponseReceiver();


    // Broadcast component
    // Class mô phỏng một bộ thu sóng
    // (Thu tín hiệu gửi từ Service).
    public class ResponseReceiver extends BroadcastReceiver {

        // on broadcast received
        @Override
        public void onReceive(Context context, Intent intent) {

            // Kiểm tra nhiệm vụ của Intent gửi đến.
            if(intent.getAction().equals(SimpleIntentService.ACTION_1)) {
                int value = intent.getIntExtra("percel", -1);

                new ShowProgressBarTask().execute(value);
            }
        }
    }

    // Class làm nhiệm vụ hiển thị giá trị cho ProgressBar.
    class ShowProgressBarTask extends AsyncTask<Integer, Integer, Integer> {

        @Override
        protected Integer doInBackground(Integer... args) {
            return args[0];
        }

        @Override
        protected void onPostExecute(Integer result) {
            super.onPostExecute(result);

            progressBar.setProgress(result);
            percelText.setText(result + " % Loaded");

            if (result == 100) {
                percelText.setText("Completed");
                startButton.setEnabled(true);
            }

        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.startButton= (Button) this.findViewById(R.id.button_start);
        this.stopButton = (Button)this.findViewById(R.id.button_stop);
        this.percelText = (TextView) this.findViewById(R.id.text_percel);
        this.progressBar = (ProgressBar) this.findViewById(R.id.progressBar);

        Toast.makeText(this, "progressBar.getMax = "+progressBar.getMax(), Toast.LENGTH_SHORT).show();  //kq = 100, nghĩa là giá trị max mặc định của progess bar = 100
    }

    @Override
    protected void onResume() {
        super.onResume();

        // Đăng ký bộ thu sóng với Activity.
        registerReceiver(receiver, new IntentFilter(SimpleIntentService.ACTION_1));
    }

    @Override
    protected void onStop() {
        super.onStop();

        // Hủy đăng ký bộ thu sóng với Activity.
        unregisterReceiver(receiver);
    }

    // Phương thức được gọi khi người dùng nhấn vào nút Start.
    public void startButtonClicked(View view)  {
        startButton.setEnabled(false);

        // Intent yêu cầu gửi đến Service.
        serviceIntent = new Intent(this, SimpleIntentService.class);

        // Chạy dịch vụ.
        startService(serviceIntent);
    }

    public void stopButtonClicked(View view)  {
        if(serviceIntent!= null)  {
            stopService(serviceIntent);
        }
    }

}