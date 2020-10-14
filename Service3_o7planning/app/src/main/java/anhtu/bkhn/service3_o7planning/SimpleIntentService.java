package anhtu.bkhn.service3_o7planning;

import android.app.IntentService;
import android.content.Intent;
import android.content.Context;

import android.app.IntentService;
import android.content.Intent;
import android.os.SystemClock;


public class SimpleIntentService extends IntentService {

    public static final String ACTION_1 = "THIS_IS_MY_ACTION";

    public SimpleIntentService() {
        super("SimpleIntentService");
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        // Tạo một đối tượng Intent (Một đối tượng phát sóng).
        Intent broadcastIntent = new Intent();

        // Sét tên hành động (Action) của Intent này.
        // Một Intent có thể thực hiện nhiều hành động khác nhau.
        // (Có thể coi là nhiều nhiệm vụ).
        broadcastIntent.setAction(SimpleIntentService.ACTION_1);


        // Vòng lặp 100 lần phát sóng của Intent.
        for (int i = 0; i <= 100; i++) {

            // Sét đặt giá trị cho dữ liệu gửi đi.
            // (Phần trăm của công việc).
            broadcastIntent.putExtra("percel", i);

            // Send broadcast
            // Phát sóng gửi đi.
            sendBroadcast(broadcastIntent);

            // Sleep 50 Milliseconds.
            SystemClock.sleep(50);
        }

    }
}