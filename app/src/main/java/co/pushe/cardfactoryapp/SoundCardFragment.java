package co.pushe.cardfactoryapp;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.io.IOException;

import co.pushe.cardfactoryapp.cards.SoundCard;

/**
 * Created by Matin on 5/31/2018.
 */

public class SoundCardFragment extends CardFragment {
    private static final String TAG = "SoundCardFragment";

    private MediaPlayer mediaPlayer;
    private ProgressBar progressBar;
    private TextView bufferingTV;
    private Button replayBtn;

    public SoundCardFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");
        View v = inflater.inflate(R.layout.fragment_sound_card, container, false);
        cardFindViewById(v);

        mediaPlayer = new MediaPlayer();
        try {
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setDataSource(((SoundCard)getTheCard()).getSoundPath());
            mediaPlayer.prepareAsync();

            mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
                @Override
                public void onPrepared(MediaPlayer player) {
                    mediaPlayer.start();
                    bufferingTV.setVisibility(View.INVISIBLE);
                    progressBar.setVisibility(View.INVISIBLE);
                    replayBtn.setVisibility(View.INVISIBLE);

                }
            });
            mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mediaPlayer) {
                    replayBtn.setVisibility(View.VISIBLE);
                }
            });

            replayBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    mediaPlayer.start();
                    replayBtn.setVisibility(View.INVISIBLE);
                }
            });

        } catch (IOException e) {
            e.printStackTrace();
        }

        Log.d(TAG, "onCreateView: ends");
        return v;
    }

    @Override
    protected void cardFindViewById(View view) {
        Log.d(TAG, "cardFindViewById: starts");
        super.cardFindViewById(view);
        progressBar = view.findViewById(R.id.progressBar);
        bufferingTV = view.findViewById(R.id.buffering_message);
        replayBtn = view.findViewById(R.id.replayBtn);
        setComponents();
        Log.d(TAG, "cardFindViewById: ends");
    }

    @Override
    protected void setComponents() {
        Log.d(TAG, "setComponents: starts");
        super.setComponents();
        replayBtn.setVisibility(View.INVISIBLE);
        Log.d(TAG, "setComponents: ends");
    }


    // to stop the sound when the card is changed
    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mediaPlayer != null)
        {
            try {
                mediaPlayer.release();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mediaPlayer != null)
        {
            try {
                mediaPlayer.release();
            } catch(Exception e) {
                e.printStackTrace();
            }
        }
    }


}
