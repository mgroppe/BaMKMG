using UnityEngine;
using System.Collections.Generic;

public static class PathFinder
{
	public static List<GameObject> generatePath (GameObject startTile, GameObject endTile)
	{
		if (endTile.GetComponent<TileBehaviour> ().tile.isBlocked)
			return new List<GameObject> ();
		List<Path> possibleMoves = new List<Path> ();
		List<Path> triedMoves = new List<Path> ();
		Path currentTile = null;
		Path finishCheck = new Path (null, endTile, 0, 0);
		possibleMoves.Add (new Path (null, startTile, 0, Distance (startTile, endTile)));
		while (!possibleMoves.Contains (finishCheck) && possibleMoves.Count > 0) {
			currentTile = possibleMoves [0];

			possibleMoves.Remove (currentTile);
			triedMoves.Add (currentTile);
			foreach (GameObject g in currentTile.tile.GetComponent<TileBehaviour>().tile.getUnblockedOrthogonalNeighbours()) {
				Path p = new Path (currentTile, g, currentTile.g + 1, Distance (g, endTile));
				if (!triedMoves.Contains (p)) {
					if (!possibleMoves.Contains (p))
						Insert (possibleMoves, p);
					else {
						
						if (possibleMoves [possibleMoves.IndexOf (p)].f > p.f) {
							possibleMoves.Remove (p);
							Insert (possibleMoves, p);
						}
					}
				}
			}
			foreach (GameObject g in currentTile.tile.GetComponent<TileBehaviour>().tile.getUnblockedDiagonalNeighbours()) {
				Path p = new Path (currentTile, g, currentTile.g + 2, Distance (g, endTile));
				if (!triedMoves.Contains (p)) {
					if (!possibleMoves.Contains (p))
						Insert (possibleMoves, p);
					else {
						if (possibleMoves [possibleMoves.IndexOf (p)].f > p.f) {
							possibleMoves.Remove (p);
							Insert (possibleMoves, p);
						}
					}
				}
			}

		}
		if (!possibleMoves.Contains (finishCheck))
			return new List<GameObject> ();
		currentTile = new Path (currentTile, endTile, 0, 0);
		List<GameObject> result = new List<GameObject> ();
		while (currentTile != null) {
			result.Add (currentTile.tile);
			currentTile = currentTile.parent;
		}
		result.Reverse ();
		return result;
	}

	public static int PathLength (List<GameObject> path)
	{
		int result = 0;
		for (int i = 0; i < path.Count - 1; i++) {
			Point location1 = path [i].GetComponent<TileBehaviour> ().tile.location;
			Point location2 = path [i + 1].GetComponent<TileBehaviour> ().tile.location;
			result += Mathf.Abs (location1.x - location2.x) + Mathf.Abs (location1.y - location2.y);
		}
		return result;
	}

	private static int Distance (GameObject t1, GameObject t2)
	{

		Tile tile1 = t1.GetComponent<TileBehaviour> ().tile;
		Tile tile2 = t2.GetComponent<TileBehaviour> ().tile;
		int result = 0;
		result += Mathf.Abs (tile1.location.x - tile2.location.x);
		result += Mathf.Abs (tile1.location.y - tile2.location.y);
		return result;
	}


	private static void Insert (List<Path> pList, Path p)
	{
		bool inserted = false;
		int x = 0;
		while (x < pList.Count && !inserted) {
			if (pList [x].f > p.f) {
				inserted = true;
				pList.Insert (x, p);
			}
			x++;
		}
		if (!inserted)
			pList.Add (p);
	}
}
