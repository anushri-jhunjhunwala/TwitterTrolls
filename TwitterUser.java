
/**
 * This object represents a twitter user and contains their name id tweet count
 * and story count
 *
 * @author Farzana Patwa Anushri Jhunjhunwala
 * @version 12/5/19
 */
public class TwitterUser
{
    // instance variables - replace the example below with your own
    private String name; //screen name 
    private String userId; // user id 
    private int tCount; // tweet count
    private int sCount; //story count
    

    /**
     * Constructor for objects of class TwitterUser
     */
    public TwitterUser(String n, String u, int t, int s)
    {
        name = n;
        userId = u;
        tCount = t;
        sCount = s;
        
    }
    /**
     * gets the name of the twitter user
     * 
     * @return String - name of the user
     */
    public String getName(){
        return name;
    }
    /**
     * gets the id of the twitter user
     * 
     * @return String - id of the user
     */
    public String getID(){
        return userId;
    }
    /**
     * gets the tweet count of the twitter user
     * 
     * @return int - tweet count of the user
     */
    public int getTweetCount(){
        return tCount;
    }
    /**
     * gets the story count of the twitter user
     * 
     * @return int - story count of the user
     */
    public int getStoryCount(){
        return sCount;
    }
    /** 
     * Returns a string representation of the twitter user 
     * 
     * @return String - representation of the object
     * 
     *  */
    public String toString(){
        String s = "Name: " + name + " ID:" +userId + " Tweet Count: " + tCount + " Story Count: " + sCount;
        return s;
    }
    
    

    
}
