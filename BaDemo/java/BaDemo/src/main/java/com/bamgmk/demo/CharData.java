package com.bamgmk.demo;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by marting on 16.03.2017.
 */

public class CharData {
    public int size;
    public List<Integer> initiative;
    public List<Integer> attackRange;
    public List<Integer> movement;
    public List<Integer> maxHealth;
    public List<Integer> damage;
    public List<Boolean> isEnemy;
    public List<Integer> model;

    public CharData(){
        size =0;
        initiative = new LinkedList<Integer>();
        attackRange = new LinkedList<Integer>();
        movement = new LinkedList<Integer>();
        maxHealth = new LinkedList<Integer>();
        damage = new LinkedList<Integer>();
        isEnemy = new LinkedList<Boolean>();
        model = new LinkedList<Integer>();
    }

    public void addChar (int initiative, int attackrange, int movement, int maxhealth,int damage, boolean isEnemy, int model){
        size++;
        this.initiative.add(initiative);
        this.attackRange.add(attackrange);
        this.movement.add(movement);
        this.maxHealth.add(maxhealth);
        this.damage.add(damage);
        this.isEnemy.add(isEnemy);
        this.model.add(model);
    }

    public void addChar(GameCharacter c){
        size++;
        this.initiative.add(c.initiative);
        this.attackRange.add(c.attackRange);
        this.movement.add(c.movement);
        this.maxHealth.add(c.maxHealth);
        this.damage.add(c.damage);
        this.isEnemy.add(c.isEnemy);
        this.model.add(c.model);
    }


}
