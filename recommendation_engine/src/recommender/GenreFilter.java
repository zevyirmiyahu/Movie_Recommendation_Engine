package recommender;

public class GenreFilter implements Filter {

	private String genre;
	
	public GenreFilter(String genre) {
		this.genre = genre;
	}
	
	@Override
	public boolean satisfies(String id) {
		return MovieDatabase.getGenres(id).contains(genre); //Movies may have several genre, use contains
	}

}
