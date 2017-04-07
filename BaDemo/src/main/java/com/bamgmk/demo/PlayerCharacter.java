package com.bamgmk.demo;

import java.io.Serializable;
import java.util.List;

/**
 * Created by marting on 16.03.2017.
 */

public class PlayerCharacter extends GameCharacter implements Serializable{
    public String name;
    public GameItem armor;
    public GameItem weapon;
    public GameItem shoes;
    public int lvl;
    public int xp;
    public int xpForNextLvl;
    public boolean isActive;

    public PlayerCharacter(int initiative, int attackRange, int movement, int maxHealth, int mindamage, int maxdamage, boolean isEnemy, int model, String name, boolean isActive) {
        super(initiative, attackRange, movement, maxHealth, mindamage, maxdamage, isEnemy, model);
        this.name = name;
        this.isActive = isActive;
        armor = null;
        weapon = null;
        shoes = null;
        lvl = 1;
        xp = 0;
        xpForNextLvl = 100;
    }

    public void equip(GameItem gameItem, List<GameItem> itemList){
        switch (gameItem.type){
            case GameItem.weapon:
                if (weapon != null)
                    unequip(weapon,itemList);
                weapon = gameItem;
                gameItem.addStats(this);
                break;
            case GameItem.armor:
                if (armor != null)
                    unequip(armor,itemList);
                armor = gameItem;
                gameItem.addStats(this);
                break;
            case GameItem.shoes:
                if (shoes != null)
                    unequip(shoes,itemList);
                shoes = gameItem;
                gameItem.addStats(this);
                break;
        }

        itemList.remove(gameItem);


    }

    public void unequip(GameItem gameItem, List<GameItem> itemList) {
        gameItem.removeStats(this);
        switch (gameItem.type){
            case GameItem.armor:
                armor = null;
                break;
            case GameItem.weapon:
                weapon = null;
                break;
            case GameItem.shoes:
                shoes = null;
                break;
        }

        itemList.add(gameItem);
    }

    public void addxp(int amount){
        xp += amount;
        while(xp >= xpForNextLvl)
            lvlup();
    }

    public static PlayerCharacter createCharacter (int type, String name,boolean isActive){
        switch (type){
            case 0:
                return new PlayerCharacter(50,1,4,500,65,85,false,0,name,isActive);
            case 1:
                return new PlayerCharacter(55,1,4,400,100,150,false,1,name,isActive);
            case 2:
                return new PlayerCharacter(75,1,6,360,50,150,false,2,name,isActive);
            case 3:
                return new PlayerCharacter(40,6,3,325,75,95,false,3,name,isActive);

        }
        return null;
    }

    private void lvlup() {
        lvl ++;
        maxHealth += hpWithOutItems()*(Math.random() * 0.1 +0.1);
        mindamage += mindmgWithOutItems()*(Math.random() * 0.1 +0.1);
        maxdamage += maxdmgWithOutItems()*(Math.random() * 0.1 + 0.1);
        initiative += initiativeWithoutItems()*(Math.random() * 0.1 + 0.1);

        xp -= xpForNextLvl;
        xpForNextLvl *= 1.25;
    }

    private double initiativeWithoutItems() {
        double result = initiative;
        if (armor != null)
            result -= armor.initiative;
        if (weapon != null)
            result -= weapon.initiative;
        if (shoes != null)
            result -= shoes.initiative;
        return result;
    }

    private double maxdmgWithOutItems() {
        double result = maxdamage;
        if (armor != null)
            result -= armor.maxdmg;
        if (weapon != null)
            result -= weapon.maxdmg;
        if (shoes != null)
            result -= shoes.maxdmg;
        return result;
    }

    private double mindmgWithOutItems() {
        double result = mindamage;
        if (armor != null)
            result -= armor.mindmg;
        if (weapon != null)
            result -= weapon.mindmg;
        if (shoes != null)
            result -= shoes.mindmg;
        return result;
    }

    private double hpWithOutItems() {
        double result = maxHealth;
        if (armor != null)
            result -= armor.hp;
        if (weapon != null)
            result -= weapon.hp;
        if (shoes != null)
            result -= shoes.hp;

        return result;
    }


}
