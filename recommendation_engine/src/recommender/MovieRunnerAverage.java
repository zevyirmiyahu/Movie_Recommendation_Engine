package recommender;

import java.util.ArrayList;
import java.util.Collections;

public class MovieRunnerAverage {
	
	public void printAverageRatings() {
		SecondRatings secondRatings = new SecondRatings();
		//SecondRatings secondRatings = new SecondRatings("ratedmovies_short.csv", "ratings_short.csv");
		//System.out.println("Movie size: " + secondRatings.getMovieSize());
		//System.out.println("Rater size: " + secondRatings.getRaterSize());
		secondRatings.getAverageRatings(12);
	}
	
	public void getAverageRatingOneMovie() {
		SecondRatings secondRatings = new SecondRatings("ratedmoviesfull.csv", "ratings.csv");
		String title = "The Interview";
		String movie_id = secondRatings.getID(title);
		ArrayList<Rating> avgRatings = secondRatings.getAverageRatings(0);
		System.out.println("*****************************************");
		for(int i = 0; i < avgRatings.size(); i++) {
			if(avgRatings.get(i).getItem().equals(title)) {
				double avg = avgRatings.get(i).getValue();
				System.out.println(title + " " + avg);
			}
		}
	}
}
