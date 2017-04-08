using System.Collections;
using System;
using System.Collections.Generic;
using UnityEngine;

[Serializable]
public class CharData {

	public int size;
	public int[] initiative;
	public int[] attackRange;
	public int[] movement;
	public int[] maxHealth;
	public int[] maxdamage;
	public int[] mindamage;
	public bool[] isEnemy;
	public int[] model;

	// public Character (int initiative, int attackRange, int movement, int maxHealth, int damage, Point location, bool isEnemy, GameObject parent)
	public Character generateCharacter(int pos, Point location,GameObject parent){
		return new Character(initiative[pos],attackRange[pos],movement[pos],maxHealth[pos],mindamage[pos],maxdamage[pos],location,isEnemy[pos],parent);
	}
}
