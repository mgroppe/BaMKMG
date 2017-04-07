package com.bamgmk.demo;

import java.io.Serializable;

/**
 * Created by marting on 16.03.2017.
 */

public class GameCharacter implements Serializable {
    public int initiative;
    public int attackRange;
    public int movement;
    public int maxHealth;
    public int mindamage;
    public int maxdamage;
    public boolean isEnemy;
    public int model;

    public GameCharacter(int initiative, int attackRange, int movement, int maxHealth, int mindamage, int maxdamage, boolean isEnemy, int model) {
        this.initiative = initiative;
        this.attackRange = attackRange;
        this.movement = movement;
        this.maxHealth = maxHealth;
        this.mindamage = mindamage;
        this.maxdamage= maxdamage;
        this.isEnemy = isEnemy;
        this.model = model;
    }

    public static GameCharacter createEnemy (int lvl, int type){
        GameCharacter enemy = new GameCharacter(0,0,0,0,0,0,true,0);
        switch (type){
            case 0:
                enemy = new GameCharacter(50,1,4,500,65,85,true,0);
                break;
            case 1:
                enemy = new GameCharacter(55,1,4,400,100,150,true,1);
                break;
            case 2:
                enemy =new  GameCharacter(75,1,6,360,50,150,true,2);
                break;
            case 3:
                enemy = new GameCharacter(40,6,3,325,75,95,true,3);
                break;
        }
        enemy.multiplyStats(Math.pow(1.15,lvl-1));
        if (lvl >= 3){
            enemy.multiplyStats(1.2);
            if(lvl>=8)
                enemy.multiplyStats(1.25);
        }
        return enemy;
    }
    public static GameCharacter createSolo(int lvl, int type){
        GameCharacter enemy = new GameCharacter(0,0,0,0,0,0,true,0);
        switch (type){
            case 0:
                enemy = new GameCharacter(50,1,4,500,65,85,true,4);
                break;
            case 1:
                enemy = new GameCharacter(55,1,4,400,100,150,true,5);
                break;
            case 2:
                enemy =new  GameCharacter(75,1,6,360,50,150,true,6);
                break;
            case 3:
                enemy = new GameCharacter(40,6,3,325,75,95,true,7);
                break;
        }
        enemy.multiplyStats(Math.pow(1.15,lvl-1));
        if (lvl >= 3){
            enemy.multiplyStats(1.2);
            if(lvl>=8)
                enemy.multiplyStats(1.25);
        }
        return enemy;
    }

    public void multiplyStats(double multiplier){
        mindamage *= multiplier;
        maxdamage *= multiplier;
        maxHealth *= multiplier;
        initiative *= multiplier;
    }

    public String getTypeString(){
        switch (model){
            case 0:
                return "Wikinger";
            case 1:
                return "Berserker";
            case 2:
                return "Schakalskrieger";
            case 3:
                return "Priesterin des Ra";
            case 4:
                return "Wikinger-Boss";
            case 5:
                return "Berserker-Boss";
            case 6:
                return "Schakalskrieger-Boss";
            case 7:
                return "Priesterin-Boss";
        }
        return "";
    }

    public int getWeaponImageId(){
        switch (model){
            case 0:
                return R.drawable.weapon_morningstar;
            case 1:
                return R.drawable.weapon_axe;
            case 2:
                return R.drawable.weapon_khopesh;
            case 3:
                return R.drawable.weapon_staff;

            default:
                throw new RuntimeException("no drawable found");
        }
    }

    public int getMapImageId(){
        switch (model){
            case 0:
                return R.drawable.token_small_morningstar;
            case 1:
                return R.drawable.token_small_berserker;
            case 2:
                return R.drawable.token_small_anubis_warrior;
            case 3:
                return R.drawable.token_small_priestess;
            case 4:
                return R.drawable.token_small_morningstar;
            case 5:
                return R.drawable.token_small_berserker;
            case 6:
                return R.drawable.token_small_anubis_warrior;
            case 7:
                return R.drawable.token_small_priestess;
            default:
                throw new RuntimeException("no drawable found");
        }
    }

    public int getImageId(){
        switch (model){
            case 0:
                return R.drawable.token_morningstar;
            case 1:
                return R.drawable.token_berserker;
            case 2:
                return R.drawable.token_anubis_warrior;
            case 3:
                return R.drawable.token_priestess;

            default:
                throw new RuntimeException("no drawable found");
        }
    }
    public int getGreyedOutImageId(){
        switch (model){
            case 0:
                return R.drawable.token_gray_morningstar;
            case 1:
                return R.drawable.token_gray_berserker;
            case 2:
                return R.drawable.token_gray_anubis;
            case 3:
                return R.drawable.token_gray_priestess;

            default:
                throw new RuntimeException("no drawable found");
        }
    }
    public int getSelectedImageId(){
        switch (model){
            case 0:
                return R.drawable.token_selected_morningstar;
            case 1:
                return R.drawable.token_selected_berserker;
            case 2:
                return R.drawable.token_selected_anubis;
            case 3:
                return R.drawable.token_selected_priestess;

            default:
                throw new RuntimeException("no drawable found");
        }
    }
}
