package com.pushe.hector.acfc_1;

import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.AsyncTask;

import java.io.IOException;

public class SoundPrepare extends AsyncTask<String, Void, MediaPlayer>{
    DownloadCompleteListener downloadCompleteListener;

    public SoundPrepare(DownloadCompleteListener dCL) {
        this.downloadCompleteListener = dCL;
    }

    @Override
    protected MediaPlayer doInBackground(String... strings) {
        MediaPlayer mediaPlayer = new MediaPlayer();
        mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
        try {
            mediaPlayer.setDataSource(strings[0]);
            mediaPlayer.prepare(); // might take long! (for buffering, etc)
        } catch (IOException e) {
            e.printStackTrace();
        }
        return mediaPlayer;
    }

    @Override
    protected void onPostExecute(MediaPlayer mediaPlayer) {
        downloadCompleteListener.soundPrepareComplete(mediaPlayer);
    }
}
