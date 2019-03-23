package model;

/**
 * Class represeting a static element in the game
 */
public abstract class StaticElement {
    private final int value;

    public StaticElement(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
