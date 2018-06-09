package recommender;

import java.util.ArrayList;
import java.util.Random;

public class RecommendationRunner implements Recommender {
	
	@Override
	public ArrayList<String> getItemsToRate() {
		ArrayList<String> movieItems = new ArrayList<String>();
		ArrayList<String> movies = MovieDatabase.filterBy(new TrueFilter());
		Random random = new Random();
		for(int k = 0; k < 10; k++) {
			int index = random.nextInt(movies.size());
			String item = movies.get(index);
			if(movieItems.contains(item)) {
				index = random.nextInt(movies.size()); //get another random index
				item = movies.get(index); //get new random item
				movieItems.add(item);
			} 
			else {
				movieItems.add(item);
			}
		}
		return movieItems;
	}

	@Override
	public void printRecommendationsFor(String webRaterID) {
		
		try {
			FourthRatings fr = new FourthRatings();
			ArrayList<Rating> ratings = fr.getSimilarRatings(webRaterID, 1, 1);
			String header = "<table> <tr> <th>Title</th> <th>Genre</th> <th>Year</th> <th>Length</th> </th>";
			if(ratings.size() == 0) {
				System.out.println("<h1>No movies recommendations</h1>");
			}
			else {
				System.out.println(header);
				for(int i = 0; i < 10; i++) {
					String item = ratings.get(i).getItem();
					String title = MovieDatabase.getTitle(item);
					String genre = MovieDatabase.getGenres(item);
					int year = MovieDatabase.getYear(item);
					int minutes = MovieDatabase.getMinutes(item);
					String s = toString(title, genre, year, minutes);
					System.out.println(s);
				}
				System.out.println("</table>");
			}
		} catch(IndexOutOfBoundsException e) {
			e.printStackTrace();
		}
	}
	
	private String toString(String title, String genre, int year, int minutes) {
		return "<tr> <td>" + title + "</td>" + "<td>" + genre + "</td>" + "<td>" + year + "</td>" +  "<td>" + minutes + "</td> </tr>";
	}
}


