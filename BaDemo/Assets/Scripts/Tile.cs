using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class Tile {
	public Point location;
	public bool isBlocked;
	public List<GameObject> oNeighbours = null;
	public List<GameObject> dNeighbours = null;

	public Tile ( int x, int y){
		location = new Point (x, y);
		oNeighbours = new List<GameObject> ();
		dNeighbours = new List<GameObject> ();
	}


	public List<GameObject> getUnblockedDiagonalNeighbours (){
		List<GameObject> result = new List<GameObject> ();
		foreach (GameObject g in dNeighbours) {
			if (!g.GetComponent<TileBehaviour> ().tile.isBlocked)
				result.Add (g);
		}
		return result;
	}


	public List<GameObject> getUnblockedOrthogonalNeighbours(){
		List<GameObject> result = new List<GameObject> ();
		foreach (GameObject g in oNeighbours) {
			if (!g.GetComponent<TileBehaviour> ().tile.isBlocked)
				result.Add (g);
		}
		return result;
	}

}
