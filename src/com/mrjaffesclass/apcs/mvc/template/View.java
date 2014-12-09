/*
 * JAVADOC VERSION
*/
package com.mrjaffesclass.apcs.mvc.template;

import com.mrjaffesclass.apcs.messenger.MessageHandler;
import com.mrjaffesclass.apcs.messenger.Messenger;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Label;
import javax.swing.JFrame;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;

/**
 *
 * @author srios
 */
public class View extends JFrame implements MessageHandler {
    /*Variables for screen size, button size, # of buttons, board size
    *messenger, jPanels, lives and score variables. 
    */
    private final int numOfButtons = 8;
    private final int BUTTON_SIZE = 50;
    private final int BOARD_SIZE = BUTTON_SIZE * numOfButtons;
    private final Messenger messenger;
    private final Squares[][] squares = new Squares[numOfButtons][numOfButtons];
    private final JPanel panel = new JPanel();
    private final JPanel panel2 = new JPanel();
    private int lives = 3;
    private int score = 0;
    private final JLabel livesLabel = new JLabel(lives + "");
    private final JLabel scoreLabel = new JLabel(score + "");
    //makes the grids for the board
    GridLayout layout1 = new GridLayout(0, 2);
    GridLayout layout2 = new GridLayout(numOfButtons, numOfButtons);

    public View(Messenger messenger) {
        super("WATCH YOUR STEP!!");
        this.messenger = messenger;
        this.setResizable(false);
        this.setLocationRelativeTo(null);

        panel.setLayout(layout1);
        panel.setLayout(layout2);
        panel.setPreferredSize(new Dimension(BOARD_SIZE, BOARD_SIZE));
        addSquares();

        panel2.add(new Label("Score:"));
        panel2.add(scoreLabel);
        panel2.add(new Label("Remaining Lives:"));
        panel2.add(livesLabel);

        setDefaultCloseOperation(EXIT_ON_CLOSE);
        add(panel, BorderLayout.NORTH);
        add(new JSeparator(), BorderLayout.CENTER);
        add(panel2, BorderLayout.SOUTH);
        pack();

        setVisible(true);
        /*Titles game, adds messenger, make size, label panels, add exit,
        *places panels at certain locations.
        */
    }

    public void init() {
        messenger.subscribe("Model:ScoreAPoint", this);
        messenger.subscribe("Model:LoseALife", this);
        messenger.subscribe("GameOver:Reset", this);
        //subscribes to Model and GameOver.
    }

    private void addSquares() {
        int counter = 0;
        for (int y = 0; y < numOfButtons; y++) {
            for (int x = 0; x < numOfButtons; x++) {
                squares[x][y] = new Squares(x, y, messenger);
                panel.add(squares[x][y].getButton());
                //counts the squares, adds them by x and y coordinates.
            }
        }
    }

    private void loseALife() {
        lives--;
        livesLabel.setText(lives + "");
        if (lives <= 0) {
            messenger.notify("View:SendScore", score);
            messenger.notify("View:EndGame", false);
            
            setVisible(false);
            //lives decrement, when lives=0, notify the View.
        }
    }

    private void scoreAPoint() {
        score++;
        scoreLabel.setText(score + "");
        if (score >= 54) {
        messenger.notify("View:SendScore", score);
            messenger.notify("View:EndGame", true);
            /*score increment, notify as long as score is les than 54.
            *send message to View.
            */        
        }
    }

    private void reset() {
        score = 0;
        lives = 3;
        scoreLabel.setText(score + "");
        livesLabel.setText(lives + "");
        setVisible(true);
        //when reset, score and lives equal above^.
    }

    @Override
    public void messageHandler(String messageName, Object messagePayload) {
        switch (messageName) {
            case "Model:LoseALife":
                loseALife();
                break;
            case "Model:ScoreAPoint":
                scoreAPoint();
                break;
            case "GameOver:Reset":
                reset();
                break;
                //messages in handler to Model and GameOver.
        }
    }
}
