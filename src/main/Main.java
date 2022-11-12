package main;

import cards.Card;
import cards.Deck;
import checker.Checker;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import checker.CheckerConstants;
import fileio.CardInput;
import fileio.Input;
import game.Game;
import game.Player;
import game.Helpers;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * The entry point to this homework. It runs the checker that tests your implementation.
 */
public final class Main {
    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);

        for (File file : Objects.requireNonNull(directory.listFiles())) {
            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePath1 for input file
     * @param filePath2 for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void action(final String filePath1,
                              final String filePath2) throws IOException {
        ObjectMapper objectMapper = new ObjectMapper();
        Input inputData = objectMapper.readValue(new File(CheckerConstants.TESTS_PATH + filePath1),
                Input.class);

        ArrayNode output = objectMapper.createArrayNode();

        //TODO add here the entry point to your implementation
        if (Game.isInitialised() == false) {

            Deck deck;
            Card card;
            for (int playerIndex = 0; playerIndex < 2; playerIndex++) {
                Game.players[playerIndex] = new Player();
                Game.players[playerIndex].setNrDecks(inputData.getPlayerOneDecks().getNrDecks());
                Game.players[playerIndex].setNrCardsInDeck(inputData.getPlayerOneDecks().getNrCardsInDeck());
                for (int i = 0; i < Game.players[playerIndex].getNrDecks(); i++) {
                    deck = new Deck();
                    for (int j = 0; j < Game.players[playerIndex].getNrCardsInDeck(); j++) {
                        CardInput cardInput = inputData.getPlayerOneDecks().getDecks().get(i).get(j);
                        deck.cards.add(Helpers.CardInputToCard(cardInput));
                    }
                    Game.players[playerIndex].addDeck(deck);
                }
            }

            Game.setInitialised(true);
        }

        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePath2), output);
    }
}
