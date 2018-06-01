package co.pushe.cardfactoryapp.cards;

/**
 * Created by Matin on 5/31/2018.
 *
 * This class is the base class for different cards and contains common properties and methods
 * of all cards.
 *
 * different types of cards may not have a lot of different properties in this example, but this
 * type of implementation is necessary for reuse and readability.
 *
 */

public abstract class Card {

    private String title;
    private String description;
    private String cardTag;
    private int code;

    public Card(String title, String description, String cardTag, int code) {
        this.title = title;
        this.description = description;
        this.cardTag = cardTag;
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCardTag() {
        return cardTag;
    }

    public void setCardTag(String cardTag) {
        this.cardTag = cardTag;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public abstract String toString();

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Card)
        return title.equals(((Card)obj).getTitle()) &&
                    description.equals(((Card)obj).getDescription()) &&
                    cardTag.equals(((Card)obj).getCardTag()) &&
                    code == ((Card)obj).getCode();

        return false;
    }
}
