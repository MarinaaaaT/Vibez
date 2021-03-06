package marinatassi.vibez;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.TypedArray;
import android.location.Location;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;
import android.widget.Toast;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

import static android.R.attr.fragment;

/**
 * Created by Marina on 4/5/17.
 */
public class HomePage extends BaseActivity{

    public String UN;
    private TrackGPS gps;
    double longitude;
    double latitude;
    final String TAG = "HomePage.java";
    private String[] navMenuTitles;
    private TypedArray navMenuIcons;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        Intent intent = getIntent();

        navMenuTitles = getResources().getStringArray(R.array.nav_drawer_items); // load
        // titles
        // from
        // strings.xml

        navMenuIcons = getResources()
                .obtainTypedArray(R.array.nav_drawer_icons);// load icons from
        // strings.xml
        set(navMenuTitles, navMenuIcons);


        Bundle extras = getIntent().getExtras();
        UN = extras.getString("username");
        System.out.println(UN + " UN");
    }

    public void positiveClick(View view) throws IOException, ExecutionException, InterruptedException {
        Date dateobj = new Date();
        String date = new SimpleDateFormat("MM~dd~yyyy~HH~mm~ss").format(dateobj);
        System.out.println(date + "<--");

        gps = new TrackGPS(HomePage.this);

        if (gps.canGetLocation()) {
            longitude = gps.getLongitude();
            latitude = gps.getLatitude();

            Toast.makeText(getApplicationContext(),
                    "Mood Recorded",
                    Toast.LENGTH_SHORT).show();

        }
        else {
            gps.showSettingsAlert();
        }

        String location = Double.toString(latitude)+"~"+Double.toString(longitude);
        String data = UN + ":" + date +":+1:" + location + ",";
        String data2 = date +":+1:" + location;
        System.out.println(UN + " UN TEST");

        String url = "http://148.85.251.205:8863/store/" + UN + "_data/" + data;
        GetServerInfo userCheck = new GetServerInfo();
        userCheck.execute(url).get();
        GetServerInfo userCheck2 = new GetServerInfo();
        String url2 = "http://148.85.251.205:8863/store/" + "all_data/" + data2;
        System.out.println(url + url2);
        userCheck2.execute(url2);
    }

    public void test(View view) throws IOException{
        MapsActivity test = new MapsActivity();
    }

    public void neutralClick(View view) throws IOException {
        Date dateobj = new Date();
        String date = new SimpleDateFormat("MM~dd~yyyy~HH~mm~ss").format(dateobj);
        System.out.println(date + "<--");

        gps = new TrackGPS(HomePage.this);

        if (gps.canGetLocation()) {
            longitude = gps.getLongitude();
            latitude = gps.getLatitude();

            Toast.makeText(getApplicationContext(),
                    "Mood Recorded",
                    Toast.LENGTH_SHORT).show();

        }
        else {
            gps.showSettingsAlert();
        }

        String location = Double.toString(latitude)+"~"+Double.toString(longitude);
        String data = UN + ":" + date +":0:" + location +",";
        String data2 = date +":0:" + location;

        String url = "http://148.85.251.205:8863/store/" + UN + "_data/" + data;
        GetServerInfo userCheck = new GetServerInfo();
        userCheck.execute(url);
        GetServerInfo userCheck2 = new GetServerInfo();
        String url2 = "http://148.85.251.205:8863/store/" + "all_data/" + data2;
        userCheck2.execute(url2);
    }

    public void negativeClick(View view) throws IOException {
        Date dateobj = new Date();
        String date = new SimpleDateFormat("MM~dd~yyyy~HH~mm~ss").format(dateobj);
        System.out.println(date + "<--");

        gps = new TrackGPS(HomePage.this);

        if (gps.canGetLocation()) {
            longitude = gps.getLongitude();
            latitude = gps.getLatitude();

            Toast.makeText(getApplicationContext(),
                    "Mood Recorded",
                    Toast.LENGTH_SHORT).show();

        }
        else {
            gps.showSettingsAlert();
            System.out.println("HEY");
        }

        String location = Double.toString(latitude)+"~"+Double.toString(longitude);
        String data = UN + ":" + date +":-1:" + location + ",";
        String data2 = date +":-1:" + location;

        String url = "http://148.85.251.205:8863/store/" + UN + "_data/" + data;
        GetServerInfo userCheck = new GetServerInfo();
        userCheck.execute(url);
        GetServerInfo userCheck2 = new GetServerInfo();
        String url2 = "http://148.85.251.205:8863/store/" + "all_data/" + data2;
        userCheck2.execute(url2);
    }
}
