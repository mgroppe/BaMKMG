using System.Collections;
using System;
using System.Collections.Generic;
using UnityEngine;

[Serializable]
public class VicData
{
	public string []names;
	public int []models; //0 morning, 1 berserker, 2 anubis, 3 priestess
	public int []xpCurrent;
	public int []xpLvlUp;
	public int xpReward;

	public string itemName;
	public int itemType;  //0 weapon morning, 1 weapon berserker, 2 weapon anubis, 3 weapon priestess, 4 armor, 5 boots
	public int []itemStats; //0 hp 1 minAttack 2 maxAttack 3 movement 4 attackrange 5 initiative 
	/*
	0 current xp before fight starts, 
	1 xp needed for last lvlup, 
	2xp needed for next level, 
	3 xpgain for this fight*/


	public VicData (string[]names,int[]models,int[]xpCurrent,int[]xpLvlUp,int xpReward,string itemName,int itemType,int[]itemStats){
		this.names = names;
		this.models = models;
		this.xpCurrent = xpCurrent;
		this.xpLvlUp = xpLvlUp;
		this.xpReward = xpReward;
		this.itemName = itemName;
		this.itemType = itemType;
		this.itemStats = itemStats;
	}

}