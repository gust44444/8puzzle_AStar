package application.util;

import java.util.ArrayList;
import java.util.List;

import application.model.Node;

public class PriorityQueue {
	
	private List<Node> nodes = new ArrayList<Node>();
	
	public int size() {
		return nodes.size();
	}
	
	public void add(Node nodeAdd) {
		nodes.add(nodeAdd);
	}
	
	public Node getAndRemoveTop() {
		int best_index = 0;
		int best_priority = nodes.get(0).cost;
		int size = nodes.size();
		for (int i = 1; i < size; i++) {
			if (best_priority > nodes.get(i).cost) {
				best_priority = nodes.get(i).cost;
				best_index = i;
			}
		}
		Node n = nodes.get(best_index);
		nodes.remove(best_index);
		return n;
	}

}
