package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

public class AirCondPodaci {

    private String distance;

    public AirCondPodaci(String distance) {
        super();
        this.distance = distance;
    }

    public String getUdaljenost(){
        return this.distance;
    }


    public String toString(){
        return "Udaljenost:" + distance;
    }

}
