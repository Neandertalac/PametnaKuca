package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

import android.app.Activity;
import android.app.Instrumentation;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.test.TouchUtils.clickView;
import static android.test.ViewAsserts.assertGroupContains;
import static org.assertj.android.api.Assertions.assertThat;

public class MainActivityTest extends ActivityInstrumentationTestCase2<MainActivity>{
    public static final int ACTIVITY_TIMEOUT = 1000;
    private MainActivity activity;

    private LinearLayout root;
    private Button tempBtn, lightBtn, airCondBtn, enterIPBtn;

    public MainActivityTest(){
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();

        activity = getActivity();
        tempBtn = (Button) activity.findViewById(R.id.temperatureBtn);
        lightBtn = (Button) activity.findViewById(R.id.lightBtn);
        airCondBtn = (Button) activity.findViewById(R.id.airCondBtn);
        enterIPBtn = (Button) activity.findViewById(R.id.settingsBtn);
        root = (LinearLayout) activity.findViewById(R.id.root);
    }

    @MediumTest
    public void testLayout(){
        assertThat(root)
                .hasChildCount(4)
                .isVertical()
                .isVisible();
        assertGroupContains(root, launchTemperatureActivity);
        assertGroupContains(root, launchLightActivity);
        assertGroupContains(root, launchAirCondActivity);
        assertGroupContains(root, launchSettingsActivity);
    }

    @MediumTest
    public void testLaunchTemperatureActivityLayout(){
        assertThat(launchTemperatureActivity).isShown();
    }

    @MediumTest
    public void testLaunchLightActivityLayout(){
        assertThat(launchLightActivity).isShown();
    }

    @MediumTest
    public void testLaunchAirCondActivityLayout(){
        assertThat(launchAirCondActivity).isShown();
    }

    @MediumTest
    public void testLaunchSettingsActivityLayout(){
        assertThat(launchSettingsActivity).isShown();
    }

    @MediumTest
    public void testLaunchTemperatureActivity(){
        testLaunchActivity(launchTemperatureActivity, TemperatureActivity.class);
    }

    @MediumTest
    public void testLaunchLightActivity(){
        testLaunchActivity(launchLightActivity, LightActivity.class);
    }

    @MediumTest
    public void testLaunchAirCondActivity(){
        testLaunchActivity(launchAirCondActivity, AirCondActivity.class);
    }

    @MediumTest
    public void testLaunchSettingsActivity(){
        testLaunchActivity(launchSettingsActivity, SettingsActivity.class);
    }

    private void testLaunchActivity(final View button, Class<? extends Activity> activityClass) {
        final Instrumentation.ActivityMonitor monitor = getInstrumentation().addMonitor(activityClass.getName(), null, false);
        clickView(this, button);

        Activity activity = monitor.waitForActivityWithTimeout(ACTIVITY_TIMEOUT);
        assertThat(activity).isNotNull();
        assertEquals(activityClass, activity.getClass());

        activity.finish();
    }
}
