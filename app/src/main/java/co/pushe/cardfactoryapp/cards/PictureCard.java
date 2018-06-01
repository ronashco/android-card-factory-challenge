package co.pushe.cardfactoryapp.cards;

/**
 * Created by Matin on 5/31/2018.
 */

public class PictureCard extends Card {

    private String imagePath;

    public PictureCard(String title, String description, String cardTag, int code, String imagePath) {
        super(title, description, cardTag, code);
        this.imagePath = imagePath;
    }

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    @Override
    public String toString() {
        return "PictureCard{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", cardTag='" + getCardTag() + '\'' +
                ", code=" + getCode() +
                "imagePath='" + imagePath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof PictureCard)
            return super.equals(obj) &&
                 imagePath.equals(((PictureCard) obj).getImagePath());

        return false;
    }
}
