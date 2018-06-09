package recommender;

import edu.duke.*;
import java.util.*;
import org.apache.commons.csv.*;

public class FirstRatings {
	
	ArrayList<EfficientRater> raters = new ArrayList<EfficientRater>();
	private HashMap<String, Integer> freq = new HashMap<String, Integer>(); //Stores movie title and how many times a movie was rated
	private HashMap<String, Double> sumRatings = new HashMap<String, Double>(); //Stores movie id and sum of the ratings for it
	private ArrayList<String> movieIDs = new ArrayList<String>();
	
	public ArrayList<EfficientRater> loadRaters(String fileName) {
		//String is movie_id for this HashMap
		String path = "src/recommender/data/" + fileName;
		FileResource fr = new FileResource(path);
		CSVParser parser = fr.getCSVParser();
		for(CSVRecord record : parser) {
			String rater_id = record.get("rater_id");
			String movie_id = record.get("movie_id");
			
			double rating  = Double.parseDouble(record.get("rating"));
			EfficientRater effRater = new EfficientRater(rater_id, movie_id);
			effRater.addRating(movie_id, rating);
			
			if(!freq.containsKey(movie_id)) {
				movieIDs.add(movie_id);
				freq.put(movie_id, 1); //Hash maps for calculating avg rating
				sumRatings.put(movie_id, rating);
				raters.add(effRater); //ArrayList for holding efficient rater objects
			} 
			else {
				freq.put(movie_id, freq.get(movie_id) + 1);
				sumRatings.put(movie_id, sumRatings.get(movie_id) + rating);
			}
		}
		return raters;
	}
	
	public HashMap<String, Integer> getFreqHashMap() {
		return freq;
	}
	
	public HashMap<String, Double> getSumRatingsHashMap() {
		return sumRatings;
	}
	
	public void testLoadRaters() {
		ArrayList<EfficientRater> raters = loadRaters("ratings.csv");
		String rater_id = "193";
		int countID = 0; //Number of times a particular ID appears
		int maxRatingsAmount = 0;
		int movieFreq = 0; //Number of times movie appears
		String movieIDMax = ""; //MovieID that appears the most. *Must be type string to contain all digits
		String maxByRater = ""; //rater_id
		HashMap<String, Integer> mapID = new HashMap<String, Integer>();
		HashMap<String, Integer> mapMovieID = new HashMap<String, Integer>(); //Holds movie idea and frequency
		for(EfficientRater rater : raters) {
			
			if(rater.getID().equals(rater_id)) {
				countID++;
			}
			//HashMap used to count number of times a movie was rated
			if(mapMovieID.containsKey(rater.getMovieID())) {
				int val = mapMovieID.get(rater.getMovieID());
				mapMovieID.put(rater.getMovieID(), val + 1);
			}
			else {
				mapMovieID.put(rater.getMovieID(), 1);
			}
			
			//HashMap used to count number of times a rater rated a movie
			if(mapID.containsKey(rater.getID())) {
				mapID.put(rater.getID(), mapID.get(rater.getID()) + 1);
			}
			else {
				mapID.put(rater.getID(), 1);
			}
		}
		
		for(String movieID : mapMovieID.keySet()) {
			if(mapMovieID.get(movieID) > movieFreq) {
				movieFreq = mapMovieID.get(movieID);
				movieIDMax = movieID;
			}
		}
		
		for(String id : mapID.keySet()) {
			if(mapID.get(id) > maxRatingsAmount) {
				maxRatingsAmount = mapID.get(id);
				maxByRater = id;
			}
		}
		String movieID = "1798709";
		int freq = mapMovieID.get(movieID);
		int totalNumOfUniqMovies = mapMovieID.size(); //Total number of unique movies rated
		System.out.println("Movie " + movieIDMax + " was rated the most with " + movieFreq + " ratings.");
		System.out.println("Movie " + movieID + " was rated " + freq + " times.");
		System.out.println("Number of unique movies that have been rated is " + totalNumOfUniqMovies);
		System.out.println("---------------------------------");
		System.out.println("Total number of raters " + raters.size());
		System.out.println("The total number of times the rater ID " 
								+ rater_id + " appears is " + countID + " times.");
		System.out.println("---------------------------------");
		System.out.println("The maximum number of ratings is " 
								+ maxRatingsAmount + " by rater ID: " + maxByRater);
	}
	
	public ArrayList<Movie> loadMovies(String fileName) {
		ArrayList<Movie> movies = new ArrayList<Movie>();
		String path = "src/recommender/data/" + fileName;
		FileResource fr = new FileResource(path);
		CSVParser parser = fr.getCSVParser();
		for(CSVRecord record : parser) {
			String id = record.get("id");
			String title = record.get("title");
			String year = record.get("year");
			String genre = record.get("genre");
			String director = record.get("director");
			String country = record.get("country");
			String poster = record.get("poster");
			int length = Integer.parseInt(record.get("minutes"));
			Movie movie = new Movie(id, title, year, genre, director, country, poster, length);
			movies.add(movie);
		}
		return movies;
	}
	
	public void testLoadMovie() {
		ArrayList<Movie> movies = loadMovies("ratedmoviesfull.csv");
		HashMap<String, Integer> directors = new HashMap<String, Integer>(); //Name of directorand number of movies directed
		int comedyCount = 0; // Number of comedies in file
		int greaterCount = 0; //Number of movies longer than x amount of minutes
		int directorCount = 0; //Number of directors for a movie
		int movieLength = 150;
		for(Movie movie : movies) {
			System.out.println(movie.toString());
			if(movie.getGenres().contains("Comedy")) {
				comedyCount++;
			}
			if(movie.getMinutes() > movieLength) {
				greaterCount++;
			}
			
			
			String directorNames = movie.getDirector();
			if(directors.containsKey(directorNames)) {
				directors.put(directorNames, directors.get(directorNames) + 1);
			}
			else {
				directors.put(directorNames, 1);
			}
			
		}
		int amount = 0;
		String name = "";
		for(String director : directors.keySet()) {
			if(directors.get(director) > amount) {
				amount = directors.get(director);
				name = director;
			}
		}
		System.out.println("---------------------------------");
		System.out.println(name + " directed the most movies with a total of " + amount);
		System.out.println("---------------------------------");
		System.out.println("The number of movies in the file are: " + movies.size());
		System.out.println("The number of comedies is: " + comedyCount);
		System.out.println("Movies greater than " + movieLength + " minutes are: " + greaterCount);
	}
}
