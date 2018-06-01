package co.pushe.cardfactoryapp;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;

/**
 * Created by Matin on 5/30/2018.
 */

/** All the other activity classes extend this one,
 * we implement the actions needed in all classes here,
 * in this simple case, activating the Toolbar
 */

public class BaseActivity extends AppCompatActivity {
    private static final String TAG = "BaseActivity";

    public static final String CARDS_BASE_URL = "http://static.pushe.co/challenge/json";


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation (ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
    }

    // the boolean parameter determines whether the activity needs the home button or not
    // (whether it is called by another activity and needs to return or not)
    void activateToolbar(boolean enableHome)
    {
        Log.d(TAG, "activateToolbar: starts");
        ActionBar actionBar = getSupportActionBar();
        if (actionBar == null)
        {
            Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
            if (toolbar != null)
            {
                setSupportActionBar(toolbar);
                actionBar = getSupportActionBar();
            }
        }
        if (actionBar != null)
            actionBar.setDisplayHomeAsUpEnabled(enableHome);

        Log.d(TAG, "activateToolbar: ends");
    }
}
