package com.bamgmk.demo;

import android.util.Log;

import com.google.android.gms.maps.model.LatLng;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * Created by marting on 27.03.2017.
 */

public class Fight {
    public List<GameCharacter> enemies = new ArrayList<>();
    public Date timeCreated;
    Random rnd = new Random();
    public GameItem loot;
    public int fightlvl;
    public String difficulty;
    public LatLng position;


    public Fight (int lvl,LatLng ll){
        position = ll;
        difficulty = "normal";
        fightlvl =0;
        int i = rnd.nextInt(20);
        if (i<=8){
            // 2 gegner lvl +1
            int j = rnd.nextInt(20);
            if (j<= 4){
                fightlvl = lvl +1;
                for (int k = 0; k<2;k++){
                    enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));
                }
            }
            else if (j<=9){
                //lvl-1
                fightlvl = lvl ;
                for (int k = 0; k<2;k++){
                    enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));
                }
            }
            else if(j<=11){
                //lvl+1
                fightlvl = lvl +2;
                for (int k = 0; k<2;k++){
                    enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));
                }
            }
            else if(j<=12){
                //lvl+2
                fightlvl = lvl +3;
                for (int k = 0; k<2;k++){
                    enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));
                }
            }
            else if(j<=15){

                //lvl-2
                fightlvl = lvl -1;
                fightlvl = Math.max(fightlvl,1);
                for (int k = 0; k<2;k++){
                    enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));
                }
            }
            else if (j<=17){
                //lvl-3
                fightlvl = lvl -2;
                fightlvl = Math.max(fightlvl,1);
                for (int k = 0; k<2;k++){
                    enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));
                }
            }
            else{
                //lvl-4
                fightlvl = lvl -3;
                fightlvl = Math.max(fightlvl,1);
                for (int k = 0; k<2;k++){
                    enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));
                }
            }

        }


        else if (i<=16){
            // 3 gegner
            int j = rnd.nextInt(20);
            if (j<= 4){
                fightlvl = lvl;
                for (int k = 0; k<3;k++){
                    enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));
                }
            }
            else if (j<=9){
                //lvl-1
                fightlvl = lvl -1;
                fightlvl = Math.max(fightlvl,1);
                for (int k = 0; k<3;k++){
                    enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));
                }
            }
            else if(j<=11){
                //lvl+1
                fightlvl = lvl +1;
                for (int k = 0; k<3;k++){
                    enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));
                }
            }
            else if(j<=12){
                //lvl+2
                fightlvl = lvl +2;
                for (int k = 0; k<3;k++){
                    enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));
                }
            }
            else if(j<=15){
                //lvl-2
                fightlvl = lvl -2;
                fightlvl = Math.max(fightlvl,1);
                for (int k = 0; k<3;k++){
                    enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));
                }
            }
            else if (j<=17){
                //lvl-3
                fightlvl = lvl -3;
                fightlvl = Math.max(fightlvl,1);
                for (int k = 0; k<3;k++){
                    enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));
                }
            }
            else{
                //lvl-4
                fightlvl = lvl -4;
                fightlvl = Math.max(fightlvl,1);
                for (int k = 0; k<3;k++){
                    enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));
                }
            }

        }
        else {
            int j = rnd.nextInt(20);
            if (j<= 4){
                fightlvl = lvl +2;

                enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));

            }
            else if (j<=9){
                //lvl-1
                fightlvl = lvl +1 ;

                enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));

            }
            else if(j<=11){
                //lvl+1
                fightlvl = lvl +3;

                enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));

            }
            else if(j<=12){
                //lvl+2
                fightlvl = lvl +4;

                enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));

            }
            else if(j<=15){
                //lvl-2
                fightlvl = lvl;

                enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));

            }
            else if (j<=17){
                //lvl-3
                fightlvl = lvl -1;
                fightlvl = Math.max(fightlvl,1);

                enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));

            }
            else{
                //lvl-4
                fightlvl = lvl -2;
                fightlvl = Math.max(fightlvl,1);
                enemies.add(GameCharacter.createEnemy(fightlvl,rnd.nextInt(4)));
            }
            // 1 gegner lvl +2
        }
        Log.d("test","fight strength formula: "+(enemies.size() + fightlvl - lvl));

        if(enemies.size()+fightlvl - lvl >= 4){
            difficulty = "hard";
        }
        if(enemies.size()+fightlvl - lvl <=1){
            difficulty ="easy";
        }


        //roll item type (0-2)
        //itemlvl = fightlvl
        // rarity chances : 60% normal,30%rare,10%epic
        // average hp on armor shoe : 25 % hp  general stats : 50 / 100/ 200 % of lvl 0/1/2 range
        //
        i = rnd.nextInt(3);
        int j = rnd.nextInt(10);
        if(j<=5){
            loot = GameItem.createItem(i,0,fightlvl,enemies.get(0).model);
        }
        else if (j<=8){
            loot = GameItem.createItem(i,1,fightlvl,enemies.get(0).model);
        }
        else{
            loot = GameItem.createItem(i,2,fightlvl,enemies.get(0).model);
        }


        timeCreated = Calendar.getInstance().getTime();
    }

    int calculateXpReward(){
        int result = 10;
        result =(int) (result * Math.pow(1.25,fightlvl-1));
        result *= enemies.size();
        return result;
    }
}
