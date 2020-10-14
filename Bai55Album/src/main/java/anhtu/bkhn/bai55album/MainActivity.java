package anhtu.bkhn.bai55album;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class MainActivity extends AppCompatActivity {

    ImageView ivPhoto;
    ImageButton ibNext, ibPrev;
    CheckBox cb;

    int currPosition = -1;
    ArrayList<String> album;
    ImagesTask imagesTask;

    Timer timer = null;
    TimerTask timerTask=null;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addComponents();
        addEvents();
    }

    private void addComponents() {
        ivPhoto = (ImageView) findViewById(R.id.ivPhoto);
        ibNext = (ImageButton) findViewById(R.id.ivNext);
        ibPrev = (ImageButton) findViewById(R.id.ivPrev);
        cb = (CheckBox) findViewById(R.id.cb);

        progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("this is a title");
        progressDialog.setMessage("Downloading, please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        album=new ArrayList<>();
        album.add("https://s-media-cache-ak0.pinimg.com/736x/6d/8d/3f/6d8d3fafd6ee9aeeaca2c3b300f5e5ae.jpg");
        album.add("https://encrypted-tbn2.gstatic.com/images?q=tbn:ANd9GcTB8FoM4tAgyfQT4QTFV53LOKhm0nxgspJKo-MhlNH_xQfPhAaW");
        album.add("http://static.hdonline.vn/i/resources/new/post/thumb/2016/06/05/hoat-hinh-dragon-ball-tiet-lo-phan-moi-voi-ke-thu-den-tu-tuong-lai.jpg");;
        album.add("https://encrypted-tbn1.gstatic.com/images?q=tbn:ANd9GcSsOIPGfmFZ9773-xUTW2KWDZma8HuwtlAZYf9YM3IeyN5J-5nh");;
        album.add("http://farm6.staticflickr.com/5573/30152749663_191aff05b7_o.jpg");;
        album.add("http://static.appstore.vn/a/uploads/thumbnails/022016/dragon-ball-z-dokkan-battle_icon.png");;
        album.add("http://vignette3.wikia.nocookie.net/dragonball/images/2/29/6846_dragon_ball_z_hd_wallpapers.jpg/revision/latest?cb=20140822015018");;
        currPosition=0;

        imagesTask = new ImagesTask();   //chú ý: phải tạo mới 1 đối tượng ImagesTask riêng để thực hiện, khi ấn nút ibNext hay ibPrev cũng phải tạo mới 1 đối tượng ImagesTask để thực hiện.
        imagesTask.execute(album.get(0));

    }

    private void addEvents() {
        ibNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchNextPhoto();
            }
        });
        ibPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                watchPrevPhoto();
            }
        });
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked==true) {
                    ibNext.setEnabled(false);
                    ibPrev.setEnabled(false);

                    autoDownloadImages();
                } else {
                    ibNext.setEnabled(true);
                    ibPrev.setEnabled(true);
                    if(timer!=null) timer.cancel();
                }
            }
        });

    }

    private void autoDownloadImages() {
        timerTask=new TimerTask() { //TimeTask cũng là đa tiến trình, nhưng là dạng định giờ
            @Override
            public void run() {
                runOnUiThread(new Runnable() {      //draw GUI while download,...
                    @Override
                    public void run() {
                        currPosition++;
                        if(currPosition==album.size()) currPosition=0;
                        ImagesTask imagesTask = new ImagesTask();
                        imagesTask.execute(album.get(currPosition));
                    }
                });
            }
        };
        timer = new Timer();
        timer.schedule(timerTask, 0, 3000); //tham số 2 là delay (khi chạy lên thì delay 1 khoảng thời gian mới bắt đầu chạy), tham số 3 là thời gian lặp lại

    }

    private void watchPrevPhoto() {
        currPosition--;
        if(currPosition==-1) currPosition=album.size()-1;
        imagesTask = new ImagesTask();
        imagesTask.execute(album.get(currPosition));
    }

    private void watchNextPhoto() {
        currPosition++;
        if(currPosition == album.size()-1) currPosition=0;
        imagesTask = new ImagesTask();
        imagesTask.execute(album.get(currPosition));
    }

    ///inner class:
    class ImagesTask extends AsyncTask<String, Void, Bitmap> {  //vì ko biết bao giờ download xong nên tham số 2 ta nên để kiểu Void
        //Tham số 1 là đầu vào cho doInBackground(), ở đây ta cần 1 đường link nên nó là kiểu String
        //tham số 2 dùng cho hàm onProgressUpdate()
        //tham số 3 là kq trả về sau khi thực hiện xong doInBackground(), ở đây ta cần 1 bitmap để setImageBitmap cho cái ivPhoto nên nó là kiểu Bitmap

        @Override
        protected void onPreExecute() {     //this method will be called firstly.
            super.onPreExecute();
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(Bitmap bitmap) {
            super.onPostExecute(bitmap);
            ivPhoto.setImageBitmap(bitmap);
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            String url = params[0];
            try {
                Bitmap bitmap = BitmapFactory.decodeStream(new URL(url).openConnection().getInputStream());
                return bitmap;
            } catch (IOException e) {
                e.printStackTrace();
            }

            ///if no connection: return ic_no_connection.png:
            BitmapDrawable bitmapDrawable = (BitmapDrawable) getDrawable(R.drawable.ic_no_connection);
            Bitmap bitmap = bitmapDrawable.getBitmap();
            return bitmap;
        }
    }
}
