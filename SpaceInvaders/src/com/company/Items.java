package com.company;

import java.util.ArrayList;
import java.util.List;

public class Items {

    List<Geschoss> item = new ArrayList<>();


    public Items() {
        for (int i = 0; i < 30; i++) {
            item.add(new ExplosivGeschoss());
        }
        for(int j=0; j<10; j++) {
            item.add(new LahmesGeschoss());
        }
        for(int w=0; w<100; w++) {
            item.add(new StandardGeschoss());
        }
        for(int p=0; p<20; p++){
            item.add(new PortalGeschoss());
        }
        item.add(new LaserGeschoss());
        item.add(new LaserGeschoss());
        item.add(new LaserGeschoss());
    }


}