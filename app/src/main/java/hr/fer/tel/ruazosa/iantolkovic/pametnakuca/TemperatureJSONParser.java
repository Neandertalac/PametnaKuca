package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;


import org.json.JSONException;
import org.json.JSONObject;

public class TemperatureJSONParser {
    private String result;
    private TemperaturePodaci rez;

    public TemperatureJSONParser(String result){
        super();
        this.result=result;
    }

    public void parseJSON() {
        try {

            JSONObject jObject = new JSONObject(result);

            this.rez = new TemperaturePodaci(
                    jObject.getString("temperatura"),
                    jObject.getString("vlaznost"),
                    jObject.getString("prozorOZ"),
                    jObject.getString("vrataOZ")
            );
        } catch (JSONException e) {
            e.getStackTrace();
        }
    }

    public TemperaturePodaci getData(){
        return rez;
    }
}
