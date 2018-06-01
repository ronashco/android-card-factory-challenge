package co.pushe.cardfactoryapp.web;

import org.junit.Test;

import java.util.ArrayList;

import co.pushe.cardfactoryapp.cards.Card;
import co.pushe.cardfactoryapp.cards.PictureCard;
import co.pushe.cardfactoryapp.cards.SoundCard;
import co.pushe.cardfactoryapp.cards.VibratorCard;

import static org.junit.Assert.assertEquals;

/**
 * Created by Matin on 6/1/2018.
 *
 * tests the json data parsing is in GetCardsDataTest, method onDownloadComplete
 *
 */
public class GetCardsDataTest implements GetCardsData.OnDataAvailableListener {
    private ArrayList<Card> theCards;
    private DownloadStatus status;

    @Test
    public void onDownloadComplete() throws Exception {
        GetCardsData getCardsData = new GetCardsData(this , "http://static.pushe.co/challenge/json");
        String jsonData = "{ \"cards\": [ { \"code\": 1, \"title\": \"Exercise\", \"description\": \"Exercise on a regular basis.\", \"tag\": \"sport\" }, { \"code\": 0, \"title\": \"Painting\", \"description\": \"Look at this beautiful painting\", \"image\": \"http://static.pushe.co/challenge/sky.jpg\", \"tag\": \"art\" }, { \"code\": 2, \"title\": \"Let's have fun\", \"description\": \"Listen to the music\", \"sound\": \"http://static.pushe.co/challenge/sound.mp3\", \"tag\": \"fun\" }, { \"code\": 1, \"title\": \"Hey!\", \"description\": \"Have you called your parents lately!\", \"tag\": \"fun\" }, { \"code\": 0, \"title\": \"Sports\", \"description\": \"Have you ever played one of theses sports?\", \"image\": \"http://static.pushe.co/challenge/sport.jpg\", \"tag\": \"sport\" } ] }";
        getCardsData.onDownloadComplete(jsonData , DownloadStatus.OK);

        ArrayList<Card> expectedResult = getExpectedResult();
        assertEquals(theCards.size() , expectedResult.size());
        for (int i = 0; i < theCards.size(); i++) {
            assertEquals(expectedResult.get(i), theCards.get(i));
        }
        assertEquals(status, DownloadStatus.OK);



    }

    @Override
    public void onDataAvailable(ArrayList<Card> cards, DownloadStatus status) {
        theCards = cards;
        this.status = status;
    }

    private ArrayList<Card> getExpectedResult() {
        ArrayList<Card> result = new ArrayList<>();
        result.add(new VibratorCard("Exercise" , "Exercise on a regular basis." , "sport" , 1));
        result.add(new PictureCard("Painting" , "Look at this beautiful painting" , "art" , 0 , "http://static.pushe.co/challenge/sky.jpg"));
        result.add(new SoundCard("Let's have fun" , "Listen to the music" , "fun" , 2 , "http://static.pushe.co/challenge/sound.mp3"));
        result.add(new VibratorCard("Hey!" , "Have you called your parents lately!" , "fun" , 1));
        result.add(new PictureCard("Sports" , "Have you ever played one of theses sports?" , "sport" , 0 , "http://static.pushe.co/challenge/sport.jpg"));

        return result;
    }
}