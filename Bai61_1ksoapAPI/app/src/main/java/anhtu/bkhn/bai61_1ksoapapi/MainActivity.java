package anhtu.bkhn.bai61_1ksoapapi;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import org.ksoap2.SoapEnvelope;
import org.ksoap2.serialization.SoapObject;
import org.ksoap2.serialization.SoapPrimitive;
import org.ksoap2.serialization.SoapSerializationEnvelope;
import org.ksoap2.transport.HttpTransportSE;

import config.Configuration;

public class MainActivity extends AppCompatActivity {

    EditText txtC;
    Button btConvert;
    TextView tvF;

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtC = (EditText) findViewById(R.id.txtC);
        btConvert= (Button) findViewById(R.id.btConvert);
        tvF= (TextView) findViewById(R.id.tvF);
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Notice!");
        progressDialog.setMessage("Handling, please wait...");
        progressDialog.setCanceledOnTouchOutside(false);

        btConvert.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                convertToDegreeF();
            }
        });
    }

    private void convertToDegreeF() {
        String doC = txtC.getText().toString().trim();

        CToFTask task = new CToFTask();
        task.execute(doC);
    }

    class CToFTask extends AsyncTask<String, Void, String> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            tvF.setText("");
            progressDialog.show();
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            tvF.setText(s);
            progressDialog.dismiss();
        }

        @Override
        protected void onProgressUpdate(Void... values) {
            super.onProgressUpdate(values);
        }

        @Override
        protected String doInBackground(String... params) {
            try {
                String degreeCelsius = params[0];
                SoapObject request = new SoapObject(Configuration.NAME_SPACE, Configuration.METHOD_C_TO_F);   //request này để đưa đối tượng lên server
                request.addProperty(Configuration.PARAM_C_TO_F_CELSIUS, degreeCelsius);

                SoapSerializationEnvelope envelope = new SoapSerializationEnvelope((SoapEnvelope.VER11));
                envelope.dotNet = true;
                envelope.setOutputSoapObject(request);

                HttpTransportSE httpTransportSE = new HttpTransportSE(Configuration.SERVER_URL);
                httpTransportSE.call(Configuration.SOAP_ACTION_C_TO_F, envelope);

                SoapPrimitive data = (SoapPrimitive) envelope.getResponse();
                return data.toString();

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
            return null;
        }
    }


}
