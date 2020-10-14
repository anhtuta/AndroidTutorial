package anhtu.bkhn.asynctaks1withprogressbar;

import android.os.AsyncTask;
import android.os.SystemClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    EditText txtButtonNumber;
    Button btDraw;
    ProgressBar progressBarPercent;
    LinearLayout layoutButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addComponents();
        addEventts();
    }

    private void addComponents() {
        txtButtonNumber = (EditText) findViewById(R.id.txtButton);
        btDraw = (Button) findViewById(R.id.btDrawBtn);
        progressBarPercent  = (ProgressBar) findViewById(R.id.progressBarPercent);
        layoutButton = (LinearLayout) findViewById(R.id.layoutButton);
    }

    private void addEventts() {
        btDraw.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawButtonInRealTime();
            }

        });
    }

    private void drawButtonInRealTime() {
        int n = Integer.parseInt(txtButtonNumber.getText().toString().trim());
        ButtonTask buttonTask = new ButtonTask();
        buttonTask.execute(n);
    }


    ///inner class:
    class ButtonTask extends AsyncTask<Integer, Integer, Void> {  //tham so 1 = input (so luong button), tham so 2 = so luong phan tram, tham so 3 la kq, ko quan tam
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            layoutButton.removeAllViews();  //delete all buttons before start
            progressBarPercent.setProgress(0);
        }

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            progressBarPercent.setProgress(100);
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            int value = values[1];
            int percent = values[0];
            progressBarPercent.setProgress(percent);

            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.MATCH_PARENT, //width
                    LinearLayout.LayoutParams.WRAP_CONTENT  //height
            );
            Button btn = new Button(MainActivity.this);
            btn.setLayoutParams(params);
            btn.setText(value+"");

            layoutButton.addView(btn);
        }

        @Override
        protected Void doInBackground(Integer... params) {
            int n = params[0];  //mặc định n = giá trị input(tham số đầu tiên trong ButtonTask<>, và vì mảng params chỉ có 1 phần tử nên n = phần tử đầu tiên của mảng đó
            Random rd = new Random();
            for(int i=0; i<n; i++) {
                int percent = i*100/n;
                int value = rd.nextInt(500);
                publishProgress(percent, value);
                SystemClock.sleep(100);
            }

            return null;
        }
    }
}
