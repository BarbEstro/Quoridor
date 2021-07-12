package gj.quoridor.player.barbieri;

import gj.quoridor.player.Player;

public class BarbieriPlayer implements Player{
	
	private int idG; //indice del giocatore
	private int idA; //indice dell'avversario
	private boolean isFirst;//indica se è il primoa giocare
	private IA ia; //strategia
	private Control control;//controllo
	private Imaginary_Table table;//tavolo
	
	public BarbieriPlayer() {
		Azzera();
	}

	@Override
	public int[] move() {
		int[] move = new int[2];
		move = ia.strategy(control);
		if(move[0] == 0)
			ia.setIdG(move[1]);
		move = control.translation(move, isFirst, idG, table);
		idG = ia.getIdG();
		return move ;
	}

	@Override
	public void start(boolean arg0) {
		this.isFirst = arg0;
		Azzera();
		table = new Imaginary_Table(9, arg0);
		ia = new IA(idG, idA, table.getSize(), arg0, table);
		control = new Control();
	}

	@Override
	public void tellMove(int[] arg0) {
		idA = control.traslationEnemy(arg0, idA, isFirst, table);
		ia.setIdA(idA);
	}
	
	public void Azzera() {
		if (isFirst == true) {
			idG = 4;
			idA = 76;
		} else {
			idG = 76;
			idA = 4;
		}
		
	}
	

}
