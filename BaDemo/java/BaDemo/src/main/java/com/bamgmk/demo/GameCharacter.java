package com.bamgmk.demo;

/**
 * Created by marting on 16.03.2017.
 */

public class GameCharacter {
    public int initiative;
    public int attackRange;
    public int movement;
    public int maxHealth;
    public int damage;
    public boolean isEnemy;
    public int model;

    public GameCharacter(int initiative, int attackRange, int movement, int maxHealth, int damage, boolean isEnemy, int model) {
        this.initiative = initiative;
        this.attackRange = attackRange;
        this.movement = movement;
        this.maxHealth = maxHealth;
        this.damage = damage;
        this.isEnemy = isEnemy;
        this.model = model;
    }
}
