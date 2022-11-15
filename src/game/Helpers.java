package game;

import cards.Card;
import cards.environments.Firestorm;
import cards.environments.HeartHound;
import cards.environments.Winterfell;
import cards.heroes.*;
import cards.minions.Minion;
import cards.minions.casterMinions.Disciple;
import cards.minions.casterMinions.Miraj;
import cards.minions.casterMinions.TheCursedOne;
import cards.minions.casterMinions.TheRipper;
import com.fasterxml.jackson.databind.ObjectMapper;
import fileio.*;

public class Helpers {
    static public ObjectMapper mapper = new ObjectMapper();

    static public Card CardInputToCard(CardInput cardInput) {
        return switch (cardInput.getName()) {
            case "Sentinel", "Berserker" -> new Minion(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName(),
                    cardInput.getHealth(),
                    cardInput.getAttackDamage(),
                    0,
                    false);
            case "Goliath", "Warden" -> new Minion(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName(),
                    cardInput.getHealth(),
                    cardInput.getAttackDamage(),
                    1,
                    true);
            case "The Ripper" -> new TheRipper(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName(),
                    cardInput.getHealth(),
                    cardInput.getAttackDamage(),
                    1);
            case "Miraj" -> new Miraj(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName(),
                    cardInput.getHealth(),
                    cardInput.getAttackDamage(),
                    1);
            case "The Cursed One" -> new TheCursedOne(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName(),
                    cardInput.getHealth(),
                    cardInput.getAttackDamage(),
                    0);
            case "Disciple" -> new Disciple(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName(),
                    cardInput.getHealth(),
                    cardInput.getAttackDamage(),
                    0);
            case "Firestorm" -> new Firestorm(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName());
            case "Winterfell" -> new Winterfell(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName());
            case "Heart Hound" -> new HeartHound(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName());
            case "Lord Royce" -> new LordRoyce(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName());
            case "Empress Thorina" -> new EmpressThorina(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName());
            case "King Mudface" -> new KingMudface(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName());
            case "General Kocioraw" -> new GeneralKocioraw(cardInput.getMana(),
                    cardInput.getDescription(),
                    cardInput.getColors().toArray(new String[0]),
                    cardInput.getName());
            default -> null;
        };
    }

}
