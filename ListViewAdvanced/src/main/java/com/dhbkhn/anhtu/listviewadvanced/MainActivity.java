package com.dhbkhn.anhtu.listviewadvanced;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    //trong listview, thay vì dùng simple_list_item_1, ta dùng row.xml để tạo từng hàng cho listview
    ListView lvStudent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        addComponents();
    }

    private void addComponents() {
        ArrayList<Student> arrStud=new ArrayList();
        arrStud.add(new Student("Son go ku", "098329932"));
        arrStud.add(new Student("Vegeta", "0832440032"));
        arrStud.add(new Student("Son go han", "0112929923"));
        arrStud.add(new Student("trunks", "099933399"));
        arrStud.add(new Student("krilin", "09887776663"));
        arrStud.add(new Student("Picollo", "09911223344"));
        arrStud.add(new Student("Ma bu", "0912344353"));
        arrStud.add(new Student("Ta anh tu", "0944823223"));
        arrStud.add(new Student("Trung nguyen", "08132342344"));
        arrStud.add(new Student("Yamcha", "098823734"));

        StudentAdapter studAdapter=new StudentAdapter(MainActivity.this, R.layout.row, arrStud);

        lvStudent=(ListView)findViewById(R.id.lvStud);
        lvStudent.setAdapter(studAdapter);
    }
}
