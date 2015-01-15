package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Typeface;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(SettingsActivity.PREFS_NAME,MODE_PRIVATE);
        String IP = prefs.getString("serverIP","No IP defined");

        if(IP.equals("No IP defined")){
            Intent getIP = new Intent(MainActivity.this,SettingsActivity.class);
            startActivityForResult(getIP,1);
        }

        new SpajanjeProba().execute();

        setContentView(R.layout.activity_main);
        Typeface font = Typeface.createFromAsset(getAssets(), "fontawesome-webfont.ttf");

        Button temperatureBtn = (Button) findViewById(R.id.temperatureBtn);
        Button airCondBtn = (Button) findViewById(R.id.airCondBtn);
        Button lightBtn = (Button) findViewById(R.id.lightBtn);
        Button settingsBtn = (Button) findViewById(R.id.settingsBtn);
        settingsBtn.setTypeface(font);

        temperatureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchTemperatureActivity();
            }
        });

        airCondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchAirCondActivity();
            }
        });

        lightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchLightActivity();
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                launchSettingsActivity();
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        new SpajanjeProba().execute();
    }

    private void launchActivity(final Class<? extends Activity> activityClass) {
        final Intent i = new Intent(this, activityClass);
        startActivity(i);
    }

    private void launchTemperatureActivity(){
        launchActivity(TemperatureActivity.class);
    }

    private void launchLightActivity(){
        launchActivity(LightActivity.class);
    }

    private void launchAirCondActivity(){
        launchActivity(AirCondActivity.class);
    }

    private void launchSettingsActivity(){
        launchActivity(SettingsActivity.class);
    }


    public class SpajanjeProba extends AsyncTask<String,Void,Boolean> {

        @Override
        protected Boolean doInBackground(String... urlData) {
            return isConnectedToServer();

        }

        @Override
        protected void onPostExecute(Boolean result){
            if(!result){
                Log.d("PROJEKT",String.valueOf(result));
                Intent getIP = new Intent(MainActivity.this,ConnectionErrorActivity.class);
                startActivity(getIP);
            }
        }

        public boolean isConnectedToServer() {
            SharedPreferences prefs = getSharedPreferences(SettingsActivity.PREFS_NAME,MODE_PRIVATE);
            String IP = prefs.getString("serverIP","No IP defined");
            try{
                URL myUrl = new URL("http://"+IP+":8080/klima");
                HttpURLConnection connection = (HttpURLConnection) myUrl.openConnection();
                connection.setConnectTimeout(100);
                if(connection.getResponseCode()!=200)throw new IOException(connection.getResponseMessage());
                return true;
            } catch (IOException e) {
                return false;
            }
        }
    }

}
