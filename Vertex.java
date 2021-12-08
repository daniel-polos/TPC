import java.util.ArrayList;

public class Vertex {
    int id;
    int color;
    ArrayList<Vertex> neighbors;
    Vertex(int id){
        this.id = id;
        neighbors = new ArrayList<Vertex>(0);
    }
    public int getId(){
        return id;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public int getColor() {
        return color;
    }
    public void addEdge(Vertex neighbor){
        neighbors.add(neighbor);
    }
    public void removeEdge(Vertex remove_neoghbor){
        neighbors.remove(remove_neoghbor);

    }
    public ArrayList<Vertex> getNeighbors(){
        return this.neighbors;
    }
}