﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Character  {
	public int initiative;
	public int attackRange;
	public int movement;
	public int maxHealth;
	public int minDamage;
	public int maxDamage;
	public int currentHealth;
	public Point location;
	public int currentInitiative;
	public bool isEnemy;
	public bool facingRight;
	public GameObject parent;
	public bool hasMoved;
	public bool defending;

	public Character (int initiative, int attackRange, int movement, int maxHealth, int minDamage,int maxDamage, Point location, bool isEnemy, GameObject parent){
		this.initiative = initiative;
		this.attackRange = attackRange;
		this.movement = movement;
		this.maxHealth = maxHealth;
		this.minDamage = minDamage;
		this.maxDamage = maxDamage;
		this.currentHealth = maxHealth;
		this.location = location;
		this.isEnemy = isEnemy;
		this.facingRight = true;
		this.parent = parent;
		this.hasMoved = false;
		this.defending = false;
	}

	public void nextAiMove(){
		Gridmanager gridmanager = Gridmanager.instance;
		bool hasAttacked = false;
		foreach (GameObject g in gridmanager.initiative) {
			Character c = g.GetComponent<TestCharacterBehaviour> ().testChar;
			if (!c.isEnemy && Mathf.Max(Mathf.Abs(c.location.x-location.x),Mathf.Abs(c.location.y-location.y)) <= attackRange){
				parent.GetComponent<TestCharacterBehaviour> ().attack (g);
				hasAttacked = true;
				break;
			}
		}
		if (!hasAttacked && !hasMoved) {
			List<GameObject> path = new List<GameObject> ();
			foreach (GameObject g in gridmanager.initiative) {
				Character c = g.GetComponent<TestCharacterBehaviour> ().testChar;
				if (!c.isEnemy) {
					foreach (GameObject g2 in gridmanager.field[c.location.x,c.location.y].GetComponent<TileBehaviour>().tile.getUnblockedOrthogonalNeighbours()) {
						
						List<GameObject> possiblePath = PathFinder.generatePath (gridmanager.field [location.x, location.y], g2);
						if (path.Count == 0 || PathFinder.PathLength(path) > PathFinder.PathLength(possiblePath)) {
							path = possiblePath;
						}
					}
					foreach (GameObject g2 in gridmanager.field[c.location.x,c.location.y].GetComponent<TileBehaviour>().tile.getUnblockedDiagonalNeighbours()) {

						List<GameObject> possiblePath = PathFinder.generatePath (gridmanager.field [location.x, location.y], g2);
						if (path.Count == 0 || PathFinder.PathLength(path) > PathFinder.PathLength(possiblePath)) {
							path = possiblePath;
						}
					}
				}
			}
			if (path.Count > 0) {
				while (PathFinder.PathLength(path) > movement) {
					path.RemoveAt (path.Count - 1);
				}
				parent.GetComponent<TestCharacterBehaviour> ().Walk (path);
			}

		} else {
			if (!hasAttacked) {
				parent.GetComponent<TestCharacterBehaviour> ().defend ();
			}
		}
	
	}
}
