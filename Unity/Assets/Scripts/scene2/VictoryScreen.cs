using System.Collections;
using System.Collections.Generic;
using UnityEngine;
using UnityEngine.UI;

public class VictoryScreen : MonoBehaviour {
	
	public GameObject deletable;
	public GameObject itemBgTurning;
	public GameObject itemBackground;
	public GameObject[] tokens;
	public GameObject[] items;
	public GameObject[] nameTexts;
	public GameObject[] itemTexts;
	public GameObject[] bars;

	VicData vd;

	GameObject[] pictures;

	GameObject[] textObjects;
	GameObject glow;
	GameObject glow2;
	GameObject item;
	bool ready, switched,sunInPlace;
	int fullSize;


	void generateTokens(int[]models){
		pictures = new GameObject[models.Length];
		for (int i=0; i<models.Length;i++){
			GameObject s = (GameObject)Instantiate (this.GetComponent<VictoryScreen>().tokens [models [i]]);
			pictures [i] = s;
			s.transform.SetParent  (this.transform);
			s.transform.localPosition=new Vector3 (-17.0f,3.5f+(-2.5f*i),0f);
		}	
	}
		
	void generateNames(string[]names){
		for (int i = 0; i < nameTexts.Length; i++) {
			Text t = nameTexts[i].GetComponent<Text> ();
			t.text = "";
		}
		for (int i = 0; i < names.Length; i++) {
			Text t = nameTexts[i].GetComponent < Text>();
			t.text = names [i];
		}
	}

	void generateXpBars(int []xpCurrent, int []xpLvlUp,int xpReward){
		for (int j = 0; j < bars.Length; j++) {
			bars[j].GetComponent<ExpBar>().hasData = true;
			bars[j].GetComponent<ExpBar>().startBar = (float)xpCurrent [j]/ (float)xpLvlUp [j];
			bars[j].GetComponent<ExpBar>().endBar = ((float)xpCurrent[j]+(float)xpReward)/(float)xpLvlUp[j];
			bars[j].GetComponent<Image> ().fillAmount = bars[j].GetComponent<ExpBar>().startBar;
		}
	}

	void generateGlow(){
		glow = (GameObject)Instantiate (this.GetComponent<VictoryScreen> ().itemBgTurning);
		glow.transform.localPosition = new Vector3 (0.0f, 0.0f, 0f);
		glow2= (GameObject)Instantiate (this.GetComponent<VictoryScreen> ().itemBgTurning);
		glow2.transform.localPosition = new Vector3 (0.0f, 0.0f, 0f);

		GameObject bg = (GameObject)Instantiate (this.GetComponent<VictoryScreen> ().itemBackground);
		bg.transform.localPosition = new Vector3 (0.0f, 0.0f, 0f);
		sunInPlace = true;
	}

	void generateItem(){
		item=(GameObject)Instantiate(this.GetComponent<VictoryScreen>().items[vd.itemType]);
		item.transform.localPosition = new Vector3 (0.0f, 0.0f, 0f);
		item.transform.localScale-=new Vector3(.6f,.6f,0f);
		SpriteRenderer sprite;
		sprite = item.GetComponent<SpriteRenderer> ();
		if (sprite) sprite.sortingLayerName = "Stones";

		Text t =	itemTexts [0].GetComponent<Text> ();
		t.text=vd.itemName;
		int j = 0;
		t = itemTexts [1].GetComponent<Text> ();
		t.text = "";

		//0 hp 1 minAttack 2 maxAttack 3 movement 4 attackrange 5 initiative 
		for (int i = 0; i < vd.itemStats.Length; i++) {
			if (i == 3) {
				t = itemTexts [2].GetComponent<Text> ();
				t.text = "";
			}
			if (vd.itemStats [i] != 0) {
				if (i==0)
					t.text += " LP: " + vd.itemStats [0];
				if (i==1)
					t.text+=" Schaden: " + vd.itemStats [1] + "-" + vd.itemStats [2];
				if (i==3)
					t.text += " Geschwindigkeit: " + vd.itemStats [3];
				if(i==4)
				t.text += " Angriffsreichweite: " + vd.itemStats [4];
				if(i==5)t.text += " Initiative: " + vd.itemStats [5];

				}
			}

		}

	void changeToItem(){
	//	yield return new WaitForSecondsRealtime (2);
		Destroy(deletable);
		foreach (GameObject g in pictures)
			Destroy (g);
		generateGlow ();
		generateItem ();
		Invoke ("changeToJava",6);
	}

	void changeToJava(){
		AndroidJavaClass activityClass;
		AndroidJavaObject activity;



		activityClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		activity = activityClass.GetStatic<AndroidJavaObject>("currentActivity");
		activity.Call("victory");
	}
	

	


	// Use this for initialization
	void Start () {
		textObjects = GameObject.FindGameObjectsWithTag ("TextObject");
		switched = false;
		sunInPlace = false;
		fullSize = 0;

		//comment for testing in unity

		AndroidJavaClass activityClass;
		AndroidJavaObject activity;

		activityClass = new AndroidJavaClass("com.unity3d.player.UnityPlayer");
		activity = activityClass.GetStatic<AndroidJavaObject>("currentActivity");
		string cd = activity.Get<string> ("vicData");


		//uncomment for tests in unity
	//	string cd = "{\"itemName\":\"seltene Schuhe\",\"itemStats\":[18,0,0,1,0,10],\"xpLvlUp\":[100,100,100],\"models\":[0,1,3],\"names\":[\"Leipzig\",\"Essling\",\"Hitler\"],\"xpCurrent\":[0,0,0],\"itemType\":5,\"xpReward\":20}";
		vd = JsonUtility.FromJson<VicData> (cd);


		

		string []names={"a","b","c"};
		int[]models={0,2,1};
		int[] xpCurrent = { 10, 50, 20 };
		int[] xpLvlUp = { 100, 100, 100 };
		int xpReward = 20;

		int[] itemStats=new int[6];
		itemStats [0] = 10;
		itemStats [1] = 5;
		itemStats [2] = 10;
		itemStats [5] = 10;
		string itemName = "lvl 1 Morgenstern des Grauens";
		int itemType=5;
		//vd=new VicData(names,models,xpCurrent,xpLvlUp,xpReward,itemName,itemType,itemStats);

		generateXpBars(vd.xpCurrent,vd.xpLvlUp,vd.xpReward);
		generateTokens(vd.models);
		generateNames(vd.names);
	}

	// Update is called once per frame
	void Update () {
		
		if (!switched) {
			ready = true;
			/*
			  for (int i = 0; i < bars.Length; i++) {
			
				if (!bars [i].GetComponent<ExpBar> ().isfinished)
					ready = false;
			}
			*/
			if (ready){
				switched = true;
				Invoke ("changeToItem",3);
			}

		}
		else if (sunInPlace) {
			glow.transform.Rotate (new Vector3 (0, 0, 0.1f));
			glow2.transform.Rotate (new Vector3 (0, 0, -0.1f));
			if (fullSize < 100) {
				item.transform.localScale += new Vector3 (0.005f, 0.005f, 0);
				fullSize++;
			}
		}
	}
}
