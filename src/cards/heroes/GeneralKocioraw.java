package cards.heroes;

import game.Game;

public class GeneralKocioraw extends Hero {
    public GeneralKocioraw(final int mana,
                           final String description,
                           final String[] colors,
                           final String name) {
        super(mana, description, colors, name);
    }

    /**
     * increases attack damage of all minions on target row by 1
     * @param targetRow casts this hero's ability on target row
     */
    @Override
    public void ability(final int targetRow) {
        Game.playground[targetRow].forEach(target -> {
            target.setAttackDamage(target.getAttackDamage() + 1);
        });

        this.setHasAttacked(true);
    }
}
