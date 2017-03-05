using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class ProjectileBehaviour : MonoBehaviour {
	public Vector3 startPos;
	public Vector3 goalPos;
	private Animator anim;

	public float speed;
	public float minDistance;
	private bool moving;
	// Use this for initialization
	void Start () {
		moving = true;
		anim = GetComponent<Animator> ();
	}

	// Update is called once per frame
	void Update () {
		if (moving && Vector3.Distance (transform.position,goalPos) > minDistance) {
			transform.Translate (new Vector3 (
				(goalPos.x - startPos.x) / speed / Vector3.Distance (startPos, goalPos),
				(goalPos.y - startPos.y) / speed /  Vector3.Distance (startPos, goalPos), 0));
		} else {
			anim.SetTrigger ("ranged_reached");
			moving = false;
			Gridmanager gm = Gridmanager.instance;
			gm.inAnimation.Remove (this.gameObject);
			Destroy (this.gameObject,0.5f);
		}
	}
}
