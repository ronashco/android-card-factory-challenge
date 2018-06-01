package co.pushe.cardfactoryapp;

import android.test.ActivityInstrumentationTestCase2;
import android.widget.Button;

/**
 * Created by Matin on 6/1/2018.
 *
 *
 * a simple test that checks if fixture has been set up correctly.
 */
public class MainActivityTest extends
        ActivityInstrumentationTestCase2<MainActivity> {

    private MainActivity testActivity;
    private Button testTryButton;

    public MainActivityTest() {
        super(MainActivity.class);
    }

    @Override
    protected void setUp() throws Exception {
        super.setUp();

        // Starts the activity under test using
        // the default Intent with:
        // action = {@link Intent#ACTION_MAIN}
        // flags = {@link Intent#FLAG_ACTIVITY_NEW_TASK}
        // All other fields are null or empty.
        testActivity = getActivity();
        testTryButton = testActivity.findViewById(R.id.try_btn);
    }

        public void testPreconditions() {

        assertNotNull("testActivity is null", testActivity);
        assertNotNull("try button is null", testTryButton);
    }

}