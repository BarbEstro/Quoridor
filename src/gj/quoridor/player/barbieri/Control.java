package gj.quoridor.player.barbieri;

public class Control {

	//traduce le mosse del giocatore (idG)
	public int[] translation(int[] play, boolean isFirst, int idP, Imaginary_Table table) {
		if (play[0] == 0) {
			if (isFirst == true) {
				traslationT(play, idP);
			} else {
				traslationF(play, idP);
			}
		} else {
			remove(play[1], table);
		}

		return play;
	}

	private int[] traslationT(int[] play, int idP) {
		if (play[1] == idP + 9) {
			play[1] = 0;
			return play;
		}
		if (play[1] == idP + 1) {
			play[1] = 2;
			return play;
		}

		if (play[1] == idP - 1) {
			play[1] = 3;
			return play;
		}
		if (play[1] == idP - 9) {
			play[1] = 1;
			return play;
		}
		return null;
	}

	private int[] traslationF(int[] play, int idP) {
		if (play[1] == idP - 9) {
			play[1] = 0;
			return play;
		}
		if (play[1] == idP - 1) {
			play[1] = 2;
			return play;
		}

		if (play[1] == idP + 1) {
			play[1] = 3;
			return play;
		}
		if (play[1] == idP + 9) {
			play[1] = 1;
			return play;
		}
		return null;
	}

	//rimuove i muri
	private void remove(int idW, Imaginary_Table table) {
		
		
		if(table.isH_or_V(idW)== false){
			table.removeVertical(table.getWalls_structure()[idW].getWalls().get(0));
		}else{
			table.removeHorizontal(table.getWalls_structure()[idW].getWalls().get(0));
		}
		
		table.deleteBrother(idW);

	}


	//traduce le mosse dell'avversario
	public int traslationEnemy(int[] play, int idA,boolean isFirst, Imaginary_Table table){
		int change = isFirst==true? -9 : 9;
		int change2 = isFirst==true? -1:1; 
		if(play[0] == 0 & play[1] == 0){
			idA = idA + change;
		}else if(play[0] == 0 & play[1] == 1){
			idA = idA - change;
		}else if(play[0] == 0 & play[1] == 2){
			idA = idA + change2;
		}else if(play[0] == 0 & play[1] == 3){
			idA = idA - change2;
		}else{
			remove(play[1], table);
		}
		
		return idA;
	}
	
	//Se dei muri bloccano un target nella scelta del percorso minimo 
	//evidenzia come output [0][0] questo, ovviamente non può esistere. Questa funzione evita questo problema
	public boolean remove_a_blocked_path(int[][] paths){
		boolean result = false;
		for(int i = 0; i < paths.length;i++){
			if(paths[i][1]==0){
				paths[i][1] = 200;
				result = true;
			}
		}
		return result;
	}
}
