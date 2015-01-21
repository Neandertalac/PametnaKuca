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
        if(prozorOZ!="0" && prozorOZ!="1")
            return "Nema podataka";
        return prozorOZ;
    }

    public String getVrataOZ(){
        if(vrataOZ!="0" && vrataOZ!="1")
            return "Nema podataka";
        return vrataOZ;
    }


    public String toString(){
        return "Temperatura: "+temperatura+", "+"Vlaznost: " +vlaznost+","+"Prozor: "+prozorOZ+", "+"Vrata: "+vrataOZ;
    }

}
