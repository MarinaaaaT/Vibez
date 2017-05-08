package marinatassi.vibez;

import android.support.v4.app.FragmentActivity;
import android.os.Bundle;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.BitmapDescriptor;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.CameraPosition;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

public class MapsActivity extends FragmentActivity implements OnMapReadyCallback {

    private GoogleMap mMap;
    Double [][] latLangTable = DailyData.mostRecentMoodsAllData(10);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_maps);
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;
        LatLng latlng;
        double lat;
        double lng;
        double mood;
        System.out.println(latLangTable.length + "Len");

        for(int i = 0; i < latLangTable.length; i++){
            mood = 1.0 * latLangTable[0][i];
            lat = 1.0 * latLangTable[1][i];
            lng = 1.0 * latLangTable[2][i];
            latlng = new LatLng(lat, lng);

            System.out.println(mood + " " + lat + " " + lng + "INFO");

            int moodInt = (int) mood;

            if(moodInt == 1) {
                mMap.addMarker(new MarkerOptions().position(latlng).title("Good Vibez").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_YELLOW)));
            }
            else if(moodInt == 0){
                mMap.addMarker(new MarkerOptions().position(latlng).title("Neutral Vibez").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
            }
            else{
                mMap.addMarker(new MarkerOptions().position(latlng).title("Bad Vibez").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)));
            }
        }
//        LatLng latlng = new LatLng(37.421998333333335, -122.08400000000002);
//        mMap.addMarker(new MarkerOptions().position(latlng).title("Test").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_GREEN)));
//        latlng = new LatLng(32, -122);
//        mMap.addMarker(new MarkerOptions().position(latlng).title("Test").icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_BLUE)));
    }
}
