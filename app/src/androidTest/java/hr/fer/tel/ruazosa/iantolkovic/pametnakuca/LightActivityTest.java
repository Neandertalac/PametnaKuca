package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;


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
}
