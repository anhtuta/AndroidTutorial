package anhtu.bkhn.alarmmanagerdemo;

/**
 * Created by anhtu on 16/04/2017.
 */
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.widget.Toast;

public class MyBroadcastReceiver extends BroadcastReceiver {
    MediaPlayer mp;
    @Override
    public void onReceive(Context context, Intent intent) {
        mp=MediaPlayer.create(context, R.raw.noi_nay_co_anh);
        mp.start();
        Toast.makeText(context, "Start playing the song: noi_nay_co_anh....", Toast.LENGTH_LONG).show();
    }
}