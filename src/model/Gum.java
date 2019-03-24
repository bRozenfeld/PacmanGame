package model;

/**
 * Class representing a gum that pacman has to eat to win
 */
public class Gum extends StaticElement {

    /**
     * define if this is a super gum or not
     */
    private final boolean isSuper;

    public Gum(int value, boolean isSuper) {
        super(value);
        this.isSuper = isSuper;
    }

    public boolean getIsSuper() {
        return isSuper;
    }
}
