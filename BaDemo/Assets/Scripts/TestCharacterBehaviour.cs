﻿using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TestCharacterBehaviour : MonoBehaviour {
	public float speed;
	public float minDistance;
	public Character testChar = null;

	public Animator anim;

	private List<GameObject> path;
	private GameObject currentTile;
	private GameObject nextTile;
	private bool moving= false;

	void Start () {
		anim = GetComponent<Animator> ();
		path = new List<GameObject> ();
		anim.enabled = true;
	}
	public void Walk (List<GameObject> path){
		
		this.path = path;
		Debug.Log (anim == null);
		anim.SetBool ("moving", true);
		currentTile = path [0];
		nextTile = path [1];
		path.Remove (path [0]);
		moving = true;
		Gridmanager.instance.inAnimation = true;
		if (nextTile.transform.position.x > currentTile.transform.position.x && !testChar.facingRight || nextTile.transform.position.x<currentTile.transform.position.x && testChar.facingRight) {
			flip ();
		}
	}
	public void setPosWithOffset (Vector3 pos){
		transform.position= pos + testChar.offset;		
	}


	void walkNextStep(){
		if (Vector3.Distance (transform.position-testChar.offset, nextTile.transform.position) < minDistance) {
			if (path.Count <= 1) {
				setPosWithOffset (nextTile.transform.position);
				testChar.location = nextTile.GetComponent<TileBehaviour> ().tile.location;
				moving = false;
				anim.SetBool ("moving", false);
				Gridmanager.instance.inAnimation = false;
				nextTile = null;
				currentTile = null;
				path = null;

			} else {
				currentTile = path [0];
				nextTile = path [1];
				if (nextTile.transform.position.x > currentTile.transform.position.x && !testChar.facingRight || nextTile.transform.position.x<currentTile.transform.position.x && testChar.facingRight) {
					flip ();
				}
				testChar.location = currentTile.GetComponent<TileBehaviour> ().tile.location;
				path.Remove (currentTile);
		//		Debug.Log (nextTile.transform.position + " " + currentTile.transform.position);
				this.transform.Translate (new Vector3 ((nextTile.transform.position.x - currentTile.transform.position.x) / speed, (nextTile.transform.position.y - currentTile.transform.position.y) / speed, 0));
			}
		} else {
			this.transform.Translate (new Vector3((nextTile.transform.position.x -currentTile.transform.position.x)/speed,(nextTile.transform.position.y -currentTile.transform.position.y)/speed,0));
		}
	}

	void flip(){
		transform.localScale = new Vector3 (transform.localScale.x * -1, transform.localScale.y, transform.localScale.z);
		testChar.facingRight = !testChar.facingRight;
	}

	// Update is called once per frame
	void Update () {


		if (moving) {
			walkNextStep ();
		}
		else anim.SetTrigger ("attack");
	}
}