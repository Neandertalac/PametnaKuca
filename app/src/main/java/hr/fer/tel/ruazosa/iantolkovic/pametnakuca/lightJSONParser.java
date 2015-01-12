package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

public class lightJSONParser {
    private String result;
    private lightPodaci rez;

    public lightJSONParser(String result){
        this.result = result;
    }

    public void parseJSON(){
        try {
            JSONObject jsonObject = new JSONObject(result);

            this.rez = new lightPodaci(
                    jsonObject.getString("prisustvo"),
                    jsonObject.getString("osvjetljenje"));
        } catch (JSONException e){
            Log.i("Svjetlo",e.getMessage(),e);
        }
    }

    public lightPodaci getData(){
        return this.rez;
    }
}
