package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;


public class TemperaturePodaci {
    private String temperatura, vlaznost, prozorOZ,vrataOZ;

    public TemperaturePodaci(String temperatura, String vlaznost, String prozorOZ, String vrataOZ){
        super();
        this.temperatura = temperatura;
        this.prozorOZ = prozorOZ;
        this.vrataOZ = vrataOZ;
        this.vlaznost = vlaznost;
    }

    public String getTemperatura(){
        return  this.temperatura;
    }

    public String getVlaznost(){
        return this.vlaznost;
    }

    public String getProzorOZ(){
        return prozorOZ;
    }

    public String getVrataOZ(){
        return vrataOZ;
    }


    public String toString(){
        return "Temperatura: "+temperatura+", "+"Vlaznost" +","+"Prozor: "+prozorOZ+", "+"Vrata"+vrataOZ;
    }

}
