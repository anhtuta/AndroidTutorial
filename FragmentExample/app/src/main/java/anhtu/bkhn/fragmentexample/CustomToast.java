package anhtu.bkhn.fragmentexample;

import android.app.Dialog;
import android.content.Context;
import android.os.Handler;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.TextView;

public class CustomToast extends Dialog {
	public CustomToast(Context context, String text) {
		super(context);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		LayoutInflater inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

		View layout = inflater.inflate(R.layout.toast, null);
		TextView tv = (TextView) layout.findViewById(R.id.toastText);
		tv.setText(text);		
		setContentView(layout);
		show();
		setCanceledOnTouchOutside(true);
		setCancelable(true);
		Window window = getWindow();
		window.setGravity(Gravity.CENTER);
		new Handler().postDelayed(new Runnable() {

			public void run() {
				dismiss();
			}
		}, 2500);
	}
}
