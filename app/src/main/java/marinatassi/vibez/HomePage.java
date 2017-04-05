package marinatassi.vibez;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Marina on 4/5/17.
 */
public class HomePage extends AppCompatActivity{
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);
        Intent intent = getIntent();

        Bundle extras = getIntent().getExtras();
        String UD = extras.getString("uData");

        TextView test = (TextView) findViewById(R.id.test);
        test.setText(UD);
    }
}
