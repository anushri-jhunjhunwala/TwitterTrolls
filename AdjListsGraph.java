/**
 * representation of a graph using adjacent lists for vertices and acts 
 *
 * @author Farzana and Anushri
 * @version 11/6/2019
 */
import java.util.Vector;
import java.util.LinkedList;
import java.util.Scanner;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.File;
import java.io.IOException;
import javafoundations.LinkedQueue;
import javafoundations.ArrayStack;
public class AdjListsGraph<T> implements Graph<T>
{
    // instance variables 
    protected Vector<T> vertices;
    protected Vector<LinkedList<T>> arcs; // indices correspond to the vertices vector
    /**
     * Constructor for objects of class AdjListsGraph
     */
    public AdjListsGraph()
    {
        // initialise instance variables
        vertices = new Vector<T>();
        arcs = new Vector<LinkedList<T>>();
    }

    /** 
     * Returns a string representation of this graph.
     * 
     * @return a string represenation of this graph, containing its vertices
     * and its arcs/edges
     * 
     *  */
    public String toString(){
        String s = "This graph contains "+vertices.size() + " vertices";
        s =s+ "\n Vertices: " + vertices;

        for(int i=0; i<arcs.size(); i ++){
            s = s+"\n From " + vertices.get(i) + ":" + arcs.get(i);

        }
        s = s + "\n"+ vertices.toString();
        return s;
    }

    /** 
     * Returns a boolean indicating whether this graph is empty or not.
     * A graph is empty when it contains no vertice,a nd of course, no edges.
     *  
     *  @return true if this graph is empty, false otherwise.
     */
    public boolean isEmpty(){
        return vertices.size() == 0;
    }

    /** 
     * Returns the number of vertices in this graph. 
     * 
     * @return the number of vertices in this graph
     */
    public int getNumVertices(){
        return vertices.size();
    }

    /** 
     * Returns the number of arcs in this graph.
     * An arc between Verteces A and B exists, if a direct connection
     * from A to B exists.
     * 
     * @return the number of arcs in this graph
     *  */
    public int getNumArcs(){
        int count = 0;
        for(int i = 0; i<arcs.size(); i++){
            count = count + arcs.elementAt(i).size();

        }
        return count;
    }

    /** 
     * Adds the given vertex to this graph
     * If the given vertex already exists, the graph does not change
     * 
     * @param The vertex to be added to this graph
     * */
    public void addVertex (T vertex){
        if(!vertices.contains(vertex)) //this ensures that nothing happens 
        //if the given vertex exists already

        { 
            vertices.add(vertex);
            arcs.add(new LinkedList<T>());
        }

    }

    /** 
     * Removes the given vertex from this graph.
     * If the given vertex does not exist, the graph does not change.
     * 
     * @param the vertex to be removed from this graph
     *  */
    public void removeVertex (T vertex){
        if(vertices.contains(vertex)){
            int index = vertices.indexOf(vertex);
            vertices.remove(vertex);
            arcs.remove(index);
            for(int i = 0; i<arcs.size(); i++){
                arcs.get(i).remove(vertex);

            }
        }
    }

    /** 
     * Inserts an arc between two given vertices of this graph.
     * if at least one of the vertices does not exist, the graph 
     * is not changed.
     * 
     * @param the origin of the arc to be added to this graph
     * @param the destination of the arc to be added to this graph
     * 
     *  */
    public void addArc (T vertex1, T vertex2){
        if(vertices.contains(vertex1)&&vertices.contains(vertex2)){

            int index = vertices.indexOf(vertex1);
            arcs.get(index).add(vertex2);

        }
    }

    /** 
     * Returns true if an arc (direct connection) exists 
     * from the first vertex to the second, false otherwise
     * 
     * @return true if an arc exists between the first given vertex (vertex1),
     * and the second one (vertex2),false otherwise
     * 
     *  */
    public boolean isArc (T vertex1, T vertex2){
        int index = vertices.indexOf(vertex1);
        return arcs.get(index).contains(vertex2);
    }

    /** 
     * Removes the arc between two given vertices of this graph.
     * If one of the two vertices does not exist in the graph,
     * the graph does not change.
     * 
     * @param the origin of the arc to be removed from this graph
     * @param the destination of the arc to be removed from this graph
     * 
     * */
    public void removeArc (T vertex1, T vertex2){
        if(vertices.contains(vertex1)&&vertices.contains(vertex2)){
            int index = vertices.indexOf(vertex1);
            arcs.get(index).remove(vertex2);

        }

    }

    /** 
     * Inserts the edge between the two given vertices of this graph,
     * if both vertices exist, else the graph is not changed.
     * 
     * @param the origin of the edge to be added to this graph
     * @param the destination of the edge to be added to this graph
     * 
     *  */
    public void addEdge (T vertex1, T vertex2){
        addArc (vertex1, vertex2);
        addArc (vertex2,vertex1);

    }

    /** 
     * Removes the edge between the two given vertices of this graph,
     * if both vertices exist, else the graph is not changed.
     * 
     * @param the origin of the edge to be removed from this graph
     * @param the destination of the edge to be removed from this graph
     * 
     */
    public void removeEdge (T vertex1, T vertex2){
        removeArc(vertex1,vertex2);
        removeArc(vertex2,vertex1);
    }

    /** 
     * Returns true if an edge exists between two given vertices, i.e,
     * an arch exists from the first vertex to the second one, and an arc from
     * the second to the first vertex, false otherwise.
     *  
     * @return true if an edge exists between vertex1 and vertex2, 
     * false otherwise
     * 
     * */
    public boolean isEdge (T vertex1, T vertex2){
        if(vertices.contains(vertex1)&&vertices.contains(vertex2)){

            int index = vertices.indexOf(vertex1);
            int index2 = vertices.indexOf(vertex2);
            return arcs.get(index).contains(vertex2)&&arcs.get(index2).contains(vertex1);
        }else{
            return false;
        }

    }

    /** 
     * Returns true if the graph is undirected, that is, for every
     * pair of nodes i,j for which there is an arc, the opposite arc
     * is also present in the graph, false otherwise.  
     * 
     * @return true if the graph is undirected, false otherwise
     * */
    public boolean isUndirected(){
        for(int i = 0; i < vertices.size(); i++){
            LinkedList<T> current = arcs.get(i);
            T vertex = vertices.get(i);
            for(int j = 0; j < current.size(); j++){
                if(!isEdge(vertex,current.get(j))){
                    return false;
                }

            }

        }
        return true;
    }

    /** 
     * Return all the vertices, in this graph, adjacent to the given vertex.
     * 
     * @param A vertex in th egraph whose successors will be returned.
     * @return LinkedList containing all the vertices x in the graph,
     * for which an arc exists from the given vertex to x (vertex -> x).
     *
     * */
    public LinkedList<T> getSuccessors(T vertex){
        if(vertices.contains(vertex)){
            int index = vertices.indexOf(vertex); 
            return arcs.get(index);
        }else{
            return null;
        }

    }

    /** 
     * Return all the vertices x, in this graph, that precede a given
     * vertex.
     * 
     * @param A vertex in the graph whose predecessors will be returned.
     * @return LinkedList containing all the vertices x in the graph,
     * for which an arc exists from x to the given vertex (x -> vertex).
     * 
     * */
    public LinkedList<T> getPredecessors(T vertex){
        LinkedList<T> predecessor = new LinkedList<T>();
        for(int i = 0; i<vertices.size(); i++){
            T curVert = vertices.get(i);
            LinkedList<T> successors = getSuccessors(curVert);
            if(successors.contains(vertex)){
                predecessor.add(curVert);

            }

        }
        return predecessor;
    }

    /** 
     * Writes this graph into a file in the TGF format.
     * 
     * @param the name of the file where this graph will be written 
     * in the TGF format.
     * */
    public void saveToTGF(String tgf_file_name){
        try{
            PrintWriter writer = new PrintWriter(new File(tgf_file_name));
            //writes the corresponding number to vertex
            for(int i = 0; i < vertices.size(); i++){
                writer.println((i+1) + " " + vertices.get(i));
            }
            writer.println("#");
            for(int i = 0; i < vertices.size(); i++){
                int currentNum = i + 1;
                LinkedList<T> curList = arcs.get(i);
                for(int j = 0; j<curList.size();j++){
                    int pointTo = vertices.indexOf(curList.get(j))+1;
                    writer.println(currentNum+" "+ pointTo);
                }

            }
            writer.close();
        }
        catch(IOException e){
            System.out.println(e);

        }
    }

    /** 
     * returns the path of the breadth first traversal in the graph from given vertex 
     * 
     * @param the vertex from which the breadth first traversal should begin 
     * */
    public LinkedList<T> BFS(T vertex){
        T curVert;
        LinkedList<T> iter = new LinkedList<T>();
        if(!vertices.contains(vertex)){
            return iter;
        }
        LinkedQueue<T> queue = new LinkedQueue<T>(); //BFS uses queue data structure
        boolean [] visited = new boolean[vertices.size()];
        for(int i = 0; i < visited.length; i++){
            visited[i] = false; //initially set booleans for each corresponding vertex to false 
        }
        queue.enqueue(vertex); // add vertex to queue
        visited[vertices.indexOf(vertex)] = true;
        while(!queue.isEmpty()){ 
            curVert = queue.dequeue();
            iter.add(curVert);
            LinkedList<T> adj = getSuccessors(curVert);
            for(int i=0; i<adj.size(); i++){
                if(!visited[vertices.indexOf(adj.get(i))]){
                    queue.enqueue(adj.get(i));
                    visited[vertices.indexOf(adj.get(i))] = true;
                }

            }

        }
        return iter;
    }

    /** 
     * returns the path of the depth first traversal in the graph from given vertex 
     * 
     * @param the vertex from which the depth first traversal should begin 
     * */
    public LinkedList<T> DFS(T vertex){
        T curVert;
        LinkedList<T> iter = new LinkedList<T>();
        if(!vertices.contains(vertex)){
            return null; //if the vertex from where BFS should begin is not in the graph, then return null
        }
        ArrayStack<T> stack = new ArrayStack<T>(); // BFS uses stack data structure
        boolean [] visited = new boolean[vertices.size()]; 
        //keeps track of whether a vertice is in BFS path or not 

        for(int i = 0; i < visited.length; i++){
            visited[i] = false; //initially set the booleans for each vertice to false 
        }
        stack.push(vertex); // take the first vertex
        visited[vertices.indexOf(vertex)] = true; // visit the first vertext
        iter.add(vertex);
        while(!stack.isEmpty()){

            curVert = stack.peek();
            LinkedList<T> successors = getSuccessors(curVert);
            if(successors.size()!=0){ // if the vertex does have successors
                boolean allVisit = true;
                for(int i = 0; i<successors.size(); i++){
                    if(!visited[vertices.indexOf(successors.get(i))]){ 
                        //if each of the successors has not been visited 
                        allVisit = false;

                    }
                }
                if(allVisit){
                    stack.pop(); 
                    // if all the successors of that vertex have been visited then remove from stack
                }else{    
                    compare:
                    for(int i = 0; i<successors.size(); i++){ //go through each successor and visit 
                        if(!visited[vertices.indexOf(successors.get(i))]){
                            stack.push(successors.get(i));
                            iter.add(successors.get(i));
                            visited[vertices.indexOf(successors.get(i))] = true;
                            break compare;
                        }

                    }
                }

            }else{
                stack.pop();
            }

        }
        return iter;
    }

    public static void main(String[] args){
        System.out.println("*****TESTING*****");
        System.out.println("*****test empty graph*****");
        AdjListsGraph<String> Connected = new AdjListsGraph<String>();
        System.out.println("getNumArcs() expecting: 0 result: " + Connected.getNumArcs());
        System.out.println("getNumVertices() expecting: 0 result: " 
            + Connected.getNumVertices());
        System.out.println("isEmpty() expecting: true result: " 
            + Connected.isEmpty());
        System.out.println("*****test addVertex() *****");
        Connected.addVertex("A");
        System.out.println("getNumVertices() expecting: 1 result: " 
            + Connected.getNumVertices());
        Connected.addVertex("B");
        System.out.println("getNumVertices() expecting: 2 result: " 
            + Connected.getNumVertices());
        System.out.println("*****test addArc() and isArc() graph*****");
        System.out.println("adding arc between A and B");
        Connected.addArc("A","B");
        System.out.println("isArc() expecting: true result: " 
            + Connected.isArc("A","B"));
        System.out.println("*****test isEdge() *****");
        System.out.println("isEdge() expecting: false result: " 
            + Connected.isEdge("A","B"));
        Connected.addArc("B","A");
        System.out.println("adding an edge between B and A");
        System.out.println("isEdge() expecting: true result: " 
            + Connected.isEdge("A","B"));
        System.out.println("*****test removeVertex() *****");
        Connected.addVertex("C");
        Connected.addVertex("D");
        Connected.addArc("A","C");
        Connected.addArc("B","C");
        Connected.addArc("D","C");
        System.out.println("*****test removeArc() *****");
        System.out.println(Connected);
        System.out.println("Now removing arc from B to A");
        Connected.removeArc("B","A");
        System.out.println(Connected);
        System.out.println("*****test isUndirected()*****");
        System.out.println("expecting false for isUndirected() result:" + Connected.isUndirected());
        Connected.saveToTGF("Connected.tgf");

        System.out.println("creating a disconnected graph with 2 vertices");
        AdjListsGraph<Integer> disconnected = new AdjListsGraph<Integer>();
        disconnected.addVertex(1);
        disconnected.addVertex(2);
        System.out.println(disconnected);
        disconnected.saveToTGF("Disconnected.tgf");

        System.out.println("Creating a tree with 8 vertices");
        AdjListsGraph<Integer> tree = new AdjListsGraph<Integer>();
        tree.addVertex(1);
        tree.addVertex(2);
        tree.addVertex(3);
        tree.addVertex(4);
        tree.addVertex(5);
        tree.addVertex(6);
        tree.addVertex(7);
        tree.addArc(1,2);
        tree.addArc(1,3);
        tree.addArc(2,4);
        tree.addArc(2,5);
        tree.addArc(3,6);
        tree.addArc(3,7);
        System.out.println(tree);
        tree.saveToTGF("Tree.tgf");

        AdjListsGraph<Integer> cycle = new AdjListsGraph<Integer>();
        cycle.addVertex(1);
        cycle.addVertex(2);
        cycle.addVertex(3);
        cycle.addEdge(1,2);
        cycle.addEdge(2,3);
        cycle.addEdge(1,3);
        System.out.println("*****test isUndirected()*****");
        System.out.println(cycle);
        System.out.println("expecting true for isUndirected() result:" + cycle.isUndirected());
        cycle.saveToTGF("Cycle.tgf");

        System.out.println("*****Testing getSuccessors and getPredecessors*****");
        System.out.println("For Connected expecting: successors from A:[B,C] predecessors from A:[]");
        System.out.println(Connected.getSuccessors("A")+ " "+ Connected.getPredecessors("A"));
        System.out.println("For disconnected expecting: successors from 1:[] predecessors from 1:[]");
        System.out.println(disconnected.getSuccessors(1)+ " "+ disconnected.getPredecessors(1));
        System.out.println("For tree expecting: successors from 2:[4, 5] predecessors from 2:[1]");
        System.out.println(tree.getSuccessors(2)+ " "+ tree.getPredecessors(2));
        System.out.println("For cycle expecting: successors from 3:[2, 1] predecessors from 3:[1, 2]");
        System.out.println(cycle.getSuccessors(3)+ " "+ cycle.getPredecessors(3));

        System.out.println("*****Testing BFS and DFS*****");
        System.out.println("For Connected expecting: DFS from A:[A, B, C] BFS from A:[A, B, C]");
        System.out.println(Connected.DFS("A")+ " "+ Connected.BFS("A"));
        System.out.println("For Connected expecting: DFS from C:[C] BFS from C:[C]");
        System.out.println(Connected.DFS("C")+ " "+ Connected.BFS("C"));
        System.out.println("For disconnected expecting: DFS:[1] BFS:[1]");
        System.out.println(disconnected.DFS(1)+ " "+ disconnected.BFS(1));
        System.out.println("For tree expecting: DFS from 1:[1, 2, 4, 5, 3, 6, 7] BFS from 1:[1, 2, 3, 4, 5, 6, 7]");
        System.out.println(tree.DFS(1)+ " "+ tree.BFS(1));
        System.out.println("For tree expecting: DFS from 2:[2, 4, 5] BFS from 2:[2,4,5]");
        System.out.println(tree.DFS(2)+ " "+ tree.BFS(2));
        System.out.println("For cycle expecting: DFS:[1,2,3] BFS:[1,2,3]");
        System.out.println(cycle.DFS(1)+ " "+ cycle.BFS(1));

    }
}
