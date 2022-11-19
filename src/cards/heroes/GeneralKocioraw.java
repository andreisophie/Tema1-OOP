package cards.heroes;

import game.Game;

public class GeneralKocioraw extends Hero {
    public GeneralKocioraw(int mana, String description, String[] colors, String name) {
        super(mana, description, colors, name);
    }

    @Override
    public void ability(int targetRow) {
        Game.playground[targetRow].forEach(target -> {
            target.setAttackDamage(target.getAttackDamage() + 1);
        });

        this.setHasAttacked(true);
    }
}
