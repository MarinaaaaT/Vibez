package marinatassi.vibez;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;

public class MainActivity extends AppCompatActivity {

    public static String serverData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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

    public static boolean existingUser(String us) throws IOException, ExecutionException, InterruptedException {
        serverData = "";
        getAllUsers();
        if(serverData.equals("no users")){
            return false;
        }
        else{
            String[] all = serverData.split(",");
            for(int i = 0; i<all.length; i++){
                String[] user = all[i].split(":");
                if (user[0].toLowerCase().equals(us.toLowerCase())){
                    return true;
                }
            }
            return false;
        }
    }

    public static void getAllUsers() throws ExecutionException, InterruptedException {
        String url = "http://148.85.251.205:8863/get/all_users/Test:Test";
        GetServerInfo userCheck = new GetServerInfo();
        serverData=userCheck.execute(url).get();
    }

    public static boolean correctPassword(String pw, String us) throws IOException, ExecutionException, InterruptedException {
        serverData = "";
        getAllUsers();
        if(serverData.equals("no users")){
            return false;
        }
        else{
            String[] all = serverData.split(",");
            for(int i = 0; i<all.length; i++){
                String[] user = all[i].split(":");
                if (user[0].toLowerCase().equals(us.toLowerCase())){
                    if(user[1].equals(pw)){
                        return true;
                    }
                }
            }
            return false;
        }
    }

    public void login(View view) throws IOException, ExecutionException, InterruptedException {

        EditText username = (EditText) findViewById(R.id.editText1);
        EditText password = (EditText) findViewById(R.id.editText2);
        TextView loginFail = (TextView) findViewById(R.id.loginFail);

        String un = username.getText().toString();
        String pw = password.getText().toString();

        boolean userCorrect = existingUser(un);
        boolean passwordCorrect = correctPassword(pw, un);

        //correct username and password
        if (userCorrect && passwordCorrect) {
            Intent intent = new Intent(this, HomePage.class);
            intent.putExtra("username", un);
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
