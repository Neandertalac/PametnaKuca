package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class LightJSONParser {
    private String result;
    private LightPodaci rez;

    public LightJSONParser(String result){
        this.result = result;
    }

    public void parseJSON(){
        try {
            JSONObject jsonObject = new JSONObject(result);

            this.rez = new LightPodaci(
                    jsonObject.getString("prisustvo"),
                    jsonObject.getString("osvjetljenje"));
        } catch (JSONException e){
            Log.i("Svjetlo",e.getMessage(),e);
        }
    }

    public LightPodaci getData(){
        return this.rez;
    }
}
