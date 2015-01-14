package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

public class LightPodaci {
    private String prisustvo;
    private String osvjetljenje;

    public LightPodaci(String sonar, String osvjetljenje){
        this.prisustvo=sonar;
        this.osvjetljenje=osvjetljenje;
    }

    public double getPrisustvo(){
        return Double.parseDouble(this.prisustvo);
    }

    public String getOsvjetljenje(){
        return this.osvjetljenje;
    }

    public String toString(){
        return "prisustvo:"+this.prisustvo+";osvjetljenje:"+this.osvjetljenje;
    }
}
