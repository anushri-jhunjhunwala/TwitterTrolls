
/**
 * This class answers all the research questions about the RAT graph like most active user, most popular story etc 
 *
 * @author Farzana Patwa and Anushri Jhunjhunwala
 * @version 12/5/19
 */
import java.util.Hashtable;
import java.util.Set;
import java.util.LinkedList;
import java.util.Vector;
import java.util.Iterator;
import java.util.Scanner;
import java.io.File;
import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
public class Investigate
{
    // instance variables - replace the example below with your own
    private RATgraph r;
    private Hashtable<String,TwitterUser> userTable;
    private Vector<String> stories;

    /**
     * Constructor for objects of class Investigate
     */
    public Investigate()
    {
        // initialise instance variables
        r = new RATgraph("All_Russian-Accounts-in-TT-stories.csv.tsv");
        userTable = r.getUserTable();
        stories = r.getStories();
    }

    /** 
     * returns the user that has been involved with the most stories (most successors)
     * 
     * @return String - screen name of the user 
     */
    public String mostStories(){
        Set<String> keys = userTable.keySet();
        String maxKey = null;
        int maxValue = 0;
        for(String key: keys){
            LinkedList<String> s = r.getRatSuccessors(key);
            if(s.size()> maxValue){
                maxKey = key;
                maxValue = r.getRatSuccessors(key).size();
                //one definition of most active user is the one that has maximum number of successors 
            }

        }
        return maxKey;
    }

    /** 
     * returns the user with the highest tweet count
     * 
     * @return String - screen name of the user 
     */
    public String mostActive(){
        Set<String> keys = userTable.keySet();
        String maxKey = null;
        int maxValue = 0;
        for(String key:keys){
            if(userTable.get(key).getTweetCount()>maxValue){
                maxKey = key;
                maxValue = userTable.get(key).getTweetCount();
                //one definition of most active user is the one that has maximum number of tweets 
            }

        }
        return maxKey;
    }

    /** 
     * returns the most popular story  
     * 
     * @return String - screen name of the user 
     */
    public String mostPopularStory(){
        String maxStory = null;
        int maxLength = 0;
        for (int i=0; i < stories.size();i++) {
            if (r.getRatPredecessors(stories.get(i)).size() > maxLength)
            {
                maxLength = r.getRatPredecessors(stories.get(i)).size(); 
                //the most popular story is the one that has maximum number of predeccessors 
                maxStory = stories.get(i);
            }
        }

        return storyName(maxStory);
    }

    /** 
     * returns the largest connected component of the graph 
     * the size of the LCC would be largestConnection().size()
     * 
     * @return String - screen name of the user 
     */
    public LinkedList<String> largestConnection(){
        Set<String> keys = userTable.keySet();
        LinkedList<String> largeC = null;
        int maxValue = 0;

        for(String key:keys){
            String name = r.getUserTable().get(key).getName();
            if((r.ratDFS(name).size())>maxValue){
                largeC = r.ratDFS(name);
                //Computes the connected components (CCs) using DFS starting from any node. 
                maxValue = (r.ratDFS(name).size()); //the size of the largest connected component
            }

        }
        return largeC;
    }

    /** 
     * returns the central node of the LCC graph 
     * 
     * @return String either name or story   
     */
    public LinkedList<String> centralNodesinLCC(){
        //list of the vertices to loop through
        LinkedList<String> list = largestConnection();
        //the nodes with smallest distance sum will get stored here
        LinkedList<String> nodes = new LinkedList<String>();
        //initial comparison for the first node
        int minSum = 10000;
        for (int i=0; i < list.size();i++) {
            String current = list.get(i);
            int currentSum = distanceSum(list.get(i));
            //this if statement adds the minimum distance sum nodes into the nods linked list
            //it will clear the list if it finds a smaller sum than the previous one
            if((currentSum==minSum)){
                nodes.add(current);

            }else if(currentSum<minSum){
                nodes.clear();
                nodes.add(current);
                minSum = currentSum;

            }
        }
        //this replaces the stories ids in the nodes list with their names to give more information 
        //about the story
        for(int i = 0; i<nodes.size(); i++){
            if(stories.contains(nodes.get(i))){
                nodes.set(i,storyName(nodes.get(i)));
                
            }
            
        }
        return nodes;

    }
    

    /**
     * checks the sum of the distance between a node and every other node in the graph
     * 
     * @param String - a node
     * @return int - sum of the distance between the node and every other node in the graph
     */
    public int distanceSum(String v){
        LinkedList<String> list = largestConnection();
        int sum = 0; //the sum of all the distance from given node v to all other nodes 
        for(String node: list){
            sum+=r.diamBetween(v,node);
        }

        return sum;
    }

    /**
     * returns the title of the story when given a story id number
     * 
     * @param String - id number of a story
     * @return String - title of the story
     */
    private String storyName(String id){
        String s = "";
        String urlName = "http://twittertrails.wellesley.edu/~trails/stories/title.php?id=" + id;
        try {
            URL u = new URL(urlName);
            
            Scanner urlScan = new Scanner( u.openStream() );
            //move past the heading of the HTML doc with this line
            String header = urlScan.nextLine();
            while (urlScan.hasNext()) {
                s += urlScan.nextLine();
                
            }
        } catch (IOException ex) {
            System.out.println(ex);
        }

        return s;
    }

    /** 
     * Returns a string representation of the investigate class
     * 
     * @return all conclusions and findings 
     * 
     *  */
    public String toString(){
        String s = "The user involved with the most stories is " + mostStories() + "\n";
        s+= "The most active user i.e. the user with the highest tweet count is " + mostActive()+ "\n";
        s+= "The most popular story i.e the story that involves maximum users is " + mostPopularStory()+ "\n";
        s+= "The size of the largest connected component in the RAT graph  is " + largestConnection().size() + "\n";
        s+= "The most central nodes in the largest connected component are \n" + centralNodesinLCC()+ "\n";
        return s;
    }

    public static void main(String[] args){
        Investigate i = new Investigate();
        System.out.println(i);
    }

}
