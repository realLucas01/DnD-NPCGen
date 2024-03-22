package DnDGen.TSWP1;

import java.util.*;

public class RandomGenerator {
	
	public static void main(String []args) {
		
		System.out.println("Hier ist die Main");
		
	}
	
	public static int diceThrower(Integer diceCount, Integer diceNumber) {
		int rolled = 0;
		int addedRolls = 0;
		Random rand = new Random();
		for(int numOfDices = diceCount; numOfDices > 0; numOfDices--) {
			rolled = rand.nextInt(diceNumber);
			rolled += 1;
			System.out.println("value: " + rolled);
			addedRolls = addedRolls + rolled;
			System.out.println("value after add: " + addedRolls);

		}
		
		return addedRolls;
	}
	
	public static List<Integer> randAbilityScore() {
		Random rand = new Random();
		List<Integer> abilityScoresValue = new ArrayList<Integer>();
		List<Integer> finishedSetForAbilityScores = new ArrayList<Integer>();
		
		for(int k = 0; k < 6; k++) {
		
			int value;
			for(int i = 0; i < 4; i++) {
				value = rand.nextInt(6);
				value += 1;
				abilityScoresValue.add(value);
			
			}
		
			Collections.sort(abilityScoresValue);
		
			System.out.println("sorted: " + abilityScoresValue);
		
			abilityScoresValue.remove(0);
		
			System.out.println("removed lowest value: " + abilityScoresValue);
			
			int addValues = 0;
			for(int j = 0; j <= 2; j++) {
				addValues += abilityScoresValue.get(j);
			}
		
			System.out.println("added values: " + addValues);
			
			finishedSetForAbilityScores.add(addValues);
			
			System.out.println("set of values for ability scores: " + finishedSetForAbilityScores);
			
			abilityScoresValue.clear();
		
		}
		
		Collections.sort(finishedSetForAbilityScores);
		System.out.println("sorted set of values for ability scores: " + finishedSetForAbilityScores);
		return finishedSetForAbilityScores;
	}
	
	public static String randCharClass() {
		String charClass = "NO CLASS";
		
		return charClass;
	}
	
	public static String randRace() {
		String race = "NO RACE";
		
		return race;
	}
	
	public static String randJob() {
		String job = "NO JOB";
		
		return job;
	}
	
	public static int randLevel() {
		int level = 0;
		
		return level;
	}
	
	public static String randDescription(String race) {
		String description = "NO DESCRIPTION";
		
		return description;
	}
	
	public static List<String> randTBF() {
		List<String> tbf = new ArrayList<String>();
		
		tbf.add("NO TBF");
		
		return tbf;
	}
	
	public static String randArmor(String charClass, String race) {
		String armor = "NO ARMOR";
		
		return armor;
	}
	
	public static String randWeapon(String charClass, String race) {
		String weapon = "NO WEAPON";
		
		return weapon;
	}
	
	public static String randDeity(String alignment) {
		String deity = "NO DEITY";
		
		return deity;
	}
	
	public static String randLanguages() {
		String language = "NO LANGUAGES";
		
		return language;
	}
	
	public static String randName(String gender, String race) {
		String name = "NO NAME";
		
		return name;
	}
	
	public static int randAge(String race) {
		int age = 0;
		
		return age;
	}
	
	public static List<String> randSkillProf(String charClass, String job) {
		List<String> skillProf = new ArrayList<String>();
		
		skillProf.add("NO SKILL PROF");
		
		return skillProf;
	}
	
	public static String randPlothook() {
		String plothook = "NO PLOTHOOK";
		
		return plothook;
	}
	
	public static String randGender() {
		Random rand = new Random();
		int genderRandomNumber = rand.nextInt(2);
		String gender = "männlich";
			if(genderRandomNumber == 1) {
				gender = "weiblich";
			}
		System.out.println(gender);
		return gender;
	}
	
	public static List<String> randSpells(String charClass, int numbSpells) {
		List<String> spells = new ArrayList<String>();
		
		spells.add("NO SPELLS");
		
		return spells;
	}
	
}