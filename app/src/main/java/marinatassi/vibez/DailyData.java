package marinatassi.vibez;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

/**
 * Created by Marina on 4/10/17.
 */
public class DailyData extends AppCompatActivity{

    File userData;
    Date current;
    Date today;

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
        Date to = new Date();
        current = date;
        today = to;
        String newstring = new SimpleDateFormat("MM/dd/yyyy").format(date);
        System.out.println(newstring);
        TextView dailyDate = (TextView) findViewById(R.id.dailyDate);
        dailyDate.setText(newstring);

        Button next = (Button) findViewById(R.id.next);
        next.setVisibility(next.INVISIBLE);

        GraphView graph = (GraphView) findViewById(R.id.graph);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(new DataPoint[] {
                new DataPoint(0, 1),
                new DataPoint(1, 5),
                new DataPoint(2, 3)
        });
        graph.addSeries(series);

    }

    public static Date changeDay(Date curr, int change){
        final Calendar cal = Calendar.getInstance();
        cal.setTime(curr);
        cal.add(Calendar.DATE, change);
        System.out.println(cal.getTime());
        return cal.getTime();
    }

    public void prevDay(View view){
        current = changeDay(current, -1);
        changeDateShown(current);
        Date test = new Date();
        Button next = (Button) findViewById(R.id.next);
        if (current != today){
            next.setVisibility(View.VISIBLE);
        }
    }

    public void nextDay(View view){
        current = changeDay(current, +1);
        changeDateShown(current);
        Date test = new Date();
        if (current.equals(today)){
            view.setVisibility(View.INVISIBLE);
        }
    }

    public void changeDateShown(Date date){
        String newDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
        TextView dailyDate = (TextView) findViewById(R.id.dailyDate);
        dailyDate.setText(newDate);
    }


}
