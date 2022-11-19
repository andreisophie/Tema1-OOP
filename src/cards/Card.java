package cards;

import com.fasterxml.jackson.databind.node.ObjectNode;

public abstract class Card {
    private final int mana;
    private final String description;
    private final String[] colors;
    private final String name;

    public Card(final int mana,
                final String description,
                final String[] colors,
                final String name) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
        this.name = name;
    }

    /**
     *
     * @return mana of current instance of card
     */
    public int getMana() {
        return mana;
    }

    /**
     *
     * @return description of current instance of card
     */
    public String getDescription() {
        return description;
    }

    /**
     *
     * @return colors of current instance of card
     */
    public String[] getColors() {
        return colors;
    }

    /**
     *
     * @return name of current instance of card
     */
    public String getName() {
        return name;
    }

    /**
     *
     * @return current class instance as an ObjectNode with significant parameters,
     * to be used for output
     */
    public abstract ObjectNode toJSON();
}
