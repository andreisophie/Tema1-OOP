package cards.heroes;

import cards.Card;
import cards.minions.Minion;
import game.Game;

public class EmpressThorina extends Hero {
    public EmpressThorina(int mana, String description, String[] colors, String name) {
        super(mana, description, colors, name);
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

        Game.playground[targetRow].remove(targetIndex);
        this.setHasAttacked(true);
    }
}
