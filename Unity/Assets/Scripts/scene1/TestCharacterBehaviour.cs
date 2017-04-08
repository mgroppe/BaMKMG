using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class TestCharacterBehaviour : MonoBehaviour {
	public float speed;
	public float minDistance;
	public Character testChar;
	public GameObject projectile;
	public Vector3 offset;

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

	public void refresh(){
		testChar.hasMoved = false;
		testChar.defending = false;
		if (!testChar.isEnemy) {
			Gridmanager.instance.showReachableTiles (this.gameObject);
		}
	}
	public void defend (){
		testChar.defending = true;
		Gridmanager.instance.nextTurn ();
	}
	public void Walk (List<GameObject> path){
		Gridmanager gm = Gridmanager.instance;
		gm.deleteReachableTiles ();
		this.path = path;
//		Debug.Log (anim == null);
		anim.SetBool ("moving", true);
		currentTile = path [0];
		nextTile = path [1];
		path.Remove (path [0]);
		print("curentTilePos: "+currentTile.transform.position.x+", "+currentTile.transform.position.y);
		print("lastTilePos: "+path[path.Count-1].transform.position.x+", "+path[path.Count-1].transform.position.y);
		print (transform.position + " offset: " + offset);
		gm.inAnimation.Add(this.gameObject);
		gm.field [testChar.location.x, testChar.location.y].GetComponent<TileBehaviour> ().tile.isBlocked = false;
		if (nextTile.transform.position.x > currentTile.transform.position.x && !testChar.facingRight || nextTile.transform.position.x<currentTile.transform.position.x && testChar.facingRight) {
			flip ();
		}
		moving = true;
		testChar.hasMoved = true;

	}
	public void setPosWithOffset (Vector3 pos){
		
		transform.position= pos + offset;		
	}


	public void attack (GameObject attackedEnemy){
		anim.SetTrigger ("attack");
		if (attackedEnemy.transform.position.x > this.transform.position.x && !testChar.facingRight || attackedEnemy.transform.position.x < this.transform.position.x && testChar.facingRight)
			flip ();
		if (projectile != null) {
			GameObject newProjectile = (GameObject)Instantiate (projectile);
			Gridmanager.instance.inAnimation.Add (newProjectile);
			newProjectile.GetComponent<ProjectileBehaviour> ().startPos = this.transform.position;
			newProjectile.GetComponent<ProjectileBehaviour> ().goalPos = attackedEnemy.transform.position;
			newProjectile.transform.position = this.transform.position;
			Debug.Log (Gridmanager.instance.inAnimation.Count);
		}

		attackedEnemy.GetComponent<TestCharacterBehaviour> ().takeDmg (Random.Range(testChar.minDamage,testChar.maxDamage+1));
		Gridmanager.instance.nextTurn ();
	}

	public void takeDmg (int dmg){
		if (testChar.currentHealth <= dmg) {
			this.death ();
		} else {
			testChar.currentHealth -= dmg;
			transform.GetChild (0).localScale = new Vector3((float)testChar.currentHealth / testChar.maxHealth,transform.GetChild(0).localScale.y,1);
		}
	}
	public void death (){
		Gridmanager.instance.killCharacter (this.gameObject);
	}

	void walkNextStep(){
		
		print (transform.position + " offset: " + offset);
		if (Vector3.Distance (transform.position- offset, nextTile.transform.position) < minDistance) {
			if (path.Count <= 1) {
				
					
				setPosWithOffset (nextTile.transform.position);
				testChar.location = nextTile.GetComponent<TileBehaviour> ().tile.location;
				nextTile.GetComponent<TileBehaviour>().tile.isBlocked = true;
				moving = false;
				anim.SetBool ("moving", false);
				Gridmanager.instance.inAnimation.Remove (this.gameObject);
				nextTile = null;
				currentTile = null;
				path = null;

			} else {
				currentTile = path [0];
				nextTile = path [1];
				testChar.location = currentTile.GetComponent<TileBehaviour> ().tile.location;
				if (nextTile.transform.position.x > currentTile.transform.position.x && !testChar.facingRight || nextTile.transform.position.x<currentTile.transform.position.x && testChar.facingRight) {
					flip ();
				}
				path.Remove (currentTile);
		//		Debug.Log (nextTile.trafnsform.position + " " + currentTile.transform.position);
				this.transform.Translate (new Vector3 ((nextTile.transform.position.x - currentTile.transform.position.x) / speed, (nextTile.transform.position.y - currentTile.transform.position.y) / speed, 0));
			}
		} else {
			this.transform.Translate (new Vector3((nextTile.transform.position.x -currentTile.transform.position.x)/speed,(nextTile.transform.position.y -currentTile.transform.position.y)/speed,0));
		}
	}

	public void flip(){
		transform.localScale = new Vector3 (transform.localScale.x * -1, transform.localScale.y, transform.localScale.z);
		testChar.facingRight = !testChar.facingRight;
		this.offset.x = -this.offset.x;
		setPosWithOffset (Gridmanager.instance.calcPosition (testChar.location));
	}

	// Update is called once per frame
	void Update () {
		

		if (moving) {
			walkNextStep ();
		}
		Gridmanager gm = Gridmanager.instance;
		if (gm.activeChar == this.gameObject && testChar.isEnemy && gm.inAnimation.Count == 0) {
			testChar.nextAiMove ();
		}

	}
}

