import java.util.Scanner;
import java.io.File;
import java.util.HashMap;
import java.util.ArrayList;
import java.util.Random;
import java.io.*;

/**
 * Class that contains helper methods for the Review Lab
 **/
public class Review {
  
  private static HashMap<String, Double> sentiment = new HashMap<String, Double>();
  private static ArrayList<String> posAdjectives = new ArrayList<String>();
  private static ArrayList<String> negAdjectives = new ArrayList<String>();
 
  
  private static final String SPACE = " ";
  
  static{
    try {
      Scanner input = new Scanner(new File("src/cleanSentiment.csv"));
      while(input.hasNextLine()){
        String[] temp = input.nextLine().split(",");
        sentiment.put(temp[0],Double.parseDouble(temp[1]));
        //System.out.println("added "+ temp[0]+", "+temp[1]);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing cleanSentiment.csv");
    }
  
  
  //read in the positive adjectives in postiveAdjectives.txt
     try {
      Scanner input = new Scanner(new File("src/positiveAdjectives.txt"));
      while(input.hasNextLine()){
        String temp = input.nextLine().trim();
        //System.out.println(temp);
        posAdjectives.add(temp);
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing positiveAdjectives.txt\n" + e);
    }   
 
  //read in the negative adjectives in negativeAdjectives.txt
     try {
      Scanner input = new Scanner(new File("src/negativeAdjectives.txt"));
      while(input.hasNextLine()){
        negAdjectives.add(input.nextLine().trim());
      }
      input.close();
    }
    catch(Exception e){
      System.out.println("Error reading or parsing negativeAdjectives.txt");
    }   
  }
  
  /** 
   * returns a string containing all of the text in fileName (including punctuation), 
   * with words separated by a single space 
   */
  public static String textToString( String fileName )
  {  
    String temp = "";
    try {
      Scanner input = new Scanner(new File(fileName));
      
      //add 'words' in the file to the string, separated by a single space
      while(input.hasNext()){
        temp = temp + input.next() + " ";
      }
      input.close();
      
    }
    catch(Exception e){
      System.out.println("Unable to locate " + fileName);
    }
    //make sure to remove any additional space that may have been added at the end of the string.
    return temp.trim();
  }
  
  /**
   * @returns the sentiment value of word as a number between -1 
   * (very negative) to 1 (very positive sentiment) 
   */
  public static double sentimentVal( String word )
  {
    try
    {
      return sentiment.get(word.toLowerCase());
    }
    catch(Exception e)
    {
      return 0;
    }
  }
  
  /**
   * Returns the ending punctuation of a string, or the empty string if there is none 
   */
  public static String getPunctuation( String word )
  { 
    String punc = "";
    for(int i=word.length()-1; i >= 0; i--){
      if(!Character.isLetterOrDigit(word.charAt(i))){
        punc = punc + word.charAt(i);
      } else {
        return punc;
      }
    }
    return punc;
  }
  
  /** 
   * Randomly picks a positive adjective from the positiveAdjectives.txt file and returns it.
   */
  public static String randomPositiveAdj()
  {
    int index = (int)(Math.random() * posAdjectives.size());
    return posAdjectives.get(index);
  }
  
  /** 
   * Randomly picks a negative adjective from the negativeAdjectives.txt file and returns it.
   */
  public static String randomNegativeAdj()
  {
    int index = (int)(Math.random() * negAdjectives.size());
    return negAdjectives.get(index);
    
  }
  
  /** 
   * Randomly picks a positive or negative adjective and returns it.
   */
  public static String randomAdjective()
  {
    boolean positive = Math.random() < .5;
    if(positive){
      return randomPositiveAdj();
    } else {
      return randomNegativeAdj();
    }
  }

/** Activity 2: totalSentiment()
  * Write the code to total up the sentimentVals of each word in a review.
 */
  
  public static double totalSentiment(String filename)
  {
	  String text = textToString(filename);
    // read in the file contents into a string using the textToString method with the filename
	  double sentimentTotal = 0;
    // set up a sentimentTotal variable

    // loop through the file contents 
	  String[] words = text.split(" ");
	  for (int i=0; i<words.length; i++) {
		  double value = sentimentVal(words[i]);
		  sentimentTotal += value;
	  }
	  return sentimentTotal;
       // find each word
       // add in its sentimentVal
       // set the file contents to start after this word
   
   
   //return sentimentTotal; 
  }


  /** Activity 2 starRating method
     Write the starRating method here which returns the number of stars for the review based on its totalSentiment.
  */
  public static int starRating(String filename)
  {
    // call the totalSentiment method with the fileName
	  double numberRating = Review.totalSentiment("src/SimpleReview.txt");
    // determine number of stars between 0 and 4 based on totalSentiment value 
    int stars = -1;
    // write if statements here
    if (numberRating>=5) {
    	stars = 5;
    } else if (numberRating>=3){
    	stars = 4;
    } else if (numberRating>=1) {
    	stars = 3;
    }else if (numberRating>=-2) {
    	stars = 2;
    }else if (numberRating>=-5) {
    	stars = 1;
    }else if (numberRating<-5) {
    	stars = 0;
    }

  
    // return number of stars
    return stars; 
  }
 public static String fakeReview(String filename) {
	 String review = " ";
	 //read in the file so we know what is says - simple review
	 review = textToString(filename);
	 
	 //break at spaces so we have a list of words
	 String [] words= review.split(" ");
	 
	 for (int i=0; i<words.length; i++) {
		 if(words[i].charAt(0) == '*') {
			 int length = words[i].length();
			 double sentimentVal = Review.totalSentiment("src/SimpleReviewWithout*.txt");
			 
			 if (sentimentVal>=0) {// if review is positive, make negative
			 	if(words[i].charAt(length-1)== ',') {
			 		words[i] = randomNegativeAdj() + ",";
			 	}	else {
				 	words [i] = randomNegativeAdj();
			 	}
			 }else if (sentimentVal<0){
			 	if(words[i].charAt(length-1)== ',') {
			 		words[i] = randomPositiveAdj() + ",";
			 	}else {
			 		words [i] = randomPositiveAdj();
			 	}
			 }
		 }
	 }
	 //if word starts with * replace with randomly generated adj
	 
	 //reassemble into "review" - one continues string
	 // make sure it has spaces
	 review = " ";
	 for (int i = 0; i < words.length; i++) {
		 review += words[i] + " ";
	 }
	 //System.out.println(sentimentVal);
	 return review;
 }
  
}