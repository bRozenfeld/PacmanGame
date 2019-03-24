package model;

/**
 * Class representing a static element in the game
 * @inv {@code value > 0}
 */
public abstract class StaticElement {
    /**
     * Correspond at the score given to the player
     * When he eats this static element
     */
    private final int value;

    public StaticElement(int value) {
        this.value = value;
    }

    public int getValue() {
        invariant();
        return value;
    }

    private void invariant() {
        assert value > 0 : "Invariant violated : Value <= 0";
    }
}
