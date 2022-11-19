package cards.minions;

import cards.Card;
import cards.heroes.Hero;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import helpers.Constants;

public class Minion extends Card implements Cloneable {
    private int health;
    private int attackDamage;
    private boolean frozen;
    private boolean hasAttacked;
    private final int prefRow;
    private final boolean isTank;

    public Minion(final int mana,
                  final String description,
                  final String[] colors,
                  final String name,
                  final int health,
                  final int attackDamage,
                  final int prefRow,
                  final boolean isTank) {
        super(mana, description, colors, name);
        this.health = health;
        this.attackDamage = attackDamage;
        this.prefRow = prefRow;
        this.frozen = false;
        this.hasAttacked = false;
        this.isTank = isTank;
    }

    public Minion(final Minion minion) {
        super(minion.getMana(), minion.getDescription(), minion.getColors(), minion.getName());
        this.health = minion.getHealth();
        this.attackDamage = minion.getAttackDamage();
        this.prefRow = minion.getPrefRow();
        this.frozen = false;
        this.hasAttacked = false;
        this.isTank = minion.isTank;
    }

    /**
     *
     * @return a clone of current minion as Card object
     */
    public Card cloneMinion() {
        Minion clone = new Minion(this);
        return clone;
    }

    /**
     * attacks target minion
     * @param target minion to be attacked
     */
    public void attack(final Minion target) {
        target.setHealth(target.getHealth() - this.attackDamage);
        this.hasAttacked = true;
    }

    /**
     * attacks target minion
     * @param target minion to be attacked
     */
    public void attack(final Hero target) {
        target.setHealth(target.getHealth() - this.attackDamage);
        this.hasAttacked = true;
    }

    /**
     *
     * @return health of current minion
     */
    public int getHealth() {
        return health;
    }

    /**
     * changes health of current minion
     * @param health new value for health
     */
    public void setHealth(final int health) {
        this.health = health;
    }

    /**
     *
     * @return attack damage of current minion
     */
    public int getAttackDamage() {
        return attackDamage;
    }

    /**
     * changes attack damage of current minion
     * @param attackDamage new value for attack damage
     */
    public void setAttackDamage(final int attackDamage) {
        this.attackDamage = attackDamage;
    }

    /**
     * checks whether target if frozen
     * @return true if frozen, false otherwise
     */
    public boolean isFrozen() {
        return frozen;
    }

    /**
     * changes card's frozen state
     * @param frozen new frozen state
     */
    public void setFrozen(final boolean frozen) {
        this.frozen = frozen;
    }

    /**
     * @return the preferred row of current minion
     * 0 if minion stays in the back,
     * 1 if minion stays in the front
     */
    public int getPrefRow() {
        return prefRow;
    }

    /**
     *
     * @return true if minion has already attacked this turn, false if not
     */
    public boolean getHasAttacked() {
        return hasAttacked;
    }

    /**
     * sets whether the target has attacked
     * @param hasAttacked new state for has attacked
     */
    public void setHasAttacked(final boolean hasAttacked) {
        this.hasAttacked = hasAttacked;
    }

    /**
     *
     * @return true if current minion is tank, false if not
     */
    public boolean isTank() {
        return isTank;
    }

    /**
     *
     * @return an ObjectNode containing current object in JSON format,
     * to be used for output
     */
    public ObjectNode toJSON() {
        ObjectNode minionNode = Constants.getMapper().createObjectNode();
        minionNode.put("mana", getMana());
        minionNode.put("attackDamage", getAttackDamage());
        minionNode.put("health", getHealth());
        minionNode.put("description", getDescription());
        ArrayNode colorsNode = Constants.getMapper().createArrayNode();
        for (String color : getColors()) {
            colorsNode.add(color);
        }
        minionNode.set("colors", colorsNode);
        minionNode.put("name", getName());

        return minionNode;
    }
}
