using UnityEngine;
using System.Collections;
using UnityEngine.UI;

public class ExpBar : MonoBehaviour {
	public bool hasData;
	public bool isfinished;
	public float startBar;
	public float endBar;

	/*
	void addXP (int [] oldXP, int xpGained){
		GameObject []xpBars;

		xpBars = GameObject.FindGameObjectsWithTag ("XpBar");
		for (int i=0; i<xpBars.Length;i++)
			
	//		barDisplay = Time.time*0.10f;
	}
	*/

	void Update() {
		if (hasData&&this.GetComponent<Image>().fillAmount<endBar) {
			
			this.GetComponent<Image> ().fillAmount +=0.004f;
			//bar.fillAmount=
		}
		if (hasData && this.GetComponent < Image> ().fillAmount >= endBar) {
			this.isfinished = true;
		}
		
			//for this example, the bar display is linked to the current time,
	
		//however you would set this value based on your desired display
		//eg, the loading progress, the player's health, or whatever.
		
		//        barDisplay = MyControlScript.staticHealth;

	}
	// Use this for initialization
	void Start () {
		isfinished = false;
	}
}