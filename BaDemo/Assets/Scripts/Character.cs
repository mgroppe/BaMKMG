using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Character  {
	public Vector3 offset;
	public int initiative;
	public int attackRange;
	public int movement;
	public int maxHealth;
	public int currentHealth;
	public Point location;
	public int currentInitiative;
	public bool isEnemy;
	public bool facingRight;

	public Character (int initiative, int attackRange, int movement, int maxHealth, int currentHealth, Point location, int currentInitiative, bool isEnemy, Vector3 offset){
		this.initiative = initiative;
		this.attackRange = attackRange;
		this.movement = movement;
		this.maxHealth = maxHealth;
		this.currentHealth = currentHealth;
		this.location = location;
		this.currentInitiative = currentInitiative;
		this.isEnemy = isEnemy;
		this.facingRight = !isEnemy;
		this.offset = offset;
	}
}
