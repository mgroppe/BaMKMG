package com.bamgmk.demo;

/**
 * Created by marting on 16.03.2017.
 */

public class Item {
    public int dmg;
    public int hp;
    public boolean equipped;
    public boolean isWeapon;
    public Item (int dmg, int hp, boolean isWeapon){
        this.dmg=dmg;
        this.hp=hp;
        this.isWeapon=isWeapon;
        equipped=false;
    }

    public void addStats(PlayerCharacter playerCharacter) {
        playerCharacter.maxHealth+=hp;
        playerCharacter.damage+=dmg;
        equipped = true;
    }

    public void removeStats(PlayerCharacter playerCharacter) {
        playerCharacter.maxHealth-=hp;
        playerCharacter.damage-=dmg;
        equipped = false;
    }
}
