package hr.fer.tel.ruazosa.iantolkovic.pametnakuca;


import android.test.ActivityInstrumentationTestCase2;
import android.test.suitebuilder.annotation.MediumTest;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import static android.test.ViewAsserts.assertGroupContains;
import static org.assertj.android.api.Assertions.assertThat;

public class ConnectionErrorActivityTest extends ActivityInstrumentationTestCase2<ConnectionErrorActivity>{

    private ConnectionErrorActivity activity;

    private TextView errorMsg;
    private EditText enterIP;
    private Button submit, retry;
    private LinearLayout root, root1;

    public ConnectionErrorActivityTest(){
        super(ConnectionErrorActivity.class);
    }

    @Override
    protected void setUp() throws Exception{
        super.setUp();

        activity = getActivity();

        errorMsg = (TextView) activity.findViewById(R.id.errorMsg);
        enterIP = (EditText) activity.findViewById(R.id.ipAdress);
        submit = (Button) activity.findViewById(R.id.enterIpBtn);
        retry = (Button) activity.findViewById(R.id.retryBtn);
        root = (LinearLayout) activity.findViewById(R.id.root);
        root1 = (LinearLayout) activity.findViewById(R.id.root1);
    }

    @MediumTest
    public void testRoot(){
        assertThat(root)
                .isVisible()
                .isVertical()
                .hasChildCount(3);
        assertGroupContains(root, errorMsg);
        assertGroupContains(root, enterIP);
        assertGroupContains(root, root1);
    }

    @MediumTest
    public void testRoot1(){
        assertThat(root1)
                .isVisible()
                .isHorizontal()
                .hasChildCount(2);
        assertGroupContains(root, submit);
        assertGroupContains(root, retry);
    }
}
