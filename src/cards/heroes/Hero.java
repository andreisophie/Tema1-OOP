package cards.heroes;

import cards.Card;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.Constants;
import helpers.Helpers;

public abstract class Hero extends Card {
    private int health = Constants.heroMaxHealth;
    private boolean hasAttacked = false;

    public Hero(final int mana,
                final String description,
                final String[] colors,
                final String name) {
        super(mana, description, colors, name);
    }

    /**
     *
     * @param targetRow casts this hero's ability on target row
     */
    public abstract void ability(int targetRow);

    /**
     *
     * @return health of current hero instance
     */
    public int getHealth() {
        return health;
    }

    /**
     *
     * @param health sets health of current instance of hero
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     *
     * @return true if card has already attacked this turn,
     * false if not
     */
    public boolean hasAttacked() {
        return hasAttacked;
    }

    /**
     *
     * @param hasAttacked sets if card has already attacked this turn
     */
    public void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    /**
     *
     * @return an ObjectNode containing current instance of hero in JSON format,
     * to be used for output
     */
    @Override
    public ObjectNode toJSON() {
        ObjectNode heroNode = Helpers.mapper.createObjectNode();

        heroNode.put("mana", getMana());
        heroNode.put("description", getDescription());
        ArrayNode colorsNode = Helpers.mapper.createArrayNode();
        for (String color : getColors()) {
            colorsNode.add(color);
        }
        heroNode.put("colors", colorsNode);
        heroNode.put("name", getName());
        heroNode.put("health", getHealth());

        return heroNode;
    }
}
