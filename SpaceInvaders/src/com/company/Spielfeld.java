package com.company;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Random;

public class Spielfeld extends JPanel implements KeyListener {

    Spieler s;
    ArrayList<Geschoss> geschossArrayList;
    int geschossCounter;
    Robot robot;
    ArrayList<Timer> t;
    boolean timerStopped;
    int count;
    int counterCount;
    int getT;
    boolean durch;
    int anotherCount;
    boolean activateLaser;
    boolean einMal;


    ArrayList<Gegner> gegnerArrayList;


    Spielfeld() {
        this.setFocusable(true);
        this.requestFocusInWindow();
        this.addKeyListener(this);
        this.setBackground(Color.blue);
        s = new Spieler(0, 250, 200, 180);
        geschossArrayList = new ArrayList<>();
        geschossCounter = 0;
        gegnerArrayList = new ArrayList<>();
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }
        timerStopped = true;
        t = new ArrayList<>();
        count = -1;
        activateLaser = true;
        generateGegner();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Gegner h : gegnerArrayList) {
            h.paintComponent(g);
        }
        for (Geschoss h : geschossArrayList) {
            h.paintComponent(g);
        }
        s.paintComponent(g);
    }

    int newCounter = -1;

    void generateGegner() {
        Timer time = new Timer(1500, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fixLag();
                Random rnd = new Random();
                int rndG = rnd.nextInt(50) + 1;
                if (rndG >= 1 && rndG <= 30) {
                    gegnerArrayList.add(new MinionGegner());
                    newCounter++;
                } else if (rndG == 32 || rndG == 31) {
                    gegnerArrayList.add(new MittelGegner());
                    newCounter++;
                } else if (rndG == 40) {
                    gegnerArrayList.add(new BossGegner());
                    newCounter++;
                } else if(rndG == 35 || rndG == 36 || rndG == 37){
                    gegnerArrayList.add(new SpiegelGegner());
                    newCounter++;
                }
                if (!gegnerArrayList.isEmpty()) {
                    gegnerArrayList.get(newCounter).x = Spielfeld.super.getWidth();
                    gegnerArrayList.get(newCounter).y = rnd.nextInt(Spielfeld.super.getHeight());
                    for (Gegner a : gegnerArrayList) {
                        a.x -= 10;
                    }
                }
                repaint();
            }
        });
        time.start();
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
        if (s.y <= -s.height / 4) {
            s.y = -s.height / 4;
        }
        if (s.y >= this.getHeight() - s.height) {
            s.y = (this.getHeight() - s.height);
        }
        if (key == KeyEvent.VK_SPACE) {
            Random rdm = new Random();
            int cases = rdm.nextInt(5) + 1;
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
                case 5:
                    geschossArrayList.add(new PortalGeschoss());
            }
            if (geschossArrayList.get(geschossCounter) instanceof StandardGeschoss) {

                //doStandard((StandardGeschoss) geschossArrayList.get(geschossCounter), geschossCounter);
                doAnything(geschossArrayList.get(geschossCounter), geschossCounter);

            } else if (geschossArrayList.get(geschossCounter) instanceof ExplosivGeschoss) {

                //doExplosiv((ExplosivGeschoss) geschossArrayList.get(geschossCounter), geschossCounter);
                doAnything(geschossArrayList.get(geschossCounter), geschossCounter);

            } else if (geschossArrayList.get(geschossCounter) instanceof LaserGeschoss) {

                doLaser((LaserGeschoss) geschossArrayList.get(geschossCounter), geschossCounter);

            } else if (geschossArrayList.get(geschossCounter) instanceof LahmesGeschoss) {

                //doLahmes((LahmesGeschoss) geschossArrayList.get(geschossCounter), geschossCounter);
                doAnything(geschossArrayList.get(geschossCounter), geschossCounter);

            } else if (geschossArrayList.get(geschossCounter) instanceof PortalGeschoss) {
                //doPortal((PortalGeschoss) geschossArrayList.get(geschossCounter), geschossCounter);
                doAnything(geschossArrayList.get(geschossCounter), geschossCounter);
            }
            geschossCounter++;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void doAnything(Geschoss sg, int index) {
        sg.y = s.y + s.height / 2;
        sg.x = s.width;

        t.add(new Timer(125, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fixLag();
                for(int i = 0; i < newCounter; i++) {
                    if(sg.x < gegnerArrayList.get(i).x + gegnerArrayList.get(i).width && sg.x + sg.width > gegnerArrayList.get(i).x && sg.y < gegnerArrayList.get(i).y + gegnerArrayList.get(i).height && sg.y + sg.height > gegnerArrayList.get(i).y) {
                        gegnerArrayList.get(i).leben = gegnerArrayList.get(i).leben - sg.dmg;
                        System.out.println(gegnerArrayList.get(i).leben - sg.dmg);
                        sg.color = new Color(0, 0, 0, 0);
                        sg.dmg = 0;
                        if (gegnerArrayList.get(i).leben <= 0) {
                            gegnerArrayList.remove(gegnerArrayList.get(i));
                            newCounter--;
                        }
                    }
                }
                sg.x += 10;
                repaint();

            }
        }));
        count++;
        t.get(count).start();
    }

    public void doStandard(StandardGeschoss sg, int index) {

        sg.y = s.y + s.height / 2;
        sg.x = s.width;

        t.add(new Timer(125, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fixLag();
                sg.x += 10;

                for(int i = 0; i < gegnerArrayList.size(); i++) {

                    if(sg.x < gegnerArrayList.get(i).x + gegnerArrayList.get(i).width && sg.x + sg.width > gegnerArrayList.get(i).x && sg.y < gegnerArrayList.get(i).y + gegnerArrayList.get(i).height && sg.y + sg.height > gegnerArrayList.get(i).y && !einMal) {

                        gegnerArrayList.get(i).leben = gegnerArrayList.get(i).leben - sg.dmg;
                        System.out.println(gegnerArrayList.get(i).leben - sg.dmg);

                        geschossArrayList.remove(geschossArrayList.get(geschossArrayList.indexOf(geschossArrayList.get(index))));
                        geschossCounter--;
                        einMal = true;
                        if (gegnerArrayList.get(i).leben <= 0) {
                            gegnerArrayList.remove(gegnerArrayList.get(gegnerArrayList.indexOf(gegnerArrayList.get(i))));
                            newCounter--;
                        }

                    }
                }
                repaint();

            }
        }));
        count++;
        einMal=false;
        t.get(count).start();

    }

    public void doExplosiv(ExplosivGeschoss eg, int index) {

        eg.y = s.y + s.height / 2;
        eg.x = s.width;

        t.add(new Timer(125, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fixLag();
                eg.x += 10;
                for(int i = 0; i < gegnerArrayList.size(); i++) {

                    if(eg.x < gegnerArrayList.get(i).x + gegnerArrayList.get(i).width && eg.x + eg.width > gegnerArrayList.get(i).x && eg.y < gegnerArrayList.get(i).y + gegnerArrayList.get(i).height && eg.y + eg.height > gegnerArrayList.get(i).y && !einMal) {
                        geschossArrayList.remove(geschossArrayList.get(geschossArrayList.indexOf(geschossArrayList.get(index))));
                        geschossCounter--;
                        einMal=true;
                        gegnerArrayList.get(i).leben = gegnerArrayList.get(i).leben - eg.dmg;
                        System.out.println(gegnerArrayList.get(i).leben - eg.dmg);
                        if (gegnerArrayList.get(i).leben <= 0) {
                            gegnerArrayList.remove(gegnerArrayList.get(gegnerArrayList.indexOf(gegnerArrayList.get(i))));
                            newCounter--;
                        }

                    }

                }
                repaint();
            }
        }));
        einMal=false;
        count++;
        t.get(count).start();

    }

    public void doPortal(PortalGeschoss pg, int index) {
        pg.y = s.y + s.height / 2;
        pg.x = s.width;


        t.add(new Timer(125, new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        fixLag();
                        pg.x += 10;
                        for(int i = 0; i < gegnerArrayList.size(); i++) {

                            if(pg.x < gegnerArrayList.get(i).x + gegnerArrayList.get(i).width && pg.x + pg.width > gegnerArrayList.get(i).x && pg.y < gegnerArrayList.get(i).y + gegnerArrayList.get(i).height && pg.y + pg.height > gegnerArrayList.get(i).y  && !einMal) {
                                einMal=true;
                                geschossArrayList.remove(geschossArrayList.get(geschossArrayList.indexOf(geschossArrayList.get(index))));
                                geschossCounter--;
                                gegnerArrayList.get(i).leben = gegnerArrayList.get(i).leben - pg.dmg;
                                System.out.println(gegnerArrayList.get(i).leben - pg.dmg);
                                if (gegnerArrayList.get(i).leben <=0) {
                                    gegnerArrayList.remove(gegnerArrayList.get(gegnerArrayList.indexOf(gegnerArrayList.get(i))));
                                    newCounter--;
                                }

                            }

                        }
                        repaint();

                    }
                }
                )
        );
        einMal=false;
        count++;
        t.get(count).start();
    }


    public void doLaser(LaserGeschoss lg, int index) {
        durch = false;
        anotherCount = 0;
        lg.y = s.y + s.height / 2;
        lg.x = s.width;
        counterCount = 0;

        if (activateLaser) {
            t.add(new Timer(20, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    fixLag();
                    activateLaser = false;
                    lg.width += 10;
                    lg.y = s.y + s.height / 2;
                    int max = (Spielfeld.super.getWidth() / 10) + 10;
                    if (counterCount >= max && !durch) {
                        durch = true;
                    } else if (durch) {
                        anotherCount++;
                        if (anotherCount >= 5000 / 20) {
                            lg.color = new Color(0, 0, 0, 0);
                            stopTimer(t.get(getT));
                            activateLaser = true;
                        }
                    }
                    for(int i = 0; i < gegnerArrayList.size(); i++) {

                        if(lg.x < gegnerArrayList.get(i).x + gegnerArrayList.get(i).width && lg.x + lg.width > gegnerArrayList.get(i).x && lg.y < gegnerArrayList.get(i).y + gegnerArrayList.get(i).height && lg.y + lg.height > gegnerArrayList.get(i).y) {
                            gegnerArrayList.get(i).leben = gegnerArrayList.get(i).leben - lg.dmg;
                            System.out.println(gegnerArrayList.get(i).leben - lg.dmg);
                            if (gegnerArrayList.get(i).leben <= 0) {
                                gegnerArrayList.remove(gegnerArrayList.get(gegnerArrayList.indexOf(gegnerArrayList.get(i))));
                                newCounter--;
                            }

                        }

                    }
                    repaint();
                    counterCount++;

                }
            }));
            count++;
            t.get(count).start();
            getT = count;
        }


    }

    public void doLahmes(LahmesGeschoss lhg, int index) {

        lhg.y = s.y + s.height / 2;
        lhg.x = s.width;

        t.add(new Timer(125, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fixLag();
                lhg.x += 10;
                for (int i = 0; i < gegnerArrayList.size(); i++) {
                    if (lhg.x < gegnerArrayList.get(i).x + gegnerArrayList.get(i).width && lhg.x + lhg.width > gegnerArrayList.get(i).x && lhg.y < gegnerArrayList.get(i).y + gegnerArrayList.get(i).height && lhg.y + lhg.height > gegnerArrayList.get(i).y&&!einMal) {
                        einMal=true;
                        geschossArrayList.remove(geschossArrayList.get(geschossArrayList.indexOf(geschossArrayList.get(index))));
                        geschossCounter--;
                        gegnerArrayList.get(i).leben = gegnerArrayList.get(i).leben - lhg.dmg;
                        if (gegnerArrayList.get(i).leben <= 0) {
                            gegnerArrayList.remove(gegnerArrayList.get(gegnerArrayList.indexOf(gegnerArrayList.get(i))));
                            newCounter--;
                        }
                    }
                }
                repaint();
            }

        }));
        einMal=false;
        count++;
        t.get(count).start();

    }


    public void fixLag() {
        PointerInfo pi = MouseInfo.getPointerInfo();
        Point p = pi.getLocation();
        robot.mouseMove((int) p.getX(), (int) p.getY());
    }

    public void stopTimer(Timer timer) {
        timer.stop();
        timerStopped = false;
    }

}


