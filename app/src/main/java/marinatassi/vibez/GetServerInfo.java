package marinatassi.vibez;

/**
 * Created by Marina on 4/14/17.
 */

import android.os.AsyncTask;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

public class GetServerInfo extends AsyncTask<String, Void, Void> {

    @Override
    protected Void doInBackground(String... url) {
        try {
            URL store = new URL(url[0]);
            HttpURLConnection urlConnection = (HttpURLConnection) store.openConnection();
            System.out.println(urlConnection.getResponseMessage());
            System.out.println("HELP");
        } catch (IOException e){
            throw new RuntimeException(e);
        }
        return null;
    }

}
