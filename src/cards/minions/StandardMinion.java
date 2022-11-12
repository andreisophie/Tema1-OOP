package cards.minions;

public class StandardMinion extends Minion{
    boolean isTank;

    public boolean isTank() {
        return isTank;
    }

    public void setTank(boolean tank) {
        isTank = tank;
    }
}
