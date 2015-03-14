package test;

/**
 * Testes da activity Main
 * Created by brunomb on 02/12/14.
 */

import org.junit.Test;

import activity.MainScreen;
import android.test.ActivityInstrumentationTestCase2;

import com.robotium.solo.Solo;

public class MainScreenTest extends
ActivityInstrumentationTestCase2<activity.MainScreen> {

    private Solo solo;

    @SuppressWarnings("deprecation")
    public MainScreenTest() {
        super("activity", MainScreen.class);
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
        final int frameLayout;
        final int listSliderMenu;
        final int statusBarBackground;
        frameLayout = br.com.les.where2go.R.id.frame_container;
        listSliderMenu = br.com.les.where2go.R.id.list_slidermenu;
        statusBarBackground = br.com.les.where2go.R.id.statusBarBackground;
        assertNotNull(getActivity().findViewById(frameLayout));
        assertNotNull(getActivity().findViewById(listSliderMenu));
        assertNotNull(getActivity().findViewById(statusBarBackground));
    }

    @Test
    public void testEnterMaindScreen() throws InterruptedException {
        solo.sleep(5000);
    }
}
