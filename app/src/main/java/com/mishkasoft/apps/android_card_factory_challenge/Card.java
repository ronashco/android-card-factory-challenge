package com.mishkasoft.apps.android_card_factory_challenge;

import android.content.Context;
import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Vibrator;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class Card {
    int code;
    String title, description, tag;
    String url;
    static final MediaPlayer player = new MediaPlayer();

    public Card(JSONObject object) {
        try {
            this.code = object.getInt("code");
            this.title = object.getString("title");
            this.description = object.getString("description");
            this.tag = object.getString("tag");
            if (code == 0)
                this.url = object.getString("image");
            else if (code == 1)
                this.url = "";
            else if (code == 2)
                this.url = object.getString("sound");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void showCard(final Context context, TextView titleTextView, TextView descriptionTextView, final ImageView imageView, RelativeLayout background) {
        titleTextView.setText(title);
        descriptionTextView.setText(description);
        imageView.setVisibility(View.INVISIBLE);
        player.reset();
        switch (tag) {      //ba tavajjoh be tag rang e background ro taeen mikone
            case "sport":
                background.setBackgroundColor(Color.CYAN);
                break;
            case "fun":
                background.setBackgroundColor(Color.YELLOW);
                break;
            case "art":
                background.setBackgroundColor(Color.rgb(200, 100, 100));
                break;
        }
        switch (code) {
            case 0: // aks ro neshoon mide
                imageView.setVisibility(View.VISIBLE);
                Picasso.with(context).load(url).into(imageView);
                break;
            case 1:// vibre mizane
                Vibrator v = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                long[] pattern = {0, 300, 100, 300};
                v.vibrate(pattern, -1);
                break;
            case 2://ahang ro pakhsh mikone
                player.setAudioStreamType(AudioManager.STREAM_MUSIC);
                try {
                    player.setDataSource(url);
                } catch (IOException e) {
                    e.printStackTrace();
                }
                player.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                    @Override
                    public void onPrepared(MediaPlayer mediaPlayer) {
                        player.start();
                    }
                });
                player.prepareAsync();

                break;
        }
    }

}
