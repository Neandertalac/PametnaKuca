package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

import android.content.Context;
import android.content.SharedPreferences;
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

public class TemperatureEnterTest extends ActivityInstrumentationTestCase2<TemperatureEnter>{

    private TemperatureEnter activity;

    private LinearLayout root;
    private TextView enterTempText;
    private EditText enterTemp;
    private Button returnBtn;

    public TemperatureEnterTest() {
        super(TemperatureEnter.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();

        SharedPreferences.Editor editor = getInstrumentation().getTargetContext().getSharedPreferences(SettingsActivity.PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString("serverIP","10.0.2.2");
        editor.putString("zeljenaTemperatura","20");
        editor.putString("granUdaljenost","100");
        editor.commit();

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

    @MediumTest
    public void testEnterTempText(){
        assertThat(enterTempText)
                .isVisible()
                .hasText(R.string.enterTemperatureText);
    }

    @MediumTest
    public void testTemperatureEnter(){
        assertThat(enterTemp)
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
