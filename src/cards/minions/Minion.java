package cards.minions;

import cards.Card;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.Helpers;

import java.util.Arrays;

public class Minion extends Card {
    private int health;
    private int attackDamage;
    private boolean frozen;
    private boolean hasAttacked;
    private int prefRow;
    private boolean isTank;

    public Minion(int mana,
                  String description,
                  String[] colors,
                  String name,
                  int health,
                  int attackDamage,
                  int prefRow,
                  boolean isTank) {
        super(mana, description, colors, name);
        this.health = health;
        this.attackDamage = attackDamage;
        this.prefRow = prefRow;
        this.frozen = true;
        this.hasAttacked = false;
        this.isTank = isTank;
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

    public boolean isTank() {
        return isTank;
    }

    public void setTank(boolean tank) {
        isTank = tank;
    }

    public ObjectNode toJSON() {
        ObjectNode minionNode = Helpers.mapper.createObjectNode();
        minionNode.put("mana", getMana());
        minionNode.put("attackDamage", getAttackDamage());
        minionNode.put("health", getHealth());
        minionNode.put("description", getDescription());
        ArrayNode colorsNode = Helpers.mapper.createArrayNode();
        for (String color : getColors()) {
            colorsNode.add(color);
        }
        minionNode.set("colors", colorsNode);
        minionNode.put("name", getName());

        return minionNode;
    }

    @Override
    public String toString() {
        return "{" +
                "health: " + health +
                ", mana: " + this.getMana() +
                ", attackDamage: " + attackDamage +
                ", Description: '" + this.getDescription() + '\'' +
                ", colors: " + Arrays.toString(this.getColors()) +
                ", name: '" + this.getName() + '\'' +
                '}';
    }
}
