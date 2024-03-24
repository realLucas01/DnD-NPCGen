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

	//UNGETESTET
	/* TO DO @Luca */
	public static String randCharClass() {
		ArrayList<String> rndClass =  new ArrayList<String>();
		rndClass= DBMS.getRandRow("Class");

		return rndClass.get(1);

	}
	
	/* TO DO @Lenard */
	public static String randRace() {
		String race = "NO RACE";
		
		return race;
	}
	
	/* TO DO @Lenard */
	public static String randJob() {
		String job = "NO JOB";
		
		return job;
	}

	//UNGETESTET
	/* TO DO @Luca */
	public static int randLevel() {
		Random rndLevel = new Random();

		int level = rndLevel.nextInt(21);
		//Achtung, Level 0 ist möglich und ein Sonderfall der später beachtet werden muss
		
		return level;
	}
	
	/* TO DO @Lenard */
	public static String randDescription(String race) {
		String description = "NO DESCRIPTION";
		
		return description;
	}
	
	/* TO DO @Luca */
	public static List<String> randTBF() {
		List<String> tbf = new ArrayList<String>();
		
		tbf.add("NO TBF");
		
		return tbf;
	}
	
	/* TO DO @Luca */
	public static String randArmor(String charClass, String race) {
		String armor = "NO ARMOR";
		//Pain
		
		return armor;
	}
	
	/* TO DO @Luca */
	public static String randWeapon(String charClass, String race) {
		String weapon = "NO WEAPON";
		//Pain

		return weapon;
	}
	
	/* TO DO @Lenard */
	public static String randDeity(String alignment) {
		String deity = "NO DEITY";
		
		return deity;
	}

	//UNGETESTET
	/* TO DO @Luca */
	public static String randLanguages() {

		ArrayList<String> rndLang =  new ArrayList<String>();
		rndLang = DBMS.getRandRow("Language");

		return rndLang.get(1);
	}
	
	/* TO DO @Lenard */
	public static String randName(String gender, String race) {
		String name = "NO NAME";
		
		return name;
	}

	//UNGETESTET
	/* TO DO @Luca */
	public static int randAge(String race) {
		ArrayList<String> raceList =  new ArrayList<String>();
		raceList = DBMS.RowWithPref(race);

		int ageAdult = Integer.parseInt(raceList.get(4));
		int ageMax = Integer.parseInt(raceList.get(5));

		Random rndAge = new Random();

		int age = rndAge.nextInt(ageMax) + ageAdult;
		
		return age;
	}

	//UNGETESTET
	/* TO DO @Luca */
	public static List<String> randSkillProf(ArrayList<String> possibleSkills, Integer numberSkill, ArrayList<String> skillList) {
		Random rndSkill= new Random();

		//alle bereits bekannten Skills werden entfernt und wenn dann keine möglichen mehr frei sind wird die SkillList wieder zurück gegeben
		possibleSkills.removeAll(skillList);
		if(possibleSkills.isEmpty()){
			return skillList;
		}

		//es werden solange skills zufällig ausgewählt und hinzugefügt, bis die richtige Anzahl an Skills hinzugefügt wurde
		for (Integer currentNumberOfNewSkills =0; currentNumberOfNewSkills < numberSkill; currentNumberOfNewSkills++ ){

			Integer lenghtListPossibleSkills = possibleSkills.size();
			Integer skillIndex = rndSkill.nextInt(lenghtListPossibleSkills);
			skillList.add(possibleSkills.get(skillIndex));
			possibleSkills.remove(skillIndex);

		}

		return skillList;
}
	
	/* TO DO @Luca */
	public static String randPlothook() {
		String plothook = "NO PLOTHOOK";
		
		return plothook;
	}
	
	/* TO DO @Lenard */
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
	
	/* TO DO @Luca */
	public static List<String> randSpells(String charClass, int numbSpells) {
		List<String> spells = new ArrayList<String>();
		//PAIN
		spells.add("NO SPELLS");
		
		return spells;
	}
	
}