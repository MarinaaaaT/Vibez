package marinatassi.vibez;

import android.support.v7.app.AppCompatActivity;
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
 * Created by Marina on 4/10/17.
 */
public class DailyData extends AppCompatActivity{

    File userData;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_data);
        Intent intent = getIntent();

        Bundle extras = getIntent().getExtras();
        String UD = extras.getString("uData");
        System.out.println(UD);

        try {
            userData = UtilFile.getFile(UD, this.getApplicationContext());
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("ERROR");
        }

        Date date = new Date();
        String newstring = new SimpleDateFormat("MM/dd/yyyy").format(date);
        System.out.println(newstring);
        TextView dailyDate = (TextView) findViewById(R.id.dailyDate);
        dailyDate.setText(newstring);


    }


}
