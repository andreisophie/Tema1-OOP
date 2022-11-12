package cards.heroes;

import cards.Card;

public abstract class Hero extends Card {
    private int health = 30;

    public Hero(int mana, String description, String[] colors, String name) {
        super(mana, description, colors, name);
    }

    abstract void ability(int targetRow);
}
