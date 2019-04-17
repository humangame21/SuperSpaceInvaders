package com.company;

import java.util.ArrayList;
import java.util.List;

public class Items {

    List<Geschoss> item = new ArrayList<>();


    public Items() {
        for (int i = 0; i < 25; i++) {
            item.add(new ExplosivGeschoss());
            item.add(new LaserGeschoss());
        }
        for(int j=0; j<9; j++) {
            item.add(new LahmesGeschoss());
        }
        for(int w=0; w<50; w++) {
            item.add(new StandardGeschoss());
        }
        for(int p=0; p<20; p++){
            item.add(new PortalGeschoss());
        }


    }
}