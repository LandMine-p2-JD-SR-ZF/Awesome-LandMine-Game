/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mrjaffesclass.apcs.mvc.template;

import com.mrjaffesclass.apcs.messenger.MessageHandler;
import com.mrjaffesclass.apcs.messenger.Messenger;
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

/**
 *
 * @author srios
 */
public class GameOver extends JFrame implements MessageHandler {
    //Label, button and int variables.
    JLabel winorlose = new JLabel("game");
    JButton button = new JButton("Would you like to play again?");
    int score;
    private final Messenger m;
    /* set messenger to m. (shortcut).
    *now make GameOver class.
    */
    public GameOver(Messenger m) {
        this.m = m;
        setResizable(false);
        this.setLocationRelativeTo(null);
        setPreferredSize(new Dimension(300, 100));
        add(winorlose);
        listener();
        add(button, BorderLayout.SOUTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        pack();
        setVisible(false);
        /*this will make the class stay the same size, give it a size, add
        *add winorlose, then listen for an action to happen. Will put the button
        *at the bottom, and exit programon close.
        */
        
    }

    public void init(){
      //subscribes to the view
        m.subscribe("View:EndGame", this); 
       m.subscribe("View:SendScore", this);
    }
    private void listener() {
    //button becomes listener.
        button.addActionListener(new ActionListener() {

            @Override
            //notify GameOver of action.
            public void actionPerformed(ActionEvent e) {
                m.notify("GameOver:Reset");
                setVisible(false);
            }
        });
    }

    private void appear(boolean state){
        if(state)
            winorlose.setText("CONGRATS, YOU WIN!!! With a final score of : "+score);
        else
            winorlose.setText("*BOOM!!!* SORRY! You lose with a final score of : "+score);
        setVisible(true);
        /* This class above creates two states whether player wins or loses.
        * If a loss, will give lose string with score. Otherwise, win string+score.
        */
    }
    private void setScore(int s){
        score = s;
        
    }
    @Override
    public void messageHandler(String messageName, Object messagePayload) {
            switch(messageName){
                case "View:EndGame": appear((boolean)messagePayload);break;
                case "View:SendScore" : setScore((int)messagePayload);break;
            }
    }

}

