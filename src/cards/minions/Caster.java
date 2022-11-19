package cards.minions;

public interface Caster {
    /**
     *
     * @param target uses ability of current minion on target minion
     */
    void ability(Minion target);
}
