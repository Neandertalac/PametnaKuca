package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;


import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.test.ViewAsserts.assertGroupContains;
import static org.assertj.android.api.Assertions.assertThat;

public class LightActivityTest extends ActivityInstrumentationTestCase2<LightActivity> {

    private LightActivity activity;

    ImageView picture;
    TextView message;
    Button returnBtn;
    LinearLayout root;

    public LightActivityTest(){
        super(LightActivity.class);
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

        picture = (ImageView) activity.findViewById(R.id.picture);
        message = (TextView) activity.findViewById(R.id.lightLayoutPrintText);
        returnBtn = (Button) activity.findViewById(R.id.lightReturnButton);
        root = (LinearLayout) activity.findViewById(R.id.root);
    }

    @MediumTest
    public void testRoot(){
        assertThat(root)
                .isVisible()
                .hasChildCount(3)
                .isVertical();

        assertGroupContains(root, picture);
        assertGroupContains(root, message);
        assertGroupContains(root, returnBtn);
    }

    @MediumTest
    public void testPicture(){
        assertThat(picture).isShown();
    }

    @MediumTest
    public void testReturnBtn(){
        assertThat(returnBtn)
                .isVisible()
                .hasText(R.string.returnToMainActivityBtnText);
    }
}
