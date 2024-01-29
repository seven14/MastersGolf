
   // need the keyboard input
import java.util.Scanner;
   // Random class for automated execution
import java.util.Random;


public class MastersGolf {

   public static void main(String[] args) {
   
         // Random names generated at: http://random-name-generator.info/ (50, M/F, Rare)
      String[] autoPlayers = {"Alyson Myles", "Kemberly Battle", "Heidy Garland", "Easter Solis", "Leigha Andersen", "Margarett Ruff", "Micheline Ness", "Latrina Egan", "Eneida Sneed", "Lea Earl", 
                        "Lavone Roney", "Kimiko Peter", "Setsuko Mcclendon", "Maia Beers", "Kasie Peyton", "Sanda Easley", "Dionne Mosby", "Denae Cameron", "Broderick Parnell", "Karleen Ricks", 
                        "Britt Mayes", "Rolanda Snowden", "Dudley Langley", "Mac Hudgens", "Rebbecca Hyatt", "Jamika Hooper", "Denyse Scully", "Inge Woodcock", "Gaston Mintz", "Lisette Ocasio", 
                        "Katharina Whitmire", "Stevie Sage", "Nilda Foust", "Trudy Schell", "Candelaria Boatwright", "Gia Nesbitt", "Valery Jung", "Sylvie Escamilla", "Aundrea Meador", "Jamila Anglin", 
                        "Jaleesa Hadden", "Coralie Southerland", "Crysta Guajardo", "Penni Mast", "Yael Hardin", "Margert Bader", "Tristan Aguirre", "Monnie Short", "Cathrine Whelan", "Mathilda Lipscomb"};
         // RNG
      Random gimmeSome = new Random();
         // Set to 1 if you want random score cards, extra credits :)
      int fullAuto = 1;

         // initalize playerCount
      int playerCount = 0;
         // Player name length
      int howLong = 0;
         // Array for storing best player stats (ID, combinedScore) dummy value for score comparison
      int[] topDog = {0, 200};
         // Banner decoration
      String dash = "-";

         // Create a String Scanner object to read from the keyboard. 
      Scanner getStr = new Scanner(System.in);
         // Create an Int Scanner object (Work around while I figure a better way to deal with string "new line" and int intake issues)
         // * A new line feed (i.e \n) IS NOT automatically generated when the nextInt() method takes in the values. 
         // * It simply takes the "next int" and moves on. This is the same for the rest of the nextFoo() methods EXCEPT nextLine().
      Scanner getInt = new Scanner(System.in);
      
         // Create scorecard object
      ScoreCardGetter scorecard = new ScoreCardGetter();
      
      
         // Greeting Banner
      System.out.println("It's the Master's Golf Tournament. Time to track scores for the golfers.");
      
         // Check if we are switched to auto
      if(fullAuto != 1) {
            // Get number of players, validate input
         do {
            System.out.println("\nPlease enter the number of players: ");
            while (!getInt.hasNextInt()) {
               System.out.println("That's not a valid number!");
               System.out.println("\nPlease enter the number of players: ");
               getInt.next();
            }
            playerCount = getInt.nextInt();
               // Player count, min: 1, max: 40
            if((playerCount < 1) || (playerCount > 40)) { System.out.println("That's not a valid number!"); }
         } while ((playerCount < 1) || (playerCount > 40));
      } else { // loading a fresh magazine
         playerCount = (gimmeSome.nextInt(36) + 5);
      }
      
      
         // Record stats for each player in the count
      for(int count = 0; count < playerCount; count++) {
      
            // Combined score holder, reset each player iteration
         int bothScores = 0;
            // Generic score holder, reset each player iteration
         int score = 0;
         
            // Check if we are switched to auto
         if(fullAuto != 1) {
               // Player banner message, picked 100 as max length, (max_len/2 - half_len " Player ## ")
            System.out.println(dash.repeat(45) + " Player " + (count+1) + " " + dash.repeat(45));
               // Prompt for player name
            System.out.println("Please enter the Player " + (count+1) + " Name: ");
               // Collect user input
            String name = getStr.nextLine();
               // Measure names for header formating
            if(howLong < name.length()) { howLong = name.length(); }
            scorecard.setGolfer(count, name);
            
            
               // Collect Day 1 score and validate
            do {
               System.out.println("Please enter the day 1 score for " + name + " : ");
               
               while (!getInt.hasNextInt()) {
                  System.out.println("That's not a valid number!");
                  System.out.println("Please enter the day 1 score for " + name + " : ");
                  getInt.next();
               }
               score = getInt.nextInt();
                  // Validate score is in range
               if((score < 1) || (score > 95)) { System.out.println("That's not a valid number!"); }
            } while ((score < 1) || (score > 95));
               // Store Day 1 score
            scorecard.setScore1(count, score);
               // Add to combined score
            bothScores += score;
            
            
               // Collect Day 2 score and validate
            do {
               System.out.println("Please enter the day 2 score for " + name + " : ");
               while (!getInt.hasNextInt()) {
                  System.out.println("That's not a valid number!");
                  System.out.println("Please enter the day 2 score for " + name + " : ");
                  getInt.next();
               }
               score = getInt.nextInt();
                  // Validate score is in range
               if((score < 1) || (score > 95)) { System.out.println("That's not a valid number!"); }
            } while ((score < 1) || (score > 95));
               // Store Day 2 score
            scorecard.setScore2(count, score);
               // Add to combined score
            bothScores += score;
               // Store combined score
            scorecard.setCombined(count, bothScores);
               // Find the lowest combined scores while I have them handy
            if(bothScores < topDog[1]) {
               topDog[0] = count;
               topDog[1] = bothScores;
            }
            
         } else { // spinning up all barrels
               // Player banner message
            System.out.println(dash.repeat(43) + " Player " + (count+1) + " " + dash.repeat(43));
               // Grab a name
            String name = autoPlayers[count];
            System.out.println(name);
            //String junk = get.next();  // Consume newline left-over, need a better way to deal with this
               // Measure names for header formating
            if(howLong < name.length()) { howLong = name.length(); }
               // Store player name
            scorecard.setGolfer(count, name);
               // Generate a score, best legal score is an 18
            score = ( gimmeSome.nextInt(77) + 18 );
            System.out.println("Day1 Score: " + score);
               // Store Day 1 score
            scorecard.setScore1(count, score);
               // Add to combined score
            bothScores += score;
               // Generate a score, best legal score is an 18
            score = ( gimmeSome.nextInt(77) + 18 );
            System.out.println("Day2 Score: " + score);
               // Store Day 2 score
            scorecard.setScore2(count, score);
               // Add to combined score
            bothScores += score;
               // Store combined score
            scorecard.setCombined(count, bothScores);
               // Find the lowest combined scores while I have them handy
            if(bothScores < topDog[1]) {
               topDog[0] = count;
               topDog[1] = bothScores;
            }
         }
      }
      
      
         // Print the results
         // Tournament header
      System.out.println(dash.repeat(37) + " Tournament Statistics " + dash.repeat(37));
         // Fields header
      System.out.printf("%-"+(howLong+3)+"s%-15s%-15s%-15s", "PlayerID", "Day1 Score", "Day2 Score", "Combined Score\n");
         // Loop through scores to display
      for(int count = 0; count < playerCount; count++) {
         System.out.printf("%-"+(howLong+3)+"s%-15d%-15d%-15d\n",scorecard.getGolfer(count), scorecard.getScore1(count), scorecard.getScore2(count), scorecard.getCombinedScore(count));
      }
         // Display leader
      System.out.println(dash.repeat(45) + " Leader " + dash.repeat(45));
         // Use the topDog determined prior
      System.out.println(scorecard.getGolfer(topDog[0]));
         // Within stroke header
      System.out.println(dash.repeat(31) + " Players Within 10 Strokes of Leader " + dash.repeat(30));
         // Loop through to determine runners-up
      for(int count = 0; count < playerCount; count++) {
         if( (scorecard.getCombinedScore(count) <= (topDog[1] + 10)) && (scorecard.getCombinedScore(count) >= (topDog[1] - 10)) ) {
               // Do not show leader in runners-up list
            if( count != topDog[0] ) {  System.out.println(scorecard.getGolfer(count));  }
         }
      }
         // Display Good Bye banner
      System.out.println(dash.repeat(36) + " See Ya' At the 19th Hole " + dash.repeat(36));

   }
}

      class ScoreCardGetter {
            // Player names array
         String[] golfPlayers = new String[40];
            // Round 1 scores array
         int[] round1Scores = new int[40];
            // Round 2 scores array
         int[] round2Scores = new int[40];
            // Combined scores array
         int[] combinedScores = new int[40];         
            // Store player name method
         void setGolfer(int num, String golfer) { golfPlayers[num] = golfer; }
            // Store player Day1 score method
         void setScore1(int num, int score1) { round1Scores[num] = score1; }
            // Store player Day2 score method
         void setScore2(int num, int score2) { round2Scores[num] = score2; }
            // Store player combined score method
         void setCombined(int num, int both) { combinedScores[num] = both; }
         
            // Fetch player name method
         String getGolfer(int num) { return golfPlayers[num]; }
            // Fetch player Day1 score method
         int getScore1(int num) { return round1Scores[num]; }
            // Fetch player Day2 score method
         int getScore2(int num) { return round2Scores[num]; }
            // Fetch player combined score method
         int getCombinedScore(int num) { return combinedScores[num]; }
      }



