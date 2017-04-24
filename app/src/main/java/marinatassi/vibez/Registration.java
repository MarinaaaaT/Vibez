package marinatassi.vibez;

import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import java.io.File;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.ExecutionException;

/**
 * Created by Marina on 4/5/17.
 */

public class Registration extends AppCompatActivity {


    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.registration);
        Intent intent = getIntent();

        Bundle extras = getIntent().getExtras();
        String UN = extras.getString("UN");
        String PW = extras.getString("PW");

        EditText username = (EditText) findViewById(R.id.Username);
        EditText password = (EditText) findViewById(R.id.Password);

        username.setText(UN);
        password.setText(PW);
    }

    public void hideError(View view){

        TextView UN1 = (TextView) findViewById(R.id.ErrorUN1);
        UN1.setVisibility(View.INVISIBLE);

        TextView UN2 = (TextView) findViewById(R.id.ErrorUN2);
        UN2.setVisibility(View.INVISIBLE);

        TextView PW = (TextView) findViewById(R.id.ErrorPW);
        PW.setVisibility(View.INVISIBLE);


    }

    public void register(View view) throws IOException, ExecutionException, InterruptedException {
        EditText username = (EditText) findViewById(R.id.Username);
        String un = username.getText().toString();

        EditText password = (EditText) findViewById(R.id.Password);
        String pw = password.getText().toString();

        EditText confirm = (EditText) findViewById(R.id.PasswordConfirm);
        String c = confirm.getText().toString();

        if(MainActivity.existingUser(un)){
            TextView UN1 = (TextView) findViewById(R.id.ErrorUN1);
            UN1.setVisibility(View.VISIBLE);
            //show username error
        }
        else if(!pw.equals(c)){
            //show password error
            TextView PW = (TextView) findViewById(R.id.ErrorPW);
            PW.setVisibility(View.VISIBLE);
        }
        else if(un.contains("%") || un.contains(",") || un.contains(":")){
            //show username error
            TextView UN2 = (TextView) findViewById(R.id.ErrorUN2);
            UN2.setVisibility(View.VISIBLE);
        }
        else{
            String url = "http://148.85.251.144:8820/store/all_users/" + un + ":" + pw;
            GetServerInfo userCheck = new GetServerInfo();
            userCheck.execute(url);
            Intent intent = new Intent(this, HomePage.class);
            intent.putExtra("username", un);
            startActivity(intent);
        }
    }
}