package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

import android.test.suitebuilder.annotation.MediumTest;

import junit.framework.TestCase;

public class TemperatureJSONParserTest extends TestCase{

    @MediumTest
    public void testGetData(){
        TemperaturePodaci podaci = new TemperaturePodaci("20","50","1","1");
        String ret = "{\"temperatura\":20,\"prozorOZ\":1,\"vlaznost\":50,\"vrataOZ\":1}";
        TemperatureJSONParser parser = new TemperatureJSONParser(ret);
        parser.parseJSON();
        assertEquals(podaci.getProzorOZ(), parser.getData().getProzorOZ());
        assertEquals(podaci.getTemperatura(), parser.getData().getTemperatura());
        assertEquals(podaci.getVrataOZ(), parser.getData().getVrataOZ());
        assertEquals(podaci.getVlaznost(), parser.getData().getVlaznost());
    }

    @MediumTest
    public void testGetProzorOZEnter0(){
        String ret = "{\"temperatura\":20,\"prozorOZ\":0,\"vlaznost\":50,\"vrataOZ\":1}";
        TemperatureJSONParser parser = new TemperatureJSONParser(ret);
        parser.parseJSON();
        assertEquals("0", parser.getData().getProzorOZ());
    }

    @MediumTest
    public void testGetProzorOZEnter1(){
        String ret = "{\"temperatura\":20,\"prozorOZ\":1,\"vlaznost\":50,\"vrataOZ\":0}";
        TemperatureJSONParser parser = new TemperatureJSONParser(ret);
        parser.parseJSON();
        assertEquals("1", parser.getData().getProzorOZ());
    }

    @MediumTest
    public void testGetProzorCanReturnOnly0or1(){
        String ret = "{\"temperatura\":20,\"prozorOZ\":1.2,\"vlaznost\":50,\"vrataOZ\":0}";
        TemperatureJSONParser parser = new TemperatureJSONParser(ret);
        parser.parseJSON();
        assertEquals("Nema podataka", parser.getData().getProzorOZ());
    }

    @MediumTest
    public void testGetVrataOZEnter0(){
        String ret = "{\"temperatura\":20,\"prozorOZ\":0,\"vlaznost\":50,\"vrataOZ\":0}";
        TemperatureJSONParser parser = new TemperatureJSONParser(ret);
        parser.parseJSON();
        assertEquals("0", parser.getData().getVrataOZ());
    }

    @MediumTest
    public void testGetVrataOZEnter1(){
        String ret = "{\"temperatura\":20,\"prozorOZ\":1,\"vlaznost\":50,\"vrataOZ\":1}";
        TemperatureJSONParser parser = new TemperatureJSONParser(ret);
        parser.parseJSON();
        assertEquals("1", parser.getData().getVrataOZ());
    }

    @MediumTest
    public void testGetVrataCanReturnOnly0or1(){
        String ret = "{\"temperatura\":20,\"prozorOZ\":1.2,\"vlaznost\":50,\"vrataOZ\":0.5}";
        TemperatureJSONParser parser = new TemperatureJSONParser(ret);
        parser.parseJSON();
        assertEquals("Nema podataka", parser.getData().getVrataOZ());
    }

    @MediumTest
    public void testTemperatureShouldReturnReals(){
        String ret = "{\"temperatura\":20.2,\"prozorOZ\":1,\"vlaznost\":50,\"vrataOZ\":0}";
        TemperatureJSONParser parser = new TemperatureJSONParser(ret);
        parser.parseJSON();
        assertEquals("20.2", parser.getData().getTemperatura());
    }

    @MediumTest
    public void testHumidityShouldReturnReals(){
        String ret = "{\"temperatura\":20.2,\"prozorOZ\":1,\"vlaznost\":50.9,\"vrataOZ\":0}";
        TemperatureJSONParser parser = new TemperatureJSONParser(ret);
        parser.parseJSON();
        assertEquals("50.9", parser.getData().getVlaznost());
    }

    @MediumTest
    public void testToString(){
        String ret = "{\"temperatura\":20,\"prozorOZ\":1,\"vlaznost\":50,\"vrataOZ\":0}";
        TemperatureJSONParser parser = new TemperatureJSONParser(ret);
        parser.parseJSON();
        assertEquals("Temperatura: 20, Vlaznost: 50,Prozor: 1, Vrata: 0", parser.getData().toString());
    }
}
