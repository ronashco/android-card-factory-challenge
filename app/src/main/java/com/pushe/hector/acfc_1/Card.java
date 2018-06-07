package com.pushe.hector.acfc_1;

import android.annotation.TargetApi;
import android.graphics.Bitmap;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;

/**
 * Card class for abstracting cards
 * Card class has 3 subclassed: PictureCard, VibratorCard, SoundCard
 */
public abstract class Card {
    Code code;
    Tag tag;
    String title;
    String description;

    public Card(Code code, Tag tag, String title, String description) {
        this.code = code;
        this.tag = tag;
        this.title = title;
        this.description = description;
    }

}

class PictureCard extends Card {
    String imageURL;
    boolean imageDownloaded;
    Bitmap imageBitmap;

    public PictureCard(Code code, Tag tag, String title, String description,String imageURL) {
        super(code,tag,title,description);
        this.imageURL = imageURL;
        this.imageDownloaded = false;
        this.imageBitmap = null;
    }
}

class VibratorCard extends Card {
    public VibratorCard(Code code, Tag tag, String title, String description) {
        super(code, tag, title, description);
    }
}

class SoundCard extends Card {
    String soundURL;

    public SoundCard(Code code, Tag tag, String title, String description, String soundURL) {
        super(code, tag, title, description);
        this.soundURL = soundURL;
    }
}

enum Code {
    PICTURE_CARD,   // 0
    VIBRATOR_CARD,  // 1
    SOUND_CARD,      // 2
    UNDEFINED
}

enum Tag {
    SPORT,  // sport
    ART,    // art
    FUN,     // fun
    UNDEFINED
}
