package cards.heroes;

import cards.Card;

import java.util.Arrays;

public abstract class Hero extends Card {
    private int health = 30;

    public Hero(int mana, String description, String[] colors, String name) {
        super(mana, description, colors, name);
    }

    abstract void ability(int targetRow);

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
