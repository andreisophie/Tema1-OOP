package cards;

import java.util.Arrays;

public abstract class Card {
    private final int mana;
    private final String description;
    private final String[] colors;
    private final String name;

    public Card(int mana,
                String description,
                String[] colors,
                String name) {
        this.mana = mana;
        this.description = description;
        this.colors = colors;
        this.name = name;
    }

    public int getMana() {
        return mana;
    }

    public String getDescription() {
        return description;
    }

    public String[] getColors() {
        return colors;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return "Card{" +
                "mana=" + mana +
                ", description='" + description + '\'' +
                ", colors=" + Arrays.toString(colors) +
                ", name='" + name + '\'' +
                '}';
    }
}
