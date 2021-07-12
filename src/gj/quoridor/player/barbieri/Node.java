package gj.quoridor.player.barbieri;

import java.util.ArrayList;

/*
 * Il nodo contiene una variabile x e y per indicare le coordinate.I nears per indicare i vicini, infine abbiamo
 * la variabile id per identificare la casella vera e propria, dove si muove il giocatore.
 */
public class Node {
	private int x;
	private int y;
	private ArrayList<Integer> nears;
	private int id;
	
	//Costruttore
	public Node(int x, int y, int indx){
		this.x = x;
		this.y = y;
		id = indx;
		nears = new ArrayList<Integer>();
	}
	
	//metodi setter e getter
	public int getX() {
		return x;
	}
	public void setX(int x) {
		this.x = x;
	}
	public int getY() {
		return y;
	}
	public void setY(int y) {
		this.y = y;
	}
	public ArrayList<Integer> getNears() {
		return nears;
	}
	public void setNears(ArrayList<Integer> nears) {
		this.nears = nears;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	@Override
	public String toString(){
		return "x: "+x+" y: "+y+" id: "+id;
	}
	
}
