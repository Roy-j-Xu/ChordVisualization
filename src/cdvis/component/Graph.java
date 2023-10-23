package cdvis.component;
import java.util.Arrays;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class Graph {
    private HashMap<Integer, LinkedList<Integer>> adjacency;

    public Graph() {
        adjacency = new HashMap<>();
    }

    public void addVertex(int vertex) {
        adjacency.put(vertex, new LinkedList<>());
    }

    public void addEdge(int source, int destination) {
        adjacency.get(source).add(destination);
        adjacency.get(destination).add(source);
    }

    public LinkedList<Integer> getNeighbors(int vertex) {
        return adjacency.get(vertex);
    }

    public LinkedList<Integer> getAllVertices() {
        return new LinkedList<>(adjacency.keySet());
    }
    
    public int vertexNumber() {
    	return this.getAllVertices().size();
    }
    
    public int[] algebraicBFS(int source, int end) {
    	int[] parent = new int[this.vertexNumber()];
    	boolean[] visited = new boolean[this.vertexNumber()];
    	int[] distance = new int[this.vertexNumber()];
    	Arrays.fill(distance, 1000);
    	
    	Queue<Integer> queue = new LinkedList<>();
    	queue.add(source);
    	parent[source] = source;
    	visited[source] = true;
    	distance[source] = 0;
    	
    	while (!queue.isEmpty()) {
    		int current = queue.poll();
    		LinkedList<Integer> randomNeighbors = new LinkedList<>(this.getNeighbors(current));
    		for (int neighbor:randomNeighbors) {
    			if (!visited[neighbor]) {
    				visited[neighbor] = true;
    				queue.add(neighbor);
    				distance[neighbor] = distance[current] + 1;
    				parent[neighbor] = current;
    			}
    		}
    	}
    	
    	int[] coef = {0,0,0};
    	int current = end;
    	int difference;
    	while (true) {
    		difference = current - parent[current];
    		if (difference == 3 || difference == -3) {
    			coef[0] += difference / 3;
    		} 
    		else if (difference == 4 || difference == -4) {
    			coef[1] += difference / 4;
    		}
    		else if (difference == 7 || difference == -7) {
    			coef[2] += difference / 7;
    		}
    		else {
    			break;
    		}
    		current = parent[current];
    	}
    	return coef;
    }

    public void print() {
        for (int i : this.getAllVertices()) {
            System.out.println(adjacency.get(i));
        }
    }
    
}
