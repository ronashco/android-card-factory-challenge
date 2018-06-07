package com.pushe.hector.acfc_1;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements JSONDownloadCompleteListener {
//    public static final String EXTRA_MESSAGE = "com.example.myfirstapp.MESSAGE";
    public static final String CARDS_JSON_URL = "http://static.pushe.co/challenge/json";
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if (isNetworkConnected()) {
            progressDialog = new ProgressDialog(this);
            progressDialog.setMessage("Please wait...");
            progressDialog.setCancelable(false);
            progressDialog.show();

            startDownload();
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

    private void startDownload() {
//        new DownloadRepoTask(this).execute("https://api.github.com/repositories");
        new DownloadCardsInfo(this).execute(CARDS_JSON_URL);
    }

    @Override
    public void jsonDownloadComplete(ArrayList<Card> cards) {

    }

    /** Called when the user taps the Send button */
//    public void sendMessage(View view) {
//        Intent intent = new Intent(this, DisplayMessageActivity.class);
//        EditText editText = (EditText) findViewById(R.id.editText);
//        String message = editText.getText().toString();
//        intent.putExtra(EXTRA_MESSAGE, message);
//        startActivity(intent);
//    }
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
