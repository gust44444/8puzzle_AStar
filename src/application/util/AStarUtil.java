package application.util;

import java.util.ArrayList;
import java.util.List;

import application.model.Node;
import application.model.State;

public class AStarUtil {
	
	public static ArrayList<Node> getSolvedList(State initialState, State goalState) {
		SolverUtil.getInstance().populateHashTarget(goalState);	// Popula hash que mais tarde será usada para descobrir o valor de Manhattan (soma das distâncias de todos os elementos até a sua posição original)
		PriorityQueue openList = new PriorityQueue();			//Lista aberta que funciona como uma "fila prioritária"
		List<Node> closedList = new ArrayList<Node>();
		Node root = new Node(null, initialState, SolverUtil.getInstance().getManhattanCost(initialState));
		openList.add(root);
		closedList.add(root);
		while (openList.size() > 0) {	// Enquanto ainda ouver Nós dentro da lista aberta continua...
			Node current = openList.getAndRemoveTop(); // Tira o Nó que está mais próximo do estado final e armazena o mesmo nessa variável
			if (current.state.equals(goalState)) {
				ArrayList<Node> solvedList = new ArrayList<Node>();		//	Valida se o estado atual é igual ao estado desejado	
				Node solutionNode = current;							//	caso sim adiciona o nó atual e todos os seus pais dentro da lista e retorna a lista solução.
				do {
					solvedList.add(solutionNode);
					solutionNode = solutionNode.root;
				} while (solutionNode != null);
				SolverUtil.getInstance().sortSolvedListByDepth(solvedList);	//	ordena a lista solução por profundidade (para mostrar na interface todos os passos desde o estado inicial)
				return solvedList;
			}
 			for (Integer[] neighbour : SolverUtil.getInstance().getNeighbour(current.state)) {
				State state = new State(SolverUtil.getInstance().cloneArray(current.state.board));		// Verifica quais posições em torno da posição vazia é possível move-la (pega os vizinhos)
				state.swapEmptyPosition(neighbour);														// Cria estados para cada posição possível de mover o espaço vazio
				if (!SolverUtil.getInstance().isStateInList(closedList, state)) {						// Valida se o estado criado já está dentro da lista fechada, caso sim não cria um nó do mesmo, e nem adiciona dentro das listas aberta e fechada.
					Node n = new Node(current, state, SolverUtil.getInstance().getManhattanCost(state));
					openList.add(n);
					closedList.add(n);
				}
			}
		}
		return null;
	}
	
}
