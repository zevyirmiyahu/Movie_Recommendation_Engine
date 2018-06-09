package recommender;

public class MinutesFilter implements Filter {
	private int minMinutes;
	private int maxMinutes;
	
	public MinutesFilter(int minMinutes, int maxMinutes) {
		this.minMinutes = minMinutes;
		this.maxMinutes = maxMinutes;
	}
	
	@Override
	public boolean satisfies(String id) {
		int minutes = MovieDatabase.getMinutes(id);
		if(minutes >= minMinutes && minutes <= maxMinutes) return true;
		return false;
	}
	
}
