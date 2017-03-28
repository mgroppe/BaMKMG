package com.bamgmk.demo;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by marting on 27.03.2017.
 */

public class Fight {
    public GameCharacter[] enemies = new GameCharacter[3];
    public Date timeCreated;


    public Fight (int fighttype, int lvl){
        switch (fighttype) {
            case 0:
                enemies[0] = new GameCharacter(7,1,3,70+lvl*10,2+lvl*3,true,0);
                enemies[1] = new GameCharacter(7,1,3,70+lvl*10,2+lvl*3,true,0);
                enemies[2] = new GameCharacter(6,1,5,52+lvl*8,3+lvl*4,true,1);

                break;
            case 1:
                enemies[0] = new GameCharacter(6,1,5,52+lvl*8,3+lvl*4,true,1);
                enemies[1] = new GameCharacter(6,1,5,52+lvl*8,3+lvl*4,true,1);
                enemies[2] = new GameCharacter(7,1,3,70+lvl*10,2+lvl*3,true,0);
                break;
            case 2:
                break;
                enemies[0] = new GameCharacter(6,1,4,70+lvl*8,3+lvl*3,true,2);
                enemies[1] = new GameCharacter(6,1,4,70+lvl*8,3+lvl*3,true,2);
                enemies[2] = new GameCharacter(5,4,2,40+lvl*6,3+lvl*2,true,3);
            case 3:
                enemies[0] = new GameCharacter(5,4,2,40+lvl*6,3+lvl*2,true,3);
                enemies[1] = new GameCharacter(5,4,2,40+lvl*6,3+lvl*2,true,3);
                enemies[2] = new GameCharacter(6,1,4,70+lvl*8,3+lvl*3,true,2);
                break;
            default :
                break;
        }
        timeCreated = Calendar.getInstance().getTime();
    }
}
