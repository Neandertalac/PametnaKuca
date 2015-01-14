package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;


public class AirCondActivity extends Activity {
    private AirCondPodaci podaci;
    protected final int INTERVAL = 5000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(SettingsActivity.PREFS_NAME, MODE_PRIVATE);
        String distance = prefs.getString("granUdaljenost","No distance defined");

        if(distance.equals("No distance defined") | distance.equals("") ){
            Intent enterIP = new Intent(AirCondActivity.this, AirCondDistanceEnter.class);
            startActivityForResult(enterIP,1);
        }else{
            glavnaAktivnost();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        if(requestCode==1){
            if(resultCode ==RESULT_OK){
                glavnaAktivnost();
            }
        }
    }

    public void glavnaAktivnost(){
        setContentView(R.layout.activity_air_cond);
        Button settings = (Button) findViewById(R.id.settings);

        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(AirCondActivity.this,AirCondDistanceEnter.class);
                AirCondActivity.this.startActivity(settingsIntent);
            }
        });
        if(onConnected(getCurrentFocus()) == true) {
            final Handler handler = new Handler();
            TimerTask timerTask = new TimerTask() {
                @Override
                public void run() {
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                new SpajanjeServer().execute("klima");
                            } catch (Exception e) {
                                e.printStackTrace();
                                //Intent settingsIntent = new Intent(TemperatureActivity.this,TemperatureEnter.class);
                                //TemperatureActivity.this.startActivity(settingsIntent);
                            }
                        }
                    });
                }
            };
            Timer timer = new Timer();
            timer.schedule(timerTask, 0,INTERVAL);
        }
    }


    public boolean onConnected(View view){
        ConnectivityManager connMng = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = connMng.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){
            return true;
        }else{
            String ispis = getString(R.string.intConnErrorText);
            Toast.makeText(this.getApplicationContext(), ispis, Toast.LENGTH_LONG);
            finish();
            return false;
        }
    }


    public class SpajanjeServer extends AsyncTask<String,Void,String> {

        private String jsonUdaljenost;

        @Override
        protected String doInBackground(String... urlData) {
            try {
                SharedPreferences prefs = getSharedPreferences(SettingsActivity.PREFS_NAME,MODE_PRIVATE);
                String IP = prefs.getString("serverIP", "greska");
                if(IP.equals("greska")) Toast.makeText(getApplicationContext(),"Potrebno je definirati " +
                        "IP adresu poslužitelja",Toast.LENGTH_LONG).show();
                URL url;
                url = new URL("http://"+IP+":8080/" + urlData[0]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                if (conn.getResponseCode() != 200) {
                    throw new IOException(conn.getResponseMessage());
                }
                BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                this.jsonUdaljenost = read.readLine();
                Log.d("PROJEKT", "učitao sam: " + this.jsonUdaljenost);
                read.close();

                conn.disconnect();
            } catch (MalformedURLException e) {
                Log.i("Udaljenost", e.getMessage(), e);
            } catch (IOException e) {
                Log.i("Udaljenost", e.getMessage(), e);
            }
            return this.jsonUdaljenost;
        }


        protected void onPostExecute(String result){
            AirCondJSONParser parser = new AirCondJSONParser(result);
            parser.parseJSON();
            podaci = parser.getData();
            ispis();
        }

    }

    private void ispis(){
        TextView editText2 = (TextView) AirCondActivity.this.findViewById(R.id.ispisUdalj);
        editText2.setText(podaci.getUdaljenost(), TextView.BufferType.NORMAL);

        TextView stanje = (TextView) AirCondActivity.this.findViewById(R.id.Stanje);
        TextView ispisUdalj = (TextView) AirCondActivity.this.findViewById(R.id.ispisZeljUdalj);

        SharedPreferences prefs = getSharedPreferences(SettingsActivity.PREFS_NAME,MODE_PRIVATE);
        String udaljenostMemorija = prefs.getString("granUdaljenost","undefined");

        if(udaljenostMemorija.compareTo("undefined") != 0){
            ispisUdalj.setText(udaljenostMemorija);
        } else{
            ispisUdalj.setText("Nedefinirano.");
        }

        float udaljenostMemorijaFloat = Float.parseFloat(udaljenostMemorija);

        float temp = Float.parseFloat(podaci.getUdaljenost());

        if(temp < udaljenostMemorijaFloat) stanje.setText("Hlađenje/grijanje je smanjeno.");
        else stanje.setText("Hlađenje/grijanje radi standardnim intenzitetom.");

    }


}
