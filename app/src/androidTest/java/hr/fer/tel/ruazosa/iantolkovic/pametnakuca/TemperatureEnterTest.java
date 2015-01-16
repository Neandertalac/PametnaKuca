package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.test.ViewAsserts.assertGroupContains;
import static org.assertj.android.api.Assertions.assertThat;

public class TemperatureEnterTest extends ActivityInstrumentationTestCase2<TemperatureEnter>{

    private TemperatureEnter activity;

    private LinearLayout root;
    private TextView enterTempText;
    private EditText enterTemp;
    private Button returnBtn;

    public TemperatureEnterTest(){
        super(TemperatureEnter.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();

        activity = getActivity();

        enterTempText = (TextView) activity.findViewById(R.id.enterTempText);
        enterTemp = (EditText) activity.findViewById(R.id.temperatureEnter);
        returnBtn = (Button) activity.findViewById(R.id.temperatureBtn);
        root = (LinearLayout) activity.findViewById(R.id.root);
    }

    @MediumTest
    public void testRoot(){
        assertThat(root)
                .isVisible()
                .isVertical()
                .hasChildCount(3);

        assertGroupContains(root, enterTempText);
        assertGroupContains(root, enterTemp);
        assertGroupContains(root, returnBtn);
    }
}
