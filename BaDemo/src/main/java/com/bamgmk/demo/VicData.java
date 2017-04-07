package com.bamgmk.demo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by marting on 02.04.2017.
 */

public class VicData {
    public List<String> names;
    public List<Integer> models; //0 morning, 1 berserker, 2 anubis, 3 priestess
    public List<Integer> xpCurrent;
    public List<Integer> xpLvlUp;
    public int xpReward;

    public String itemName;
    public int itemType;  //0 weapon morning, 1 weapon berserker, 2 weapon anubis, 3 weapon priestess, 4 armor, 5 boots
    public List<Integer> itemStats; //0 hp 1 minAttack 2 maxAttack 3 movement 4 attackrange 5 initiative 6 lvl
	/*
	0 current xp before fight starts,
	1 xp needed for last lvlup,
	2xp needed for next level,
	3 xpgain for this fight*/



    public VicData (){
        this.names = new ArrayList<>();
        this.models = new ArrayList<>();
        this.xpCurrent = new ArrayList<>();
        this.xpLvlUp = new ArrayList<>();
        this.xpReward = 0;
        this.itemName = "";
        this.itemType = 0;
        this.itemStats = new ArrayList<>();
    }

    public void addChar(PlayerCharacter pc ){
        names.add(pc.name);
        models.add(pc.model);
        xpCurrent.add(pc.xp);
        xpLvlUp.add(pc.xpForNextLvl);
    }



    public void addReward(Fight f){
        itemName = f.loot.name;
        itemStats.add(f.loot.hp);
        itemStats.add(f.loot.mindmg);
        itemStats.add(f.loot.maxdmg);
        itemStats.add(f.loot.movement);
        itemStats.add(f.loot.attackrange);
        itemStats.add(f.loot.initiative);
        itemStats.add(f.loot.lvl);
        switch (f.loot.type){
            case GameItem.armor:
                itemType = 4;
                break;
            case GameItem.weapon:
                itemType = f.loot.weaponType;
                break;
            case GameItem.shoes:
                itemType= 5;
                break;
        }
        xpReward = f.calculateXpReward();

    }
}
