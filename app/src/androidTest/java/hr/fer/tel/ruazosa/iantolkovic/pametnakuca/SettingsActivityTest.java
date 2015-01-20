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

public class SettingsActivityTest extends ActivityInstrumentationTestCase2<SettingsActivity> {

    private SettingsActivity activity;

    private LinearLayout root;
    private TextView enterIPText;
    private EditText IPAddress;
    private Button submit;

    public SettingsActivityTest(){
        super(SettingsActivity.class);
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

        root = (LinearLayout) activity.findViewById(R.id.root);
        enterIPText = (TextView) activity.findViewById(R.id.enterIPText);
        IPAddress = (EditText) activity.findViewById(R.id.ipAdress);
        submit = (Button) activity.findViewById(R.id.enterIpBtn);
    }

    @MediumTest
    public void testRoot(){
        assertThat(root)
                .isVisible()
                .isVertical()
                .hasChildCount(3);

        assertGroupContains(root, enterIPText);
        assertGroupContains(root, IPAddress);
        assertGroupContains(root, submit);
    }

    @MediumTest
    public void testEnterIPText(){
        assertThat(enterIPText)
                .isVisible()
                .hasText(R.string.enterIPAdressText);
    }

    @MediumTest
    public void testIPAddress(){
        assertThat(IPAddress)
                .isVisible()
                .hasMaxLines(1)
                .hasInputType(TYPE_CLASS_NUMBER | TYPE_NUMBER_FLAG_DECIMAL);
    }

    @MediumTest
    public void testSubmit(){
        assertThat(submit)
                .isVisible()
                .hasText(R.string.ipEnterBtnText);
    }

}
