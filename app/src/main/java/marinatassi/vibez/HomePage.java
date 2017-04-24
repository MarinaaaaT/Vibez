package marinatassi.vibez;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Marina on 4/5/17.
 */
public class HomePage extends AppCompatActivity{

    String UN;

    final String TAG = "HomePage.java";

    LocationHelper.LocationResult locationResult;
    LocationHelper locationHelper;
    Button buttonLogin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        Intent intent = getIntent();

        Bundle extras = getIntent().getExtras();
        String UN = extras.getString("username");

//        buttonLogin = (Button) findViewById(R.id.positive);
//        buttonLogin.setOnClickListener(new OnClickListenerButtonLogin());
//
//        // to get location updates, initialize LocationResult
//        this.locationResult = new LocationHelper.LocationResult(){
//            @Override
//            public void gotLocation(Location location){
//
//                //Got the location!
//                if(location!=null){
//
//                    double latitude = location.getLatitude();
//                    double longitude = location.getLongitude();
//
//                    Log.e(TAG, "lat: " + latitude + ", long: " + longitude);
//
//                    // here you can save the latitude and longitude values
//                    // maybe in your text file or database
//
//                }else{
//                    Log.e(TAG, "Location is null.");
//                }
//
//            }
//
//        };
//
//        // initialize our useful class,
//        this.locationHelper = new LocationHelper();
    }

    // prevent exiting the app using back pressed
    // so getting user location can run in the background
//    @Override
//    public void onBackPressed() {
//
//        new AlertDialog.Builder(HomePage.this)
//                .setTitle("User Location App")
//                .setMessage("This will end the app. Use the home button instead.")
//                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//
//                        dialog.cancel();
//                    }
//                }).show();
//
//
//    }

    public void positiveClick(View view) throws IOException {
        Date dateobj = new Date();
        String date = new SimpleDateFormat("MM/dd/yyyy HH|mm|ss").format(dateobj);
        System.out.println(date + "<--");

        String location = "N/A";
        String data = UN + ":" + date +":+1:" + location;
        String data2 = date +":+1:" + location;

        String url = "http://148.85.251.144:8820/store/" + UN + "_data/" + data;
        GetServerInfo userCheck = new GetServerInfo();
        userCheck.execute(url);
        String url2 = "http://148.85.251.144:8820/store/" + "all_data/" + data2;
        userCheck.execute(url2);
        Intent intent = new Intent(this, DailyData.class);
        intent.putExtra("username", UN);
        startActivity(intent);
    }

    public void neutralClick(View view) throws IOException {
        Date dateobj = new Date();
        String date = new SimpleDateFormat("MM/dd/yyyy HH|mm|ss").format(dateobj);
        System.out.println(date + "<--");

        String location = "N/A";
        String data = UN + ":" + date +":0:" + location;
        String data2 = date +":0:" + location;

        String url = "http://148.85.251.144:8820/store/" + UN + "_data/" + data;
        GetServerInfo userCheck = new GetServerInfo();
        userCheck.execute(url);
        String url2 = "http://148.85.251.144:8820/store/" + "all_data/" + data2;
        userCheck.execute(url2);
        Intent intent = new Intent(this, DailyData.class);
        intent.putExtra("username", UN);
        startActivity(intent);
    }

    public void negativeClick(View view) throws IOException {
        Date dateobj = new Date();
        String date = new SimpleDateFormat("MM/dd/yyyy HH|mm|ss").format(dateobj);
        System.out.println(date + "<--");

        String location = "N/A";
        String data = UN + ":" + date +":-1:" + location;
        String data2 = date +":-1:" + location;

        String url = "http://148.85.251.144:8820/store/" + UN + "_data/" + data;
        GetServerInfo userCheck = new GetServerInfo();
        userCheck.execute(url);
        String url2 = "http://148.85.251.144:8820/store/" + "all_data/" + data2;
        userCheck.execute(url2);
        Intent intent = new Intent(this, DailyData.class);
        intent.putExtra("username", UN);
        startActivity(intent);
    }
}
