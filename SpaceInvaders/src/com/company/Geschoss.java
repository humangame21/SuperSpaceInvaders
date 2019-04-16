package com.company;

import java.awt.*;

abstract public class Geschoss extends Graphics {

    int geschwindigkeit;
    int dmg;
    int x;
    int y;
    boolean pierce;
    boolean explosion;
    Color color;
    Geschoss() {

    }

    public void paintComponent(Graphics g) {

    }
}
