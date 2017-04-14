package marinatassi.vibez;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState)  {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        URL url = new URL("localhost");
        HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
    }

    public void keyboardAdjust(View view){
        //adjust padding to account for keyboard
        View parent = (View) view.getParent();
    }

    public static String[][] userData(String file){
        String[] users = file.split("\n");
        String [][] userData = new String[users.length][4];
        for (int i = 0; i < users.length; i++){
            userData[i] = users[i].split("%");
        }
        return userData;
    }

    public static int existingUser(String us, File userInfo) throws IOException {
        String file = UtilFile.fileToString(userInfo);
        String[][] userdata = userData(file);
        for(int i = 0; i < userdata.length; i++){
            if(userdata[i][0].equals(us)){
                return i;
            }
        }
        return -1;
    }

    public static boolean correctPassword(String pw, int us, File userInfo) throws IOException{
        String file = UtilFile.fileToString(userInfo);
        String[][] userdata = userData(file);
        if(userdata[us][1].equals(pw)){
            return true;
        }
        return false;
    }

    public void login(View view) throws IOException {

        EditText username = (EditText) findViewById(R.id.editText1);
        EditText password = (EditText) findViewById(R.id.editText2);
        TextView loginFail = (TextView) findViewById(R.id.loginFail);
        Button register = (Button) findViewById(R.id.registerButton);
        String un = username.getText().toString();

        File userInfo = UtilFile.getFile("userInfo.txt", this.getApplicationContext());
        int userID = existingUser(username.getText().toString(), userInfo);

        //correct username and password
        if (userID != -1 && correctPassword((password.getText().toString()), userID, userInfo)) {
            String data = un + "Data.txt";
            Intent intent = new Intent(this, HomePage.class);
            intent.putExtra("uData", data);
            startActivity(intent);
        }
        //wrong username or password
        else {
            loginFail.setVisibility(View.VISIBLE);
        }
    }

    public void register(View view){

        Intent intent = new Intent(this, Registration.class);
        EditText username = (EditText) findViewById(R.id.editText1);
        String un = username.getText().toString();
        intent.putExtra("UN", un);


        EditText password = (EditText) findViewById(R.id.editText2);
        String pw = password.getText().toString();
        intent.putExtra("PW", pw);
        startActivity(intent);
    }
}
