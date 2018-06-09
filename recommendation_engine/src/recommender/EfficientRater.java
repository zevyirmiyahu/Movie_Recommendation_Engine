package recommender;

import java.util.ArrayList;
import java.util.HashMap;

public class EfficientRater implements Rater {
	private String myID;
    private String movieID;
    private HashMap<String, Rating> myRatings; //The HashMap key is the movie_id
    
    public EfficientRater(String raterID) {
    	myID = raterID;
    	myRatings = new HashMap<String, Rating>();
    }

    public EfficientRater(String id, String movieID) {
        myID = id;
        this.movieID = movieID;
        myRatings = new HashMap<String, Rating>();
    }
    

    public void addRating(String item, double rating) {
        myRatings.put(item, new Rating(item,rating));
    }

    public boolean hasRating(String item) {
    	if(myRatings.containsKey(item)){
    		return true; 
        }
        return false;
    }

    public String getID() {
        return myID;
    }
    
    public String getMovieID() {
    	return movieID;
    }

    public double getRating(String item) {
    	if (myRatings.containsKey(item)) {
    		return myRatings.get(item).getValue();
    	}
        return -1.0;
    }

    public int numRatings() {
        return myRatings.size();
    }

    public ArrayList<String> getItemsRated() {
    	return new ArrayList<String>(myRatings.keySet());
    }
}
