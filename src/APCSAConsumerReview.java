
public class APCSAConsumerReview {

	public static void main(String[] args) {
		//double value = Review.sentimentVal("useful");
		//System.out.println(value);
		
		double total = Review.totalSentiment("src/SimpleReview.txt");
		System.out.println("Total Sentiment: " + total);
		
		//double stars = Review.starRating("src/SimpleReview.txt");
		//System.out.println("You get " + stars + " stars");
	
		
		String fake = Review.fakeReview("src/SimpleReview.txt");
		System.out.println(fake);
	}

}
