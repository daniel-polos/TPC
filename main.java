import java.util.ArrayList;
import java.util.Random;
import java.lang.Math;
public class main {
    static int n=18; //Example Test
    static int k= 5; //Example Test
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Graph myGraph = new Graph(n);
        createGraph(myGraph, n); //create the actual graph graph
        if (k<=2){
            boolean k_path =k_less_3(myGraph);
            if(k_path){
                System.out.println("Success! This graph has a path length k");
            }
            if(!k_path) {
                System.out.println("There is NOT a k-path in this graph");
            }
            long finish_A = System.currentTimeMillis();
            long timeElapsed_A = finish_A - start;
            System.out.println("Runtime:" + timeElapsed_A);
            return;
        }
        Random rand = new Random();
        graph_color_algo(myGraph, k, n, rand);
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.print("Runtime:" + timeElapsed);


    }
    // ***** Example Test ***** ////
    public  static  void createGraph(Graph myGraph, int n ){
        myGraph.getVertex(0).addEdge(myGraph.getVertex(1));
        myGraph.getVertex(1).addEdge(myGraph.getVertex(2));
        myGraph.getVertex(5).addEdge(myGraph.getVertex(4));
        myGraph.getVertex(4).addEdge(myGraph.getVertex(6));
        myGraph.getVertex(5).addEdge(myGraph.getVertex(3));
        myGraph.getVertex(7).addEdge(myGraph.getVertex(8));
        myGraph.getVertex(8).addEdge(myGraph.getVertex(9));
        myGraph.getVertex(10).addEdge(myGraph.getVertex(9));
        myGraph.getVertex(11).addEdge(myGraph.getVertex(12));
        myGraph.getVertex(11).addEdge(myGraph.getVertex(15));
        myGraph.getVertex(11).addEdge(myGraph.getVertex(13));
        myGraph.getVertex(14).addEdge(myGraph.getVertex(16));
        myGraph.getVertex(16).addEdge(myGraph.getVertex(14));


    }
    public static  boolean k_less_3(Graph my_graph){
        if (k<2) {
            return true;
        }
        else{
            ArrayList<Vertex> vertices = my_graph.getVertices();

            for(int i=0; i<vertices.size(); i++ ){
                if (vertices.get(i).getNeighbors().size()>0){
                    return true;
                }
            }
            return false;
        }
    }
    public static  void graph_color_algo(Graph myGraph, int k, int n, Random rand){
        double k_double = k;
        boolean found= false;
        double k_power = Math.pow(k_double, k_double);
        for(int i=0; i<k_power; i++){
            Graph temp_graph= new Graph(n);
            temp_graph = myGraph;
            createGraph(temp_graph, n); // copying the graph to new iteration
            paint(temp_graph, k, n, rand);
            erase_Unnecessary_edges(temp_graph, k, n);
            ArrayList<Vertex> first_layer= find_k_path(temp_graph.getVertices(), 0);
            if (find_All(temp_graph,first_layer, 0)) {
                System.out.println("Success! This graph has a path length k");
                found =true;
                break;
            }
        }
        if (!found)
            System.out.println("There is NOT a k-path in this graph");
    }
    public static void paint(Graph temp_graph,int k, int n, Random rand) {
        for (int j = 0; j < n; j++) {
            temp_graph.getVertices().get(j).setColor(rand.nextInt(k)); //painting each vertex in the temp graph
        }
    }
    public static void erase_Unnecessary_edges(Graph temp_graph, int k, int n){
        ArrayList<Vertex> vertices= temp_graph.getVertices();
        for(int i=0; i<n; i++){
            Vertex vertex = vertices.get(i);
            ArrayList<Vertex> neibs = vertex.getNeighbors();
            for(int j=0; j< neibs.size(); j++){
                if (vertex.getColor()+ 1 != neibs.get(j).getColor())
                    vertex.removeEdge(neibs.get(j));
            }
        }

    }
    public static ArrayList<Vertex> find_k_path(ArrayList<Vertex> vertext_list, int color){
        ArrayList<Vertex> colored_by_k_vertexs = new ArrayList<Vertex>(0);
        for (int i = 0 ;i<vertext_list.size(); i++){
            Vertex vertex= vertext_list.get(i);
            if(vertex.getColor()==color) {
                colored_by_k_vertexs.add(vertex);
            }
        }
        return colored_by_k_vertexs;
    }
    public static boolean find_All(Graph temp_graph,ArrayList<Vertex> layer, int k_left){
        if (k_left==k) {
            return true;
        }
        if (layer.size()==0) {
            return false;
        }
        k_left++;
        for(int i=0; i<layer.size(); i++) {
            ArrayList<Vertex> neibs =find_k_path(layer.get(i).getNeighbors(),  k_left);
            if (find_All(temp_graph, neibs, k_left)==true)
                return  true;
        }
        return false;
    }
}


//////BRUT FORCE//////////
// different approach. Much less approach /////

/*import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class main {
    static int n=18;
    static int k= 5;
    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        Graph myGraph = new Graph(n);
        if (k<=2){
            boolean k_path =k_less_3(myGraph);
            if(k_path){
                System.out.println("Success! This graph has a path length k");
            }
            if(!k_path) {
                System.out.println("There is NOT a k-path in this graph");
            }
            long finish_A = System.currentTimeMillis();
            long timeElapsed_A = finish_A - start;
            System.out.println("Runtime:" + timeElapsed_A);
            return;
        }
        createGraph(myGraph, n); //create the actual graph graph
        ArrayList<ArrayList<Vertex>> allPerm = new ArrayList<ArrayList<Vertex>>(0);
        permK(myGraph.getVertices(), 0, k, allPerm);
        boolean res =search_Patch(allPerm);
        if (!res)
            System.out.println("The is NOT a k-path in this graph");
        long finish = System.currentTimeMillis();
        long timeElapsed = finish - start;
        System.out.print("Runtime:" + timeElapsed);

    }

    public static  boolean k_less_3(Graph my_graph) {
        if (k < 2) {
            return true;
        } else {
            ArrayList<Vertex> vertices = my_graph.getVertices();

            for (int i = 0; i < vertices.size(); i++) {
                if (vertices.get(i).getNeighbors().size() > 0) {
                    return true;
                }
            }
            return false;
        }
    }
    public  static  void createGraph(Graph myGraph, int n ){
        myGraph.getVertex(0).addEdge(myGraph.getVertex(1));
        myGraph.getVertex(1).addEdge(myGraph.getVertex(2));
        myGraph.getVertex(5).addEdge(myGraph.getVertex(4));
        myGraph.getVertex(4).addEdge(myGraph.getVertex(6));
        myGraph.getVertex(5).addEdge(myGraph.getVertex(3));
        myGraph.getVertex(7).addEdge(myGraph.getVertex(8));
        myGraph.getVertex(8).addEdge(myGraph.getVertex(9));
        myGraph.getVertex(10).addEdge(myGraph.getVertex(9));
        myGraph.getVertex(11).addEdge(myGraph.getVertex(12));
        myGraph.getVertex(11).addEdge(myGraph.getVertex(15));
        myGraph.getVertex(11).addEdge(myGraph.getVertex(13));
        myGraph.getVertex(14).addEdge(myGraph.getVertex(16));
        myGraph.getVertex(16).addEdge(myGraph.getVertex(14));


    }
    static  void permK(List<Vertex> p, int i, int k, ArrayList<ArrayList<Vertex>> allPerm)
    {
        if(i == k)
        {
            List<Vertex> lV=p.subList(0, k);
            ArrayList<Vertex> vertexList= new ArrayList<Vertex>(0);
            for(int l=0; l<lV.size();l++){
                vertexList.add(lV.get(l));
            }

            allPerm.add(vertexList);
            return;
        }

        for(int j=i; j<p.size(); j++)
        {
            Collections.swap(p, i, j);
            permK(p, i+1, k, allPerm);
            Collections.swap(p, i, j);
        }
    }
    public static boolean search_Patch(ArrayList<ArrayList<Vertex>> allPerm){
        for (int i= 0; i<allPerm.size(); i++){
            ArrayList<Vertex> temp_Array= allPerm.get(i);
                for(int j=0; j<temp_Array.size(); j++){
                    if (j== temp_Array.size()-1) {
                        System.out.println("Success! There is a k-path in this graph");
                        return true;
                    }
                    if(!(temp_Array.get(j).getNeighbors().contains(temp_Array.get(j+1))))
                        break;
                }
        }
        return false;
    }
}*/