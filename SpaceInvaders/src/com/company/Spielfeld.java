package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.ArrayList;
import java.util.Random;

public class Spielfeld extends JPanel implements KeyListener {

    Spieler s;
    ArrayList<Geschoss> geschossArrayList;
    int geschossCounter;
    Robot robot;

    Spielfeld() {
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.setBackground(Color.blue);
        s = new Spieler(0, 250, 200, 100);
        geschossArrayList = new ArrayList<>();
        geschossCounter = 0;
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        s.paintComponent(g);
        for (Geschoss h : geschossArrayList) {
            h.paintComponent(g);
        }
    }


    @Override
    public void keyTyped(KeyEvent e) {
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        if (key == KeyEvent.VK_W) {
            s.y -= 5;
        } else if (key == KeyEvent.VK_S) {
            s.y += 5;
        }
        if (s.y <= 0) {
            s.y = 0;
        }
        if (s.y >= this.getHeight() - s.height) {
            s.y = (int) (this.getHeight() - s.height);
        }
        if (key == KeyEvent.VK_SPACE) {
            Random rdm = new Random();
            int cases = rdm.nextInt(4) + 1;
            switch (cases) {
                case 1:
                    geschossArrayList.add(new StandardGeschoss());
                    break;
                case 2:
                    geschossArrayList.add(new ExplosivGeschoss());
                    break;
                case 3:
                    geschossArrayList.add(new LaserGeschoss());
                    break;
                case 4:
                    geschossArrayList.add(new LahmesGeschoss());
                    break;
            }
            if(geschossArrayList.get(geschossCounter) instanceof StandardGeschoss) {

                doStandard((StandardGeschoss) geschossArrayList.get(geschossCounter));

            } else if(geschossArrayList.get(geschossCounter) instanceof ExplosivGeschoss) {

                doExplosiv((ExplosivGeschoss) geschossArrayList.get(geschossCounter));

            } else if(geschossArrayList.get(geschossCounter) instanceof LaserGeschoss) {

                doLaser((LaserGeschoss) geschossArrayList.get(geschossCounter));

            } else if(geschossArrayList.get(geschossCounter) instanceof LahmesGeschoss) {

                doLahmes((LahmesGeschoss) geschossArrayList.get(geschossCounter));

            }
            geschossCounter++;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void doStandard(StandardGeschoss sg) {

        sg.y = s.y+40;
        sg.x = s.width;

        Timer t = new Timer(125, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fixLag();
                sg.x += 10;
                repaint();
            }
        });

        t.start();

    }

    public void doExplosiv(ExplosivGeschoss eg) {

        eg.y = s.y+40;
        eg.x = s.width;

        Timer t = new Timer(125, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fixLag();
                eg.x += 10;
                repaint();
            }
        });

        t.start();

    }

    public void doLaser(LaserGeschoss lg) {

        lg.y = s.y+40;
        lg.x = s.width;

        Timer t = new Timer(125, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fixLag();
                lg.laserweite += 10;
                lg.y = s.y+40;

                repaint();
            }
        });

        t.start();

    }

    public void doLahmes(LahmesGeschoss lhg) {

        lhg.y = s.y+40;
        lhg.x = s.width;

        Timer t = new Timer(125, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fixLag();
                lhg.x += 10;
                repaint();
            }
        });

        t.start();

    }

    public void fixLag() {
        PointerInfo pi = MouseInfo.getPointerInfo();
        Point p = pi.getLocation();
        robot.mouseMove((int) p.getX(), (int) p.getY());
    }

    public void stopTimer() {

    }

}


