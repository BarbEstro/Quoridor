package gj.quoridor.player.barbieri;



public class Imaginary_Table {
	private Graph graph;// la scacchiera viene rappresentato dal grafo
	private int size;//grandezza del tavolo
	private boolean change;//modifica il tavolo in base al giocatore
	private Wall_Structure[] walls_structure;//struttura di muri

	// Costruttore
	public Imaginary_Table(int size, boolean change) {
		graph = new Graph(size * size);
		this.size = size;
		moldGraph(change);
		buildWall();
		this.change = change;
	}

	// setter and getter
	public Graph getGraph() {
		return graph;
	}

	public void setGraph(Graph graph) {
		this.graph = graph;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public boolean isChange() {
		return change;
	}

	public void setChange(boolean change) {
		this.change = change;
	}
	
	public Wall_Structure[] getWalls_structure() {
		return walls_structure;
	}

	public void setWalls_structure(Wall_Structure[] walls_structure) {
		this.walls_structure = walls_structure;
	}

	//*********************************** METODI*****************************
	
	//crea la scacchiera
	public void moldGraph(boolean change) {
		allNode();
		nearNode(change);
	}

	//all'inizio aggiunge i nodi nella lista
	private void allNode() {
		int size = 0;
		for (int x = 0; x < this.size; x++) {
			for (int y = 0; y < this.size; y++) {
				Node start = new Node(x, y, size);
				graph.addNode(start, size);
				size = size + 1;
			}
		}
	}

	//aggiunge tutti i nodi vicini
	private void nearNode(boolean change) {
		int size = 0;
		int ch = change == true ? 1 : -1;
		Node[] temp_adjacency = graph.getAdjacency_list();
		for (; size < graph.getSize(); size++) {
			final int x_n = temp_adjacency[size].getX();
			final int y_n = temp_adjacency[size].getY();
			final int id = temp_adjacency[size].getId();

			if (x_n + ch < this.size & change == true) {
				Node near = new Node(x_n + ch, y_n, id + 9);// nodo giu
				graph.addNode(near, size);
			} else {
				if (x_n + ch >= 0 & change == false) {
					Node near = new Node(x_n + ch, y_n, id - 9);// nodo su
					graph.addNode(near, size);
				}
			}
			if (y_n + 1 < this.size) { // nodo a destra
				Node near = new Node(x_n, y_n + 1, id + 1);
				graph.addNode(near, size);
			}

			if (y_n - 1 >= 0) {
				Node near = new Node(x_n, y_n - 1, id - 1); // nodo a sinistra
				graph.addNode(near, size);
			}

			if (x_n - 1 >= 0 & change == true) { // nodo su
				Node near = new Node(x_n - 1, y_n, id - 9);
				graph.addNode(near, size);
			} else {
				if (x_n - ch < this.size & change == false) {
					Node near = new Node(x_n - ch, y_n, id + 9);// nodo giu
					graph.addNode(near, size);
				}
			}

		}
	}

	
	//rimuove caselle in maniera verticale
	public void removeVertical(int idxW) {
		graph.removeNode(idxW, idxW + 1);
		graph.removeNode(idxW + 1, idxW);
		graph.removeNode(idxW + 9, idxW + 10);
		graph.removeNode(idxW + 10, idxW + 9);
	}

	//rimuove caselle in maniera orizzontale
	public void removeHorizontal(int idxW) {
		graph.removeNode(idxW, idxW + 9);
		graph.removeNode(idxW + 9, idxW);
		graph.removeNode(idxW + 1, idxW + 10);
		graph.removeNode(idxW + 10, idxW + 1);
	}

	//aggiunge caselle in maniera verticale
	public void addVertical(int idxW) {
		graph.addNode(graph.getAdjacency_list()[idxW], idxW + 1);
		graph.addNode(graph.getAdjacency_list()[idxW + 1], idxW);
		graph.addNode(graph.getAdjacency_list()[idxW + 9], idxW + 10);
		graph.addNode(graph.getAdjacency_list()[idxW + 10], idxW + 9);

	}

	//aggiunge caselle in maniera orizzontale
	public void addHorizontal(int idxW) {
		graph.addNode(graph.getAdjacency_list()[idxW], idxW + 9);
		graph.addNode(graph.getAdjacency_list()[idxW + 9], idxW);
		graph.addNode(graph.getAdjacency_list()[idxW + 1], idxW + 10);
		graph.addNode(graph.getAdjacency_list()[idxW + 10], idxW + 1);
	}

	/*costruisce la struttura per i muri come un grafo, infatti i muri vengono rappresentati come una lista di 
	adiacenza*/
	private void buildWall() {
		buildIndexWall();
		buildDeleteWall();
	}

	//aggiunge i muri nella lista
	private void buildIndexWall() {
		walls_structure = new Wall_Structure[128];
		int count = 0;
		boolean v_or_h = false;
		for (int i = 0; i < walls_structure.length; i++) {
			walls_structure[i] = new Wall_Structure(v_or_h, i);
			count = count + 1;
			if (count == 8) {
				v_or_h = !v_or_h;
				count = 0;
			}
		}
	}

	//aggiunge le caselle che devono essere cancellate
	private void buildDeleteWall() {
		int wall = 0;
		int indexWall = 0;
		int count = 0;
		int tempWall = 0;
		while (indexWall < 128) {
			if (walls_structure[indexWall].getWalls().isEmpty())
				addWalldelete(indexWall, wall);
			wall++;
			indexWall++;
			count++;
			if (count == 8) {
				wall = tempWall;
			}
			if (count == 16) {
				tempWall = tempWall + 9;
				wall = tempWall;
				count = 0;
			}

		}
	}

	
	//metodo che completa buildDeleteWall
	private void addWalldelete(int i, int wall) {
		walls_structure[i].getWalls().add(wall);
		walls_structure[i].getWalls().add(wall + 1);
		walls_structure[i].getWalls().add(wall + 9);
		walls_structure[i].getWalls().add(wall + 10);
	}


	/*/
	 * Metodo che mi cerca l'indice del muro in base all'id della casella.
	 * Ovviamente una casella può essere cancellata da massimo 8 muri. Questa funzione mi dice quali sono i muri che possono essere
	 * cancellati, accompagnati dalla casella che deve essere cancellata  [index wall+idCasella]
	 */
	public int[][] searchWall(int wall) {
		int[][] outcome = new int[8][2];
		initialize(outcome);
		int indxOFoutcome = 0;
		for (int i = 0; i < walls_structure.length ; i++) {
			for (int j = 0; j < walls_structure[i].getWalls().size(); j++) {
				if (walls_structure[i].getIndexWall()!= -1 & walls_structure[i].getWalls().get(j) == wall) {
					outcome[indxOFoutcome][0] = i;
					outcome[indxOFoutcome][1] = walls_structure[i].getWalls().get(0);//prende il primo elemento della lista
					indxOFoutcome = indxOFoutcome + 1;
					break;
				}
			}
		}
		return outcome;
	}

	//inizializzando l'array a -1
	private void initialize(int[][] outcome) {
		for (int i = 0; i < outcome.length; i++) {
			for (int j = 0; j < outcome[0].length; j++) {
					outcome[i][j] = -1;
			}
		}
	}

	// metodo che mi definisce se un muro è orrizontale o verticale
	public boolean isH_or_V(int indexWall){
		boolean result = false;
		
		result = walls_structure[indexWall].isV_or_h();
		
		return result;
	}

	// metodo di eleminazione gemelli per evitare l'intersezione tra muri
	public void deleteBrother(int indexwall){
		boolean V_or_H = isH_or_V(indexwall);
		if(V_or_H == false){
			if(indexwall+16>127){
				deleteIndexWall(indexwall);
				deleteIndexWall(indexwall+8);
				deleteIndexWall(indexwall-16);
			}else{
				deleteIndexWall(indexwall);
				deleteIndexWall(indexwall+8);
				deleteIndexWall(indexwall+16);
				if(indexwall-16>=0)
				deleteIndexWall(indexwall-16);
			}
		}else{
			if(indexwall%8==7){
				deleteIndexWall(indexwall);
				deleteIndexWall(indexwall-8);
				deleteIndexWall(indexwall-1);
			}else{
				deleteIndexWall(indexwall);
				deleteIndexWall(indexwall-8);
				deleteIndexWall(indexwall+1);
				if(indexwall%8!=0)
				deleteIndexWall(indexwall-1);
			}
		}
		
	}
	
	//elimina l'indice del muro ponendolo a -1
	private void deleteIndexWall(int indexwall){
		walls_structure[indexwall].setIndexWall(-1);
	}
	
}
