package marinatassi.vibez;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
    }

    public void positiveClick(View view) throws IOException {
        DateFormat df = new SimpleDateFormat("dd/MM/yy HH:mm:ss");
        Date dateobj = new Date();
        String date = dateobj.toString();
        String data = "+1%" + date;
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
        String data = "0%" + date;
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
        String data = "-1%" + date;
        UtilFile.writeToFile(data, userData);
        Bundle extras = getIntent().getExtras();
        String UD = extras.getString("uData");
        Intent intent = new Intent(this, DailyData.class);
        intent.putExtra("uData", UD);
        startActivity(intent);
    }
}
