package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;


import android.test.suitebuilder.annotation.MediumTest;

import junit.framework.TestCase;

public class AirCondJSONParserTest extends TestCase{

    @MediumTest
    public void testGetData(){
        AirCondPodaci podaci = new AirCondPodaci("20.20");
        String ret = "{\"sonar\":345.387}";
        AirCondJSONParser parser = new AirCondJSONParser(ret);
        parser.parseJSON();
        assertEquals(podaci.getUdaljenost(), parser.getData().getUdaljenost());
    }

    @MediumTest
    public void testUdaljenostShouldreturnErrorWhenDistanceIsNegative(){
        AirCondPodaci podaci = new AirCondPodaci("-1");
        assertEquals(podaci.getUdaljenost(), "Nedefinirano");

    }
}
