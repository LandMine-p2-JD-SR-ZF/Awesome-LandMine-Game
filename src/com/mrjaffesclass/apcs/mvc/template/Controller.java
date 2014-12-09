package com.mrjaffesclass.apcs.mvc.template;

import com.mrjaffesclass.apcs.messenger.*;

/**
 *
 * The Controller is the master of the App you're writing. It instantiates the
 * view and the model, receives messages from the View in response to user
 * interface (UI) actions like clicking a button, changing an input field, etc.
 * It also sends and receives messages to the Model to communicate changes
 * required and changes made to the Model variables.
 *
 * @author zabdiel
 * @version 1.0
 */
public class Controller implements MessageHandler {

    private final Messenger messenger;

    /**
     *messenger is the name of our messaging class. It will be implemented 
     * in all of the necessary classes.
     */
    public Controller() {
        // Create the local messaging class
        messenger = new Messenger();

        // Making the View
        View view = new View(messenger);    
        // This creates our view and gives it a way to talk.
        view.init();
        view.setVisible(true);
        //Make it visable or else.

        // Making the Model
        Model model = new Model(messenger);  
        // This creates our model and gives it a voice.
        model.init();
        
        GameOver gameOver = new GameOver(messenger);
        //GameOver class important in what happens when you win or lose.
        gameOver.init();
    }

    
    public void init() {

    }

    @Override
    public void messageHandler(String messageName, Object messagePayload) {

    }

    /**
     * Program entry -- main is called when the program starts
     *
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Controller app = new Controller();  // Create our controller...
        app.init();                         // ...and init it too
    }

}
