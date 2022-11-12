package cards.minions;

public class StandardMinion extends Minion{
    boolean isTank;

    public StandardMinion(int mana,
                          String description,
                          String[] colors,
                          String name,
                          int health,
                          int attackDamage,
                          int prefRow,
                          boolean isTank) {
        super(mana, description, colors, name, health, attackDamage, prefRow);
        this.isTank = isTank;
    }

    public boolean isTank() {
        return isTank;
    }

    public void setTank(boolean tank) {
        isTank = tank;
    }
}
