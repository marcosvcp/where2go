package test;

/**
 * Testes da activity Main
 * Created by brunomb on 02/12/14.
 */
import activity.EventListActivity;
import activity.MainActivity;
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

    public void testLayout() {
        final int authButtonId, textView1Id, enterEventListButtonId;
        authButtonId = br.com.les.where2go.R.id.authButton;
        textView1Id = br.com.les.where2go.R.id.textView1;
        enterEventListButtonId = br.com.les.where2go.R.id.enterEventListButton;
        assertNotNull(getActivity().findViewById(authButtonId));
        assertNotNull(getActivity().findViewById(textView1Id));
        assertNotNull(getActivity().findViewById(enterEventListButtonId));
    }

    public void testLoginWithFacebook() throws Exception {
        setUp();
        //TODO Esperando funcionalidade ficar estavel para ser testada
    }

    public void testEnterEventList() throws Exception {
        setUp();
        // Check if solo it is in the correct activity
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
        // Click on the enter event button
        solo.clickOnView(getActivity().findViewById(br.com.les.where2go.R.id.enterEventListButton));
        // Wait 1.5 sec
        solo.sleep(1500);
        // Check if solo it is in the correct activity
        solo.assertCurrentActivity("Wrong Activity", EventListActivity.class);
        // Back to the parent activity
        solo.goBack();
        // Wait 1.5 sec
        solo.sleep(1500);
        // Check if solo it is in the correct activity
        solo.assertCurrentActivity("Wrong Activity", MainActivity.class);
    }

}
