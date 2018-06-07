package com.pushe.hector.acfc_1;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;

import java.net.URL;

public class DownloadImage extends AsyncTask<String, Void, BitmapAndURLWrap> {
    DownloadCompleteListener downloadCompleteListener;

    public DownloadImage(DownloadCompleteListener dCL) {
        this.downloadCompleteListener = dCL;
    }

    @Override
    protected BitmapAndURLWrap doInBackground(String... strings) {
        String imageURL = strings[0];
        Bitmap image = null;
        try {
            image = BitmapFactory.decodeStream(new URL(imageURL).openConnection().getInputStream());
        }
        catch (Exception e) {
            e.printStackTrace();
        }
        return new BitmapAndURLWrap(image, strings[0]);
    }

    @Override
    protected void onPostExecute(BitmapAndURLWrap imageWithURL) {
//        super.onPostExecute(result);

        downloadCompleteListener.imageDownloadComplete(imageWithURL);
    }
}

class BitmapAndURLWrap {
    Bitmap imageBitmap;
    String imageURL;

    public BitmapAndURLWrap(Bitmap imageBitmap, String imageURL) {
        this.imageBitmap = imageBitmap;
        this.imageURL = imageURL;
    }
}
