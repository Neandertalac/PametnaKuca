package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;
import android.test.suitebuilder.annotation.MediumTest;

import junit.framework.TestCase;

public class LightJSONParserTest extends TestCase {

    @MediumTest
    public void testGetData(){
        LightPodaci podaci = new LightPodaci("1", "100.34");
        String ret = "{\"prisustvo\":1,\"osvjetljenje\":100.34}";
        LightJSONParser parser = new LightJSONParser(ret);
        parser.parseJSON();
        assertEquals(podaci.getOsvjetljenje(), parser.getData().getOsvjetljenje());
        assertEquals(podaci.getPrisustvo(), parser.getData().getPrisustvo());
    }

    @MediumTest
    public void  testGetPrisustvoSchouldWorkOnlyWith0or1(){
        LightPodaci podaci = new LightPodaci("5", "100.34");
        assertEquals(podaci.getPrisustvo(), -1.0);
    }

    @MediumTest
    public void testToString(){
        String ret = "{\"prisustvo\":1,\"osvjetljenje\":100.34}";
        LightJSONParser parser = new LightJSONParser(ret);
        parser.parseJSON();
        assertEquals("prisustvo:1;osvjetljenje:100.34", parser.getData().toString());
    }
}
