using UnityEngine;
using System.Collections;

public class TileBehaviour : MonoBehaviour {
	public Tile tile;
	// Use this for initialization
	void Start () {
	
	}

	void OnMouseOver(){
		if (Input.GetMouseButtonUp (0))
			Gridmanager.instance.TileClicked (tile.location);
	}
	// Update is called once per frame
	void Update () {
	
	}
}
