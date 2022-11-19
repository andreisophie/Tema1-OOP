package cards.environments;

import cards.Card;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.Constants;

public abstract class Environment extends Card {
    public Environment(final int mana,
                       final String description,
                       final String[] colors,
                       final String name) {
        super(mana, description, colors, name);
    }

    public Environment(final Environment environment) {
        super(environment.getMana(),
                environment.getDescription(),
                environment.getColors(),
                environment.getName());
    }

    /**
     *
     * @return an ObjectNode of current instance in JSON format,
     * to be used for output
     */
    @Override
    public ObjectNode toJSON() {
        ObjectNode environmentNode = Constants.getMapper().createObjectNode();

        environmentNode.put("mana", getMana());
        environmentNode.put("description", getDescription());
        ArrayNode colorsNode = Constants.getMapper().createArrayNode();
        for (String color : getColors()) {
            colorsNode.add(color);
        }
        environmentNode.put("colors", colorsNode);
        environmentNode.put("name", getName());

        return environmentNode;
    }

    /**
     *
     * @param targetRow casts the ability of this instance of Environment on target row
     */
    public abstract void ability(int targetRow);


}
