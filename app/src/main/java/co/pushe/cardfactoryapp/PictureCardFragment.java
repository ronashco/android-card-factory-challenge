package co.pushe.cardfactoryapp;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.squareup.picasso.Picasso;

import co.pushe.cardfactoryapp.cards.PictureCard;

/**
 * Created by Matin on 5/31/2018.
 */

public class PictureCardFragment extends CardFragment {
    private static final String TAG = "PictureCardFragment";

    private ImageView pictureIV;

    public PictureCardFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.d(TAG, "onCreateView: starts");
        View v = inflater.inflate(R.layout.fragment_picture_card, container, false);
        cardFindViewById(v);

        Log.d(TAG, "onCreateView: ends");
        return v;
    }

    @Override
    protected void cardFindViewById(View view) {
        Log.d(TAG, "cardFindViewById: starts");
        super.cardFindViewById(view);
        pictureIV = view.findViewById(R.id.pictureIV);
        setComponents();
        Log.d(TAG, "cardFindViewById: ends");
    }

    @Override
    protected void setComponents() {
        Log.d(TAG, "setComponents: starts");
        super.setComponents();
        Picasso.with(getContext())
                .load(((PictureCard)getTheCard()).getImagePath())
                .error(R.drawable.picture_error)
                .placeholder(R.drawable.placeholder)
                .into(pictureIV);
        Log.d(TAG, "setComponents: ends");
    }
}
