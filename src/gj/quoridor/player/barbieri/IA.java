package gj.quoridor.player.barbieri;

import java.util.ArrayList;

public class IA {
	private int idG; //casella dove si trova il giocatore
	private int idA; // casella dove si trova l'avversario
	private int[] target; // ho bisogno di due destinazioni diverse
	private int[] target2;
	private Imaginary_Table table; //scacchiera
	private int walls;//quanti muri posso effettuare
	private boolean change; //valore booleano per indicare se il giocatore parte per primo o no

	
	//costruttore
	public IA(int idG, int idA, int size, boolean change, Imaginary_Table table) {
		this.idG = idG;
		this.idA = idA;
		target = new int[size];
		target2 = new int[size];
		Target(true, target);
		Target(false, target2);
		this.table = table;
		walls = 10;
		this.change = change;
	}

	//setter and getter
	public int getIdG() {
		return idG;
	}

	public void setIdG(int idG) {
		this.idG = idG;
	}

	public int getIdA() {
		return idA;
	}

	public void setIdA(int idA) {
		this.idA = idA;
	}

	public int[] getTarget() {
		return target;
	}

	public void setTarget(int[] target) {
		this.target = target;
	}

	//Il target sarebbe la destinazione o goal, che cambia in base al valore booleano change
	private void Target(boolean change, int[] target) {
		if (change == true) {
			int idx = 72;
			for (int i = 0; i < target.length; i++, idx++) {
				target[i] = idx;
			}
		} else {
			for (int i = 0; i < target.length; i++) {
				target[i] = i;
			}
		}
	}

	/*/
	 * La strategia constiste semplicemente nel calcolare il percorso minimo del giocatore
	 * e dell'avversario. Se Il giocatore è più vicino al suo target allora si muove altrimenti inserisce un muro.
	 * Ovviamente le condizioni per inserire un muro non è solo quello di controllare la distanza, ma anche quello di
	 * controllare se ho abbastanza muri e se i muri
	 * disponibili non chiudono la strada all'avversario.
	 */
	public int[] strategy(Control control) {
		int[] play = new int[2];
		int[][] pathPlayer = pathsMinum(idG, change);
		int[][] pathAdversary = pathsMinum(idA, !change);
		int[] P = choicePath(pathPlayer);
		int[] A = choicePath(pathAdversary);
		
		P = controlRoad(pathPlayer, control, P);
		A = controlRoad(pathAdversary, control, A);

		if (P[1] > A[1] & closedRoad(A[0])==false & walls > 0) {
			play[0] = 1;
			play[1] = IndexWall(A[0]);
			walls = walls-1;
			return play;
		} else {

			play[0] = 0;
			play[1] = P[0];
		}

		return play;
	}

	/*
	 * Questa funzione mi calcola il percorso minimo per ogni target e lo inserisce in
	 * un array bidimensionale
	 */
	public int[][] pathsMinum(int idx, boolean change) {
		int[] target = change == true ? this.target : target2;
		int[][] paths = new int[9][2];
		ArrayList<Integer> temp = new ArrayList<>();
		int size = 0;

		for (int i = 0; i < target.length; i++) {
			temp = table.getGraph().shortpath(idx, target[i]);
			size = temp.size();
			if(size != 0){
			paths[i][0] = temp.get(size - 1);
			paths[i][1] = size;
			}else{
				paths[i][0] = 0;
				paths[i][1] = 0;
			}
		}

		return paths;
	}

	// Dopo vari percorsi, mi deve scegliere il percorso breve
	public int[] choicePath(int[][] pathsMinum) {
		int position = 0;

		for (int i = 0; i < pathsMinum.length; i++) {
			if (pathsMinum[i][1] < pathsMinum[position][1]) {
				position = i;
			}
		}

		return pathsMinum[position];
	}

	/*/
	 * metodo che ricalcola tutto il percorso utilizzando i vari muri dando come risultato
	 l'indice del muro che blocca di più il percorso.
	 */
	private int IndexWall(int idA) {
		int indexWall = 0;
		int[][] overcome = table.searchWall(idA);
		int[] minumPath = new int[8];
		int[] minumPathP = new int[8];
		for(int i = 0; i < minumPath.length;i++){
			if(overcome[i][0]!=-1){
			minumPath[i] = choiceWall(overcome[i]);
			minumPathP[i] = closedForP(overcome[i]);
			}
		}
		for(int i = 0; i < 8; i++){
			if(minumPathP[i] == 0)
				minumPath[i] = 0;
		}
		indexWall = maxindexPath(minumPath);
		indexWall = overcome[indexWall][0];
		
		return indexWall;
	}
	
	//metodo che cancella il muro e calcola il percorso
	private int choiceWall(int[] wall_structure){
		int[][] Path = null;
		int[] minumPath = null;
		if(table.isH_or_V(wall_structure[0])==false){
			table.removeVertical(wall_structure[1]);
			Path = pathsMinum(idA, !change);
			table.addVertical(wall_structure[1]);
		}else{
			table.removeHorizontal(wall_structure[1]);
			Path = pathsMinum(idA, !change);
			table.addHorizontal(wall_structure[1]);
		}
		
		minumPath = choicePath(Path);
		
		return minumPath[1];
	}
	
	//metodo che sceglie tra i vari percorsi minimi il maggiore dando come risultato l'indice
	private int maxindexPath(int[] minumpath){
		int max = 0;
		for(int i = 0; i < minumpath.length;i++){
			if(minumpath[max] < minumpath[i])
				max = i;
		}
		
		return max;
	}
	
	//valuta se esiste un percorso per l'avversario
	private boolean closedRoad(int idA){
		boolean result = false;
		int[][] overcome = table.searchWall(idA);
		int[] minumPath = new int[8];
		int[] minumPathP = new int[8];//*
		for(int i = 0; i < minumPath.length;i++){
			if(overcome[i][0]!=-1){
			minumPath[i] = choiceWall(overcome[i]);
			minumPathP[i] = closedForP(overcome[i]);//*
			}
		}
		
		for(int i = 0; i < 8; i++){
			if(minumPathP[i] == 0)
				minumPath[i] = 0;
		}
		
		result = closed(minumPath);
		
		return result;
	}
	
	//se esce un percorso con tutti zero significa che non si può mettere nessun muro
	private boolean closed(int[] road){
		for(int i = 0; i < road.length;i++){
			if(road[i] != 0)
				return false;
			
		}
		
		return true;
	}
	
	//Ritorna un secondo percorso minimo
	private int[] controlRoad(int[][] paths,Control control, int[] pathsMinum){
		if(control.remove_a_blocked_path(paths))
			pathsMinum = choicePath(paths);
		return pathsMinum;
	}
	
	//valuta se ci sono muri che chiudono il percorso al giocatore(idG), se ci fossero muri che bloccano
	//il suo percorso torna zero altrimento uno
	private int closedForP(int[] wall_structure){
		int[][] Path;
		if(table.isH_or_V(wall_structure[0])==false){
			table.removeVertical(wall_structure[1]);
			Path = pathsMinum(idG, change);
			table.addVertical(wall_structure[1]);
		}else{
			table.removeHorizontal(wall_structure[1]);
			Path = pathsMinum(idG, change);
			table.addHorizontal(wall_structure[1]);
		}
		if(countPath(Path) == 0)
			return 0;
		else return 1;
		
	}
	
	private int countPath(int[][] Path){
		int count = 0;
		
		for(int i = 0; i < Path.length; i++){
			if(Path[i][0] != 0)
				count = count +1;
		}
		
		return count;
	}
	

}
