package cards.minions;

public interface Caster {
    boolean checkTarget(Minion target);
    void ability(Minion target);
}
