import java.util.*;
import java.util.ArrayList;

public class Graph {
    int n;
    ArrayList<Vertex> vertices;
    Graph(int n){
        this.n=n;
        this.vertices = new ArrayList<Vertex>(n);
        for(int i=0; i<n; i++){
            Vertex temVertex= new Vertex(i);
            this.vertices.add(temVertex);
        }
    }
    /*public static void addEdge(ArrayList<ArrayList<Integer> > adj,
                        int u, int v)
    {
        adj.get(u).add(v);
    }*/
    public Vertex getVertex(int id){
        return vertices.get(id);
    }
    public ArrayList<Vertex> getVertices(){
        return this.vertices;

    }
}

