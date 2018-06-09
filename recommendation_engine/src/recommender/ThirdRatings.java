package recommender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class ThirdRatings {
    private ArrayList<EfficientRater> myRaters;
    private HashMap<String, Integer> freq = new HashMap<String, Integer>();
    private HashMap<String, Double> sumRatings = new HashMap<String, Double>();
    
    public ThirdRatings() {
        // default constructor
        this("ratings.csv");
    }
    
    public ThirdRatings(String ratingsfile) {
    	FirstRatings firstRatings = new FirstRatings();
    	myRaters = firstRatings.loadRaters(ratingsfile);
    	freq = firstRatings.getFreqHashMap();
    	sumRatings = firstRatings.getSumRatingsHashMap();
    }
    
    public int getRaterSize() {
    	return myRaters.size();
    }
    
    public ArrayList<Rating> getAverageRatings(int minimalRaters) {
    	ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
    	ArrayList<Rating> avgRatings = new ArrayList<Rating>();
    	for(int i = 0; i < movies.size(); i++) {
    		String movie_id = movies.get(i);
    		if(getAverageByID(movie_id, minimalRaters) != 0.0) {
    			double avg = getAverageByID(movie_id, minimalRaters);
    			String title = MovieDatabase.getTitle(movie_id);
    			Rating rating = new Rating(title, avg);
    			avgRatings.add(rating);
    		}
    	}
    	Collections.sort(avgRatings); //sort list
    	return avgRatings;
    }
    
    public ArrayList<Rating> getAverageRatingsByFilter(int minimalRaters, Filter filterCriteria) {
    	double total = 0.0;
    	int count = 0;
    	ArrayList<String> movieIDs = MovieDatabase.filterBy(filterCriteria);
    	ArrayList<Rating> avgRatings = new ArrayList<Rating>();
    	for(int k = 0; k < movieIDs.size(); k++) {
    		String movie_id = movieIDs.get(k);
  			//int rater_id = Integer.parseInt(myRaters.get(k).getID());
  			int RaterAmount = freq.get(movie_id);
  			if(RaterAmount >= minimalRaters) {
  				
  				//Checks first if sumRatings contains movie_id, must check otherwise null pointer exception
  				if(sumRatings.containsKey(movie_id)) {
  					total = sumRatings.get(movie_id);
  					count = freq.get(movie_id);
  					String title = MovieDatabase.getTitle(movie_id);
  					String criteria = getCriteriaToPrint(filterCriteria, movie_id);
  					Rating rating = new Rating(title + " " + criteria, total/count);
  					avgRatings.add(rating);	
  				}
            }
        }
    	Collections.sort(avgRatings); //sort list
  		return avgRatings;
    }
    
    //Allows for proper retrieval of criteria of movie in order to be added to output statement
    private String getCriteriaToPrint(Filter filterCriteria, String movie_id) {
    	
    	if(filterCriteria instanceof AllFilters) {
    		return "Year: " + MovieDatabase.getYear(movie_id) + " Time: " 
    					+ MovieDatabase.getMinutes(movie_id) +  " Genre: " + MovieDatabase.getGenres(movie_id);
    	}
    	if(filterCriteria instanceof YearAfterFilter) {
    		return "Year: " + MovieDatabase.getYear(movie_id);
    	}
    	if(filterCriteria instanceof GenreFilter) {
    		return "Genre: " + MovieDatabase.getGenres(movie_id);
    	}
    	if(filterCriteria instanceof MinutesFilter) {
    		return "Time: " + MovieDatabase.getMinutes(movie_id);
    	}
    	if(filterCriteria instanceof DirectorFilter) {
    		return "Director(s): " + MovieDatabase.getDirector(movie_id);
    	}
    	
    	return MovieDatabase.getTitle(movie_id);
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
  	private double getAverageByID(String id, int minRaters) {
  		double total = 0;
  		double count = 0;
  		for(int k = 0; k < myRaters.size(); k++) {
  			String movie_id = myRaters.get(k).getMovieID(); 
  			int RaterAmount = freq.get(movie_id);
  			if(movie_id.equals(id) && RaterAmount >= minRaters) {
  				
            //if(movie_id.equals(id) && raterAmounts.get(movie_id) > minRaters) {
            	total = sumRatings.get(movie_id);
            	count = freq.get(movie_id);
            }
        }
  		if(count != 0) {
  			return total / count; //Return average rating for that movie ID
  		}
  		else return 0.0;
  	}
  	
  	//Uses HashMap to count each appearance of movie_id
  	private HashMap<String, Integer> numOfRaters() {
  		HashMap<String, Integer> map = new HashMap<String, Integer>();
  		for(EfficientRater ef: myRaters) {
  			String ef_movie_id = ef.getMovieID(); //movie ID found in myRaters
  			if(map.containsKey(ef_movie_id)) {
  				map.put(ef_movie_id, map.get(ef_movie_id) + 1);
  			} else if(!map.containsKey(ef_movie_id)) {
  				map.put(ef_movie_id, 1);
  			}
  		}
  		return map;
  	}
}
