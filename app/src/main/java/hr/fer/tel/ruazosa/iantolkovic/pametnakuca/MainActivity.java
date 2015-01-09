package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

import android.app.Activity;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;


public class MainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Typeface font = Typeface.createFromAsset( getAssets(), "fontawesome-webfont.ttf" );

        Button temperatureBtn = (Button) findViewById(R.id.temperatureBtn);
        Button airCondBtn = (Button) findViewById(R.id.airCondBtn);
        Button lightBtn = (Button) findViewById(R.id.lightBtn);
        Button settingsBtn = (Button) findViewById(R.id.settingsBtn);

        settingsBtn.setTypeface(font);

        temperatureBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        airCondBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        lightBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

}
