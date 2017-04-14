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

    File userData;
    String UD;

    final String TAG = "HomePage.java";

    LocationHelper.LocationResult locationResult;
    LocationHelper locationHelper;
    Button buttonLogin;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        Intent intent = getIntent();

        Bundle extras = getIntent().getExtras();
        String UD = extras.getString("uData");

        try {
            userData = UtilFile.getFile(UD, this.getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR");
        }

        //Test to view user data file name
        TextView test = (TextView) findViewById(R.id.test);
        test.setText(UD);

        buttonLogin = (Button) findViewById(R.id.positive);
        buttonLogin.setOnClickListener(new OnClickListenerButtonLogin());

        // to get location updates, initialize LocationResult
        this.locationResult = new LocationHelper.LocationResult(){
            @Override
            public void gotLocation(Location location){

                //Got the location!
                if(location!=null){

                    double latitude = location.getLatitude();
                    double longitude = location.getLongitude();

                    Log.e(TAG, "lat: " + latitude + ", long: " + longitude);

                    // here you can save the latitude and longitude values
                    // maybe in your text file or database

                }else{
                    Log.e(TAG, "Location is null.");
                }

            }

        };

        // initialize our useful class,
        this.locationHelper = new LocationHelper();
    }

    // prevent exiting the app using back pressed
    // so getting user location can run in the background
    @Override
    public void onBackPressed() {

        new AlertDialog.Builder(HomePage.this)
                .setTitle("User Location App")
                .setMessage("This will end the app. Use the home button instead.")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                }).show();


    }

    public void positiveClick(View view) throws IOException {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        String date = dateobj.toString();

        String location = "N/A";
        String data = "+1:" + date + ":" + location;

        //ADD Method to get current location as a String and add to String Data
        
        UtilFile.writeToFile(data, userData);
        Bundle extras = getIntent().getExtras();
        String UD = extras.getString("uData");
        Intent intent = new Intent(this, DailyData.class);
        intent.putExtra("uData", UD);
        startActivity(intent);
    }

    public void neutralClick(View view) throws IOException {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        String date = dateobj.toString();
        String location = "N/A";
        String data = "0:" + date +":" + location;


        UtilFile.writeToFile(data, userData);
        Bundle extras = getIntent().getExtras();
        String UD = extras.getString("uData");
        Intent intent = new Intent(this, DailyData.class);
        intent.putExtra("uData", UD);
        startActivity(intent);
    }

    public void negativeClick(View view) throws IOException {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        String date = dateobj.toString();

        String location = "N/A";
        String data = "-1:" + date + ":" + location;

        UtilFile.writeToFile(data, userData);
        Bundle extras = getIntent().getExtras();
        String UD = extras.getString("uData");
        Intent intent = new Intent(this, DailyData.class);
        intent.putExtra("uData", UD);
        startActivity(intent);
    }
}
