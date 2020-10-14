package anhtu.bkhn.drawable_stp;
import java.io.InputStream;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.view.Menu;
import android.widget.ImageView;

public class MainActivity extends Activity {

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView imageView = (ImageView) findViewById(R.id.image);
//        InputStream resource = getResources().openRawResource(R.drawable.goku);
//        Bitmap bitmap = BitmapFactory.decodeStream(resource);
        //imageView.setBackground(new MyRoundCornerDrawable(bitmap));
        imageView.setBackground(getResources().getDrawable(R.drawable.goku));
    }

}