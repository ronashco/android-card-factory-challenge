package co.pushe.cardfactoryapp.web;

/**
 * Created by Matin on 5/31/2018.
 *
 * it has the current status of the raw data being downloaded by the 'RawDataDownloader' class
 *
 */

public enum DownloadStatus {
    IDLE ,
    PROCESSING ,
    NOT_INITIALIZED ,
    FAILED_OR_EMPTY ,
    OK
}
