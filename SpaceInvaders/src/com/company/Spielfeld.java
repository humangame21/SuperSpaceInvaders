package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Spielfeld extends JPanel implements KeyListener {

    Spieler s;



    Spielfeld() {
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.setBackground(Color.blue);
        s = new Spieler(250, 0, 100, 200);
        repaint();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        s.paintComponent(g);
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            s.x-=5;
        } else if(key==KeyEvent.VK_S){
            s.x+=5;
        }
        if(s.x <= -50) {
            s.x = -50;
        }
        if(s.x >= this.getHeight() - s.width - s.width*0.5) {
            s.x = (int) (this.getHeight() - s.width - s.width*0.5);
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
