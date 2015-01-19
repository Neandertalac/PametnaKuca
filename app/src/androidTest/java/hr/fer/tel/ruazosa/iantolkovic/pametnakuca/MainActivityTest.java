package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;

import android.app.Activity;
import android.app.Instrumentation;
import android.content.Context;
import android.content.SharedPreferences;
import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

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

        SharedPreferences.Editor editor = getInstrumentation().getTargetContext().getSharedPreferences(SettingsActivity.PREFS_NAME, Context.MODE_PRIVATE).edit();
        editor.putString("serverIP","10.0.2.2");
        editor.putString("zeljenaTemperatura","20");
        editor.putString("granUdaljenost","100");
        editor.commit();

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
        assertGroupContains(root, tempBtn);
        assertGroupContains(root, lightBtn);
        assertGroupContains(root, airCondBtn);
        assertGroupContains(root, enterIPBtn);
    }

    @MediumTest
    public void testLaunchTemperatureActivityLayout(){
        assertThat(tempBtn).isShown();
    }

    @MediumTest
    public void testLaunchLightActivityLayout(){
        assertThat(lightBtn).isShown();
    }

    @MediumTest
    public void testLaunchAirCondActivityLayout(){
        assertThat(airCondBtn).isShown();
    }

    @MediumTest
    public void testLaunchSettingsActivityLayout(){
        assertThat(enterIPBtn).isShown();
    }

    @MediumTest
    public void testLaunchTemperatureActivity(){
        testLaunchActivity(tempBtn, TemperatureActivity.class);
    }

    @MediumTest
    public void testLaunchLightActivity(){
        testLaunchActivity(lightBtn, LightActivity.class);
    }

    @MediumTest
    public void testLaunchAirCondActivity(){
        testLaunchActivity(airCondBtn, AirCondActivity.class);
    }

    @MediumTest
    public void testLaunchSettingsActivity(){
        testLaunchActivity(enterIPBtn, SettingsActivity.class);
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
