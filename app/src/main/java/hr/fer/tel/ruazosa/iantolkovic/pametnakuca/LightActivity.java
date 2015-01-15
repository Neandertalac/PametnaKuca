package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Timer;
import java.util.TimerTask;

public class LightActivity extends Activity {
    public static final int INTERVAL = 1000;
    private LightPodaci podaci;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        glavnaAktivnost();
    }

    public void glavnaAktivnost(){
        setContentView(R.layout.activity_light);
        Button retBtn = (Button) findViewById(R.id.lightRtrnButton);

        retBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
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
                            try{
                                new SpajanjeServer().execute("svjetlo");
                            }catch(Exception e){
                                e.printStackTrace();
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
            return false;
        }
    }

    public class SpajanjeServer extends AsyncTask<String,Void,String> {

        private String jsonPodaci;

        @Override
        protected String doInBackground(String... urlData) {
            try {
                SharedPreferences prefs = getSharedPreferences(SettingsActivity.PREFS_NAME,MODE_PRIVATE);
                String IP = prefs.getString("serverIP","No IP defined");
                URL url;
                url = new URL("http://"+IP+":8080/" + urlData[0]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("GET");
                if (conn.getResponseCode() != 200) {
                    throw new IOException(conn.getResponseMessage());
                }
                BufferedReader read = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                this.jsonPodaci = read.readLine();
                Log.d("PROJEKT", "učitao sam: " + this.jsonPodaci);
                read.close();

                conn.disconnect();
            } catch (MalformedURLException e) {
                Log.i("Temperatura", e.getMessage(), e);
            } catch (IOException e) {
                Log.i("Temperatura", e.getMessage(), e);
            }
            return this.jsonPodaci;
        }

        protected void onPostExecute(String result){
            setContentView(R.layout.activity_light);
            TextView ispis = (TextView) findViewById(R.id.lightLayoutPrintText);
            ImageView pic = (ImageView) findViewById(R.id.picture);

            if(result!=null) {
                LightJSONParser parser = new LightJSONParser(result);
                parser.parseJSON();
                podaci = parser.getData();
                if (podaci.getPrisustvo() == 0) {
                    pic.setImageResource(R.drawable.zaruljaoff);
                    ispis.setText("Nema prisutnih osoba u prostoriji, nije potrebno paliti svjetlo.");
                } else {
                    if (Double.parseDouble(podaci.getOsvjetljenje()) < 250) {
                        pic.setImageResource(R.drawable.zaruljaon);
                        ispis.setText("U prostoriji se netko nalazi i ostvjetljenje nije zadovoljavajuće.");
                    } else {
                        pic.setImageResource(R.drawable.zaruljaoff);
                        ispis.setText("U prostoriji se netko nalazi, ali je osvjetljenje zadovoljavajuće.");
                    }
                }
            }else{
                pic.setImageResource(R.drawable.upitnik);
                ispis.setText("Podaci o osvjetljenju i prisutnosti nisu dostupni");
            }

        }
    }



}
