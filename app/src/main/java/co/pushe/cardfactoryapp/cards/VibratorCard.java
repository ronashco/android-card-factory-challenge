package co.pushe.cardfactoryapp.cards;

/**
 * Created by Matin on 5/31/2018.
 */

public class VibratorCard extends Card {

    public VibratorCard(String title, String description, String cardTag, int code) {
        super(title, description, cardTag, code);
    }

    @Override
    public String toString() {
        return "VibratorCard{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", cardTag='" + getCardTag() + '\'' +
                ", code=" + getCode() +
                '}';
    }
}
