package com.pushe.hector.acfc_1;

import android.graphics.Bitmap;
import android.media.MediaPlayer;

import java.util.ArrayList;

public interface DownloadCompleteListener {
    void jsonDownloadComplete(ArrayList<Card> cards);
    void imageDownloadComplete(BitmapAndURLWrap imageWithURL);
    void soundPrepareComplete(MediaPlayer mediaPlayer);
}

