package vn.edu.hust.tung.myradio;

import android.app.Activity;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import vn.edu.hust.tung.myradio.Objects.RadioChannel;
import vn.edu.hust.tung.myradio.R;

/**
 * Created by tung on 3/19/17.
 */

public class RadioHolder{
    TextView tvRadio = null;
    ImageView ivRadio = null;
    Activity activity;

    private int id_image[] = {R.drawable.us_radio,
            R.drawable.bbc1, R.drawable.bbc2, R.drawable.danceuk, R.drawable.hit90,
            R.drawable.coasttwo, R.drawable.onefm, R.drawable.stoke,
            R.drawable.firststep, R.drawable.us_radio, R.drawable.us_radio, R.drawable.play105,
            R.drawable.classicrockfloridalogo, R.drawable.us_radio, R.drawable.us_radio
    };

    public RadioHolder(View row, Activity activity){
        this.activity = activity;
        tvRadio = (TextView) row.findViewById(R.id.textView_radio);
        ivRadio = (ImageView) row.findViewById(R.id.iv_radio);
    }

    public void setView(RadioChannel radioChannel) {
        Log.i("on set", " flag");
        try{
            tvRadio.setText(radioChannel.getName());
            //new SetImage(radioChannel, ivRadio, activity).execute("");
            //ivRadio.setImageResource(radioChannel.getId());
            ivRadio.setImageResource(id_image[radioChannel.getId()]);
        }catch (Exception e){
            System.err.println("radioChannel.getId() = "+radioChannel.getId());
            Toast.makeText(activity, e.getMessage(), Toast.LENGTH_SHORT).show();
            System.out.println(e.getMessage());
        }
    }

    //  chú ý: NẾU KO CÓ ACTIVITY LÀ SAI, ko chạy đc:
//    private TextView tvRadio;
//    private ImageView ivRadio;
//
//    public RadioHolder(View row) {
//        tvRadio = (TextView) row.findViewById(R.id.textView_radio);
//        ivRadio = (ImageView) row.findViewById(R.id.iv_radio);
//    }
//
//    public void setView(RadioChannel radioChannel) {
//        tvRadio.setText(radioChannel.getName());
//        ivRadio.setImageResource(radioChannel.getId());
//    }
}
