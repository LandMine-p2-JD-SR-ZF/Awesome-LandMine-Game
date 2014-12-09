/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrjaffesclass.apcs.mvc.template;

import com.mrjaffesclass.apcs.messenger.MessageHandler;
import com.mrjaffesclass.apcs.messenger.Messenger;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JToggleButton;
/*
*@author srios
*/

public class Squares implements MessageHandler {
    //Variables to be implemeted into Squars class.
    private final Messenger messenger;
    private final int x, y;
    private final JToggleButton button;
    private boolean selected;

    @SuppressWarnings("LeakingThisInConstructor")
    Squares(int x, int y, Messenger messenger) {
        this.messenger = messenger;
        this.x = x;
        this.y = y;
        button = new JToggleButton();
        listener();
        messenger.subscribe("GameOver:Reset", this);
        //Messages subscribed to GameOver class and messenger.
    }

    private void listener() {
        button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //Execute when button is pressed
                if (!selected) {
                    messenger.notify("Square:position", new Dimension(x, y), true);
                }
                selected = true;
                button.setSelected(true);
                /*Listens for an action (click  of square). Selects button
                 *disables ability to be clicked again.
                */
                
            }
        });
    }

    public JToggleButton getButton() {
        return button;
        //returns button when clicked.
    }

    private void reset(){
        selected=false;
        button.setSelected(false);
        //Resets pushed buttons.
    }
    @Override
    public void messageHandler(String messageName, Object messagePayload) {
        switch (messageName) {
            case "GameOver:Reset":
                reset();
                break;
                //message payoad to reset in GameOver.

        }
    }
}
