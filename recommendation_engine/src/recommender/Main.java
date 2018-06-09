package recommender;

public class Main {
	public static void main(String[] args) {
		FirstRatings fr = new FirstRatings();
		MovieRunnerAverage ma = new MovieRunnerAverage();
		MovieRunnerWithFilter mf = new MovieRunnerWithFilter();
		MovieRunnerSimilarRatings mr = new MovieRunnerSimilarRatings();
		RecommendationRunner rr = new RecommendationRunner();
		//mf.printAverageRatings();
		//mf.printAverageRatingsByYear();
		//mf.printAverageRatingsByGenre();
		//mf.printAverageRatingsByMinutes();
		//mf.printAverageRatingsByDirectors();
		//mf.printAverageRatingsByYearAfterAndGenre();
		//mf.printeAverageRatingsByDirectorAndMinutes();
		//fr.testLoadMovie();
		//fr.testLoadRaters();
		//ma.printAverageRatings();
		//ma.getAverageRatingOneMovie();
		//mr.printAverageRatings();
		//mr.printSimilarRatings();
		//mr.printerSimilarRatingsByGenre();
		//mr.printSimilarRatingsByDirector();
		//mr.printSimilarRatingsByGenreAndMinutes();
		//mr.printSimilarRatingsByYearAfterAndMinutes();
		rr.printRecommendationsFor("71");
	}
}
