
/**
 * This class reads from a file of Russian twitter accounts and their 
 * associated stories and creates a RAT graph in tgf format 
 *
 * @author Farzana Patwa and Anushri Jhunjhunwala 
 * @version 12/5/19
 */
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Hashtable;
import java.util.Vector;
import java.util.Set;
public class RATgraph 
{
    // instance variables 
    private String inFile;
    private Hashtable<String,TwitterUser> users;
    private Vector<String> stories;

    AdjListsGraph<String> RAT;

    /**
     * Constructor for objects of class RATgraph
     */
    public RATgraph(String f)
    {
        // initialise instance variables
        inFile = f;
        users = new Hashtable<String,TwitterUser>(300);
        stories = new Vector<String>();
        RAT = new AdjListsGraph<String>();
        readToGraph();
    }

    /**
     * Reads graph data from the input file  
     * @exception FileNotFoundException thrown when the input file 
     * is not found
     */
    public void readToGraph()
    {

        try { 
            //set up Scanner on the input file
            Scanner scan = new Scanner(new File(inFile));
            //read and ignore the top line in the file (header)
            String header = scan.nextLine();
            //read data from input file, one line per account
            while (scan.hasNext()) {
                //read name and id as Strings
                String name = scan.next();
                RAT.addVertex(name);
                String id = scan.next();
                //try to read the numbers 
                //NumberFormatExceptions are possible
                try { 
                    int tweet = Integer.parseInt(scan.next());
                    int storyC = Integer.parseInt(scan.next());
                    users.put(name,new TwitterUser(name,id,tweet,storyC));

                }
                catch (NumberFormatException e) { //a number was not properly formatted
                    System.out.println ("Format error in input file: in " +
                        name +"'s data");
                    System.out.println ("This account is ignored");
                    scan.nextLine();//ignore the rest of the current line from the file
                    continue; //exit *only* this loop iteration 
                }
                String[] allStories = scan.next().split(",");
                for(int i = 0; i<allStories.length; i++){
                    //will do nothing if already in graph
                    RAT.addVertex(allStories[i]);
                    RAT.addEdge(name,allStories[i]);

                    if (!stories.contains(allStories[i])) {
                        stories.add(allStories[i]);
                    }

                }
            }
            scan.close();
        }
        catch (FileNotFoundException e) {  
            System.out.println ("The file " + inFile + " was not found.");
            System.out.println ("The program terminates.");
            System.exit(0); //exit the whole program.
        }  

    }

    /** 
     * Return all the vertices, in this graph, adjacent to the given vertex.
     * 
     * @param A vertex in th egraph whose successors will be returned.
     * @return LinkedList containing all the vertices x in the graph,
     * for which an arc exists from the given vertex to x (vertex -> x).
     *
     * */
    public LinkedList<String> getRatSuccessors(String vertex){
        return RAT.getSuccessors(vertex);
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
    public LinkedList<String> getRatPredecessors(String vertex){
        return RAT.getPredecessors(vertex);
    }

    /** 
     * returns the path of the depth first traversal in the graph from given vertex 
     * 
     * @param the vertex from which the depth first traversal should begin 
     * @return LinkedList<String> of the path of the DFS
     * */
    public LinkedList<String> ratDFS(String vertex){
        return RAT.DFS(vertex);
    }

    /** 
     * returns the path of the breadth first traversal in the graph from given vertex 
     * 
     * @param the vertex from which the breadth first traversal should begin 
     * @return LinkedList<String> of the path of the BFS 
     * */
    public LinkedList<String> ratBFS(String vertex){
        return RAT.BFS(vertex);
    }
       

    /**
     * checks diameter of a BFS search between two nodes
     * 
     * @param  String - starting node, String - target node
     * @return int - the diameter of the search from a given node to the target
     */
    public int diamBetween(String v1, String v2){
        int diamCount = 0;
        Set<String> keys = users.keySet();
        Vector<String> s = stories;
        LinkedList<String> b = ratBFS(v1);
        //initialize a boolean to keep track of changes in level
        boolean isUser;
        if(keys.contains(b.get(0))){
            isUser = true;
        }else{
            isUser = false; 
        }

        for(int i = 0; i < b.size(); i++){
            String current = b.get(i);
            //when it switches from a story to a user add to the diameter
            //switch boolean to true because it switched to a user level
            if(keys.contains(current)&&isUser==false){
                diamCount+=1;
                isUser = true;

                
            }
            //when it switches from a user to a story add to the diameter
            //switch boolean to false because it switched to a story level
            else if(s.contains(current)&&isUser==true)
            {
                diamCount+=1;
                isUser = false;

            }
            //when the target is found return the diameter
            if( current.equals(v2)){
                return diamCount;
            }

        }

        return diamCount;
    }

    /**
     * gets the hashtable of users
     * 
     * @return Hashtable<String,TwitterUser> - users
     */
    public Hashtable<String,TwitterUser> getUserTable(){
        return users;
    }

    /**
     * gets the vector of stories
     * 
     * @return Vector<String> - stories
     */
    public Vector<String> getStories(){
        return stories;
    }

    /**
     * Writes the RAT graph to a tgf file
     */
    public void RATToTGF(String f){
        RAT.saveToTGF(f);
    }

    /** 
     * Returns a string representation of the rat graph
     * 
     * @return a string represenation of this graph, containing its vertices
     * and its arcs/edges
     * 
     *  */
    public String toString(){
        String s = RAT.toString();
        return s;
    }

    public static void main(String[] args){
        RATgraph r = new RATgraph("All_Russian-Accounts-in-TT-stories.csv.tsv");
        Set<String> keys = r.getUserTable().keySet();
        Vector<String> s = r.getStories();
        System.out.println(r);
        LinkedList<String> b = r.ratBFS("Jenn_Abrams");
        System.out.println(b);
        System.out.println("Checking dist from Jenn_Abrams to 7371058705 exp: 1");
        System.out.println("Result: " +r.diamBetween("Jenn_Abrams","7371058705"));
        System.out.println("Checking dist from Jenn_Abrams to AmelieBaldwin exp: 2");
        System.out.println("Result: " +r.diamBetween("Jenn_Abrams","AmelieBaldwin"));
        

       
        

    }
}
