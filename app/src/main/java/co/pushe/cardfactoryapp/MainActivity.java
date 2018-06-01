package co.pushe.cardfactoryapp;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Random;

import co.pushe.cardfactoryapp.cards.Card;
import co.pushe.cardfactoryapp.web.DownloadStatus;
import co.pushe.cardfactoryapp.web.GetCardsData;

public class MainActivity extends BaseActivity implements GetCardsData.OnDataAvailableListener {

    private static final String TAG = "MainActivity";
    private Button tryButton;
    private ProgressBar progressBar;
    private TextView initializationTV;
    private Button retryButton;
    private ImageView initFailIV;

    private ArrayList<Card> cards;
    private Random random = new Random();
    private ArrayList<Integer> seenCardsIndexes = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        Log.d(TAG, "onCreate: starts");
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        activateToolbar(false);

        tryButton = findViewById(R.id.try_btn);
        progressBar = findViewById(R.id.progressBar);
        retryButton = findViewById(R.id.retryBtn);
        initializationTV = findViewById(R.id.initialization_message);
        initFailIV = findViewById(R.id.init_failIV);

        initFailIV.setVisibility(View.INVISIBLE);
        retryButton.setVisibility(View.INVISIBLE);


        retryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                progressBar.setVisibility(View.VISIBLE);
                initializationTV.setText(R.string.initialization_message);
                initFailIV.setVisibility(View.INVISIBLE);
                retryButton.setVisibility(View.INVISIBLE);

                getCardsData();
            }
        });


        tryButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ShowNextRandomCard();
            }
        });

        getCardsData();
        Log.d(TAG, "onCreate: ends");
    }

    private void getCardsData() {
        Log.d(TAG, "getCardsData: starts");

        tryButton.setVisibility(View.INVISIBLE);

        GetCardsData getCardsData = new GetCardsData(this , CARDS_BASE_URL);
        getCardsData.execute();

        Log.d(TAG, "getCardsData: ends");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_exit) {
            finish();
        }

        return super.onOptionsItemSelected(item);
    }


    /**
     * disabling the phone back button
     */
    @Override
    public void onBackPressed() {

    }

    @Override
    public void onDataAvailable(ArrayList<Card> cards, DownloadStatus status) {
        Log.d(TAG, "onDataAvailable: starts");

        if (status == DownloadStatus.OK) {

            this.cards = cards;
            tryButton.setVisibility(View.VISIBLE);
            ShowNextRandomCard();

        }
        else if (status == DownloadStatus.FAILED_OR_EMPTY){
            progressBar.setVisibility(View.INVISIBLE);
            initializationTV.setText(R.string.init_fail_message);
            initFailIV.setVisibility(View.VISIBLE);
            retryButton.setVisibility(View.VISIBLE);
        }

        Log.d(TAG, "onDataAvailable: ends");
    }

    private void ShowNextRandomCard() {
        Log.d(TAG, "ShowNextRandomCard: starts");

        int nextCardIndex = getRandomIndex();
        CardFragment nextFragment = CardFragment.newInstance(cards.get(nextCardIndex));
        nextFragment.setTheCard(cards.get(nextCardIndex));

        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(R.id.fragment, nextFragment);

        transaction.commit();

        Log.d(TAG, "ShowNextRandomCard: ends");

    }

    /**
     *
     * @return the next random index
     */

    private int getRandomIndex() {
        Log.d(TAG, "getRandomIndex: starts");

        if (seenCardsIndexes.size() == cards.size()) {
            Toast.makeText(this , getString(R.string.next_round_toast) , Toast.LENGTH_LONG).show();
            seenCardsIndexes.clear();
        }

        int index = random.nextInt(cards.size());
        while (seenCardsIndexes.contains(index))
            index = random.nextInt(cards.size());

        seenCardsIndexes.add(index);
        Log.d(TAG, "getRandomIndex: ends with returning index: " + index);
        return index;
    }
}
