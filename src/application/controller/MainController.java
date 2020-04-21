package application.controller;

import java.util.ArrayList;
import java.util.List;

import application.model.Line;
import application.model.Node;
import application.model.State;
import application.util.AStarUtil;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.TextFieldTableCell;

public class MainController {
	
    @FXML
    private TableView<Line> tblInit;

    @FXML
    private TableColumn<Line, String> col_1Init;

    @FXML
    private TableColumn<Line, String> col_2Init;

    @FXML
    private TableColumn<Line, String> col_3Init;

    @FXML
    private TableView<Line> tblGoal;

    @FXML
    private TableColumn<Line, String> col_1Goal;

    @FXML
    private TableColumn<Line, String> col_2Goal;

    @FXML
    private TableColumn<Line, String> col_3Goal;
    
    @FXML
    private Label lblTime;
    
    @FXML
    private Label lblSteps;
    
    private long timeExecution;
    
    private void listDefaultValues() {
    	String col1 = String.valueOf(1);
    	String col2 = String.valueOf(2);
    	String col3 = String.valueOf(3);
    	Line line1Or = new Line(col1, col2, col3);
    	Line line1Mod = new Line(col1, col2, col3);
    	col1 = String.valueOf(4);
    	col2 = String.valueOf(5);
    	col3 = String.valueOf(6);
    	Line line2Or = new Line(col1, col2, col3);
    	Line line2Mod = new Line(col1, col2, col3);
    	col1 = String.valueOf(7);
    	col2 = String.valueOf(8);
    	col3 = "";
    	Line line3Or = new Line(col1, col2, col3);
    	Line line3Mod = new Line(col1, col2, col3);
    	tblInit.getItems().addAll(line1Or, line2Or, line3Or);
    	tblGoal.getItems().addAll(line1Mod, line2Mod, line3Mod);
    }
	
	public void inicializaTbl() {
		 initCols();
	   }

	private void initCols() {
		col_1Init.setCellValueFactory(cellData -> cellData.getValue().col1Property());
		col_2Init.setCellValueFactory(cellData -> cellData.getValue().col2Property());
		col_3Init.setCellValueFactory(cellData -> cellData.getValue().col3Property());
		col_1Goal.setCellValueFactory(cellData -> cellData.getValue().col1Property());
		col_2Goal.setCellValueFactory(cellData -> cellData.getValue().col2Property());
		col_3Goal.setCellValueFactory(cellData -> cellData.getValue().col3Property());
		editableCols();
	}

	private void editableCols() {
		col_1Init.setCellFactory(TextFieldTableCell.forTableColumn());
		col_1Init.setOnEditCommit(e -> {
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setCol1(e.getNewValue());;
		});
		col_2Init.setCellFactory(TextFieldTableCell.forTableColumn());
		col_2Init.setOnEditCommit(e -> {
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setCol2(e.getNewValue());;
		});
		col_3Init.setCellFactory(TextFieldTableCell.forTableColumn());
		col_3Init.setOnEditCommit(e -> {
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setCol3(e.getNewValue());;
		});
		col_1Goal.setCellFactory(TextFieldTableCell.forTableColumn());
		col_1Goal.setOnEditCommit(e -> {
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setCol1(e.getNewValue());;
		});
		col_2Goal.setCellFactory(TextFieldTableCell.forTableColumn());
		col_2Goal.setOnEditCommit(e -> {
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setCol2(e.getNewValue());;
		});
		col_3Goal.setCellFactory(TextFieldTableCell.forTableColumn());
		col_3Goal.setOnEditCommit(e -> {
			e.getTableView().getItems().get(e.getTablePosition().getRow()).setCol3(e.getNewValue());;
		});
		
		tblInit.setEditable(true);
		tblGoal.setEditable(true);
	}


	public void initialize() {
		inicializaTbl();
		listDefaultValues();
	}
	
	
	/*
	 * Método do click no botão: converte as duas tabelas da tela para objetos do tipo estado inicial e "goal" que seria o estado objetivo e então executa o algoritmo
	 * pega a lista retornada e chama o método para atualizar a interface
	 */
	
	@FXML
	public void btRunAlgorithmClick() throws InterruptedException {
		State initialState = convertObservabelListToState(tblInit.getItems());
		State goalState = convertObservabelListToState(tblGoal.getItems());
		ArrayList<Node> solvedList = runAStar(initialState, goalState);
		refreshInterface(solvedList);
	}
	
	/*
	 * Executa o algoritmo e antes salvando o tempo de inicio do algoritmo para subtrair e descobrir o tempo de execução do mesmo
	 * Retorna a lista solução do algoritmo
	 */

	private ArrayList<Node> runAStar(State initialState, State goalState) {
		long startTime = System.currentTimeMillis();
		ArrayList<Node> solvedList = AStarUtil.getSolvedList(initialState, goalState);
		timeExecution = System.currentTimeMillis() - startTime;
		return solvedList;
	}
	
	/*
	 * Atualiza a interface com o tempo de execução e a quantidade de movimentos, chamando também o método que exibirá a troca de estados na tela.
	 * 
	 */

	private void refreshInterface(ArrayList<Node> solvedList) throws InterruptedException {
		lblTime.setText("Tempo de execução: "+timeExecution+" ms");
		lblSteps.setText("Passos: "+solvedList.get(solvedList.size() - 1).depth); //Obs: pega a profundidade do ultimo estado, ou seja, a quantidade de movimentos.
		showSolution(solvedList);
	}
	
	/*
	 * Método que converte a lista que está representada nas tabelas em uma matriz e instancia um novo estado. (que seriam os estado iniciais e finais)
	 * 
	 */

	private State convertObservabelListToState(ObservableList<Line> lines) {
		String[][] board = new String[3][3];
		for (int i = 0; i < 3; i++) {
			Line line = lines.get(i);
			board[i][0] = line.getCol1();
			board[i][1] = line.getCol2();
			board[i][2] = line.getCol3();
		}
		State state = new State(board);
		return state;
	}
	
	/*
	 * Método que mostra a solução em tempo real e faz uma pausa de 500 ms entre cada estado
	 * 
	 */
	
	private void showSolution(List<Node> solvedList) {
		ObservableList<Line> lines = FXCollections.observableArrayList();
		tblInit.setItems(lines);
		new Thread(()->{
			for (Node node : solvedList) {
				Platform.runLater(() -> addLines(lines, node));
				try {
					Thread.sleep(500);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}).start();
	}

	private void addLines(ObservableList<Line> lines, Node node) {
		lines.clear();
		for (int i = 0; i < 3; i++) {
			lines.add(new Line(node.state.board[i][0], node.state.board[i][1], node.state.board[i][2]));
		}
	}
	
}
