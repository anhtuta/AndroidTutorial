package anhtu.bkhn.asynctaskdemo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

//Để sử dụng AsyncTask chúng ta tạo một class và kế thừa lại class AsyncTask, thực thi hàm doInBackground().
//Để cập nhật giao diện chúng ta sử dụng hàm onPostExecute() hàm này sẻ chuyển giao kết quả từ hàm doInBackground()
//và chạy trên UI thread.

//Giới thiệu AsyncTask  trong Android
//        AsyncTask là phương tiện khác để xử lý công việc sử dụng background thread và giao tiếp với UI thread mà không dùng Thread hay Handler
//
//        Trong AsyncTask<Params, Progress, Result>  có 3 đối số là các Generic Type:
//
//        Params: Là giá trị ((biến) được truyền vào khi gọi thực thi tiến trình và nó sẽ  được truyền vào doInBackground
//
//        Progress: Là  giá trị (biến) dùng để update giao diện diện lúc tiến trình thực thi, biến này sẽ được truyền vào hàm onProgressUpdate.
//
//        Result: Là biến dùng để lưu trữ kết quả trả về sau khi tiến trình thực hiện xong.
//
//        Những đối số nào không sử dụng trong quá trình thực thi tiến trình thì ta thay bằng Void.
//
//        Thông thường trong 1 AsyncTask sẽ chứa 4 hàm, đó là :
//
//        onPreExecute() : Tự động được gọi đầu tiên khi tiến trình được kích hoạt.
//
//        doInBackground(): Được thực thi trong quá trình tiến trình chạy nền, thông qua hàm này để ta gọi hàm onProgressUpdate để cập nhật giao diện (gọi lệnh publishProgress). Ta không thể cập nhật giao diện trong hàm doInBackground().
//
//        onProgressUpdate (): Dùng để cập nhật giao diện lúc runtime
//
//        onPostExecute(): Sau khi tiến trình kết thúc thì hàm này sẽ tự động sảy ra. Ta có thể lấy được kết quả trả về sau khi thực hiện tiến trình kết thúc ở đây.
//
//        Trong 4 hàm trên thì hàm doInBackground() bắt buộc phải tồn tại, còn các hàm khác có thể khuyết, nhưng theo Tui các bạn nên sử dụng đầy đủ 4 hàm đã nêu.



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
