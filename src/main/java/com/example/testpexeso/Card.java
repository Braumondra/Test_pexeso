package com.example.testpexeso;

import javafx.scene.control.Button;

public class Card {
    private int id;
    private Button button;
    private boolean matched=false;

    public Card(int id) {
        this.id = id;
        this.button = new Button("?");
        this.button.setMinSize(80,80);
    }

    public void flip(){
        button.setText(String.valueOf(id));
    }

    public Button getButton() {
        return button;
    }

    public int getId() {
        return id;
    }

    public boolean isMatched() {
        return matched;
    }

    public void setMatched(boolean matched) {
        this.matched = matched;
    }

    public void fliBack() {
        button.setText("?");;
    }
}
