package recommender;

import java.util.ArrayList;

public class MovieRunnerWithFilter {
	
	public void printAverageRatings() {
		String ratingFile = "ratings.csv";
		String movieFile = "ratedmoviesfull.csv";
		int minNumOfRaters = 35;
		
		MovieDatabase.initialize(movieFile);
		ThirdRatings thirdRatings = new ThirdRatings(ratingFile);
		ArrayList<Rating> avgRatings = thirdRatings.getAverageRatings(minNumOfRaters);

		System.out.println("read data for " + thirdRatings.getRaterSize() + " raters");
		System.out.println("read data for " + MovieDatabase.size() + " movies");
		System.out.println("number of movies found: " + avgRatings.size());
		for(int i = 0; i < avgRatings.size(); i++) {
			System.out.println(avgRatings.get(i));
		}
	}
	
	public void printAverageRatingsByYear() {
		String ratingFile = "ratings.csv";
		String movieFile = "ratedmoviesfull.csv";
		int minNumOfRaters = 20;
		int year = 2000;
				
		MovieDatabase.initialize(movieFile);
		ThirdRatings thirdRatings = new ThirdRatings(ratingFile);
		ArrayList<Rating> avgRatings = thirdRatings.getAverageRatingsByFilter(minNumOfRaters, new YearAfterFilter(year));
		System.out.println("read data for " + thirdRatings.getRaterSize() + " raters");
		System.out.println("read data for " + MovieDatabase.size() + " movies");
		System.out.println("number of movies found: " + avgRatings.size());
		for(int i = 0; i < avgRatings.size(); i++) {
			System.out.println(avgRatings.get(i));
		}
	}
	
	public void printAverageRatingsByGenre() {
		String ratingFile = "ratings.csv";
		String movieFile = "ratedmoviesfull.csv";
		int minNumOfRaters = 20;
		String genre = "Comedy";
		
		MovieDatabase.initialize(movieFile);
		ThirdRatings thirdRatings = new ThirdRatings(ratingFile);
		ArrayList<Rating> avgRatings = thirdRatings.getAverageRatingsByFilter(minNumOfRaters, new GenreFilter(genre));
		System.out.println("read data for " + thirdRatings.getRaterSize() + " raters");
		System.out.println("read data for " + MovieDatabase.size() + " movies");
		System.out.println("number of movies found: " + avgRatings.size());
		for(int i = 0; i < avgRatings.size(); i++) {
			System.out.println(avgRatings.get(i));
		}
	}
	
	public void printAverageRatingsByMinutes() {		
		String ratingFile = "ratings.csv";
		String movieFile = "ratedmoviesfull.csv";
		int minNumOfRaters = 5;
		int minMinutes = 105;
		int maxMinutes = 135;
		
		MovieDatabase.initialize(movieFile);
		ThirdRatings thirdRatings = new ThirdRatings(ratingFile);
		ArrayList<Rating> avgRatings = thirdRatings.getAverageRatingsByFilter(minNumOfRaters, new MinutesFilter(minMinutes, maxMinutes));
		System.out.println("read data for " + thirdRatings.getRaterSize() + " raters");
		System.out.println("read data for " + MovieDatabase.size() + " movies");
		System.out.println("number of movies found: " + avgRatings.size());
		for(int i = 0; i < avgRatings.size(); i++) {
			System.out.println(avgRatings.get(i));
		}
	}
	
	public void printAverageRatingsByDirectors() {
		String ratingFile = "ratings.csv";
		String movieFile = "ratedmoviesfull.csv";
		int minNumOfRaters = 4;
		String directors = "Clint Eastwood,Joel Coen,Martin Scorsese,Roman Polanski,Nora Ephron,Ridley Scott,Sydney Pollack";
		
		MovieDatabase.initialize(movieFile);
		ThirdRatings thirdRatings = new ThirdRatings(ratingFile);
		ArrayList<Rating> avgRatings = thirdRatings.getAverageRatingsByFilter(minNumOfRaters, new DirectorFilter(directors));
		System.out.println("read data for " + thirdRatings.getRaterSize() + " raters");
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
	
	public void printeAverageRatingsByDirectorAndMinutes() {
		String ratingFile = "ratings.csv";
		String movieFile = "ratedmoviesfull.csv";
		int minNumOfRaters = 3;
		String directors = "Clint Eastwood,Joel Coen,Tim Burton,Ron Howard,Nora Ephron,Sydney Pollack";
		int minMinutes = 90;
		int maxMinutes = 180;
		
		AllFilters allfilters = new AllFilters();
		allfilters.addFilter(new DirectorFilter(directors));
		allfilters.addFilter(new MinutesFilter(minMinutes, maxMinutes));
		
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
}
