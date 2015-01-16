package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.test.ViewAsserts.assertGroupContains;
import static org.assertj.android.api.Assertions.assertThat;


public class TemperatureActivityTest extends ActivityInstrumentationTestCase2<TemperatureActivity>{

    private TemperatureActivity activity;

    private TextView currTemptext;
    private TextView currTemp;
    private TextView currHumidityText;
    private TextView currHumidity;
    private TextView window;
    private TextView doors;
    private TextView actionsText;
    private TextView actions;
    private Button changeTempBtn;
    private Button returnBtn;
    LinearLayout root;
    LinearLayout root1;

    public TemperatureActivityTest(){
        super(TemperatureActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();

        activity = getActivity();

        currTemptext = (TextView) activity.findViewById(R.id.currTempText);
        currTemp = (TextView) activity.findViewById(R.id.ispisTemp);
        currHumidityText = (TextView) activity.findViewById(R.id.currHumText);
        currHumidity = (TextView) activity.findViewById(R.id.ispisVlaz);
        window = (TextView) activity.findViewById(R.id.prozor1);
        doors = (TextView) activity.findViewById(R.id.vrata1);
        actionsText = (TextView) activity.findViewById(R.id.actionsText);
        actions = (TextView) activity.findViewById(R.id.Aktivnosti);
        changeTempBtn = (Button) activity.findViewById(R.id.settings);
        returnBtn = (Button) activity.findViewById(R.id.returnBtn);
        root = (LinearLayout) activity.findViewById(R.id.root);
        root1 = (LinearLayout) activity.findViewById(R.id.root1);
    }

    @MediumTest
    public void testRoot(){
        assertThat(root)
                .isVisible()
                .isVertical()
                .hasChildCount(11);
        assertGroupContains(root, currTemptext);
        assertGroupContains(root, currTemp);
        assertGroupContains(root, currHumidityText);
        assertGroupContains(root, currHumidity);
        assertGroupContains(root, window);
        assertGroupContains(root, doors);
        assertGroupContains(root, actionsText);
        assertGroupContains(root, actions);
        assertGroupContains(root, root1);
    }

    @MediumTest
    public void testRoot1(){
        assertThat(root1)
                .isVisible()
                .isHorizontal()
                .hasChildCount(2);
        assertGroupContains(root1, changeTempBtn);
        assertGroupContains(root1, returnBtn);
    }
}
