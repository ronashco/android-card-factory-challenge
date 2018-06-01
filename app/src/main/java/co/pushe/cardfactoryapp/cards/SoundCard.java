package co.pushe.cardfactoryapp.cards;

/**
 * Created by Matin on 5/31/2018.
 */

public class SoundCard extends Card {

    private String soundPath;

    public SoundCard(String title, String description, String cardTag, int code, String soundPath) {
        super(title, description, cardTag, code);
        this.soundPath = soundPath;
    }

    public String getSoundPath() {
        return soundPath;
    }

    public void setSoundPath(String soundPath) {
        this.soundPath = soundPath;
    }

    @Override
    public String toString() {
        return "SoundCard{" +
                "title='" + getTitle() + '\'' +
                ", description='" + getDescription() + '\'' +
                ", cardTag='" + getCardTag() + '\'' +
                ", code=" + getCode() +
                "soundPath='" + soundPath + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof SoundCard)
            return super.equals(obj) &&
                    soundPath.equals(((SoundCard) obj).getSoundPath());

        return false;
    }
}
