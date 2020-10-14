package anhtu.bkhn.bai66map;

import android.app.ProgressDialog;
import android.content.pm.PackageManager;
import android.location.Location;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Spinner spinnerMapType;
    ArrayList<String> listType;
    ArrayAdapter<String> adapterType;
    ProgressDialog progressDialog;

    GoogleMap.OnMyLocationChangeListener onMyLocationChangeListener = new GoogleMap.OnMyLocationChangeListener() {
        @Override
        public void onMyLocationChange(Location location) {
            LatLng loca = new LatLng(location.getLatitude(), location.getLongitude());
            if (mMap != null) {
                mMap.clear();
                Marker marker = mMap.addMarker(new MarkerOptions().position(loca));
                mMap.animateCamera(CameraUpdateFactory.newLatLngZoom(loca, 16.0f));
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.myMap);
        mapFragment.getMapAsync(this);

        addComponents();
        addEvents();
    }

    private void addComponents() {
        spinnerMapType = (Spinner) findViewById(R.id.spinnerType);
        listType = new ArrayList<>();
        listType.addAll(Arrays.asList(getResources().getStringArray(R.array.arrMapType)));
        adapterType = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, listType);
        adapterType.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerMapType.setAdapter(adapterType);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Notice!");
        progressDialog.setMessage("Map is loading, please wait...");
    }

    private void addEvents() {
        spinnerMapType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                changeModeDisplay(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                changeModeDisplay(0);
            }
        });
    }

    private void changeModeDisplay(int position) {
        switch (position) {
            case 0:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
            case 1:
                mMap.setMapType(GoogleMap.MAP_TYPE_SATELLITE);
                break;
            case 2:
                mMap.setMapType(GoogleMap.MAP_TYPE_TERRAIN);
                break;
            case 3:
                mMap.setMapType(GoogleMap.MAP_TYPE_HYBRID);
                break;
            case 4:
                mMap.setMapType(GoogleMap.MAP_TYPE_NONE);
                break;
            default:
                mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);
                break;
        }

    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        mMap.setMyLocationEnabled(true);
        mMap.setOnMapLoadedCallback(new GoogleMap.OnMapLoadedCallback() {
            //hàm này cho biết khi nào map load xong
            @Override
            public void onMapLoaded() {
                //khi map đã load xong thì làm j đó ở đây...
                progressDialog.dismiss();
                Toast.makeText(MainActivity.this, "map loaded!", Toast.LENGTH_SHORT).show();

//                //display my home's location here:
//                LatLng myHome = new LatLng(20.959973, 105.802078);
//                mMap.addMarker(new MarkerOptions()
//                        .position(myHome)
//                        .title("My home")
//                        .snippet("This is my home's location. My home is near here....")
//                );
//                mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(myHome, 13)); //zoom tối đa là từ 0-18

                //display current location:
                ///displayCurrentLocation();
            }
        });
        mMap.setOnMyLocationChangeListener(onMyLocationChangeListener);
    }
}
