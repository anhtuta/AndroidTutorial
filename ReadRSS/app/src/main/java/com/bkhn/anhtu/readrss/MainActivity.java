package com.bkhn.anhtu.readrss;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//đã xem xong 3 video đầu, còn 2 video nữa. đếch hiểu j
public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                new ReadData().execute("http://vnexpress.net/rss/khoa-hoc.rss");
            }
        });
    }

    class ReadData extends AsyncTask<String, Integer, String> { //tham số 1 ta sẽ truyền vào 1 đường link URL, nên có dạng String, Tham số 2 là Progress, ta để kiểu Integer, thứ 3 là kq trả về, ta chọn là String

        @Override
        protected String doInBackground(String... params) {
            return readDataFromInternet(params[0]);
        }

        @Override
        protected void onPostExecute(String s) {
            XMLDOMParser parser = new XMLDOMParser();
            Document document = parser.getDocument(s);
            NodeList nodeList = document.getElementsByTagName("item");
            NodeList nodeListDescription = document.getElementsByTagName("description");
            String image = "";
            String title = "";
            String link = "";

            for (int i = 0; i<nodeList.getLength(); i++) {
                String cdata = nodeListDescription.item(i+1).getTextContent(); //lấy thẻ description. ta bỏ thằng description đầu tiên nên sẽ có i + 1
                Pattern p = Pattern.compile("<img[^>]+src\\s*=\\s*['\"]([^'\"]+)['\"][^>]*>");

                Matcher matcher = p.matcher(cdata);
                if(matcher.find()) {
                    image = matcher.group(1);
                }
                Element element = (Element) nodeList.item(i);
                title += parser.getValue(element, "title"); //the tag we wanna get is <title>
                link = parser.getValue(element, "link"); //get the tag: <link>



            }
            super.onPostExecute(s);


        }
    }

    private static String readDataFromInternet(String theUrl) {
        StringBuilder strBuilder = new StringBuilder();

        try  {
            // create a url object
            URL url = new URL(theUrl);

            // create a urlconnection object
            URLConnection urlConnection = url.openConnection();

            // wrap the urlconnection in a bufferedreader
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));

            String line;

            // read from the urlconnection via the bufferedreader
            while ((line = bufferedReader.readLine()) != null)  {
                strBuilder.append(line + "\n");
            }
            bufferedReader.close();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        return strBuilder.toString();
    }

}
