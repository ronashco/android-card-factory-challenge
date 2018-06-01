package co.pushe.cardfactoryapp.web;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by Matin on 5/30/2018.
 *
 * This class sends an http get request to a given url and returns the raw data downloaded.
 * The action is done on a background thread using AsyncTask class
 * (if the caller itself in on a background thread, this action can be run on the same thread,
 * using the method 'executeOnSameThread')
 *
 */


class RawDataDownloader extends AsyncTask<String, Void, String> {

    private static final String TAG = "RawDataDownloader";
    private DownloadStatus downloadStatus;
    private OnDownloadCompleteListener listener;

    public interface OnDownloadCompleteListener {
        void onDownloadComplete (String data , DownloadStatus status);
    }

    public RawDataDownloader(OnDownloadCompleteListener listener) {
        this.downloadStatus = DownloadStatus.IDLE;
        this.listener = listener;
    }

    @Override
    protected void onPostExecute(String s) {
        Log.d(TAG, "onPostExecute: starts");
        Log.d(TAG, "onPostExecute: Parameter is " + s);

        listener.onDownloadComplete(s , downloadStatus);

        Log.d(TAG, "onPostExecute: ends");
        super.onPostExecute(s);
    }

    @Override
    protected String doInBackground(String... strings) {
        HttpURLConnection connection = null;
        BufferedReader reader = null;

        try {
            downloadStatus = DownloadStatus.PROCESSING;
            URL url = new URL(strings[0]);
            connection = (HttpURLConnection) url.openConnection();
            connection.connect();

            // GET: to receive data (the default)
            // POST: to send data through the connection
            connection.setRequestMethod("GET");
            int response = connection.getResponseCode();
            Log.d(TAG, "doInBackground: The response code was " + response);

            StringBuilder result = new StringBuilder();

            reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            for (String line = reader.readLine() ; line != null ; line = reader.readLine())
            {
                result.append(line).append("\n");
            }

            downloadStatus = DownloadStatus.OK;
            return result.toString();

        } catch (MalformedURLException e) {
            Log.e(TAG, "doInBackground: Invalid Url " + e.getMessage());
        } catch (IOException e) {
            Log.e(TAG, "doInBackground: IO Exception Reading Data " + e.getMessage());
        } catch (SecurityException e) {
            Log.e(TAG, "doInBackground: Security Exception. Needs permission? " + e.getMessage());
        }finally {
            if (connection != null)
                connection.disconnect();
            if (reader != null)
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e(TAG, "doInBackground: Error closing stream " + e.getMessage());
                }
        }

        downloadStatus = DownloadStatus.FAILED_OR_EMPTY;
        return null;
    }
}
