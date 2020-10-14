package anhtu.bkhn.menudemo;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    ListView lvTinhTP;
    ArrayList<String> listTinhTP;
    ArrayAdapter<String> adapterTinhTP;

    TextView txtHl, txtDisplaymn;
    Button btDisplaymn, btDisplaymn2, btDisplaymn3;
    Button lastSelectedBtn = null;  //lưu lại button cuối cùng vừa đc nhấn

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtHl = (TextView) findViewById(R.id.txtHello);
        txtDisplaymn = (TextView) findViewById(R.id.txtDisplayMenu);
        btDisplaymn = (Button) findViewById(R.id.btDisplayMenu);
        btDisplaymn2 = (Button) findViewById(R.id.btDisplayMenu2);
        btDisplaymn3 = (Button) findViewById(R.id.btDisplayMenu3);

        lvTinhTP = (ListView) findViewById(R.id.lvTP);
        listTinhTP = new ArrayList<>();
        listTinhTP.addAll(Arrays.asList(getResources().getStringArray(R.array.arrayTinhTP)));
        adapterTinhTP = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listTinhTP);
        lvTinhTP.setAdapter(adapterTinhTP);

        addEvents();

        //đăng ký context menu cho thằng nào muốn tạo ra context menu
        registerForContextMenu(txtDisplaymn);
        registerForContextMenu(btDisplaymn);
        registerForContextMenu(btDisplaymn2);
        registerForContextMenu(btDisplaymn3);

    }

    private void addEvents() {
        //when click button:
        btDisplaymn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedBtn = btDisplaymn;
            }
        });
        btDisplaymn2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedBtn = btDisplaymn2;
            }
        });
        btDisplaymn3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                lastSelectedBtn = btDisplaymn3;
            }
        });

        //when long click button:
        btDisplaymn.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                lastSelectedBtn = btDisplaymn;
                return false;  //nếu return true thì khi ấn long click thì sẽ ko xuất hiện context menu
            }
        });
        btDisplaymn2.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                lastSelectedBtn = btDisplaymn2;
                return false;
            }
        });
        btDisplaymn3.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                lastSelectedBtn = btDisplaymn3;
                return false;
            }
        });
    }


    //////////this is option menu///////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.option_menu, menu);
        //menuInflater.inflate(R.menu.search_menu, menu);

        //lay search_menu:
//        MenuItem menuItemSearch = menu.findItem(R.id.search_menu);
//        SearchView searchView = (SearchView) menuItemSearch.getActionView();
//        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
//            @Override
//            public boolean onQueryTextSubmit(String query) {
//                return false;
//            }
//
//            @Override
//            public boolean onQueryTextChange(String newText) {
//                adapterTinhTP.getFilter().filter(newText);
//                return false;
//            }
//        });
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mnGreen) {
            txtHl.setBackgroundColor(Color.GREEN);
        } else if(item.getItemId() == R.id.mnRed) {
            txtHl.setBackgroundColor(Color.RED);
        } else if(item.getItemId() == R.id.mnYellow) {
            txtHl.setBackgroundColor(Color.YELLOW);
        } else if(item.getItemId() == R.id.mnGreen_text) {
            txtHl.setTextColor(Color.GREEN);
        } else if(item.getItemId() == R.id.mnRed_text) {
            txtHl.setTextColor(Color.RED);
        } else if(item.getItemId() == R.id.mnYellow_text) {
            txtHl.setTextColor(Color.YELLOW);
        }

        return super.onOptionsItemSelected(item);
    }
    /////////////////

    //////////////this is context menu/////////////

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.context_menu, menu);
        super.onCreateContextMenu(menu, v, menuInfo);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.contextmn_Bold) {
            txtHl.setTypeface(Typeface.DEFAULT_BOLD);
        } else if(item.getItemId() == R.id.contextmn_Italic) {
            txtHl.setTypeface(Typeface.defaultFromStyle(Typeface.ITALIC));
        } else if(item.getItemId() == R.id.contextmn_Delete) {
            lastSelectedBtn.setVisibility(View.INVISIBLE);
        }

        return super.onContextItemSelected(item);
    }
}
