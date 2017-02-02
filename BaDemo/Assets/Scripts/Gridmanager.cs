using UnityEngine;
using System.Collections;
using System.Collections.Generic;

public class Gridmanager : MonoBehaviour {
	public GameObject square;
	public GameObject character;
	public GameObject rock;
	public GameObject selectedTile;
	public int columns;
	public int rows;
	public bool inAnimation;

	private GameObject[,] field;
	private List<GameObject> initiative;
	private GameObject activeChar;
	private float squaresize;
	public static Gridmanager instance;




	void setsize(){
		squaresize = square.GetComponent<SpriteRenderer> ().sprite.bounds.extents.x * 2;
	}
	//calculates the position of Tile [0,0] based on the size of the square sprite
	Vector3 calcInitPosition(){
		return new Vector3 (-squaresize * columns / 2f + squaresize / 2f, squaresize * rows / 2f - squaresize / 2f,0);
	}
	//calculates the position of Square [x,y]
	public Vector3 calcPosition (int x, int y){
		Vector3 initPosition = calcInitPosition ();
		float xcoord = initPosition.x + x * squaresize;
		float ycoord = initPosition.y - y * squaresize;
		return new Vector3 (xcoord, ycoord, 0);
	}
	// Generates the Grid and initializes the Tiles
	void generateGrid(){
		for (int x = 0; x < columns; x++)
			for (int y = 0; y < rows; y++) {
				GameObject newSquare = (GameObject)Instantiate (square);
				newSquare.transform.position = calcPosition (x, y);
				newSquare.transform.SetParent (this.transform);
				TileBehaviour tb = newSquare.GetComponent<TileBehaviour> ();
				tb.tile = new Tile (x, y);
				// Tiles to the left get added as neighbours and add this tile
				if (x > 0) {
					TileBehaviour tb2 = field [x - 1, y].GetComponent<TileBehaviour> ();
					tb.tile.oNeighbours.Add(field[x-1,y]);
					tb2.tile.oNeighbours.Add (newSquare);
					
				}
				// Tiles on the top get added to neighbours and add this tile
				if (y > 0){
					
					TileBehaviour tb2 = field [x, y-1].GetComponent<TileBehaviour> ();
					tb.tile.oNeighbours.Add(field[x,y-1]);
					tb2.tile.oNeighbours.Add (newSquare);
				}
				// Tile to the bottom left adds this as diagonal neighbour
				if (x > 0 && y < (rows-1)) {
					TileBehaviour tb2 = field [x - 1, y + 1].GetComponent<TileBehaviour> ();
					tb.tile.dNeighbours.Add(field[x-1,y+1]);
					tb2.tile.dNeighbours.Add (newSquare);
				}
				if (x > 0 && y > 0) {
					TileBehaviour tb2 = field [x - 1, y - 1].GetComponent<TileBehaviour> ();
					tb.tile.dNeighbours.Add (field [x - 1, y - 1]);
					tb2.tile.dNeighbours.Add (newSquare);
				}
				field [x,y] = newSquare;
			}
	}

	void generateRocks(){
	
		foreach (GameObject g in field) {
			if (!g.GetComponent<TileBehaviour> ().tile.isBlocked && Random.value < 0.05f) {
				GameObject newRock = (GameObject)Instantiate (rock);
				newRock.transform.position = g.transform.position;
				g.GetComponent<TileBehaviour> ().tile.isBlocked = true;

			}
		}
	}
	void setCharacter(){
		Vector3 position = calcInitPosition ();
		//GameObject newCharacter = (GameObject)Instantiate (character,position,transform.rotation);
		//die 0.2 sind testChar.offsetY, gefällt mir momentan gar nicht. Gibt es eine Möglichkeit, die position von newCharacter nachträglich zu manipulieren?
		GameObject newCharacter = (GameObject)Instantiate (character,new Vector3(position.x, position.y+0.2f, position.z),transform.rotation);
		newCharacter.transform.SetParent  (this.transform);
		newCharacter.GetComponent<TestCharacterBehaviour> ().testChar = new Character (10, 1, 3, 10, 10, new Point (0, 0), 10, false,new Vector3(0f,0.2f,0f));

		field [newCharacter.GetComponent<TestCharacterBehaviour>().testChar.location.x, newCharacter.GetComponent<TestCharacterBehaviour>().testChar.location.y].GetComponent<TileBehaviour> ().tile.isBlocked = true;
		//if (newCharacter.GetComponent<TestCharacterBehaviour>().testChar.isEnemy) newCharacter.GetComponent<TestCharacterBehaviour>().testChar.facingRight=false;
		activeChar = newCharacter;
	}

	void showReachableTiles(GameObject character){
		List<GameObject> tiles = new List<GameObject> ();
		List<GameObject> lastStep = new List<GameObject> ();
		tiles.Add (field [character.GetComponent<TestCharacterBehaviour> ().testChar.location.x, character.GetComponent<TestCharacterBehaviour> ().testChar.location.y]);
		for (int i = 0; i < character.GetComponent<TestCharacterBehaviour> ().testChar.movement; i++) {
			GameObject[] copy = new GameObject[tiles.Count];
			tiles.CopyTo (copy);
			foreach (GameObject g in lastStep) {
				foreach (GameObject g2 in g.GetComponent<TileBehaviour>().tile.getUnblockedDiagonalNeighbours())
					if (!tiles.Contains (g2))
						tiles.Add (g2);
			}
			foreach (GameObject g in copy) {
				if (!lastStep.Contains(g))lastStep.Add (g);
				foreach (GameObject g2 in g.GetComponent<TileBehaviour>().tile.getUnblockedOrthogonalNeighbours())
					if (!tiles.Contains (g2))
						tiles.Add (g2);
			}
		}
		foreach (GameObject g in tiles) {
			GameObject newSelectedTile = (GameObject)Instantiate (selectedTile);
			newSelectedTile.transform.position = g.transform.position;
		}

	}

	void deleteReachableTiles(){
		foreach (GameObject g in GameObject.FindGameObjectsWithTag("SelectedTile"))
			Destroy (g);
	}

	public void TileClicked(Point location){
		if (!inAnimation) {
			Point start = activeChar.GetComponent<TestCharacterBehaviour> ().testChar.location;
			List<GameObject> path = PathFinder.generatePath (field [start.x, start.y], field [location.x, location.y]);

			if (PathFinder.PathLength (path) <= activeChar.GetComponent<TestCharacterBehaviour> ().testChar.movement && path.Count > 0) {
				field [start.x, start.y].GetComponent<TileBehaviour> ().tile.isBlocked = false;
				field [location.x, location.y].GetComponent<TileBehaviour> ().tile.isBlocked = true;
				activeChar.GetComponent<TestCharacterBehaviour> ().testChar.location = location;
				deleteReachableTiles ();
				activeChar.GetComponent<TestCharacterBehaviour> ().Walk (path);
				showReachableTiles (activeChar);
			}
		}

	}


	// Use this for initialization
	void Start () {
		field = new GameObject[columns,rows];
		setsize ();	
		generateGrid ();
		setCharacter ();
		generateRocks ();
		showReachableTiles (activeChar);
		instance = this;
	}





	}

