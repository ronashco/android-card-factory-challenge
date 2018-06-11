package com.pushe.hector.acfc_1;

import android.graphics.Bitmap;
import android.media.MediaPlayer;

import java.util.ArrayList;

/**
 * interface for passing result from AsyncTask classes to activities
 */
public interface DownloadCompleteListener {

    /**
     * after reading and parsing cards information in json, this function will be called
     * to pass cards list to MainActivity
     * @param cards cards objects generated from information of cards in json file
     */
    void jsonDownloadComplete(ArrayList<Card> cards);

    /**
     * after downloading image of a PictureCard object, this function will be called
     * to pass image to MainActivity and save it to Bitmap field of PictureCard object
     * @param imageWithURL Wrapping of Bitmap image and it's URL
     */
    void imageDownloadComplete(BitmapAndURLWrap imageWithURL);

    /**
     * after being ready to play sound in MediaPlayer object, this function will be called
     * to pass MediaPlayer object to MainActivity
     * @param mediaPlayer MediaPlayer object prepared for playing sound
     */
    void soundPrepareComplete(MediaPlayer mediaPlayer);
}

