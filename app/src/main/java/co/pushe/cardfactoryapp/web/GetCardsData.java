package co.pushe.cardfactoryapp.web;

import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import co.pushe.cardfactoryapp.cards.Card;
import co.pushe.cardfactoryapp.cards.PictureCard;
import co.pushe.cardfactoryapp.cards.SoundCard;
import co.pushe.cardfactoryapp.cards.VibratorCard;

/**
 * Created by Matin on 5/30/2018.
 *
 * This class parses json data retrieved by class 'Raw Data Downloader,'
 * is a AsyncTask and work on a background thread
 *
 */

public class GetCardsData implements RawDataDownloader.OnDownloadCompleteListener{

    private static final String TAG = "GetCardsData";

    private ArrayList<Card> cards = null;
    private OnDataAvailableListener listener;
    private String baseUrl;


    public interface OnDataAvailableListener {
        void onDataAvailable(ArrayList<Card> cards , DownloadStatus status);
    }

    public GetCardsData(OnDataAvailableListener listener, String baseUrl) {
        Log.d(TAG, "GetCardsData: called");
        this.listener = listener;
        this.baseUrl = baseUrl;
    }

    public void execute()
    {
        Log.d(TAG, "execute: starts");
        RawDataDownloader dataDownloader = new RawDataDownloader(this);
        dataDownloader.execute(baseUrl);
        Log.d(TAG, "execute: ends");
    }

    public ArrayList<Card> getCards() {
        return cards;
    }

    @Override
    public void onDownloadComplete(String data, DownloadStatus status) {
        Log.d(TAG, "onDownloadComplete: starts. status is: " + status);
        Log.d(TAG, "onDownloadComplete: data is:\n" + data);
        if (status == DownloadStatus.OK)
        {
            cards = new ArrayList<>();

            try{
                JSONObject jsonData = new JSONObject(data);
                JSONArray cardsArray = jsonData.getJSONArray("cards");

                for (int i = 0 ; i < cardsArray.length() ; i++) {
                    JSONObject jsonCard = cardsArray.getJSONObject(i);
                    int code = jsonCard.getInt("code");
                    String title = jsonCard.getString("title");
                    String description = jsonCard.getString("description");
                    String tag = jsonCard.getString("tag");

                    Card cardObject = null;
                    switch (code) {
                        case 0:
                            String imageLink = jsonCard.getString("image");
                            cardObject = new PictureCard(title , description , tag , code , imageLink);
                            break;
                        case 1:
                            cardObject = new VibratorCard(title , description , tag , code);
                            break;
                        case 2:
                            String soundLink = jsonCard.getString("sound");
                            cardObject = new SoundCard(title , description , tag , code , soundLink);
                            break;

                    }
                    cards.add(cardObject);

                    Log.d(TAG, "onDownloadComplete: One card is extracted " + cardObject.toString());
                }
            }catch (JSONException e){
                e.printStackTrace();
                Log.e(TAG, "onDownloadComplete: Error Processing Json Data " + e.getMessage());
            }
        }

        if (listener != null)
        {
            // now informing the caller that processing is done - possibly returning null if there was an error
            listener.onDataAvailable(cards , status);
        }
        Log.d(TAG, "onDownloadComplete: ends");
    }
}
