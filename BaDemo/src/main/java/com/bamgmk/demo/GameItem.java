package com.bamgmk.demo;

import java.io.Serializable;

/**
 * Created by marting on 16.03.2017.
 */

public class GameItem implements Serializable {
    public int lvl;
    public String name;
    public int mindmg;
    public int maxdmg;
    public int hp;
    public int movement;
    public int initiative;
    public int attackrange;
    public int type;
    public int weaponType;


    public static final int weapon = 0, armor =1,shoes =2;

    public static final int morningstar =0,berserker =1,anubis = 2, priestess =3;


    public GameItem(int lvl, String name, int mindmg, int maxdmg, int hp, int movement, int initiative, int attackrange, int type, int weaponType) {
        this.lvl = lvl;
        this.name = name;
        this.mindmg = mindmg;
        this.maxdmg = maxdmg;
        this.hp = hp;
        this.movement = movement;
        this.initiative = initiative;
        this.attackrange = attackrange;
        this.type = type;
        this.weaponType = weaponType;
    }

    public GameItem (VicData vd){
        switch (vd.itemType){
            case 0:
                type = weapon;
                weaponType = morningstar;
                break;
            case 1:
                type = weapon;
                weaponType = berserker;
                break;
            case 2:
                type = weapon;
                weaponType = anubis;
                break;
            case 3:
                type = weapon;
                weaponType = priestess;
                break;
            case 4:
                type = armor;
                weaponType = morningstar;
                break;
            case 5:
                type = shoes;
                weaponType = morningstar;
                break;
        }
        //0 hp 1 minAttack 2 maxAttack 3 movement 4 attackrange 5 initiative
        hp = vd.itemStats.get(0);
        mindmg = vd.itemStats.get(1);
        maxdmg = vd.itemStats.get(2);
        movement = vd.itemStats.get(3);
        attackrange = vd.itemStats.get(4);
        initiative = vd.itemStats.get(5);
        lvl = vd.itemStats.get(6);
        name = vd.itemName;



    }



    public void addStats(PlayerCharacter playerCharacter) {
        playerCharacter.maxHealth+=hp;
        playerCharacter.mindamage+=mindmg;
        playerCharacter.maxdamage+=maxdmg;
        playerCharacter.movement += movement;
        playerCharacter.attackRange+=attackrange;
        playerCharacter.initiative += initiative;
    }

    public void removeStats(PlayerCharacter playerCharacter) {
        playerCharacter.maxHealth-=hp;
        playerCharacter.mindamage-=mindmg;
        playerCharacter.maxdamage-=maxdmg;
        playerCharacter.movement -= movement;
        playerCharacter.attackRange-=attackrange;
        playerCharacter.initiative -= initiative;
    }

    public static GameItem createItem(int type,int rarity, int lvl, int model) {
        GameItem item;
        switch (type) {
            case 0: {
                if (model == 0 || model == 4)
                    // enemy = new GameCharacter(50,1,4,500,65,85,true,0);
                    switch (rarity) {
                        case 0:
                            item = new GameItem(lvl, "Morgenstern", 38, 42, 0, 0, 0, 0, GameItem.weapon, GameItem.morningstar);
                            item.lvlupstats(lvl);
                            return item;
                        case 1:
                            item = new GameItem(lvl, "seltener Morgenstern", 65, 85, 0, 0, 0, 0, GameItem.weapon, GameItem.morningstar);
                            item.lvlupstats(lvl);
                            return item;
                        case 2:
                            item = new GameItem(lvl, "epischer Morgenstern", 130, 170, 0, 0, 0, 0, GameItem.weapon, GameItem.morningstar);
                            item.lvlupstats(lvl);
                            return item;
                    }
            }
            if (model == 1 || model == 5) {
                // enemy = new GameCharacter(55,1,4,400,100,150,true,1);
                switch (rarity) {
                    case 0:
                        item = new GameItem(lvl, "Äxte", 50, 75, 0, 0, 0, 0, GameItem.weapon, GameItem.berserker);
                        item.lvlupstats(lvl);
                        return item;
                    case 1:
                        item = new GameItem(lvl, "seltene Äxte", 100, 150, 0, 0, 0, 0, GameItem.weapon, GameItem.berserker);
                        item.lvlupstats(lvl);
                        return item;
                    case 2:
                        item = new GameItem(lvl, "epische Äxte", 200, 300, 0, 0, 0, 0, GameItem.weapon, GameItem.berserker);
                        item.lvlupstats(lvl);
                        return item;
                }
            }
            if (model == 2 || model == 6) {
                //enemy =new  GameCharacter(75,1,6,360,50,150,true,2);
                switch (rarity) {
                    case 0:
                        item = new GameItem(lvl, "Khopesh", 25, 75, 0, 0, 0, 0, GameItem.weapon, GameItem.anubis);
                        item.lvlupstats(lvl);
                        return item;
                    case 1:
                        item = new GameItem(lvl, "seltener Khopesh", 50, 150, 0, 0, 0, 0, GameItem.weapon, GameItem.anubis);
                        item.lvlupstats(lvl);
                        return item;
                    case 2:
                        item = new GameItem(lvl, "epischer Khopesh", 100, 300, 0, 0, 0, 0, GameItem.weapon, GameItem.anubis);
                        item.lvlupstats(lvl);
                        return item;
                }
            }
            if (model == 3 || model == 7){
                //enemy = new GameCharacter(40,6,3,325,75,95,true,3);
                switch (rarity) {
                    case 0:
                        item = new GameItem(lvl, "Stab", 38, 47, 0, 0, 0, 0, GameItem.weapon, GameItem.priestess);
                        item.lvlupstats(lvl);
                        return item;
                    case 1:
                        item = new GameItem(lvl, "seltener Stab", 75, 95, 0, 0, 0, 1, GameItem.weapon, GameItem.priestess);
                        item.lvlupstats(lvl);
                        return item;
                    case 2:
                        item = new GameItem(lvl, "epischer Stab", 75, 95, 0, 0, 0, 2, GameItem.weapon, GameItem.priestess);
                        item.lvlupstats(lvl);
                        return item;
                }
            }




            case 1:
                switch (rarity){
                    case 0:
                        item = new GameItem(lvl,"Rüstung",0,0,200,0,0,0,GameItem.armor,GameItem.priestess);
                        item.lvlupstats(lvl);
                        return item;
                    case 1:
                        item = new GameItem(lvl,"seltene Rüstung",0,0,400,0,0,0,GameItem.armor,GameItem.priestess);
                        item.lvlupstats(lvl);
                        return item;
                    case 2:
                        item = new GameItem(lvl,"epische Rüstung",0,0,800,0,0,0,GameItem.armor,GameItem.priestess);
                        item.lvlupstats(lvl);
                        return item;
                }
                break;
            case 2:
                switch (rarity){
                    case 0:
                        item = new GameItem(lvl,"Schuhe",0,0,25,0,27,0,GameItem.shoes,GameItem.priestess);
                        item.lvlupstats(lvl);
                        return item;
                    case 1:
                        item = new GameItem(lvl,"seltene Schuhe",0,0,100,1,55,0,GameItem.shoes,GameItem.priestess);
                        item.lvlupstats(lvl);
                        return item;
                    case 2:
                        item = new GameItem(lvl,"epische Schuhe",0,0,200,2,110,0,GameItem.shoes,GameItem.priestess);
                        item.lvlupstats(lvl);
                        return item;
                }
                break;
        }
        return null;
    }

    public void lvlupstats(int lvl){
        for (int i =0; i< lvl; i++){
            multiplyStats(Math.random()*0.1+1.1);
        }
    }


    public void multiplyStats(double multiplier){
        mindmg *= multiplier;
        maxdmg *= multiplier;
        hp *= multiplier;
        initiative *= multiplier;
    }

}
