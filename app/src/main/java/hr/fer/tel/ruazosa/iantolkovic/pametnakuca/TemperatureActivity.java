package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.Spanned;
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


public class TemperatureActivity extends Activity {

    private TemperaturePodaci podaci;
    private static final int INTERVAL=3000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        SharedPreferences prefs = getSharedPreferences(SettingsActivity.PREFS_NAME, MODE_PRIVATE);
        String temperature = prefs.getString("zeljenaTemperatura","No temperature defined");

        if(temperature.equals("No temperature defined") | temperature.equals("")){
            Intent enterIP = new Intent(TemperatureActivity.this, TemperatureEnter.class);
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
        setContentView(R.layout.activity_temperature);
        Button settings = (Button) findViewById(R.id.settings);
        Button returnBtn = (Button) findViewById(R.id.returnBtn);

        returnBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent settingsIntent = new Intent(TemperatureActivity.this,TemperatureEnter.class);
                TemperatureActivity.this.startActivity(settingsIntent);
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
                                new SpajanjeServer().execute("temp");
                             } catch (Exception e) {
                                 e.printStackTrace();
                             }
                         }
                     });
                    }
                };
            Timer timer = new Timer();
            timer.schedule(timerTask, 0, INTERVAL);

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

        private String jsonPodaci;

        @Override
        protected String doInBackground(String... urlData) {
            try {
                SharedPreferences prefs = getSharedPreferences(SettingsActivity.PREFS_NAME,MODE_PRIVATE);
                String IP = prefs.getString("serverIP","No IP defined");
                URL url;
                url = new URL("http://"+IP+":8080/" + urlData[0]);

                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setConnectTimeout(200);
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
            if(result==null){
                ispisError();
            }else{
                TemperatureJSONParser parser = new TemperatureJSONParser(result);
                parser.parseJSON();
                podaci = parser.getData();
                ispis();
            }
        }

    }

    private void ispis(){
        String zaIspis = podaci.getTemperatura() + "\u2103";
        TextView editText2 = (TextView) TemperatureActivity.this.findViewById(R.id.ispisTemp);
        editText2.setText(zaIspis, TextView.BufferType.NORMAL);

        //Ispis trenutne vlaznsti u prostoriji
        String vlaznost = podaci.getVlaznost()+"%";
        TextView editText3 = (TextView) TemperatureActivity.this.findViewById(R.id.ispisVlaz);
        editText3.setText(vlaznost, TextView.BufferType.NORMAL);

        //Ipis dali su prozori otvoren ili zatvoreni
        TextView vrataIspis = (TextView) TemperatureActivity.this.findViewById(R.id.vrata1);
        if(podaci.getVrataOZ().equals("1"))vrataIspis.setText("Vrata su otvorena.");
        else vrataIspis.setText("Vrata su zatvorena.");

        TextView prozorIspis = (TextView) TemperatureActivity.this.findViewById(R.id.prozor1);
        if(podaci.getProzorOZ().equals("1"))prozorIspis.setText("Prozor je otvoren.");
        else prozorIspis.setText("Prozor je zatvoren.");

        TextView aktivnoti = (TextView) TemperatureActivity.this.findViewById(R.id.Aktivnosti);
        TextView ispisTemp = (TextView) TemperatureActivity.this.findViewById(R.id.ispisZeljTemp);

        //Dohvacanje podataka o temperaturi iz memorije
        SharedPreferences prefs = getSharedPreferences(SettingsActivity.PREFS_NAME,MODE_PRIVATE);
        String temperature = prefs.getString("zeljenaTemperatura","No temperature defined");
        ispisTemp.setText("Željena temperatura: "+temperature+"\u2103");
        //zeljTemp - temperatura pohranjena u memoriji
        Float zeljTemp = Float.parseFloat(temperature);

        //Ispis potrebne aktivnosti
        float temp = Float.parseFloat(podaci.getTemperatura());

        if(temp<zeljTemp && ((podaci.getProzorOZ().equals("1")) || (podaci.getVrataOZ().equals("1")))){
            aktivnoti.setText("Zatvorite otvorena vrata ili prozore");
            aktivnoti.setTextColor(Color.parseColor("#F80000"));
        }
        else if(temp>zeljTemp && ((podaci.getProzorOZ().equals("0")) || (podaci.getVrataOZ().equals("0")))){
            aktivnoti.setText("Otvorite vrata ili prozor");
            aktivnoti.setTextColor(Color.parseColor("#F80000"));
        }
        else {
            aktivnoti.setText("Nije potrebna nikakva aktivnost");
            aktivnoti.setTextColor(Color.parseColor("#808080"));
        }
    }

    private void ispisError(){
        TextView editText2 = (TextView) TemperatureActivity.this.findViewById(R.id.ispisTemp);
        editText2.setText("N/A", TextView.BufferType.NORMAL);
        TextView editText3 = (TextView) TemperatureActivity.this.findViewById(R.id.ispisVlaz);
        editText3.setText("N/A", TextView.BufferType.NORMAL);
        TextView vrataIspis = (TextView) TemperatureActivity.this.findViewById(R.id.vrata1);
        vrataIspis.setText("Nisu dostupni podaci o stanju vratiju.");
        TextView prozorIspis = (TextView) TemperatureActivity.this.findViewById(R.id.prozor1);
        prozorIspis.setText("Nisu dostupni podaci o stanju prozora.");
        TextView aktivnoti = (TextView) TemperatureActivity.this.findViewById(R.id.Aktivnosti);
        TextView ispisTemp = (TextView) TemperatureActivity.this.findViewById(R.id.ispisZeljTemp);
        SharedPreferences prefs = getSharedPreferences(SettingsActivity.PREFS_NAME,MODE_PRIVATE);
        String temperature = prefs.getString("zeljenaTemperatura","No temperature defined");
        Spanned title = Html.fromHtml("<b> Željena temperatura: </b>");
        ispisTemp.setText(title+temperature+"\u2103");
        aktivnoti.setText("Podaci o stanju u prostoriji nisu dostupni.");

    }

}
