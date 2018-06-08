package com.pushe.hector.acfc_1;

import android.util.Log;

import java.util.ArrayList;

/**
 * utility class for parsing json formatted string
 */
public class Utility {

    /**
     * converts information in json String to Card objects
     * @param jsonString
     * @return Card objects generated from information in json String
     * @see Card
     */
    public ArrayList<Card> getCardsFromJSONString(String jsonString) {
        ArrayList<String> items = seperateItemsInJSON(jsonString);
        ArrayList<Card> cards = new ArrayList<Card>();
        for (String item : items) {
            cards.add(stringItemToCard(item));
        }

        return cards;
    }

    /**
     * seperates items in json String
     * @param jsonString string in json format
     * @return ArrayList of items as String in main json String
     */
    private ArrayList<String> seperateItemsInJSON(String jsonString) {
        ArrayList<String> items = new ArrayList<String>();
        for (int i = 1; i < jsonString.length(); i++) {
            if (jsonString.charAt(i) == '{') {
                for (int j = i + 1; j < jsonString.length(); j++) {
                    if (jsonString.charAt(j) == '}') {
                        items.add(jsonString.substring(i + 1, j));
                        i = j + 1;
                        break;
                    }
                }
            }
        }
        return items;
    }

    /**
     * converts one item of a json String (of several items) to a Card object
     * @param item one item in a json String of several items
     * @return Card object generated from item String
     * @see Card
     */
    private Card stringItemToCard(String item) {
        Code code = Code.UNDEFINED;
        Tag tag = Tag.UNDEFINED;
        String title = null;
        String description = null;
        String imageURL = null;
        String soundURL = null;

        for (int i = 0; i < item.length(); i++) {
            if (item.charAt(i) == '"') {
                for (int j = i + 1; j < item.length(); j++) {
                    if (item.charAt(j) == '"') {
                        int k;
                        if (item.substring(i + 1, j).equals("code")) {
                            for (k = j + 4; k < item.length(); k++) {
                                if (item.charAt(k) == ',') {
                                    switch (Integer.parseInt(item.substring(j + 3, k))) {
                                        case 0:
                                            code = Code.PICTURE_CARD;
                                            break;
                                        case 1:
                                            code = Code.VIBRATOR_CARD;
                                            break;
                                        case 2:
                                            code = Code.SOUND_CARD;
                                            break;
                                        default:
                                            // something wrong
                                    }

                                    i = k + 1;
                                    break;
                                }
                            }
                        } else {
                            for (k = j + 5; k < item.length(); k++) {
                                if (item.charAt(k) == '"') {
                                    if (item.substring(i + 1, j).equals("title")) {
                                        title = item.substring(j + 4, k);
                                    }  else if (item.substring(i + 1, j).equals("description")) {
                                        description = item.substring(j + 4, k);
                                    } else if (item.substring(i + 1, j).equals("tag")) {
                                        String tagName = item.substring(j + 4, k);
                                        if (tagName.equals("sport")) {
                                            tag = Tag.SPORT;
                                        } else if (tagName.equals("art")) {
                                            tag = Tag.ART;
                                        } else if (tagName.equals("fun")) {
                                            tag = Tag.FUN;
                                        }
                                    } else if (item.substring(i + 1, j).equals("image")) {
                                        imageURL = item.substring(j + 4, k);
                                    } else if (item.substring(i + 1, j).equals("sound")) {
                                        soundURL = item.substring(j + 4, k);
                                    }

                                    i = k + 1;
                                    break;
                                }
                            }
                        }

                        break;
                    }
                }
            }
        }

        if (code == Code.PICTURE_CARD) {
            return new PictureCard(code, tag, title, description, imageURL);
        } else if (code == Code.VIBRATOR_CARD) {
            return new VibratorCard(code, tag, title, description);
        } else if (code == Code.SOUND_CARD) {
            return new SoundCard(code, tag, title, description, soundURL);
        } else {
            return null;    // something wrong
        }

    }
}