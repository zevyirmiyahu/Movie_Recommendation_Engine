package recommender;

import java.util.*;

public class SecondRatings {
    private ArrayList<Movie> myMovies;
    private ArrayList<EfficientRater> myRaters;
    private HashMap<ArrayList<Movie>, ArrayList<EfficientRater>> myMap 
    											= new HashMap<ArrayList<Movie>, ArrayList<EfficientRater>>();
    
    public SecondRatings() {
        // default constructor
        this("ratedmoviesfull.csv", "ratings.csv");
    }
    
    public SecondRatings(String moviefile, String ratingsfile) {
    	FirstRatings firstRatings = new FirstRatings();
    	myMovies = firstRatings.loadMovies(moviefile);
    	myRaters = firstRatings.loadRaters(ratingsfile);
    	myMap.put(myMovies, myRaters);
    }
    
    public int getMovieSize() {
    	return myMovies.size();
    }
    
    public int getRaterSize() {
    	return myRaters.size();
    }
    
    public String getID(String title) {
    	String id = "NO SUCH TITLE";
    	for(int i = 0; i < myMovies.size(); i++) {
    		if(myMovies.get(i).getTitle().equals(title)) {
    			id = myMovies.get(i).getID();
    		}
    	}
    	return id;
    }
    
    //Returns the title of a movie given a movie_id, if not movie is found returns, "movie was NOT found."
    public String getTitle(String movie_id) {
    	String title = "Movie was NOT found.";
    	for(int i = 0; i < myMovies.size(); i++) {
    		if(myMovies.get(i).getID().equals(movie_id)) {
    			title = myMovies.get(i).getTitle();
    		}
    	}
    	return title;
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
    	ArrayList<Rating> avgRatings = new ArrayList<Rating>();
    	for(int i = 0; i < myMovies.size(); i++) {
    		String movie_id = myMovies.get(i).getID();
    		if(getAverageByID(movie_id, minimalRaters) != 0.0) {
    			double avg = getAverageByID(movie_id, minimalRaters);
    			String title = getTitle(movie_id);
    			System.out.println(title + " " + avg);
    			Rating rating = new Rating(title, avg);
    			avgRatings.add(rating);
    		}
    	}
    	lowestAvgScoredMovie(avgRatings);
    	return avgRatings;
    }
    
    //Finds lowest scored movie (May need to fix)
    private void lowestAvgScoredMovie(ArrayList<Rating> avgRatings) {
    	double temp = avgRatings.get(0).getValue(); //set to first element and compare from there
    	String title = avgRatings.get(0).getItem();
    	for(int i = 0; i < avgRatings.size(); i++) {
    		if(avgRatings.get(i).getValue() < temp) {
    			temp = avgRatings.get(i).getValue();
    			title = avgRatings.get(i).getItem();
    		}
    	}
    	System.out.println("The movie: " + title + " has the lowest average rating of: " + temp);
    }
    
  //Returns the average rating for a movie ID
  	private double getAverageByID(String id, int minimalRaters) {
  		double total = 0;
  		double count = 0;
  		for(int k = 0; k < myRaters.size(); k++) {
  			String movie_id = myRaters.get(k).getMovieID();
            if (movie_id.equals(id) && numberOfRaters(movie_id) > minimalRaters) {
            	total += myRaters.get(k).getRating(id);
            	count++;
            }
        }
  		if(count != 0) {
  			return total / count; //Return average rating for that movie ID
  		}
  		else return 0.0;
  	}
  	
  	//Checks the amount of raters
  	private int numberOfRaters(String movie_id) {
  		int freq = 0;
  		for(int i = 0; i < myRaters.size(); i++) {
  			if(myRaters.get(i).getMovieID().equals(movie_id)) {
  				freq++;
  			}
  		}
  		return freq;
  	}
}
