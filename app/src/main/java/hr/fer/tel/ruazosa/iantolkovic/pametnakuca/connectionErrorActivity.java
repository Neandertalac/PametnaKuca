package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


public class ConnectionErrorActivity extends Activity {
    public static final String PREFS_NAME="MyPrefsFile";
    private static final String PATTERN ="^(([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.){3}([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connection_error);

        final Button enterIpBtn = (Button) findViewById(R.id.enterIpBtn);
        final EditText ipAddress = (EditText) findViewById(R.id.ipAdress);
        final Button retryBtn = (Button) findViewById(R.id.retryBtn);

        retryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(ConnectionErrorActivity.this,MainActivity.class);
                ConnectionErrorActivity.this.startActivity(settingsIntent);
            }
        });

        enterIpBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String IPAddress = ipAddress.getText().toString();
                if(!checkIP(IPAddress)){
                    Toast.makeText(getApplicationContext(), "Unjeli ste nepostojeći IP", Toast.LENGTH_LONG).show();
                }else{
                    SharedPreferences.Editor editor = getSharedPreferences(PREFS_NAME, MODE_PRIVATE).edit();
                    editor.putString("serverIP",IPAddress);
                    editor.commit();
                    Toast.makeText(getApplicationContext(),"IP je pohranjen",Toast.LENGTH_LONG).show();
                    Intent returnIntent = new Intent(ConnectionErrorActivity.this,MainActivity.class);
                    startActivity(returnIntent);
                    finish();
                }
            }
        });
    }

    public static boolean checkIP (String IP){
        Pattern pattern = Pattern.compile(PATTERN);
        Matcher matcher = pattern.matcher(IP);
        return matcher.matches();
    }

}
