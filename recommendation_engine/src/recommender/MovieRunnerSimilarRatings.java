package recommender;

import java.util.ArrayList;

public class MovieRunnerSimilarRatings {
	
	public void printAverageRatings() {
		String ratingFile = "ratings_short.csv";
		String movieFile = "ratedmovies_short.csv";
		int minNumOfRaters = 1;
		
		RaterDatabase.initialize(ratingFile);
		MovieDatabase.initialize(movieFile);
		FourthRatings fourthRatings = new FourthRatings(ratingFile);
		
		ArrayList<Rating> avgRatings = fourthRatings.getAverageRatings(minNumOfRaters);

		System.out.println("read data for " + RaterDatabase.size() + " raters");
		System.out.println("read data for " + MovieDatabase.size() + " movies");
		System.out.println("number of movies found: " + avgRatings.size());
		for(int i = 0; i < avgRatings.size(); i++) {
			System.out.println(avgRatings.get(i));
		}
	}
	
	public void printAverageRatingsByYearAfterAndGenre() {
		String ratingFile = "ratings.csv";
		String movieFile = "ratedmoviesfull.csv";
		int minNumOfRaters = 8;
		int year = 1990;
		String genre = "Drama";
		
		AllFilters allfilters = new AllFilters();
		allfilters.addFilter(new YearAfterFilter(year));
		allfilters.addFilter(new GenreFilter(genre));
		
		MovieDatabase.initialize(movieFile);
		ThirdRatings thirdRatings = new ThirdRatings(ratingFile);
		ArrayList<Rating> avgRatings = thirdRatings.getAverageRatingsByFilter(minNumOfRaters, allfilters);
		System.out.println("read data for " + thirdRatings.getRaterSize() + " raters");
		System.out.println("read data for " + MovieDatabase.size() + " movies");
		System.out.println("number of movies found: " + avgRatings.size());
		for(int i = 0; i < avgRatings.size(); i++) {
			System.out.println(avgRatings.get(i));
		}
	}
	
	public void printSimilarRatings() {
		String ratingFile = "ratings.csv";
		String movieFile = "ratedmoviesfull.csv";
		String rater_id = "71";
		int numSimilarRaters = 20;
		int numMinimalRaters = 5;
		int counter = 0;
		
		RaterDatabase.initialize(ratingFile);
		MovieDatabase.initialize(movieFile);
		FourthRatings fourthRatings = new FourthRatings(ratingFile);
		
		ArrayList<Rating> list = fourthRatings.getSimilarRatings(rater_id, numSimilarRaters, numMinimalRaters);
		
		for(Rating rating : list) {
			System.out.println(counter + " " + MovieDatabase.getTitle(rating.getItem()) + ", " + rating.getValue());
			counter++;
		}
	}
	
	public void printerSimilarRatingsByGenre() {
		String ratingFile = "ratings.csv";
		String movieFile = "ratedmoviesfull.csv";
		String rater_id = "964";
		String genre = "Mystery";
		
		int numSimilarRaters = 20;
		int numMinimalRaters = 5;
		int counter = 0;
		
		RaterDatabase.initialize(ratingFile);
		MovieDatabase.initialize(movieFile);
		FourthRatings fourthRatings = new FourthRatings(ratingFile);
		GenreFilter filterCriteria = new GenreFilter(genre);
		
		ArrayList<Rating> list = fourthRatings.getSimilarRatingsByFilter(rater_id, numSimilarRaters, filterCriteria, numMinimalRaters);
		
		for(Rating rating : list) {
			System.out.println(counter + " " + MovieDatabase.getTitle(rating.getItem()) + ", " 
								+ rating.getValue() + "\n" + MovieDatabase.getGenres(rating.getItem()));
			System.out.println(); //space
			counter++;
		}
	}
	
	public void printSimilarRatingsByDirector() {
		String ratingFile = "ratings.csv";
		String movieFile = "ratedmoviesfull.csv";
		String rater_id = "120";
		String directors = "Clint Eastwood,J.J. Abrams,Alfred Hitchcock,Sydney Pollack,David Cronenberg,Oliver Stone,Mike Leigh";
		
		int numSimilarRaters = 10;
		int numMinimalRaters = 2;
		int counter = 0;
		
		RaterDatabase.initialize(ratingFile);
		MovieDatabase.initialize(movieFile);
		FourthRatings fourthRatings = new FourthRatings(ratingFile);
		DirectorFilter filterCriteria = new DirectorFilter(directors);
		
		ArrayList<Rating> list = fourthRatings.getSimilarRatingsByFilter(rater_id, numSimilarRaters, filterCriteria, numMinimalRaters);
		
		for(Rating rating : list) {
			System.out.println(counter + " " + MovieDatabase.getTitle(rating.getItem()) + ", " 
								+ rating.getValue() + "\n" + MovieDatabase.getDirector(rating.getItem()));
			System.out.println(); //space
			counter++;
		}
	}
	
	public void printSimilarRatingsByGenreAndMinutes() {
		String ratingFile = "ratings.csv";
		String movieFile = "ratedmoviesfull.csv";
		String rater_id = "168";
		String genre = "Drama";
		int minRunTime = 80;
		int maxRunTime = 160;
		
		int numSimilarRaters = 10;
		int numMinimalRaters = 3;
		int counter = 0;
		
		RaterDatabase.initialize(ratingFile);
		MovieDatabase.initialize(movieFile);
		FourthRatings fourthRatings = new FourthRatings(ratingFile);
		AllFilters allFilters = new AllFilters();
		allFilters.addFilter(new GenreFilter(genre));
		allFilters.addFilter(new MinutesFilter(minRunTime, maxRunTime));
		
		ArrayList<Rating> list = fourthRatings.getSimilarRatingsByFilter(rater_id, numSimilarRaters, allFilters, numMinimalRaters);
		
		for(Rating rating : list) {
			System.out.println(counter + " " + MovieDatabase.getTitle(rating.getItem()) + ", " 
								+ MovieDatabase.getMinutes(rating.getItem()) + ", " + rating.getValue() 
								+ "\n" + MovieDatabase.getGenres(rating.getItem()));
			System.out.println(); //space
			counter++;
		}
	}
	
	public void printSimilarRatingsByYearAfterAndMinutes() {
		String ratingFile = "ratings.csv";
		String movieFile = "ratedmoviesfull.csv";
		String rater_id = "314";
		int year = 1975;
		int minRunTime = 70;
		int maxRunTime = 200;
		
		int numSimilarRaters = 10;
		int numMinimalRaters = 5;
		int counter = 0;
		
		RaterDatabase.initialize(ratingFile);
		MovieDatabase.initialize(movieFile);
		FourthRatings fourthRatings = new FourthRatings(ratingFile);
		AllFilters allFilters = new AllFilters();
		allFilters.addFilter(new YearAfterFilter(year));
		allFilters.addFilter(new MinutesFilter(minRunTime, maxRunTime));
		
		ArrayList<Rating> list = fourthRatings.getSimilarRatingsByFilter(rater_id, numSimilarRaters, allFilters, numMinimalRaters);
		
		for(Rating rating : list) {
			System.out.println(counter + " " + MovieDatabase.getTitle(rating.getItem()) + ", " 
								+ MovieDatabase.getMinutes(rating.getItem()) + ", " + rating.getValue() 
								+ "\n" + MovieDatabase.getYear(rating.getItem()));
			System.out.println(); //space
			counter++;
		}
	}
}
