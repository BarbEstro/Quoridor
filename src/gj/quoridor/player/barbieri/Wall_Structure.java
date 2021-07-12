package gj.quoridor.player.barbieri;

import java.util.ArrayList;

public class Wall_Structure {
	/*
	 * variabile boleana per indicare se un muro è orrizontale o verticale.
	 * False = verticale True = orrizontale. La variabile indexWall già dal nome
	 * vuole indicare l'INDICE DEL MURO. La variabile walls invece rappresenta
	 * una lista con dentro i muri che devono essere cancellati.
	 */
	private boolean v_or_h;
	private int indexWall;
	private ArrayList<Integer> walls;
	
	//costruttore
	public Wall_Structure(boolean v_or_h, int indexWall){
		this.v_or_h = v_or_h;
		this.indexWall = indexWall;
		walls = new ArrayList<>();
	}

	//metodi setter e getter
	public boolean isV_or_h() {
		return v_or_h;
	}

	public void setV_or_h(boolean v_or_h) {
		this.v_or_h = v_or_h;
	}

	public int getIndexWall() {
		return indexWall;
	}

	public void setIndexWall(int indexWall) {
		this.indexWall = indexWall;
	}

	public ArrayList<Integer> getWalls() {
		return walls;
	}

	public void setWalls(ArrayList<Integer> walls) {
		this.walls = walls;
	}

	

}
