package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.test.ViewAsserts.assertGroupContains;
import static org.assertj.android.api.Assertions.assertThat;

public class AirCondActivityTest  extends ActivityInstrumentationTestCase2<AirCondActivity>{

    private AirCondActivity activity;

    private TextView currDistanceText;
    private TextView currDistance;
    private TextView limitDistanceText;
    private TextView limitDistance;
    private TextView airCondIntensityText;
    private TextView airCondIntensity;
    private Button changeDistanceBtn;
    private Button returnBtn;
    LinearLayout root;
    LinearLayout root1;

    public AirCondActivityTest(){
        super(AirCondActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();

        activity = getActivity();

        currDistanceText = (TextView) activity.findViewById(R.id.trenUdaljText);
        currDistance = (TextView) activity.findViewById(R.id.ispisUdalj);
        limitDistanceText = (TextView) activity.findViewById(R.id.zeljUdaljText);
        limitDistance = (TextView) activity.findViewById(R.id.ispisZeljUdalj);
        airCondIntensityText = (TextView) activity.findViewById(R.id.stanjeText);
        airCondIntensity = (TextView) activity.findViewById(R.id.Stanje);
        changeDistanceBtn = (Button) activity.findViewById(R.id.settings);
        returnBtn = (Button) activity.findViewById(R.id.airCondeReturnBtn);
        root = (LinearLayout) activity.findViewById(R.id.root);
        root1 = (LinearLayout) activity.findViewById(R.id.root1);
    }

    @MediumTest
    public void testRoot(){
        assertThat(root)
                .isVisible()
                .isVertical()
                .hasChildCount(7);

        assertGroupContains(root, currDistanceText);
        assertGroupContains(root, currDistance);
        assertGroupContains(root, limitDistanceText);
        assertGroupContains(root, limitDistance);
        assertGroupContains(root, airCondIntensityText);
        assertGroupContains(root, airCondIntensity);
        assertGroupContains(root, root1);
    }

    @MediumTest
    public void testRoot1(){
        assertThat(root1)
                .isVisible()
                .isHorizontal()
                .hasChildCount(2);
        assertGroupContains(root1, changeDistanceBtn);
        assertGroupContains(root1, returnBtn);
    }

    @MediumTest
    public void testCurrDistanceText(){
        assertThat(currDistanceText)
                .isVisible()
                .hasText(R.string.airCondCurrentDistanceText);
    }

    @MediumTest
    public void testLimitDistanceText(){
        assertThat(limitDistanceText)
                .isVisible()
                .hasText(R.string.airCondSetDistanceText);
    }

    @MediumTest
    public void testIntensityText(){
        assertThat(airCondIntensityText)
                .isVisible()
                .hasText(R.string.airCondIntText);
    }

    @MediumTest
    public void testChangeDistanceBtn(){
        assertThat(changeDistanceBtn)
                .isVisible()
                .hasText(R.string.airCondChangeDistanceText);
    }

    @MediumTest
    public void testReturnBtn(){
        assertThat(returnBtn)
                .isVisible()
                .hasText(R.string.returnToMainActivityBtnText);
    }


}
