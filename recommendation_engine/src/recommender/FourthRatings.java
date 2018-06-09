package recommender;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;

public class FourthRatings {

	public FourthRatings() {
        // default constructor
        this("ratings.csv");
    }
    
    public FourthRatings(String ratingsfile) {
    	RaterDatabase.initialize(ratingsfile);
    	//FirstRatings firstRatings = new FirstRatings();
    	//firstRatings.loadRaters(ratingsfile);
    	//freq = firstRatings.getFreqHashMap();
    	//sumRatings = firstRatings.getSumRatingsHashMap();
    }
    
    public int getRaterSize() {
    	return RaterDatabase.size();
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
    	FirstRatings firstRatings = new FirstRatings();
    	HashMap<String, Integer> freq = firstRatings.getFreqHashMap();
    	HashMap<String, Double> sumRatings = firstRatings.getSumRatingsHashMap();
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
    
    //Returns the average rating for a movie ID
  	private double getAverageByID(String id, int minRaters) {
  		String movie_id = id;
  		// key is movie id, value is sum of ratings for movie_id
  		HashMap<String, Double> total = new HashMap<String, Double>();
  		HashMap<String, Integer> freq = new HashMap<String, Integer>();
  		ArrayList<Rater> list = RaterDatabase.getRaters();
  		
  		//Fill HashMaps
  		for(int k = 0; k < RaterDatabase.size(); k++) {
  			
  			double rating = list.get(k).getRating(movie_id);
  			
  			if(rating != -1 && total.containsKey(movie_id)) {
  				freq.put(movie_id, freq.get(movie_id) + 1);
  				total.put(movie_id, total.get(movie_id) + rating);
  			} else if(rating != -1 && !total.containsKey(movie_id)) {
  				freq.put(movie_id, 1);
  				total.put(movie_id, rating);
  			}
  		}
  		if(total.get(movie_id) == null || freq.get(movie_id) == null) return 0.0;
  		else return total.get(movie_id) / freq.get(movie_id);
  	}
  	
  	public ArrayList<Rating> getSimilarRatings(String id, int numSimilarRaters, int minimalRaters) {
  		
  		ArrayList<Rating> ratings = getSimilarities(id); 
  		ArrayList<String> items = MovieDatabase.filterBy(new TrueFilter());
  		ArrayList<Rating> weightedAverageList = new ArrayList<Rating>();
  	
  		for(String item : items) {
  			
  			double sum = 0.0;
  			int count = 0;
  			for(int i = 0; i < numSimilarRaters; i++) {
  				
  				Rating similarRatings = ratings.get(i);
  				String RaterID = similarRatings.getItem();
  				Rater r = RaterDatabase.getRater(RaterID);
  				if(r.hasRating(item)) {
  					double weight = r.getRating(item);
  					sum += similarRatings.getValue() * weight ;
  					count++;
  				}
  			}
  			
  			if(count >= minimalRaters && !RaterDatabase.getRater(id).hasRating(item)) {
  				double weightedAverage = sum / count;
  				Rating rating = new Rating(item, weightedAverage);
  				weightedAverageList.add(rating);
  			}
  		}
  		Collections.sort(weightedAverageList);
  		Collections.reverse(weightedAverageList);
  		return weightedAverageList;
  	}
  	
  	
  	public ArrayList<Rating> getSimilarRatingsByFilter(String id, int numSimilarRaters, Filter filterCriteria, int minimalRaters) {
  		
  		ArrayList<Rating> ratings = getSimilarities(id); 
  		ArrayList<String> items = MovieDatabase.filterBy(filterCriteria);
  		ArrayList<Rating> weightedAverageList = new ArrayList<Rating>();
  	
  		for(String item : items) {
  			
  			double sum = 0.0;
  			int count = 0;
  			for(int i = 0; i < numSimilarRaters; i++) {
  				
  				Rating similarRatings = ratings.get(i);
  				String RaterID = similarRatings.getItem();
  				Rater r = RaterDatabase.getRater(RaterID);
  				if(r.hasRating(item)) {
  					double weight = r.getRating(item);
  					sum += similarRatings.getValue() * weight ;
  					count++;
  				}
  			}
  			
  			if(count >= minimalRaters && !RaterDatabase.getRater(id).hasRating(item)) {
  				double weightedAverage = sum / count;
  				Rating rating = new Rating(item, weightedAverage);
  				weightedAverageList.add(rating);
  			}
  		}
  		Collections.sort(weightedAverageList);
  		Collections.reverse(weightedAverageList);
  		return weightedAverageList;
  	}
  	
  	private double dotProduct(Rater me, Rater r) {

		double dotProduct = 0.0;
		ArrayList<String> myMovies = me.getItemsRated();
		for(String id : myMovies) {
			if(r.hasRating(id)) {
				double myRating = me.getRating(id);
				double rRating = r.getRating(id);
				myRating -= 5;
				rRating -= 5;
				dotProduct += myRating * rRating;
			}
		}	
	
  		
  		/*
  		double prod = 0.0;
  		ArrayList<String> me_ids = me.getItemsRated();
  		ArrayList<String> r_ids = r.getItemsRated();
  		for(String meID : me_ids) {
  			if(r_ids.contains(meID)) {
  				prod += (me.getRating(meID) - 5) * (r.getRating(meID) - 5);
  			}
  		}
  		*/
  		
  		
  		
  		
  		/*
  		double prod = 0.0;
  		String me_id = me.getID();
  		String r_id = r.getID();
  		
  		Rater meAfter = RaterDatabase.getRater(me_id);
  		Rater rAfter = RaterDatabase.getRater(r_id);
  		
  		ArrayList<String> items = MovieDatabase.filterBy(new TrueFilter());
  		
  		
  		for(String item : items) {
  			if(me.hasRating(item) && r.hasRating(item)) {
  				double me_rating = meAfter.getRating(item);
  				double r_rating = rAfter.getRating(item);
  				prod += (me_rating - 5.0) * (r_rating - 5.0);  				
  			}
  		}
  		*/
  		//return prod;
		return dotProduct;

  	}
  	
  	private ArrayList<Rating> getSimilarities(String id) {
  		
  		String rater_id = id; //Not to confuse id with movie id
  		Rater me = RaterDatabase.getRater(rater_id);
  		ArrayList<Rating> list = new ArrayList<Rating>();
  		
  		for(Rater rater : RaterDatabase.getRaters()) {
  			double prod = 0.0;
  			if(!rater.getID().equals(rater_id)) {
  				prod = dotProduct(me, rater);
  			}
  			if(prod >= 0.0) {
  				Rating rating = new Rating(rater.getID(), prod);
  				list.add(rating);
  			}
  		}
  		Collections.sort(list);
  		Collections.reverse(list);
  		return list;
  	}
}
