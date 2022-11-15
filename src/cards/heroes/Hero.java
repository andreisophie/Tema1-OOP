package cards.heroes;

import cards.Card;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import game.Helpers;

import java.util.Arrays;

public abstract class Hero extends Card {
    private int health = 30;

    public Hero(int mana, String description, String[] colors, String name) {
        super(mana, description, colors, name);
    }

    abstract void ability(int targetRow);

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

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

    @Override
    public String toString() {
        return "{" +
                "mana: " + super.getMana() +
                ", description: '" + super.getDescription() + '\'' +
                ", colors: " + Arrays.toString(super.getColors()) +
                ", name: '" + super.getName() + '\'' +
                ", health: " + health +
                '}';
    }
}
