package cards.environments;

import cards.Card;
import cards.minions.Minion;
import game.Game;

public class HeartHound extends Environment {
    public HeartHound(int mana, String description, String[] colors, String name) {
        super(mana, description, colors, name);
    }

    public HeartHound(Environment environment) {
        super(environment);
    }

    @Override
    public Card cloneCard() {
        HeartHound clone = new HeartHound(this);
        return clone;
    }

    @Override
    public void ability(int targetRow) {
        int targetIndex = -1;
        int targetHealth = -1;
        int index = -1;

        for (Minion target : Game.playground[targetRow]) {
            index++;
            if (target.getHealth() > targetHealth) {
                targetIndex = index;
                targetHealth = target.getHealth();
            }
        }

        Minion transfer = Game.playground[targetRow].remove(targetIndex);
        Game.playground[3 - targetRow].add(transfer);
    }
}
