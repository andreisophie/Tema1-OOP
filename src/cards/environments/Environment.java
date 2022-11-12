package cards.environments;

import cards.Card;

public abstract class Environment extends Card {
    abstract void ability(int targetRow);
}
