package anhtu.bkhn.bai54asynctask2;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    Button btLoad;
    ImageView ivPhoto;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ivPhoto = (ImageView) findViewById(R.id.ivEmmaWatson);

        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setMessage("Loading image, please wait...");
        progressDialog.setTitle("Thong bao");
        progressDialog.setCanceledOnTouchOutside(false);    //nếu nhấn ra ngoài thì ko tắt cái này đi

        btLoad = (Button) findViewById(R.id.btLoad);
        btLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "http://www.thefamouspeople.com/profiles/images/emma-watson-3.jpg";

                new ImageTask().execute(url);
            }
        });
    }


    class ImageTask extends AsyncTask<String, Void, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog.show();
            ivPhoto.setImageBitmap(null);
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ivPhoto.setImageBitmap(bitmap);
            progressDialog.dismiss();   //ẩn cái này đi
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                String url = params[0];
                Bitmap bitmap = BitmapFactory.decodeStream(new URL(url).openConnection().getInputStream());
                return bitmap;
            } catch (IOException e) {
                Log.e("Loi: ",e.getMessage());
            }
            return null;
        }
    }
}
