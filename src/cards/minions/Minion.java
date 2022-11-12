package cards.minions;

import java.util.Arrays;

public abstract class Minion {
    private int mana;
    private int health;
    private int attackDamage;
    private String Description;
    private String[] colors;
    private String name;
    boolean frozen;
    int prefRow;

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public int getMana() {
        return mana;
    }

    public void setMana(int mana) {
        this.mana = mana;
    }

    public int getAttackDamage() {
        return attackDamage;
    }

    public void setAttackDamage(int attackDamage) {
        this.attackDamage = attackDamage;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String[] getColors() {
        return colors;
    }

    public void setColors(String[] colors) {
        this.colors = colors;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    @Override
    public String toString() {
        return "Minion{" +
                "health=" + health +
                ", mana=" + mana +
                ", attackDamage=" + attackDamage +
                ", Description='" + Description + '\'' +
                ", colors=" + Arrays.toString(colors) +
                ", name='" + name + '\'' +
                '}';
    }
}
