package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;


import org.json.JSONException;
import org.json.JSONObject;

public class AirCondJSONParser {

    private String result;
    private AirCondPodaci rez;

    public AirCondJSONParser(String result){
        super();
        this.result=result;
    }

    public void parseJSON() {
        try {

            JSONObject jObject = new JSONObject(result);

            this.rez = new AirCondPodaci(
                    jObject.getString("sonar")
            );
        } catch (JSONException e) {
            e.getStackTrace();
        }
        catch (NullPointerException e){
            e.getStackTrace();
        }
    }

    public AirCondPodaci getData(){
        return rez;
    }

}
