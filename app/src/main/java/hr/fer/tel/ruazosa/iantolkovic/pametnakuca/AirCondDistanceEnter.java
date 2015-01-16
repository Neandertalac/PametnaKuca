package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.InputType;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


public class AirCondDistanceEnter extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_air_cond_distance_enter);

        final Button distanceBtn = (Button) findViewById(R.id.distanceBtn);
        final EditText distanceEnter = (EditText) findViewById(R.id.distanceEnter);

        distanceEnter.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

        distanceBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String distance = distanceEnter.getText().toString();

                SharedPreferences.Editor editor = getSharedPreferences(SettingsActivity.PREFS_NAME, MODE_PRIVATE).edit();
                editor.putString("granUdaljenost",distance);
                editor.commit();
                Toast.makeText(getApplicationContext(), "Udaljenost je pohranjena", Toast.LENGTH_LONG).show();
                Intent returnIntent = new Intent();
                setResult(RESULT_OK, returnIntent);
                finish();
            }
        });
    }

}
