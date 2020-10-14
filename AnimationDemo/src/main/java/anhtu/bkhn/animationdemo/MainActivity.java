package anhtu.bkhn.animationdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    Button btXoayControl, btXoayManHinh,btXoay3s, btHieuUngListView;
    Animation animation = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addComponents();
        addEvents();
    }

    private void addEvents() {
        btXoayControl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.xoaycontrol);
                btXoayControl.startAnimation(animation);
            }
        });
        btXoayManHinh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.xoaymanhinh);
                LinearLayout activity_main = (LinearLayout) findViewById(R.id.activity_main);
                activity_main.startAnimation(animation);
            }
        });
        btXoay3s.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation = AnimationUtils.loadAnimation(MainActivity.this, R.anim.xoaynguoc3s);
                btXoay3s.startAnimation(animation);

                //after 3s: turn off app:
                animation.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        Toast.makeText(MainActivity.this, "turning off this app. Bye", Toast.LENGTH_SHORT).show();
                        finish();
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
            }
        });
    }

    private void addComponents() {
        btHieuUngListView = (Button) findViewById(R.id.btHieuUngListView);
        btXoay3s = (Button) findViewById(R.id.btXoay3s);
        btXoayControl = (Button) findViewById(R.id.btXoayControl);
        btXoayManHinh = (Button) findViewById(R.id.btXoayManHinh);


    }
}
