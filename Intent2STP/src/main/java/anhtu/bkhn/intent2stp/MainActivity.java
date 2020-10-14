package anhtu.bkhn.intent2stp;

import android.content.Intent;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        Intent intent = getIntent();
        TextView text = (TextView) findViewById(R.id.textView);
        // To get the action of the intent use
        String action = intent.getAction();
        if (!action.equals(Intent.ACTION_VIEW)) {
            throw new RuntimeException("Should not happen");
        }
        // To get the data use
        Uri data = intent.getData();
        URL url;
        try {
            url = new URL(data.getScheme(), data.getHost(), data.getPath());
            BufferedReader rd = new BufferedReader(new
                    InputStreamReader(url.openStream()));
            String line = "";
            while ((line = rd.readLine()) != null) {
                text.append(line);
            }
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
    }

}
