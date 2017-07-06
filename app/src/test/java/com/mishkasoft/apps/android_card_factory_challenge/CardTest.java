package com.mishkasoft.apps.android_card_factory_challenge;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.Before;
import org.junit.Test;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static org.junit.Assert.*;
public class CardTest {
    Card card1,card2,card3;
    @Before
    public void setUp() throws Exception {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url("http://static.pushe.co/challenge/json").build();
        Response response = client.newCall(request).execute();
        JSONObject jsonObject = new JSONObject(response.body().string());
        JSONArray cardsJson = jsonObject.getJSONArray("cards");
        card1=new Card(cardsJson.getJSONObject(1));
        card2=new Card(cardsJson.getJSONObject(0));
        card3=new Card(cardsJson.getJSONObject(2));

    }

    @Test
    public void testCard() throws Exception {
        assertEquals(card1.code,0);
        assertEquals(card2.code,1);
        assertEquals(card3.code,2);

        assertEquals(card1.title,"Painting");

        assertEquals(card2.description,"Exercise on a regular basis.");

        assertEquals(card3.tag,"fun");

        assertEquals(card1.url,"http://static.pushe.co/challenge/sky.jpg");
        assertEquals(card2.url,"");
        assertEquals(card3.url,"http://static.pushe.co/challenge/sound.mp3");

    }
}
