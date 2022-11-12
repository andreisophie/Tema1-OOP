package cards.minions;

import cards.Card;

import java.util.Arrays;

public abstract class Minion extends Card {
    private int health;
    private int attackDamage;
    boolean frozen;
    boolean hasAttacked;
    int prefRow;

    public Minion(int mana,
                  String description,
                  String[] colors,
                  String name,
                  int health,
                  int attackDamage,
                  int prefRow) {
        super(mana, description, colors, name);
        this.health = health;
        this.attackDamage = attackDamage;
        this.prefRow = prefRow;
        this.frozen = true;
        this.hasAttacked = false;
    }

    public void attack(Minion target) {
        if (this.frozen) {
            System.out.println("Attacker card is frozen.");
            return;
        }
        if (this.hasAttacked) {
            System.out.println("Attacker card has already attacked this turn.");
            return;
        }
        //TODO: check if card belongs to enemy and tank cards
        target.setHealth(target.getHealth() - this.attackDamage);
        this.hasAttacked = true;
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public boolean isFrozen() {
        return frozen;
    }

    public void setFrozen(boolean frozen) {
        this.frozen = frozen;
    }

    public int getPrefRow() {
        return prefRow;
    }

    public void setPrefRow(int prefRow) {
        this.prefRow = prefRow;
    }

    public boolean getHasAttacked() {
        return hasAttacked;
    }

    public void setHasAttacked(boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    @Override
    public String toString() {
        return "Minion{" +
                "health=" + health +
                ", mana=" + this.getMana() +
                ", attackDamage=" + attackDamage +
                ", Description='" + this.getDescription() + '\'' +
                ", colors=" + Arrays.toString(this.getColors()) +
                ", name='" + this.getName() + '\'' +
                '}';
    }
}
