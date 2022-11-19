package cards.environments;

import cards.Card;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.Helpers;

public abstract class Environment extends Card {
    public Environment(int mana, String description, String[] colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public ObjectNode toJSON() {
        ObjectNode environmentNode = Helpers.mapper.createObjectNode();

        environmentNode.put("mana", getMana());
        environmentNode.put("description", getDescription());
        ArrayNode colorsNode = Helpers.mapper.createArrayNode();
        for (String color : getColors()) {
            colorsNode.add(color);
        }
        environmentNode.put("colors", colorsNode);
        environmentNode.put("name", getName());

        return environmentNode;
    }

    abstract public void ability(int targetRow);


}
