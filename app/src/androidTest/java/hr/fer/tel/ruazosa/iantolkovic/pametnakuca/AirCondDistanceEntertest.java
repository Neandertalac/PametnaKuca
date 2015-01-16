package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.test.ViewAsserts.assertGroupContains;
import static android.text.InputType.TYPE_CLASS_NUMBER;
import static android.text.InputType.TYPE_NUMBER_FLAG_DECIMAL;
import static org.assertj.android.api.Assertions.assertThat;

public class AirCondDistanceEnterTest extends ActivityInstrumentationTestCase2<AirCondDistanceEnter>{

    private AirCondDistanceEnter activity;

    private LinearLayout root;
    private TextView enterDistanceText;
    private EditText enterDistance;
    private Button returnBtn;

    public AirCondDistanceEnterTest(){
        super(AirCondDistanceEnter.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();

        activity = getActivity();

        enterDistanceText = (TextView) activity.findViewById(R.id.enterDistanceText);
        enterDistance = (EditText) activity.findViewById(R.id.distanceEnter);
        returnBtn = (Button) activity.findViewById(R.id.distanceBtn);
        root = (LinearLayout) activity.findViewById(R.id.root);
    }

    @MediumTest
    public void testRoot(){
        assertThat(root)
                .isVisible()
                .isVertical()
                .hasChildCount(3);

        assertGroupContains(root, enterDistanceText);
        assertGroupContains(root, enterDistance);
        assertGroupContains(root, returnBtn);
    }

    @MediumTest
    public void testEnterDistanceText(){
        assertThat(enterDistanceText)
                .isVisible()
                .hasText(R.string.enterAirCondDistanceText);
    }

    @MediumTest
    public void testEnterDistance(){
        assertThat(enterDistance)
                .isVisible()
                .hasMaxLines(1)
                .hasInputType(TYPE_CLASS_NUMBER | TYPE_NUMBER_FLAG_DECIMAL);
    }

    @MediumTest
    public void testReturnBtn(){
        assertThat(returnBtn)
                .isVisible()
                .hasText(R.string.ipEnterBtnText);
    }

}
