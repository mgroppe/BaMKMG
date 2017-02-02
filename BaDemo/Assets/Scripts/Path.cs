using System.Collections;
using System.Collections.Generic;
using UnityEngine;

public class Path {
	public Path parent;
	public GameObject tile;
	public int g;
	public int h;
	public int f;


	public Path (Path parent,GameObject tile, int g, int h){
		this.parent = parent;
		this.tile = tile;
		this.g = g;
		this.h = h;
		f = g + h;
	} 

	public override bool Equals(System.Object obj){
		if (obj == null)
			return false;
		Path p = obj as Path;
		if ((System.Object)p == null)
			return false;
		return (p.tile == this.tile);
	}
	public bool Equals (Path p){
		return p.tile == this.tile;
	}

	public override int GetHashCode(){
		return tile.GetHashCode ();
	}

}
