package com.dhbkhn.anhtu.async_demo;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    private ImageView hinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        hinh = (ImageView) findViewById(R.id.imageViewLogo);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new LoadImageFromInternet().execute("https://scontent.fhan3-1.fna.fbcdn.net/v/t1.0-9/16143082_1957393551160021_225590936940155142_n.jpg?oh=329e67c830e8d9c2dbeabf2cf92d9892&oe=5902A5B8"); //link của 1 hình ảnh trên internet

            }
        });

    }

    private class LoadImageFromInternet extends AsyncTask<String, Integer, String> {

        @Override
        protected String doInBackground(String... params) {
            try {
                URL u = new URL(params[0]);
                Bitmap bmp = BitmapFactory.decodeStream(u.openConnection().getInputStream());
                hinh.setImageBitmap(bmp);
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            Toast.makeText(MainActivity.this, "Load finished", Toast.LENGTH_LONG).show();
        }
    }
}
