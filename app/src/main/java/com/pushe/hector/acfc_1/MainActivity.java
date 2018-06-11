package com.pushe.hector.acfc_1;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.VibrationEffect;
import android.os.Vibrator;
import android.provider.ContactsContract;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

public class MainActivity extends AppCompatActivity implements DownloadCompleteListener {
    public static final String CARDS_JSON_URL = "http://static.pushe.co/challenge/json";
    ProgressDialog progressDialog;
    private int cardIndex;
    private ArrayList<Card> cards;
    private ArrayList<Integer> imageDownloadList = new ArrayList<Integer>();
    private MediaPlayer mediaPlayer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isNetworkConnected()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            downloadCardsInfoAsJSON();
        } else {
            new AlertDialog.Builder(this)
                    .setTitle("No Internet Connection")
                    .setMessage("It looks like your internet connection is off. " +
                            "Please turn it on and try again")
                    .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                        }
                    }).setIcon(android.R.drawable.ic_dialog_alert).show();
        }
    }

    /**
     * Checks that the device has an active internet connection or not
     * @return true if the device has an active internet connection
     */
    private boolean isNetworkConnected() {
        // Retrieves an instance of the ConnectivityManager class
        // from the current application context.
        ConnectivityManager connMgr =
                (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        // Retrieves an instance of the NetworkInfo class that
        // represents the current network connection. This will
        // be null if no network is available.
        NetworkInfo networkInfo = connMgr.getActiveNetworkInfo();

        // Check if there is an available network connection and
        // the device is connected.
        return networkInfo != null && networkInfo.isConnected();
    }

    /**
     * Creating a new AsyncTask for downloding cards information
     * as json
     */
    private void downloadCardsInfoAsJSON() {
        new DownloadCardsInfo(this).execute(CARDS_JSON_URL);
    }

    /**
     * @param cards cards objects generated from information of cards in json file
     * @see DownloadCompleteListener
     */
    @Override
    public void jsonDownloadComplete(ArrayList<Card> cards) {
        this.cards = cards;
        Collections.shuffle(this.cards);

        for (int i = 0; i < cards.size(); i++) {
            Card card = this.cards.get(i);

            if (card instanceof PictureCard) {
                this.imageDownloadList.add(new Integer(i));
                new DownloadImage(this).execute(((PictureCard) card).imageURL);
            }
        }
    }

    /**
     * @param imageWithURL Wrapping of Bitmap image and it's URL
     * @see DownloadCompleteListener
     */
    @Override
    public void imageDownloadComplete(BitmapAndURLWrap imageWithURL) {
        this.cardIndex = 1;
        showCard(cards.get(0));

        for (int i : imageDownloadList) {
            PictureCard pictureCard = (PictureCard) cards.get(i);
            if (pictureCard.imageURL.equals(imageWithURL.imageURL)) {
                pictureCard.imageBitmap = imageWithURL.imageBitmap;
                imageDownloadList.remove(new Integer(i));
                break;
            }
        }
    }

    /**
     * @param mediaPlayer MediaPlayer object prepared for playing sound
     * @see DownloadCompleteListener
     */
    @Override
    public void soundPrepareComplete(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
        this.mediaPlayer.start();
    }

    /**
     * event handler for click of try button
     * @param view representing try button in MainActivity
     */
    public void tryAnotherCard(View view) {
        if (progressDialog != null) {
            progressDialog.show();
        }

        if (this.mediaPlayer != null) {
            this.mediaPlayer.stop();
            this.mediaPlayer = null;
        }

        if (this.cardIndex >= this.cards.size()) {
            Collections.shuffle(this.cards);
            this.cardIndex = 1;
        } else {
            this.cardIndex ++;
        }
        showCard(this.cards.get(cardIndex - 1));

    }

    /**
     * shows next card when user touches try button
     * @param card next card to be shown
     */
    private void showCard(Card card) {
        if (progressDialog != null) {
            progressDialog.hide();
        }

        if (card instanceof PictureCard) {
            showPictureCard(card);
        } else if (card instanceof VibratorCard) {
            showVibratorCard(card);
        } else if (card instanceof SoundCard) {
            showSoundCard(card);
        }
    }

    /**
     * @param card next card (that is a PictureCard) to be shown
     */
    private void showPictureCard(Card card) {
        setContentView(R.layout.activity_main_picture_card);
        PictureCard picCard = (PictureCard) card;

        TextView textView1 = findViewById(R.id.textView1_pic_card);
        textView1.setText(picCard.title);

        TextView textView2 = findViewById(R.id.textView2_pic_card);
        textView2.setText(picCard.description);

        ImageView imageView1 = (ImageView) findViewById(R.id.imageView1);
        imageView1.setImageBitmap(((PictureCard) card).imageBitmap);

        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
        if (picCard.tag == Tag.FUN) {
            imageView2.setImageResource(R.drawable.fun);
        } else if (picCard.tag == Tag.ART) {
            imageView2.setImageResource(R.drawable.art);
        } else if (picCard.tag == Tag.SPORT) {
            imageView2.setImageResource(R.drawable.sport);
        }
    }

    /**
     * @param card next card (that is a VibratorCard) to be shown
     */
    private void showVibratorCard(Card card) {
        setContentView(R.layout.activity_main_vibrator_card);

        VibratorCard vibCard = (VibratorCard) card;
        TextView textView1 = findViewById(R.id.textView1_vib_card);
        textView1.setText(vibCard.title);

        TextView textView2 = findViewById(R.id.textView2_vib_card);
        textView2.setText(vibCard.description);

        Vibrator v = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
        // Vibrate for 500 milliseconds
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            v.vibrate(VibrationEffect.createOneShot(500,VibrationEffect.DEFAULT_AMPLITUDE));
        }else {
            //deprecated in API 26
            v.vibrate(500);
        }

        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
        if (vibCard.tag == Tag.FUN) {
            imageView2.setImageResource(R.drawable.fun);
        } else if (vibCard.tag == Tag.ART) {
            imageView2.setImageResource(R.drawable.art);
        } else if (vibCard.tag == Tag.SPORT) {
            imageView2.setImageResource(R.drawable.sport);
        }
    }

    /**
     * @param card next card (that is a SoundCard) to be shown
     */
    private void showSoundCard(Card card) {
        SoundCard soundCard = (SoundCard) card;
        new SoundPrepare(this).execute(soundCard.soundURL);

        setContentView(R.layout.activity_main_sound_card);

        TextView textView1 = findViewById(R.id.textView1_sound_card);
        textView1.setText(soundCard.title);

        TextView textView2 = findViewById(R.id.textView2_sound_card);
        textView2.setText(soundCard.description);

        ImageView imageView2 = (ImageView) findViewById(R.id.imageView2);
        if (soundCard.tag == Tag.FUN) {
            imageView2.setImageResource(R.drawable.fun);
        } else if (soundCard.tag == Tag.ART) {
            imageView2.setImageResource(R.drawable.art);
        } else if (soundCard.tag == Tag.SPORT) {
            imageView2.setImageResource(R.drawable.sport);
        }
    }
}