package com.dhbk.anhtu.bai35activitytransmitrecieve;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

//Cách mở một Activity như thế nào
// Hiểu và sử dụng được startActivity
//Cách truyền dữ liệu qua Activity: trực tiếp từ Intent, đóng gói vào Bundle
//Cách nhận dữ liệu truyền từ Activity: getIntent()

//nếu muốn gửi dữ liệu là 1 đối tượng thì class của đối tượng đó phải implements Serializable

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }


    public void eventBtOpen1Screen(View view) { //gọi 1 màn hình và ko làm j cả
        Intent it=new Intent(MainActivity.this, Main2Activity.class); //tham số 1: màn hình hiện tại đang sử dụng, tham số 2: màn hình cần gọi (là màn hình mà tham số 1 cần gọi)
        startActivity(it); //gửi lệnh tới android system để mở màn hình
    }

    public void eventBt2(View view) {  //gọi 1 màn hình và chỉ gửi dữ liệu sang màn hình đó mà ko nhận lại kết quả
        Intent it2= new Intent(MainActivity.this, Main3Activity.class);
        ///send primitive type:
        it2.putExtra("KIEU_BOOLEAN", true); //tham số 1 là tên biến, tham số 2 là giá trị của biến đó
        it2.putExtra("KIEU_CHAR", 'a');
        it2.putExtra("KIEU_INT", 100);
        it2.putExtra("KIEU_DOUBLE", 511.95);
        it2.putExtra("KIEU_STRING", "Ta Anh Tu"); //đưa thông số vào intent, tham số 1 là tên biến, tham số 2 là giá trị của biến đó

        ///send object type:
        Student st=new Student("Anhtu", 20134509);
        it2.putExtra("SINHVIEN", st); //chú ý SINHVIEN là tên biến, st là giá trị của biến SINHVIEN

        startActivity(it2);
    }

    public void eventBt3(View view) {  //gọi 1 màn hình và chỉ gửi dữ liệu sang màn hình đó, dùng Bundle
        Intent it3=new Intent(MainActivity.this, Main4Activity.class);
        Bundle bundle=new Bundle();

        bundle.putInt("A", 1995);
        bundle.putDouble("T", 1995.511);

        Product coca=new Product(4747, "Cocacola", 20);
        bundle.putSerializable("SANPHAM", coca);

        it3.putExtra("MY_BUNDLE", bundle);

        startActivity(it3);
    }
}
