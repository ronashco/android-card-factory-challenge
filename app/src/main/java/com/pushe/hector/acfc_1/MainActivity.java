package com.pushe.hector.acfc_1;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
//    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String CARDS_JSON_URL = "http://static.pushe.co/challenge/json";
    ProgressDialog progressDialog;
    private int cardIndex;
    private ArrayList<Card> cards;
    private ArrayList<Integer> imageDownloadList = new ArrayList<Integer>();
    private ArrayList<Integer> soundDownloadList = new ArrayList<Integer>();
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
     * @return
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

    private void downloadCardsInfoAsJSON() {
//        new DownloadRepoTask(this).execute("https://api.github.com/repositories");
        new DownloadCardsInfo(this).execute(CARDS_JSON_URL);
    }

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

    @Override
    public void imageDownloadComplete(BitmapAndURLWrap imageWithURL) {
        this.cardIndex = 1;
        showCard(cards.get(0));
//        if (progressDialog != null) {
//            progressDialog.hide();
//        }

        for (int i : imageDownloadList) {
            PictureCard pictureCard = (PictureCard) cards.get(i);
            if (pictureCard.imageURL.equals(imageWithURL.imageURL)) {
                pictureCard.imageBitmap = imageWithURL.imageBitmap;
                imageDownloadList.remove(new Integer(i));
                break;
            }
        }
    }

    @Override
    public void soundPrepareComplete(MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
        this.mediaPlayer.start();
    }

    /** Called when the user taps the Try button */
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

//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
    }

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

    public void showPictureCard(Card card) {
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

    public void showVibratorCard(Card card) {
//        setContentView(R.layout.activity_main_vibrator_card);
        setContentView(R.layout.activity_main_picture_card);

        VibratorCard vibCard = (VibratorCard) card;
        TextView textView1 = findViewById(R.id.textView1_pic_card);
        textView1.setText("vibrator card");

        TextView textView2 = findViewById(R.id.textView2_pic_card);
        textView2.setText(vibCard.title);

//        TextView textView3 = findViewById(R.id.textView3_pic_card);
//        textView3.setText(vibCard.description);

//        TextView textView4 = findViewById(R.id.textView4_pic_card);
//        textView4.setText("no image exists");

//        TextView textView5 = findViewById(R.id.textView5_pic_card);
//        if (vibCard.tag == Tag.FUN) {
//            textView5.setText("Fun");
//        } else if (vibCard.tag == Tag.ART) {
//            textView5.setText("Art");
//        } else if (vibCard.tag == Tag.SPORT) {
//            textView5.setText("Sport");
//        }
    }

    public void showSoundCard(Card card) {
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

//abstract class Card {
//    Code code;
//    Tag tag;
//    String title;
//    String description;
//
//    public Card() {
//        this.code = null;
//        this.tag = null;
//        this.title = null;
//        this.description = null;
//    }
//
//    public Card(Code code, Tag tag, String title, String description) {
//        this.code = code;
//        this.tag = tag;
//        this.title = title;
//        this.description = description;
//    }
//
//    public abstract boolean action();
//}

//class PictureCard extends Card {
//    String imageURL;
//
//    public PictureCard(Code code, Tag tag, String title, String description,String imageURL) {
//        super(code,tag,title,description);
//        this.imageURL = imageURL;
//    }
//
//    @Override
//    public boolean action() {
//        // TODO
//        return true;
//    }
//}
//
//class VibratorCard extends Card {
//    public VibratorCard(Code code, Tag tag, String title, String description) {
//        super(code, tag, title, description);
//    }
//
//    @Override
//    public boolean action() {
//        // TODO
//        return true;
//    }
//}
//
//class SoundCard extends Card {
//    String soundURL;
//
//    public SoundCard(Code code, Tag tag, String title, String description, String soundURL) {
//        super(code, tag, title, description);
//        this.soundURL = soundURL;
//    }
//
//    @Override
//    public boolean action() {
//        // TODO
//        return true;
//    }
//}
//
//enum Code {
//    PICTURE_CARD,   // 0
//    VIBRATOR_CARD,  // 1
//    SOUND_CARD      // 2
//}
//
//enum Tag {
//    SPORT,  // sport
//    ART,    // art
//    FUN     // fun
//}
