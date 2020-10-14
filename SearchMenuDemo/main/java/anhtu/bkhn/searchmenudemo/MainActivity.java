package anhtu.bkhn.searchmenudemo;

        import android.graphics.Color;
        import android.graphics.Typeface;
        import android.support.v7.app.AppCompatActivity;
        import android.os.Bundle;
        import android.view.ContextMenu;
        import android.view.Menu;
        import android.view.MenuInflater;
        import android.view.MenuItem;
        import android.view.View;
        import android.widget.AdapterView;
        import android.widget.ArrayAdapter;
        import android.widget.Button;
        import android.widget.ListView;
        import android.widget.SearchView;
        import android.widget.TextView;
        import android.widget.Toast;

        import java.util.ArrayList;
        import java.util.Arrays;
        import java.util.Collection;

public class MainActivity extends AppCompatActivity {

    ListView lvTinhTP;
    ArrayList<String> listTinhTP;
    ArrayAdapter<String> adapterTinhTP;

    TextView txtTinh;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtTinh = (TextView) findViewById(R.id.txtTinhTP);

        lvTinhTP = (ListView) findViewById(R.id.lvTP);
        listTinhTP = new ArrayList<>();
        listTinhTP.addAll(Arrays.asList(getResources().getStringArray(R.array.arrayTinhTP)));
        adapterTinhTP = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, listTinhTP);
        lvTinhTP.setAdapter(adapterTinhTP);

        lvTinhTP.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String str = listTinhTP.get(position);
                txtTinh.setText(str);
            }
        });
    }


    //////////this is option menu///////////////
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        try {
            MenuInflater menuInflater = getMenuInflater();
            menuInflater.inflate(R.menu.search_menu, menu);

            //lay search_menu:
            MenuItem menuItemSearch = menu.findItem(R.id.search_menu);
            SearchView searchView = (SearchView) menuItemSearch.getActionView();
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    adapterTinhTP.getFilter().filter(newText);
                    return false;
                }
            });
        } catch (Exception e) {
            Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == R.id.mnGreen_text) {
            txtTinh.setTextColor(Color.GREEN);
        } else if(item.getItemId() == R.id.mnRed_text) {
            txtTinh.setTextColor(Color.RED);
        } else if(item.getItemId() == R.id.mnYellow_text) {
            txtTinh.setTextColor(Color.YELLOW);
        }

        return super.onOptionsItemSelected(item);
    }
}