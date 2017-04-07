package com.bamgmk.demo;

/**
 * Created by marting on 29.03.2017.
 */

public class AdapterItem {
    public  String name;
    public  int drawableId;
    public PlayerCharacter pc;

    AdapterItem(String name, int drawableId,PlayerCharacter pc) {
        this.name = name;
        this.drawableId = drawableId;
        this.pc = pc;
    }
}
