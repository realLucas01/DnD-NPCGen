//package DnDGen.TSWP1;

import java.util.*;

public class Tswp1Application {;

		public static void main(String []args) {
			
			/* remove "//" in front of the function call to test your written functions */
			
			randGender();
			
			diceThrower(6, 6);
			
			randAbilityScore();
					
		}
		
		/* function which returns a random gender: ("mÃ¤nnlich" or "weiblich") */
		public static String randGender() {
			String gender;
			Random rand = new Random();
			int value = rand.nextInt(2);

			if (value == 0){
				gender = "female";
			}
			else{
				gender = "male";
			}
			/* 	Random will get used within all these 3 functions
			 	https://docs.oracle.com/javase/8/docs/api/java/util/Random.html
			 	https://stackoverflow.com/questions/5887709/getting-random-numbers-in-java */
			
			/* 	use print-statements to debug and figure out, if your code works properly 
			 	example: System.out.println(gender); 
			 	gender is a variable of type String */

			//System.out.println(gender);
			return gender;
		}
		
		/* function which rolls a dice with given amount of dices and dice number */
		public static int diceThrower(Integer diceCount, Integer diceNumber) {

			int addedRolls = 0;
			Random rand = new Random();

			for (int i=0; i < diceCount; i++){
				int value = rand.nextInt(diceNumber) + 1;
				addedRolls = addedRolls + value;
			}

			/* 	you want to use a for-loop, which repeats the dice-throwing-process the amount we want it to
			   	and adds the values to each other 
			   	remember to use print-statements to check if you did everything right! 
			   	addedRolls is a variable of type Integer */
			
			//System.out.println(addedRolls); 
			return addedRolls;
		}
		
		/*	- function generating 4 random values (between 1-6)
		 	- deleting the lowest value and adding the other three to each other
			- repeat the above 6 times and fill a list with these 6 values for ability scores
			- return that list */
		public static ArrayList<Integer> randAbilityScore() {

			
			ArrayList<Integer> value = new ArrayList<Integer>();
			ArrayList<Integer> finishedSetForAbilityScores = new ArrayList<Integer>();

			for ( int i=0; i < 6; i++){
				value.add(diceThrower(1, 6));
				value.add(diceThrower(1, 6));
				value.add(diceThrower(1, 6));
				value.add(diceThrower(1, 6));

				int number = value.get(0) + value.get(1) + value.get(2);

			finishedSetForAbilityScores.add(number);

				value.clear();
			}
			/* you probably want to use two Lists here
			   one for storing 4 generated values, sorting them and deleting the lowest number
			   the second to return 6 values which will later be used for ability scores */
			
			/* finishedSetForAbilityScores is a list filled with Intger-Values */

			//System.out.println(finishedSetForAbilityScores);
			return finishedSetForAbilityScores;
		}

	}

