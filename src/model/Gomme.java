package model;

/**
 * Class representing the gomme that pacman has to eat to win
 */
public class Gomme extends StaticElement {
    private boolean isSuper;

    public Gomme(int value, boolean isSuper) {
        super(value);
        this.isSuper = isSuper;
    }

    public boolean getIsSuper() {
        return isSuper;
    }
}
