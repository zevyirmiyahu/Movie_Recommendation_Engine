package recommender;

public class DirectorFilter implements Filter {
	private String[] listOfDirectors;
	
	public DirectorFilter(String directors) {
		listOfDirectors = directors.split(",");
	}
		
	@Override
	public boolean satisfies(String id) {
		for(String director : listOfDirectors) {
			if(MovieDatabase.getDirector(id).contains(director)) {
				return true;
			}
		}
		return false;
	}
}
