using UnityEngine;
using UnityEngine.SceneManagement;
using System.Collections;
using System.Collections.Generic;

public class Gridmanager : MonoBehaviour {
	public GameObject square;
	public GameObject[] playerCharacters;
	public GameObject[] enemies;
	public GameObject rock;
	public GameObject selectedTile;
	public GameObject healthBar;
	public int columns;
	public int rows;
	public bool inAnimation;

	public GameObject[,] field;
	public List<GameObject> initiative;
	public GameObject activeChar;
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
	//calculates the Position of the square[p.x,p.y]
	public Vector3 calcPosition (Point p){
		Vector3 initPosition = calcInitPosition ();
		float xcoord = initPosition.x + p.x * squaresize;
		float ycoord = initPosition.y - p.y * squaresize;
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

	//randomly puts some rocks as blocker objects on the field
	void generateRocks(){
	
		foreach (GameObject g in field) {
			if (!g.GetComponent<TileBehaviour> ().tile.isBlocked && Random.value < 0.05f) {
				GameObject newRock = (GameObject)Instantiate (rock);
				newRock.transform.position = g.transform.position;
				g.GetComponent<TileBehaviour> ().tile.isBlocked = true;

			}
		}
	}

	//creates the characters
	void setCharacter(){

		//GameObject newCharacter = (GameObject)Instantiate (character,position,transform.rotation);
		//die 0.2 sind testChar.offsetY, gefällt mir momentan gar nicht. Gibt es eine Möglichkeit, die position von newCharacter nachträglich zu manipulieren?
		foreach (GameObject g in playerCharacters){
			GameObject newCharacter = (GameObject)Instantiate (g,new Vector3(0,0,0),transform.rotation);
			newCharacter.transform.SetParent  (this.transform);
			Point location = findLocation (false);
			newCharacter.GetComponent<TestCharacterBehaviour> ().testChar = new Character (10, 1, 3, 10, 3, location, 10, false,new Vector3(0f,0.2f,0f),newCharacter);
			newCharacter.GetComponent<TestCharacterBehaviour> ().setPosWithOffset (calcPosition(location));
			field [location.x, location.y].GetComponent<TileBehaviour> ().tile.isBlocked = true;
			insertInitiative (newCharacter);
			GameObject newHealthBar = (GameObject)Instantiate (healthBar);
			newHealthBar.transform.SetParent (newCharacter.transform);
			newHealthBar.transform.localPosition = (new Vector3 (0, -0.95f, 0));
		}
		foreach (GameObject g in enemies) {
			GameObject newCharacter = (GameObject)Instantiate (g,new Vector3(0,0,0),transform.rotation);
			newCharacter.transform.SetParent  (this.transform);
			Point location = findLocation (true);
			newCharacter.GetComponent<TestCharacterBehaviour> ().testChar = new Character (10, 1, 3, 10, 2, location, 10, true,new Vector3(0f,0.2f,0f),newCharacter);
			newCharacter.GetComponent<TestCharacterBehaviour> ().setPosWithOffset (calcPosition(location));
			newCharacter.GetComponent<TestCharacterBehaviour> ().flip ();
			field [location.x, location.y].GetComponent<TileBehaviour> ().tile.isBlocked = true;
			insertInitiative (newCharacter);
			GameObject newHealthBar = (GameObject)Instantiate (healthBar);
			newHealthBar.transform.SetParent (newCharacter.transform);
			newHealthBar.transform.localPosition = (new Vector3 (0, -0.95f, 0));
		}

		//if (newCharacter.GetComponent<TestCharacterBehaviour>().testChar.isEnemy) newCharacter.GetComponent<TestCharacterBehaviour>().testChar.facingRight=false;
	}

	//inserts the Character at the correct Place in the Initiative order
	void insertInitiative (GameObject character){
		bool inserted = false;
		int x = 0;
		while (x < initiative.Count && !inserted) {
			if (initiative [x].GetComponent<TestCharacterBehaviour>().testChar.initiative > character.GetComponent<TestCharacterBehaviour>().testChar.initiative) {
				inserted = true;
				initiative.Insert (x, character);
			}
			x++;
		}
		if (!inserted)
			initiative.Add (character);
	}

	//finds a free location on the correct side of the Board for a Character
	Point findLocation(bool isEnemy){
		List<Point> possiblePoints = new List<Point> ();
		foreach (GameObject g in field) {
			Tile tile = g.GetComponent<TileBehaviour> ().tile;
			if (tile.location.x == 0 && !isEnemy && !tile.isBlocked) {
				possiblePoints.Add (tile.location);
			}
			if(tile.location.x == columns-1 && isEnemy && !tile.isBlocked){
				possiblePoints.Add(tile.location);
			}
		}
		int x = Random.Range (0, possiblePoints.Count - 1);
		return possiblePoints [x];
	
	}

	//shows the player what tiles the currently active character can reach
	public void showReachableTiles(GameObject character){
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


	public void deleteReachableTiles(){
		foreach (GameObject g in GameObject.FindGameObjectsWithTag("SelectedTile"))
			Destroy (g);
	}

	public void killCharacter(GameObject character){
		Character c = character.GetComponent<TestCharacterBehaviour> ().testChar;
		field [c.location.x, c.location.y].GetComponent<TileBehaviour> ().tile.isBlocked = false;
		initiative.Remove (character);
		if (c.isEnemy) {
			bool checkVictory = true;
			foreach (GameObject g in initiative) {
				if (g.GetComponent<TestCharacterBehaviour> ().testChar.isEnemy) {
					checkVictory = false;
					break;
				}
			}
			if (checkVictory) {
				SceneManager.LoadScene("Victory");
			}
		} else {
		}
		Destroy (character);
	}

	public void nextTurn (){
		deleteReachableTiles ();
		activeChar = initiative [0];
		activeChar.GetComponent<TestCharacterBehaviour> ().refresh ();
		initiative.Remove (activeChar);
		initiative.Add (activeChar);
	}
	//called by the Tile that was clicked and gives its Coordinates
	public void TileClicked(Point location){
		if (!inAnimation) {
			if (!activeChar.GetComponent<TestCharacterBehaviour> ().testChar.isEnemy) {
				if (field [location.x, location.y].GetComponent<TileBehaviour> ().tile.isBlocked) {
					if (activeChar.GetComponent<TestCharacterBehaviour> ().testChar.location.x == location.x && activeChar.GetComponent<TestCharacterBehaviour> ().testChar.location.y == location.y) {
						activeChar.GetComponent<TestCharacterBehaviour> ().defend ();
						Debug.Log ("clicked on self");
					} else {
						foreach (GameObject g in initiative) {
							Character c = g.GetComponent<TestCharacterBehaviour> ().testChar;
							if (c.isEnemy && c.location.x == location.x && c.location.y == location.y) {
								int distance;
								distance = Mathf.Max (Mathf.Abs (c.location.x - activeChar.GetComponent<TestCharacterBehaviour> ().testChar.location.x), Mathf.Abs (c.location.y - activeChar.GetComponent<TestCharacterBehaviour> ().testChar.location.y));
								if (activeChar.GetComponent<TestCharacterBehaviour> ().testChar.attackRange >= distance) {
									//attack code
									activeChar.GetComponent<TestCharacterBehaviour>().attack(g);
									Debug.Log ("attacked " + location.x + " " + location.y);
									break;
								}
							}
						}
					}
						
				} else {
					Point start = activeChar.GetComponent<TestCharacterBehaviour> ().testChar.location;
					List<GameObject> path = PathFinder.generatePath (field [start.x, start.y], field [location.x, location.y]);

					if (PathFinder.PathLength (path) <= activeChar.GetComponent<TestCharacterBehaviour> ().testChar.movement && path.Count > 0 && !activeChar.GetComponent<TestCharacterBehaviour>().testChar.hasMoved) {
						activeChar.GetComponent<TestCharacterBehaviour> ().Walk (path);
					}
				}
			}
			}
		}



	// Use this for initialization
	void Start () {
		initiative = new List<GameObject> ();
		field = new GameObject[columns,rows];
		setsize ();	
		generateGrid ();
		setCharacter ();
		generateRocks ();
		instance = this;
		nextTurn ();
	}


	}

