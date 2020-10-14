package anhtu.bkhn.asynctask2;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.SystemClock;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by anhtu on 08/04/2017.
 */

//Trước tiên chúng ta tạo một class MyAsyncTask.java được kế thừa từ class AsyncTask.
//
//        AsyncTask hỗ trợ việc đa tiến trình và cập nhật giao diện tổng quát trên Android, các đối số đầu vào của nó củng là kiểu generic.
//
//
//        AsyncTask<Params, ProgressValue, ResultValue>
//
//        Trong đó:
//
//        Params: Đối số đầu vào của hàm doInBackground()
//        ProgressValue: Kết quả trung gian để xử lý trạng thái của tiến trình ví dụ cập nhật trạng thái download.
//        ResultValue: Kết quả trả về của hàm doInBackground() và dùng làm đối số đầu vào cho hàm onPostExecute()
//

public class MyAsyncTask extends AsyncTask<Void, Integer, Void> {
    Activity contextCha;    //khai báo Activity để lưu trữ địa chỉ của MainActivity
    ProgressBar rotatePrb;

    public MyAsyncTask(Activity ctx, ProgressBar prb) {  //constructor này được truyền vào là MainActivity
        contextCha = ctx;
        rotatePrb = prb;
    }

    //hàm này sẽ được thực hiện đầu tiên
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        Toast.makeText(contextCha, "onPreExecute!", Toast.LENGTH_SHORT).show();
    }

    //sau đó tới hàm doInBackground
    //tuyệt đối không được cập nhật giao diện trong hàm này
    @Override
    protected Void doInBackground(Void... arg0) {
        for (int i = 0; i <= 100; i++) {
            SystemClock.sleep(30);  //nghỉ 100 milisecond thì tiến hành update UI
            publishProgress(i, i);      //khi gọi hàm này thì onProgressUpdate sẽ thực thi
        }
        return null;
    }

    /**
     * ta cập nhập giao diện trong hàm này
     */
    @Override
    protected void onProgressUpdate(Integer... values) {
        // TODO Auto-generated method stub
        super.onProgressUpdate(values);
        //thông qua contextCha để lấy được control trong MainActivity
        ProgressBar prbCha = (ProgressBar) contextCha.findViewById(R.id.prbDemo);

        //vì publishProgress truyền 2 đối số
        //nên mảng values có 2 phần tử
        int giatri = values[0];
        prbCha.setProgress(giatri);        //tăng giá trị của Progressbar lên
        TextView txtmsg = (TextView) contextCha.findViewById(R.id.txtStatus);
        txtmsg.setText(giatri + "%");   //đồng thời hiện thị giá trị là % lên TextView

        Random rd = new Random();
        int a = 1+values[1]*values[1];
        int value = rd.nextInt(a);   //lấy random trong khoảng (0,x), với x = bình phương tham số thứ 2 của hàm publishProgress()
        TextView txt2 = (TextView) contextCha.findViewById(R.id.txtRandom);
        txt2.setText("Random of "+(a-1)+" is: "+value);
    }

    /**
     * sau khi tiến trình thực hiện xong thì hàm này sảy ra
     */
    @Override
    protected void onPostExecute(Void result) {
        // TODO Auto-generated method stub
        super.onPostExecute(result);
        Toast.makeText(contextCha, "Đã update xong!", Toast.LENGTH_SHORT).show();
        rotatePrb.setVisibility(View.INVISIBLE);
    }
}
