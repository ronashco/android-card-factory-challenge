package co.pushe.cardfactoryapp;

import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import co.pushe.cardfactoryapp.cards.Card;


/**
 * This class is the base class for the cards' fragment classes
 * contains the common widgets
 * in order to get a card fragment, the newInstance static method is called
 */

public abstract class CardFragment extends Fragment {

    private static final String TAG = "CardFragment";

    private Card theCard;
    private ImageView logoImgV;
    private TextView titleTV;
    private TextView descriptionTV;
    private TextView tagTV;
    private CardView cardView;

    public CardFragment() {

    }

    /**
     * Finds the common view components in the given view.
     * @param view
     */
    protected void cardFindViewById(View view){
        tagTV = view.findViewById(R.id.tagTV);
        titleTV = view.findViewById(R.id.titleTV);
        descriptionTV = view.findViewById(R.id.descriptionTV);
        logoImgV = view.findViewById(R.id.logo_imgV);
        cardView = view.findViewById(R.id.cardView);
    }

    /**
     * This factory method creates a new instance of
     * card fragment using the provided card object as the parameter.
     *
     * @param card the next card to be shown.
     * @return A new instance of fragment CardFragment.
     */

    public static CardFragment newInstance(Card card) {
        Log.d(TAG, "newInstance: starts with code: " + card.getCode());
        CardFragment fragment;

        switch (card.getCode()){
            case 0:
                fragment = new PictureCardFragment();
                break;
            case 1:
                fragment = new VibratorCardFragment();
                break;
            default:
                fragment = new SoundCardFragment();
                break;
        }

        return fragment;
    }

    protected void setComponents() {
        tagTV.setText(String.format("#%s", theCard.getCardTag()));
        titleTV.setText(theCard.getTitle());
        descriptionTV.setText(theCard.getDescription());
        switch (theCard.getCardTag()){
            case "fun":
                logoImgV.setImageResource(R.drawable.logo_fun);
                cardView.setCardBackgroundColor(getResources().getColor(R.color.fbutton_color_sun_flower));
                tagTV.setBackgroundColor(getResources().getColor(R.color.fbutton_color_pomegranate));
                titleTV.setTextColor(getResources().getColor(R.color.fbutton_color_wet_asphalt));
                descriptionTV.setTextColor(getResources().getColor(R.color.fbutton_color_wet_asphalt));
                break;
            case "sport":
                logoImgV.setImageResource(R.drawable.logo_sport);
                cardView.setCardBackgroundColor(getResources().getColor(R.color.fbutton_color_midnight_blue));
                tagTV.setBackgroundColor(getResources().getColor(R.color.fbutton_color_amethyst));

                break;
            case "art":
                logoImgV.setImageResource(R.drawable.logo_art);
                cardView.setCardBackgroundColor(getResources().getColor(R.color.fbutton_color_green_sea));
                tagTV.setBackgroundColor(getResources().getColor(R.color.fbutton_color_asbestos));
        }
    }

    public Card getTheCard() {
        return theCard;
    }

    public void setTheCard(Card theCard) {
        this.theCard = theCard;
    }
}
