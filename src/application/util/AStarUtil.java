package application.util;

import java.util.ArrayList;
import java.util.List;

import application.model.Node;
import application.model.State;

public class AStarUtil {
	
	public static ArrayList<Node> getSolvedList(State initialState, State goalState) {
		SolverUtil.getInstance().populateHashTarget(goalState);	// Popula hash que mais tarde ser� usada para descobrir o valor de Manhattan (soma das dist�ncias de todos os elementos at� a sua posi��o original)
		PriorityQueue openList = new PriorityQueue();			//Lista aberta que funciona como uma "fila priorit�ria"
		List<Node> closedList = new ArrayList<Node>();
		Node root = new Node(null, initialState, SolverUtil.getInstance().getManhattanCost(initialState));
		openList.add(root);
		closedList.add(root);
		while (openList.size() > 0) {	// Enquanto ainda ouver N�s dentro da lista aberta continua...
			Node current = openList.getAndRemoveTop(); // Tira o N� que est� mais pr�ximo do estado final e armazena o mesmo nessa vari�vel
			if (current.state.equals(goalState)) {
				ArrayList<Node> solvedList = new ArrayList<Node>();		//	Valida se o estado atual � igual ao estado desejado	
				Node solutionNode = current;							//	caso sim adiciona o n� atual e todos os seus pais dentro da lista e retorna a lista solu��o.
				do {
					solvedList.add(solutionNode);
					solutionNode = solutionNode.root;
				} while (solutionNode != null);
				SolverUtil.getInstance().sortSolvedListByDepth(solvedList);	//	ordena a lista solu��o por profundidade (para mostrar na interface todos os passos desde o estado inicial)
				return solvedList;
			}
 			for (Integer[] neighbour : SolverUtil.getInstance().getNeighbour(current.state)) {
				State state = new State(SolverUtil.getInstance().cloneArray(current.state.board));		// Verifica quais posi��es em torno da posi��o vazia � poss�vel move-la (pega os vizinhos)
				state.swapEmptyPosition(neighbour);														// Cria estados para cada posi��o poss�vel de mover o espa�o vazio
				if (!SolverUtil.getInstance().isStateInList(closedList, state)) {						// Valida se o estado criado j� est� dentro da lista fechada, caso sim n�o cria um n� do mesmo, e nem adiciona dentro das listas aberta e fechada.
					Node n = new Node(current, state, SolverUtil.getInstance().getManhattanCost(state));
					openList.add(n);
					closedList.add(n);
				}
			}
		}
		return null;
	}
	
}
