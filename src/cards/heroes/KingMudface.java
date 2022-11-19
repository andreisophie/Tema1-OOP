package cards.heroes;

import game.Game;

public class KingMudface extends Hero {
    public KingMudface(final int mana,
                       final String description,
                       final String[] colors,
                       final String name) {
        super(mana, description, colors, name);
    }

    /**
     * Heals all minions on target row for 1 HP
     * @param targetRow casts this hero's ability on target row
     */
    @Override
    public void ability(final int targetRow) {
        Game.getPlayground()[targetRow].forEach(target -> {
            target.setHealth(target.getHealth() + 1);
        });

        this.setHasAttacked(true);
    }
}
