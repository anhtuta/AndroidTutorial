package anhtu.bkhn.bubbleshot;

import android.animation.Animator;
import android.animation.AnimatorInflater;
import android.animation.ObjectAnimator;
import android.annotation.TargetApi;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Random;

public class MainActivity extends AppCompatActivity {

    int score=0;
    Random rd;
    TextView txtscore;
    ActionBar.LayoutParams params;
    LinearLayout layoutBubble;
    Button btnCreateBubble;
    ObjectAnimator objectAnimator;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        addControls();
        addEvents();
    }

    private void addControls() {
        txtscore = (TextView) findViewById(R.id.txtScore);
        rd = new Random();
        layoutBubble = (LinearLayout)
                findViewById(R.id.layoutBubble);

        params = new ActionBar.LayoutParams(
                        LinearLayout.LayoutParams.WRAP_CONTENT,
                        LinearLayout.LayoutParams.WRAP_CONTENT
        );

        btnCreateBubble = (Button) findViewById(R.id.btnCreateBubble);
    }

    private void addEvents() {
        btnCreateBubble.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                for (int i = 0; i <= rd.nextInt(5); i++) {
                    ProcessAnim();
                }
            }
        });
    }


    @TargetApi(Build.VERSION_CODES.JELLY_BEAN)
    protected void ProcessAnim() {
        // TODO Auto-generated method stub
        //Drawing a bubble
        ImageView img = getImageView();

        img.setBackground(getDrawable());
        img.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View arg0) {
                // TODO Auto-generated method stub
                layoutBubble.removeView(arg0);
                txtscore.setText("Score : "+(score+=1));
            }
        });

        objectAnimator = (ObjectAnimator)
                AnimatorInflater.
                        loadAnimator(MainActivity.this,
                                R.animator.bubble_animation);
        objectAnimator.setDuration(rd.nextInt(1000)+2000);
        objectAnimator.setTarget(img);

        layoutBubble.addView(img,params);

        objectAnimator.addListener(new Animator.AnimatorListener() {

            @Override
            public void onAnimationStart(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationRepeat(Animator animation) {
                // TODO Auto-generated method stub

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                // TODO Auto-generated method stub
                layoutBubble.removeView((View)
                        ((ObjectAnimator)animation).getTarget());
            }

            @Override
            public void onAnimationCancel(Animator animation) {
                // TODO Auto-generated method stub

            }
        });

        objectAnimator.start();
    }

    public ImageView getImageView()
    {
        ImageView img =
                new ImageView(MainActivity.this);
        img.setX(rd.nextInt(500));
        return img;
    }

    public Drawable getDrawable()
    {
        Drawable draw;
        int i = rd.nextInt(3);
        switch (i) {
            case 0:
                draw = getResources().
                        getDrawable(R.drawable.bubble_blue);
                break;
            case 1:
                draw = getResources().
                        getDrawable(R.drawable.bubble_blue2);
                break;
            default:
                draw = getResources().
                        getDrawable(R.drawable.bubble_pink);
                break;
        }
        return draw;
    }
}
