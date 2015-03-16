package test;

/**
 * Testes da activity Main
 * Created by brunomb on 02/12/14.
 */

import org.junit.Test;

import activity.MainActivity;
import activity.MainScreen;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

public class MainActivityTest extends
ActivityInstrumentationTestCase2<activity.MainActivity> {

    private Solo solo;

    @SuppressWarnings("deprecation")
    public MainActivityTest() {
        super("activity", MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        solo = new Solo(getInstrumentation(), getActivity());
    }

    @Override
    public void tearDown() throws Exception {
        solo.finishOpenedActivities();
    }

    @Test
    public void testLayout() {
        final int authButtonId;
        final int textViewId;
        final int iconViewId;
        final int btEnterId;
        authButtonId = br.com.les.where2go.R.id.authButton;
        textViewId = br.com.les.where2go.R.id.textView1;
        btEnterId = br.com.les.where2go.R.id.bt_enter;
        assertNotNull(getActivity().findViewById(authButtonId));
        assertNotNull(getActivity().findViewById(textViewId));
        assertNotNull(getActivity().findViewById(btEnterId));
    }

    @Test
    public void testEnterMaindScreen() {
        solo.assertCurrentActivity("Wrong Activity Error", MainActivity.class);
        solo.clickOnView(getActivity().findViewById(
                br.com.les.where2go.R.id.bt_enter));
        solo.assertCurrentActivity("Wrong Activity Error", MainScreen.class);
    }
}
