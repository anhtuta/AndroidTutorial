package anhtu.bkhn.asynctask2;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
public class MainActivity extends AppCompatActivity {


    Button btStart;
    ProgressBar rotatePrb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        rotatePrb = (ProgressBar) findViewById(R.id.rotateProgressBar);
        rotatePrb.setVisibility(View.INVISIBLE);
        btStart = (Button) findViewById(R.id.btnStart);
        btStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //rotatePrb.setEnabled(true);
                rotatePrb.setVisibility(View.VISIBLE);
                new MyAsyncTask(MainActivity.this, rotatePrb).execute();
            }
        });
    }
}
