package com.company;

import java.awt.*;

abstract class Gegner extends Graphics {

    int bewegung;
    int leben;
    int schaden;

    public Gegner(int bewegung, int leben, int schaden) {
        this.bewegung = bewegung;
        this.leben = leben;
        this.schaden = schaden;
    }

    public void paintComponent(Graphics g) {
        g.setColor(Color.black);
        g.fillRect(0,0,100,100);
    }


    public int getLeben() {
        return leben;
    }

    public void setLeben(int leben) {
        this.leben = leben;
    }

    public int getSchaden() {
        return schaden;
    }

    public void setSchaden(int schaden) {
        this.schaden = schaden;
    }

    public int getBewegung() {
        return bewegung;
    }

    public void setBewegung(int bewegung) {
        this.bewegung = bewegung;
    }




}
