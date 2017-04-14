package marinatassi.vibez;

/**
 * Created by zeinaamhaz on 4/14/17.
 */

import android.content.Context;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;

public class OnClickListenerButtonLogin implements OnClickListener {

    final String TAG = "ButtonLogin";

    HomePage homePage;
    Context context;

    @Override
    public void onClick(View view) {

        Log.e(TAG, "Start");

        // to get the context and main activity
        this.context = view.getContext();
        this.homePage = ((HomePage) context);

        // disable the START button, enable the STOP button
     //   mainActivity.buttonStart.setEnabled(false);
      //  mainActivity.buttonStop.setEnabled(true);

        // start listening to location updates
        homePage.locationHelper.getLocation(homePage, homePage.locationResult);

    }

}