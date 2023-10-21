package cdvis.component;
import java.util.HashMap;
import java.util.LinkedList;

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

    public void print() {
        for (int i : this.getAllVertices()) {
            System.out.println(adjacency.get(i));
        }
    }
    
}
