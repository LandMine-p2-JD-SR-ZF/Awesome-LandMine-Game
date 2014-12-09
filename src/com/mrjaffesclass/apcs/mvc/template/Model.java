package com.mrjaffesclass.apcs.mvc.template;

import com.mrjaffesclass.apcs.messenger.*;
import java.awt.Dimension;

/**
 * The model represents the data that the app uses.
 *
 * @jordan
 * @version 1.0
 */
public class Model implements MessageHandler {

    // Messaging system for the code.
    private final Messenger messenger;
    private final boolean[][] BombMap = new boolean[8][8];

    /**
     * Model constructor: Create the data representation of the program
     * messenger is the system for communication in the model.
     * 
     * @param messages
     */
    public Model(Messenger messages) {
        messenger = messages;

    }

    /**
     * Inits the Model and subscribe to the GameOver and Square.
     */
    public void init() {
        messenger.subscribe("Square:position", this);
        messenger.subscribe("GameOver:Reset", this);
        placeBombMap();
    }

    private void placeBombMap() {
        for (int y = 0; y < 8; y++) {
            for (int x = 0; x < 8; x++) {
                BombMap[x][y] = false;
                
                /*This is the BombMap, it will place a bomb in the rows and 
                *coulmns of our grid.
                */
            }
        }

        //Makes the counter for rand bomb placement.
        int counter = 0;
        while (counter < 10) {
            int r1 = (int) (Math.random() * 8);
            int r2 = (int) (Math.random() * 8);
            if (!BombMap[r1][r2]) {
                BombMap[r1][r2] = true;
                counter++;
            }
        }
    }

    private void checkBombMap(Dimension dimension) {
        if (BombMap[(int) dimension.width][(int) dimension.height]) {
            messenger.notify("Model:LoseALife");

        } else {
            messenger.notify("Model:ScoreAPoint");
            //notify messages to be sent to the model.
        }
    }

    private void resetBoard() {
        placeBombMap();
        //When board reset, BombMap will start again.
    }

    @Override
    public void messageHandler(String messageName, Object messagePayload) {
        switch (messageName) {
            case "Square:position":
                checkBombMap((Dimension) messagePayload);
                break;
            case "GameOver:Reset":
                resetBoard();
                break;
        }
    }

}
