package cards.heroes;

import cards.Card;

public abstract class Hero extends Card {
    private int health = 30;

    abstract void ability(int targetRow);
}
