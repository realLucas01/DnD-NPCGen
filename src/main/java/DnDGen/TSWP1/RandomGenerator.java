package DnDGen.TSWP1;

import java.util.*;

public class RandomGenerator {
	
	public static void main(String []args) {
		
		System.out.println("Hier ist die Main");
		
	}
	//UNGETESTET
	//Aufteilung eines großen String in kleinere
	public static ArrayList<String> bigStringToDividedString(String bigString){
		ArrayList<String> dividedString = new ArrayList<>(); //rückgabe, des aufgeteilten Strings als eine Liste
		String[] partsOfTheBigString= bigString.split(","); //Teilung des großen Strings an den Stelle wo ein Komma ist
		Integer numberOfPartStrings = partsOfTheBigString.length; // Anzahl der Strings die voneinander getrennt wurden

		for (int currentPart=0; currentPart < numberOfPartStrings; currentPart++){ //jeder Teilstring wird der Liste hinzugefügt
			if (dividedString.contains(partsOfTheBigString[currentPart])){

			}else {
				dividedString.add(partsOfTheBigString[currentPart]);
			}
		}
		return dividedString;
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
	/* @Luca */
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
	/* @Luca */
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

	//UNGETESTET
	// Funktion um alle gewünschten Prof aus Klasse, Rasse und Features zu bekommen
	/* @Luca */
	public static ArrayList<String> figureOutProfList (String neededProfList, String charClass, String race ){
		ArrayList<String> profList = new ArrayList<>(); // Liste mit Ergebnissen die zurück gegeben wird
		ArrayList<String> databaseReturn = new ArrayList<>(); // Liste die von der DB zurück kommt
		ArrayList<String> featureList = new ArrayList<>(); // Rassen Features
		ArrayList<String> dividedString = new ArrayList<>(); // Liste die die SubStrings einer Spalte aus der DB enthalten
		String kindOfFeature; // letzte Spalte aus der DB um Festzustellen um was es sich hier handelt

	switch(neededProfList){	// Entscheidung welche ProfListe man benötigt
		case "armor":
			databaseReturn = DBMS.getRow("Class", charClass );
			profList = bigStringToDividedString(databaseReturn.get(4));

			databaseReturn = DBMS.getRow("Race", race);
			featureList = bigStringToDividedString(databaseReturn.get(3));	//Datensätzte werden aus der DB genommen, die richtigen Spalten ausgewählt und die Strings auseinander genommen

			for ( String feature : featureList){	// die erhaltenen Rassen Features werden durchgecheckt ob welche dabei sind die Armor Prof geben
				databaseReturn = DBMS.getRow("Features", feature);
				kindOfFeature = databaseReturn.get(3);

				if(kindOfFeature.contains("armor:")){	// der Teil des Features an dem man Feststellt ob es sich um einen Armor Prof handelt wird entfernt
					kindOfFeature.replace("armor:", "");
					profList.addAll(bigStringToDividedString(kindOfFeature));
					break;
				}
			}
			break;
		case "weapon":	//siehe "armor" nur jetzt mit den waffen
			databaseReturn = DBMS.getRow("Class", charClass );
			profList = bigStringToDividedString(databaseReturn.get(3));

			databaseReturn = DBMS.getRow("Race", race);
			featureList = bigStringToDividedString(databaseReturn.get(3));

			for ( String feature : featureList){
				databaseReturn = DBMS.getRow("Features", feature);
				kindOfFeature = databaseReturn.get(3);

				if(kindOfFeature.contains("weapon:")){
					kindOfFeature.replace("weapon:", "");
					profList.addAll(bigStringToDividedString(kindOfFeature));
					break;
				}
		//jederzeit erweiterbar mit weiteren cases
			}
			break;
	}
		Set<String> removeDuplicate = new HashSet<>(profList); // die ArrayList wird in ein Set gestopft um Duplicate innerhalb zu entfernen
		profList.clear();
		profList.addAll(removeDuplicate); // diese wird hier wieder zu einer ArrayList
		return profList;
	}
	
	/* TO DO @Luca */
	public static String randArmor(ArrayList<String> armorProf) {
		String armor = "NO ARMOR";
		//Pain
		
		return armor;
	}
	//UNGETESTET
	/* @Luca */
	public static String randWeapon(ArrayList<String> weaponProf) {
		Random rndNumber = new Random();	// für zufallszahlen
		//int rndWeaponProf  = 0;
		int lengthOfWeaponProf = weaponProf.size();
		String weapon;	//das Ergebniss was zurück gegeben wird
	
		//rndWeaponProf = rndNumber.nextInt(lengthOfWeaponProf);

		String rndWeapon = weaponProf.get(rndNumber.nextInt(lengthOfWeaponProf)); // aus der Prof Liste wird ein Eintrag zufällig ausgewählt

		if(rndWeapon.contains("weapon")){ //hier wird gescheckt ob es direkt eine Waffe ist oder eine Prof in einem Waffenbereich
			weapon= DBMS.getRowWithPref("weapon", rndWeapon).get(1);
		}else {
			weapon=rndWeapon;
		}
		//String weapon = "NO WEAPON";

		return weapon;
	}
	
	/* TO DO @Lenard */
	public static String randDeity(String alignment) {
		String deity = "NO DEITY";
		
		return deity;
	}

	//UNGETESTET
	/* @Luca */
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
	/* @Luca */
	public static int randAge(String race) {
		ArrayList<String> raceList =  new ArrayList<String>();
		raceList = DBMS.getRow("Race", race);

		int ageAdult = Integer.parseInt(raceList.get(4));
		int ageMax = Integer.parseInt(raceList.get(5));

		Random rndAge = new Random();

		int age = rndAge.nextInt(ageMax) + ageAdult;
		
		return age;
	}

	//UNGETESTET
	/* @Luca */
	public static List<String> randSkillProf(ArrayList<String> possibleSkills, Integer numberSkill, ArrayList<String> knownskillList) {
		Random rndSkill= new Random();

		//alle bereits bekannten Skills werden entfernt und wenn dann keine möglichen mehr frei sind, wird die SkillList wieder zurückgegeben
		possibleSkills.removeAll(knownskillList);
		if(possibleSkills.isEmpty()){
			return knownskillList;
		}

		//es werden so lange skills zufällig ausgewählt und hinzugefügt, bis die richtige Anzahl an Skills hinzugefügt wurde
		for (Integer currentNumberOfNewSkills =0; currentNumberOfNewSkills < numberSkill; currentNumberOfNewSkills++ ){

			Integer lengthListPossibleSkills = possibleSkills.size();
			Integer skillIndex = rndSkill.nextInt(lengthListPossibleSkills);
			knownskillList.add(possibleSkills.get(skillIndex));
			possibleSkills.remove(skillIndex);

		}

		return knownskillList;
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