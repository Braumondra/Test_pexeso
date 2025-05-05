package com.example.testpexeso;

import javafx.animation.PauseTransition;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;

public class HelloController {
    @FXML
    private TextField Jmeno1;
    @FXML
    private TextField Jmeno2;


    @FXML
    private Label Pexeso;
    @FXML
    private Label Hrac1;
    @FXML
    private Label Hrac2;
    @FXML
    private Label Rozhodnuti;
    @FXML
    private GridPane gridpane;

    private int skore1=0;
    private int skore2=0;
    private int currentplayer=1;
    private Card firstcard=null;
    private Card secondcard=null;
    private boolean canFlip=true;
    private String jmenoprohrace=null;
    private String jmenoprohrace2=null;
    private ArrayList <Card> cards = new ArrayList<>() ;
    public void generate(){
        for (int i=0;i<8;i++){
            cards.add(new Card(i));
            cards.add(new Card(i));
        }
    }
    private void displayCards () {
        gridpane.getChildren().clear();
        int index = 0;
        for (int row=0; row<4; row++) {
             for (int col =0; col<4; col++) {
                 Card card=cards.get(index++) ;
                 Button btn=card.getButton() ;
                 btn.setOnAction(e->handleCardClick (card)) ;
                 gridpane.add(btn,col,row) ;
                 }
             }
         }

    private void handleCardClick(Card card) {
        if (card.isMatched()||!canFlip||card.getButton().getText()!="?"){
            return;
        }
        card.flip();

        if (firstcard==null) {
            firstcard=card;
        }
        else{
            secondcard=card;
            canFlip=false;
            PauseTransition pause = new PauseTransition(Duration.seconds(1) ) ;
            pause.setOnFinished (e->checkMatch()) ;
            pause.play () ;
        }
    }

    private void checkMatch() {
        if (firstcard.getId() == secondcard.getId()) {
            firstcard.setMatched(true);
            secondcard.setMatched(true);
            if (currentplayer == 1) {
                skore1++;
                if (jmenoprohrace == null) {
                    Hrac1.setText("Hrac1: " + skore1);
                } else {
                    Hrac1.setText(jmenoprohrace + ": " + skore1);
                }
            } else {
                skore2++;
                if (jmenoprohrace2 == null) {
                    Hrac2.setText("Hrac2: " + skore2);
                } else {
                    Hrac2.setText(jmenoprohrace2 + ": " + skore2);
                }
            }
        } else {
            firstcard.fliBack();
            secondcard.fliBack();
            currentplayer = (currentplayer == 1) ? 2 : 1;
            Pexeso.setText("Pexeso Hraje: Hráč " + currentplayer);
        }
        firstcard = null;
        secondcard = null;
        canFlip = true;
        if (Gameover()) {
            if (skore1 == skore2) {
                Rozhodnuti.setText("Remíza");
            }
            if (skore2 < skore1) {
                if (jmenoprohrace == null) {
                    Rozhodnuti.setText("Vyhrál hráč 1");
                } else {
                    Rozhodnuti.setText("Vyhrál " + jmenoprohrace);
                }
            }
            if (skore1 < skore2) {
                if (jmenoprohrace2 == null) {
                    Rozhodnuti.setText("Vyhrál hrač 2");
                } else {
                    Rozhodnuti.setText("Vyhrál " + jmenoprohrace2);
                }
            }
        }
    }

    private boolean Gameover() {
        for (Card card : cards) {
            if (!card.isMatched()) {
                return false;
            }
        }
        return true;
    }

    public void initialize () {
        generate();
        Collections.shuffle(cards);
        displayCards();

    }


    public void datjmeno1(ActionEvent actionEvent) {
        jmenoprohrace=Jmeno1.getText();
        Hrac1.setText( jmenoprohrace+":");
    }

    public void datjmeno2(ActionEvent actionEvent) {
        jmenoprohrace2=Jmeno2.getText();
        Hrac2.setText( jmenoprohrace2+":");
    }
}