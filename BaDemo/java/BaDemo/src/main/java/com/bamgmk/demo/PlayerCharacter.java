package com.bamgmk.demo;

import android.content.ClipData;

/**
 * Created by marting on 16.03.2017.
 */

public class PlayerCharacter extends GameCharacter {
    public Item armor;
    public Item weapon;
    public int hpGrowth;
    public int dmgGrowth;
    public int lvl;
    public int xp;
    public int xpForNextLvl;

    public PlayerCharacter(int initiative, int attackRange, int movement, int maxHealth, int damage, boolean isEnemy, int model) {
        super(initiative, attackRange, movement, maxHealth, damage, isEnemy, model);
    }

    public PlayerCharacter(int initiative, int attackRange, int movement, int maxHealth, int damage, boolean isEnemy, int model, int hpGrowth, int dmgGrowth, int lvl, int xp) {
        super(initiative, attackRange, movement, maxHealth, damage, isEnemy, model);
        this.hpGrowth = hpGrowth;
        this.dmgGrowth = dmgGrowth;
        this.lvl = lvl;
        this.xp = xp;
        xpForNextLvl = 100;
        armor = null;
        weapon = null;
    }

    public void equip(Item item){
        if (item.isWeapon){
            if (weapon != null)
                unequip(weapon);
            weapon = item;
            item.addStats(this);
        }
        else{
            if (armor != null)
                unequip(armor);
            armor = item;
            item.addStats(this);
        }
    }

    public void unequip(Item item) {
        item.removeStats(this);
        if(item.isWeapon)
            this.weapon = null;
        else this.armor = null;
    }

    public void addxp(int amount){
        xp += amount;
        while(xp >= xpForNextLvl)
            lvlup();
    }

    private void lvlup() {
        lvl ++;
        maxHealth += hpGrowth;
        damage += dmgGrowth;
        xpForNextLvl *= 2.1;
    }


}
