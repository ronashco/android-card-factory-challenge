package co.pushe.cardfactoryapp;

import android.content.Context;
import android.os.Bundle;
import android.os.Vibrator;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

/**
 * Created by Matin on 5/31/2018.
 */

public class VibratorCardFragment extends CardFragment {
    private static final String TAG = "VibratorCardFragment";

    public VibratorCardFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");
        View v = inflater.inflate(R.layout.fragment_vibrator_card, container, false);
        cardFindViewById(v);

        Vibrator vibrator = (Vibrator) getActivity().getSystemService(Context.VIBRATOR_SERVICE);
        vibrator.vibrate(500);

        Log.d(TAG, "onCreateView: ends");
        return v;
    }

    @Override
    protected void cardFindViewById(View view) {
        Log.d(TAG, "cardFindViewById: starts");
        super.cardFindViewById(view);
        setComponents();
        Log.d(TAG, "cardFindViewById: ends");
    }

    @Override
    protected void setComponents() {
        Log.d(TAG, "setComponents: starts");
        super.setComponents();

        Log.d(TAG, "setComponents: ends");
    }
}
