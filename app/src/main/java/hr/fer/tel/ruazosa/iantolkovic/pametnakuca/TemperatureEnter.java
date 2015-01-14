package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class TemperatureEnter extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_temperature_enter);

        final Button temperatureBtn = (Button) findViewById(R.id.temperatureBtn);
        final EditText temperatureEnter = (EditText) findViewById(R.id.temperatureEnter);

        temperatureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String temperature = temperatureEnter.getText().toString();

                SharedPreferences.Editor editor = getSharedPreferences(SettingsActivity.PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("zeljenaTemperatura",temperature);
                editor.commit();
                Toast.makeText(getApplicationContext(),"Temperatura je pohranjena",Toast.LENGTH_LONG).show();
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }


}
