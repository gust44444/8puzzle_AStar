package application.model;

/*
 * Nó da árvore que referencia o estado, o pai , a profundidade e o custo do mesmo.
 */


public class Node {
	
	public Node root;
	public State state;
	public int depth;
	public int cost;
	
	public Node(Node root, State state, int cost) {
		this.root = root;
		this.state = state;
		if (this.root != null) {
			this.depth = root.depth + 1;
		}
		this.cost = cost + depth;
	}

}
