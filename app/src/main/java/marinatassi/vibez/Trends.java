package marinatassi.vibez;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.helper.DateAsXAxisLabelFormatter;
import com.jjoe64.graphview.series.DataPoint;
import com.jjoe64.graphview.series.LineGraphSeries;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by Marina on 5/6/17.
 */

public class Trends extends AppCompatActivity {
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

        today = new Date();

        // generate Dates
        Calendar calendar = Calendar.getInstance();
        Date d1 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d2 = calendar.getTime();
        calendar.add(Calendar.DATE, 1);
        Date d3 = calendar.getTime();

        GraphView graph = (GraphView) findViewById(R.id.graph1);

        // you can directly pass Date objects to DataPoint-Constructor
        // this will convert the Date to double via Date#getTime()

        DataPoint[] dataForGraph = getDataPointsforWeekly(today, data);
        LineGraphSeries<DataPoint> series = new LineGraphSeries<>(dataForGraph);

        graph.addSeries(series);

        // set date label formatter
        graph.getGridLabelRenderer().setLabelFormatter(new DateAsXAxisLabelFormatter(this.getApplication()));
        graph.getGridLabelRenderer().setNumHorizontalLabels(3); // only 4 because of the space

        // set manual x bounds to have nice steps
        graph.getViewport().setMinX(d1.getTime());
        graph.getViewport().setMaxX(d3.getTime());
        graph.getViewport().setXAxisBoundsManual(true);

    }

    public static DataPoint[] getDataPointsforWeekly(Date d, String data){

        String[] input;
        String[] inputs = data.split(",");
        int numOfInputs = inputs.length;
        System.out.println(numOfInputs);
        String[] dates = new String[numOfInputs];
        String[] moods = new String[numOfInputs];

        //Create an array of String Dates and String moods
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

        //create array of data for each of the last 7 days

        int end = graphDates.length;
        int start = graphDates.length;
        int j = 6;
        DataPoint[] weeklyData = new DataPoint[7];
        int mood = 0;
        int numOfMoods = 0;
        double averageMood = 0;

        for(int i = graphDates.length; i>=0; i--){
            if(j == -1){
                break;
            }

            String d2 = new SimpleDateFormat("MM/dd/yyyy").format(graphDates[i]);
            //if date of data equals date given
            if (d1.equals(d2)) {
                mood = mood + Integer.valueOf(moods[i]);
                numOfMoods++;
            }
            if(!d1.equals(d2)){
                if (mood == 0){
                    averageMood = 0;
                }
                else{
                    averageMood = mood/numOfMoods;
                    averageMood = round(averageMood, 4);
                }
                DataPoint temp = new DataPoint(d, averageMood);
                weeklyData[j] = temp;
                j--;
                d = DailyData.changeDay(d, -1);
                d1 = new SimpleDateFormat("MM/DD/yyyy").format(d);
                mood = 0;
                numOfMoods = 0;

                if(d1.equals(d2)){
                    mood = Integer.valueOf(moods[i]);
                    numOfMoods=1;
                }
            }
        }

        for(int i = 0; i<=j; j--){
            DataPoint temp = new DataPoint(d, 0);
            d = DailyData.changeDay(d, -1);
            weeklyData[j] = temp;
        }

        return weeklyData;
    }

    public static double round(double value, int places) {
        if (places < 0) throw new IllegalArgumentException();

        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(places, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
