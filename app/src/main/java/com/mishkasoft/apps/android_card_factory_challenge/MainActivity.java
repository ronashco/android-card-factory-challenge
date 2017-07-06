package com.mishkasoft.apps.android_card_factory_challenge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Random;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ArrayList<Card> cards = new ArrayList<>();                     //cart ha
        final ArrayList<Integer> sCards = new ArrayList<>();                 //cart haye dide shode
        final ImageView imageView = (ImageView) findViewById(R.id.imageView);
        final TextView title = (TextView) findViewById(R.id.textView);
        final TextView description = (TextView) findViewById(R.id.textView2);
        final Button tryButton = (Button) findViewById(R.id.button);
        final RelativeLayout background = (RelativeLayout) findViewById(R.id.relativeLayout);
        final Random random = new Random();
        tryButton.setOnClickListener(new View.OnClickListener() {
                                         @Override
                                         public void onClick(View view) {
                                             int cardToShow;
                                             if (sCards.size() == cards.size())         //agar hameye cart ha dide shodand
                                                 sCards.clear();
                                             while (true) {
                                                 cardToShow = random.nextInt(cards.size());
                                                 if (!sCards.contains(cardToShow)) {    //in cart dide nashode
                                                     sCards.add(cardToShow);
                                                     break;
                                                 }
                                             }
                                             cards.get(cardToShow).showCard(getApplicationContext(), title, description, imageView, background);
                                         }
                                     }
        );

        final OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder().url("http://static.pushe.co/challenge/json").build();
        final Callback callback = new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                Log.e("log", "failure " + e.toString());
                try {                               // 1 sanie sabr mikone baad dobare drarkhast ro ersal mikone
                    Thread.sleep(1000);
                } catch (InterruptedException e1) {
                    e1.printStackTrace();
                }
                client.newCall(request).enqueue(this);
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (response.isSuccessful()) {
                    try {
                        JSONObject jsonObject = new JSONObject(response.body().string());
                        final JSONArray cardsJson = jsonObject.getJSONArray("cards");
                        for (int i = 0; i < cardsJson.length(); i++) {      //be ezaye har object e daryafti ye cart dorost mikone
                            JSONObject object = cardsJson.getJSONObject(i);
                            cards.add(new Card(object));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            tryButton.setEnabled(true);
                        }
                    });

                } else {
                    try {                   // 1 sanie sabr mikone baad dobare drarkhast ro ersal mikone
                        Thread.sleep(1000);
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                    client.newCall(request).enqueue(this);
                }
            }
        };

        client.newCall(request).enqueue(callback);
    }
}
