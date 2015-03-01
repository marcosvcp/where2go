//package test;
//
///**
// * Testes da activity Main
// * Created by brunomb on 02/12/14.
// */
//
//import android.test.ActivityInstrumentationTestCase2;
//
//import com.robotium.solo.Solo;
//
//import activity.MainActivity;
//
//public class MainActivityTest extends
//        ActivityInstrumentationTestCase2<activity.MainActivity> {
//
//    private Solo solo;
//
//    @SuppressWarnings("deprecation")
//    public MainActivityTest() {
//        super("activity", MainActivity.class);
//    }
//
//    @Override
//    protected void setUp() throws Exception {
//        super.setUp();
//        solo = new Solo(getInstrumentation(), getActivity());
//
//    }
//
//    @Override
//    public void tearDown() throws Exception {
//        solo.finishOpenedActivities();
//    }
//
//    public void testLayout() {
//        final int authButtonId, textView1Id, enterEventListButtonId;
//        authButtonId = br.com.les.where2go.R.id.authButton;
//        textView1Id = br.com.les.where2go.R.id.textView1;
//        assertNotNull(getActivity().findViewById(authButtonId));
//        assertNotNull(getActivity().findViewById(textView1Id));
//    }
//
//    public void testLoginWithFacebook() throws Exception {
//        setUp();
//        //TODO Esperando funcionalidade ficar estavel para ser testada
//    }
//
//}
