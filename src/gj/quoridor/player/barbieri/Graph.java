package gj.quoridor.player.barbieri;

import java.util.ArrayList;
import java.util.LinkedList;

public class Graph {
	private Node[] adjacency_list; //la lista di adiacenza
	private int size;//la dimensione del grafo

	// Costruttore
	public Graph(int size) {
		adjacency_list = new Node[size];
		this.size = size;
	}

	// setter and getter
	public Node[] getAdjacency_list() {
		return adjacency_list;
	}

	public void setAdjacency_list(Node[] adjacency_list) {
		this.adjacency_list = adjacency_list;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	/*
	 * Nella funzione addNode vengono inseriti come parametri un nodo e un indice.
	 * Se l'indice mi indica che nella lista non è presente nessun nodo quest'ultimo
	 * viene inserito, altrimenti questo viene inserito come vicino.
	 */
	public void addNode(Node start, int indx) {
		if (adjacency_list[indx] == null) {
			adjacency_list[indx] = start;
		} else {
			adjacency_list[indx].getNears().add(start.getId());
		}
	}

	//rimuove i nodi 
	public void removeNode(Integer start, Integer delete) {
		ArrayList<Integer> nearS = adjacency_list[start].getNears();
		for(int i = 0; i < nearS.size(); i++){
			if(nearS.get(i) == delete){
				nearS.remove(i);
			}
		}
	}



	/*algortmo short path è l'algoritmo definitivo per il percorso minimo.
	 * Utilizza l'algoritmo BFS che mi segna i vicini di un nodo sorgente ed infine utilizza l'algortimo shortPath
	 * che mi calcola il percorso
*/	
	public ArrayList<Integer> shortpath(int idN, int idTarget){
		int[] prev = BFS(idN);
		ArrayList<Integer> minum = shortPath(idN, idTarget, prev);
		
		return minum;
		
	}
	
	//Un algoritmo che mi torna un array di precedenti
	public int[] BFS(int idN) {
		int idtemp = 0;
		boolean[] flag = new boolean[size];
		flagF(flag);
		int[] prev = new int[size];
		prevf(prev);
		LinkedList<Integer> queue = new LinkedList<>();
		ArrayList<Integer> temp = new ArrayList<>();
		flag[idN] = true;
		queue.add(idN);
		while (!queue.isEmpty()) {
			idtemp = queue.pop();
			temp = adjacency_list[idtemp].getNears();
			for (int i = 0; i < temp.size(); i++) {
				if (flag[temp.get(i)] == false) {
					flag[temp.get(i)] = true;
					prev[temp.get(i)] = idtemp;
					queue.add(temp.get(i));
				}

			}
		}
			return prev;
	}
	
	//algoritmo percorso minimo. Una volta che arriva a -1 significa che è arrivato al nodo sorgente
	public ArrayList<Integer> shortPath(int idN, int t, int[] prev){
		ArrayList<Integer> minum = new ArrayList<>();
		int w = t;
		while(prev[w] != -1){
			minum.add(w);
			w = prev[w];
		}
		
		return minum;
	}

	//serve per inizializzare un array di muri a false per indicare che non sono stati visitati(BFS)
	public void flagF(boolean[] f) {
		for (int i = 0; i < f.length; i++) {
			f[i] = false;
		}
	}

	//serve per inizializzare un array a -1. Viene usato nella BFS e dal metodo shorthPath per trovare il cammino
	public void prevf(int[] p) {
		for (int i = 0; i < p.length; i++) {
			p[i] = -1;
		}
	}
}
