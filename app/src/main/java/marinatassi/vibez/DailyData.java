package marinatassi.vibez;

import android.support.v7.app.AppCompatActivity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.TextView;
import android.view.View;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.util.Calendar;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.concurrent.ExecutionException;

/**
 * Created by Marina on 4/10/17.
 */
public class DailyData extends AppCompatActivity{

    Date current;
    Date today;
    String UN;
    String data;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.daily_data);
        Intent intent = getIntent();

        Bundle extras = getIntent().getExtras();
        UN = extras.getString("username");
        System.out.println(UN + " UN");

        String url = "http://148.85.251.205:8863/get/" + UN + "_data/" + UN;
        //String url2 = "http://148.85.251.205:8863/get/all_data/now"
        GetServerInfo userCheck = new GetServerInfo();
        try {
            data = userCheck.execute(url).get();
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        // sets up the Date at top of screen
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

        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();

        GraphView graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] datapts = getDataforGraph(current, data);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(datapts);
        graph.addSeries(series);

        // set manual y bounds to have nice steps
        graph.getViewport().setMinY(-1);
        graph.getViewport().setMaxY(1);
        graph.getViewport().setYAxisBoundsManual(true);



    }

    public static DataPoint[] getDataforGraph(Date d, String data){
        String[] input;
        String[] inputs = data.split(",");
        int numOfInputs = inputs.length;
        System.out.println(numOfInputs);
        String[] dates = new String[numOfInputs];
        String[] moods = new String[numOfInputs];

        //Create an array of String Dates
        for (int i = 0; i < numOfInputs; i++){
            input = inputs[i].split(":");
            dates[i] = input[0];
            moods[i] = input[1];
        }

        //create an array of Date dates
        String[] date;
        String d1 = new SimpleDateFormat("MM/dd/yyyy").format(d);
        Date[] graphDates = new Date[dates.length];
        for(int i = 0; i < numOfInputs; i++){
            date = dates[i].split("~");
            Calendar calendar = Calendar.getInstance();
            calendar.set(Integer.valueOf(date[2]), Integer.valueOf(date[0])-1, Integer.valueOf(date[1]), Integer.valueOf(date[3]), Integer.valueOf(date[4]), Integer.valueOf(date[5]));
            graphDates[i] = calendar.getTime();
        }

        //match up dates
        int start = 0;
        //find first date array location that matches current date
        for(int i = 0; i<numOfInputs;i++){
            String d2 = new SimpleDateFormat("MM/dd/yyyy").format(graphDates[i]);
            if(d1.equals(d2)){
                start = i;
                break;
            }
        }
        int end = numOfInputs;
        //find last date array location that matches current date
        for(int i = start; i<numOfInputs;i++){
            String d2 = new SimpleDateFormat("MM/dd/yyyy").format(graphDates[i]);
            if(!d1.equals(d2)){
                end = i-1;
                break;
            }
        }

        System.out.println(start + " " + end);

        //Create array of moods
        int[] mood = new int[end-start+1];
        for(int i = start; i <= end; i++){
            int j=0;
            mood[j] = Integer.valueOf(moods[i]);
            j++;
        }

        // need to add Data Points to graph and filter out data from different days
        DataPoint[] datapts = new DataPoint[end-start+1];
        for(int i = 0; i < datapts.length; i++){
            datapts[i] = new DataPoint(i+1, mood[i]);
        }


        return datapts;


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

        GraphView graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] datapts = getDataforGraph(current, data);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(datapts);
        graph.removeAllSeries();
        graph.addSeries(series);
    }

    public void nextDay(View view){
        current = changeDay(current, +1);
        changeDateShown(current);
        Date test = new Date();
        if (current.equals(today)){
            view.setVisibility(View.INVISIBLE);
        }

        GraphView graph = (GraphView) findViewById(R.id.graph);
        DataPoint[] datapts = getDataforGraph(current, data);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(datapts);
        graph.removeAllSeries();
        graph.addSeries(series);
    }

    public void changeDateShown(Date date){
        String newDate = new SimpleDateFormat("MM/dd/yyyy").format(date);
        TextView dailyDate = (TextView) findViewById(R.id.dailyDate);
        dailyDate.setText(newDate);
    }


}
