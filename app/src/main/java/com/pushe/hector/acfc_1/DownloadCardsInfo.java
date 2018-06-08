package com.pushe.hector.acfc_1;

import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * class for reading cards information as a string in json format
 */
public class DownloadCardsInfo extends AsyncTask<String, Void, String> {
    DownloadCompleteListener jsonDCL;

    public DownloadCardsInfo(DownloadCompleteListener jsonDCL) {
        this.jsonDCL = jsonDCL;
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            return downloadCardsJSON(strings[0]);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    protected void onPostExecute(String result) {
        jsonDCL.jsonDownloadComplete(new Utility().getCardsFromJSONString(result));
    }

    /**
     * Creates and opens a connection to the resource referred to by the URL.
     * @param urlString url to the resource
     * @return  string with json format
     * @throws IOException
     */
    private String downloadCardsJSON(String urlString) throws IOException {
        InputStream is = null;
        try {
            URL url = new URL(urlString);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();
            is = conn.getInputStream();
            return convertInputStreamToString(is);
        } finally {
            if (is != null) {
                is.close();
            }
        }
    }

    /**
     * converts InputStream to String
     * @param is InputStream object that will be converted to String
     * @return String data in InputStream
     * @throws IOException
     */
    private String convertInputStreamToString(InputStream is) throws IOException {
        BufferedReader r = new BufferedReader(new InputStreamReader(is));
        StringBuilder total = new StringBuilder();
        String line;
        while ((line = r.readLine()) != null) {
            total.append(line);
        }
        return new String(total);
    }
}
