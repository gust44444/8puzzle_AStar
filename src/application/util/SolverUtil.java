package application.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import application.model.Node;
import application.model.State;

public class SolverUtil {
	
	private static Map<String, Integer[]> hashTarget;
	
	private static SolverUtil instance;
	
	public static SolverUtil getInstance() {
		if (instance == null) {
			instance = new SolverUtil();
		}
		return instance;
	}
	
	public String[][] cloneArray(String[][] board) {
		String[][] newBoard = new String[3][3];
		for(int i=0; i<board.length; i++) {
			for(int j=0; j<board[i].length; j++) {
				newBoard[i][j]=board[i][j];				
			}
		}
		return newBoard;
	}
	
	public boolean isStateInList(List<Node> closedList, State state) {
		for (Node node : closedList) {
			if (node.state.equals(state)) {
				return true;
			}
		}
		return false;
	}

	public void populateHashTarget(final State goalState) {
		hashTarget = new HashMap<String, Integer[]>();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				hashTarget.put(goalState.board[i][j], new Integer[] {i, j});
			}
		}
	}
	
	public int getManhattanCost(final State currentState) {
		int h = 0;
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				Integer[] goalPostionItem = hashTarget.get(currentState.board[i][j]);
				h += Math.abs(goalPostionItem[0] - i) + Math.abs(goalPostionItem[1] - j);
			}
		}
		return h;
	}
	
	public ArrayList<Integer[]> getNeighbour(State currentState) {
		ArrayList<Integer[]> positionsNeighbor = new ArrayList<Integer[]>();
		if (currentState.emptyPosition[0] - 1 >= 0) {
			positionsNeighbor.add(new Integer[] {currentState.emptyPosition[0] - 1, currentState.emptyPosition[1]});
		}
		if (currentState.emptyPosition[1] - 1 >= 0) {
			positionsNeighbor.add(new Integer[] {currentState.emptyPosition[0], currentState.emptyPosition[1] - 1 });
		}
		if (currentState.emptyPosition[0] + 1 < 3) {
			positionsNeighbor.add(new Integer[] {currentState.emptyPosition[0] + 1, currentState.emptyPosition[1]});
		}
		if (currentState.emptyPosition[1] + 1 < 3) {
			positionsNeighbor.add(new Integer[] {currentState.emptyPosition[0], currentState.emptyPosition[1] + 1});
		}
		return positionsNeighbor;
	}
	
	public void sortSolvedListByDepth(ArrayList<Node> solvedList) {
		Collections.sort(solvedList, new Comparator<Node>(){
			public int compare(Node a, Node b) {
				return (int) (a.depth - b.depth);
			}
		});
	}

}
