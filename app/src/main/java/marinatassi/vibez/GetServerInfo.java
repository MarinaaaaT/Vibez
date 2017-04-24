package marinatassi.vibez;

/**
 * Created by Marina on 4/14/17.
 */

import android.os.AsyncTask;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetServerInfo extends AsyncTask<String, Void, String> {

    @Override
    protected String doInBackground(String... url) {
        String data = "";
        try {
            URL store = new URL(url[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) store.openConnection();
            BufferedReader in = new BufferedReader(new InputStreamReader(store.openStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                data = data + inputLine;
            }
            in.close();
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        return data;
    }

    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        MainActivity.serverData = s;
        System.out.println("DONE");
    }
}
