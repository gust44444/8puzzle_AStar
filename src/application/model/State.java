package application.model;

import java.util.Arrays;

/*
 * Estado do tabuleiro, que armazena uma matriz do tabuleiro, e a posição vazia.
 */

public class State {
	
	public String[][] board;
	public Integer[] emptyPosition;
	
	public State(String[][] board) {
		this.board = board;
		setEmptyPosition();
	}
	
	public void setEmptyPosition() {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				if (board[i][j].equals("")) {
					emptyPosition = new Integer[]{i,j};
					return;
				}
			}
		}
	}

	@Override
	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		State other = (State) obj;
		if (!Arrays.deepEquals(board, other.board))
			return false;
		return true;
	}
	
	public void swapEmptyPosition(Integer[] newPosition) {
		board[emptyPosition[0]][emptyPosition[1]] = board[newPosition[0]][newPosition[1]];
		board[newPosition[0]][newPosition[1]] = "";
		emptyPosition = newPosition;
	}

}
