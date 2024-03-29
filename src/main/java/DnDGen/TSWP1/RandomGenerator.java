package DnDGen.TSWP1;

import java.util.*;

public class RandomGenerator {

	public static DBMS DBMS;
	public static void main(String []args) {
		
		System.out.println("Hier ist die Main");
	}
	//Aufteilung eines großen String in kleinere
	public static ArrayList<String> bigStringToDividedString(String bigString) {
		ArrayList<String> dividedString = new ArrayList<>(); //rückgabe, des aufgeteilten Strings als eine Liste
		String[] partsOfTheBigString = bigString.split(","); //Teilung des großen Strings an den Stellen wo ein Komma ist
		//Integer numberOfPartStrings = partsOfTheBigString.length; // Anzahl der Strings die voneinander getrennt wurden

		for (String s : partsOfTheBigString) { //jeder Teilstring wird der Liste hinzugefügt
			if (dividedString.contains(s)) {

			} else {
				dividedString.add(s);
			}
		}
		return dividedString;
	/*}for (int currentPart=0; currentPart < numberOfPartStrings; currentPart++){ //jeder Teilstring wird der Liste hinzugefügt
		if (dividedString.contains(partsOfTheBigString[currentPart])){

		}else {
			dividedString.add(partsOfTheBigString[currentPart]);
		}
	}

	 */
	}
	public static int diceThrower(Integer diceCount, Integer diceNumber) {

		int addedRolls = 0;
			Random rand = new Random();

			for (int i=0; i < diceCount; i++){
				int value = rand.nextInt(diceNumber) + 1;
				addedRolls = addedRolls + value;
			}
		
		return addedRolls;
	}
	
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
	}

	//UNGETESTET
	/* @Luca */
	public static String randCharClass() {
		ArrayList<String> rndClass= DBMS.getRandRow("class");

		return rndClass.get(1);
	}
	
	/* TO DO @Lenard */
	public static String randRace() {
		String race = "NO RACE";	
		
		ArrayList<String> newRace = new ArrayList<String>();
		newRace = DBMS.getRandRow("race");		
		race = newRace.get(1);
		
		return race;
	}
	
	/* TO DO @Lenard */
	public static String randJob() {
		String job = "NO JOB";
		
		ArrayList<String> newJob = new ArrayList<String>();
		newJob = DBMS.getRandRow("job");
		
		job = newJob.get(1);
		
		return job;
	}

	/* @Luca */
	public static int randLevel() {
		Random rndLevel = new Random();
        //Achtung, Level 0 ist möglich und ein Sonderfall der später beachtet werden muss
		return rndLevel.nextInt(21);
	}
	
	/* TO DO @Lenard */
	public static String randDescription(String race) {
		String description = "NO DESCRIPTION";
		
		return description;
	}
	
	/* TO DO @Luca */
	public static List<String> randTBF() {
		List<String> tbf = new ArrayList<>();
		
		tbf.add("NO TBF");
		
		return tbf;
	}

	//UNGETESTET
	// Funktion um alle gewünschten Prof aus Klasse, Rasse und Features zu bekommen
	/* @Luca */
	public static ArrayList<String> figureOutProfList (String neededProfList, String charClass, String race ){
		ArrayList<String> profList = new ArrayList<>(); // Liste mit Ergebnissen die zurückgegeben wird
		ArrayList<String> databaseReturn ; // Liste die von der DB zurückkommt
		ArrayList<String> featureList ; // Rassen Features
		//ArrayList<String> dividedString = new ArrayList<>(); // Liste in der die SubStrings einer Spalte aus der DB enthalten
		String kindOfFeature; // letzte Spalte aus der DB um Festzustellen um was es sich hier handelt

	switch(neededProfList){	// Entscheidung welche ProfListe man benötigt
		case "armor":
			databaseReturn = DBMS.getRow("class", charClass );
			profList = bigStringToDividedString(databaseReturn.get(4));

			databaseReturn = DBMS.getRow("race", race);
			featureList = bigStringToDividedString(databaseReturn.get(3));	//Datensätze werden aus der DB genommen, die richtigen Spalten ausgewählt und die Strings auseinander genommen

			for ( String feature : featureList){	// die erhaltenen Rassen Features werden durchgecheckt, ob welche dabei sind die Armor Prof geben
				databaseReturn = DBMS.getRow("features", feature);
				kindOfFeature = databaseReturn.get(3);

				if(kindOfFeature.contains("armor:")){	// der Teil des Features, an dem man Feststellt, ob es sich um einen Armor Prof handelt wird entfernt
					kindOfFeature.replace("armor:", "");
					profList.addAll(bigStringToDividedString(kindOfFeature));
					break;
				}
			}
			break;
		case "weapon":	//siehe "armor" nur jetzt mit den waffen
			databaseReturn = DBMS.getRow("class", charClass );
			profList = bigStringToDividedString(databaseReturn.get(3));

			databaseReturn = DBMS.getRow("race", race);
			featureList = bigStringToDividedString(databaseReturn.get(3));

			for ( String feature : featureList){
				databaseReturn = DBMS.getRow("features", feature);
				kindOfFeature = databaseReturn.get(3);

				if(kindOfFeature.contains("weapon:")){
					kindOfFeature.replace("weapon:", " ");
					profList.addAll(bigStringToDividedString(kindOfFeature));
					break;
				}
		//jederzeit erweiterbar mit weiteren cases
			}
			break;
	}
		Set<String> removeDuplicate = new HashSet<>(profList); // die ArrayList wird in ein Set gestopft, um Duplicate innerhalb, zu entfernen
		profList.clear();
		profList.addAll(removeDuplicate); // diese wird hier wieder zu einer ArrayList
		return profList;
	}

	//UNGETESTET
	/* TO DO @Luca */
	public static String randArmor(ArrayList<String> armorProf) {
		String armor; //return value
		Random rndNumber = new Random(); //Zufallszahlen
		int lengthOfArmorProfList = armorProf.size();

		String rndArmor = armorProf.get(rndNumber.nextInt(lengthOfArmorProfList));

		if(rndArmor.contains("Armor")){
			ArrayList<String> armorList=  DBMS.getRowWithPref("armor", rndArmor);
			armor = armorList.get(1);
		}else{
			armor = rndArmor;
		}

		return armor;
	}
	//UNGETESTET
	/* @Luca */
	public static String randWeapon(ArrayList<String> weaponProf) {
		Random rndNumber = new Random();	// für zufallszahlen
		//int rndWeaponProf  = 0;
		int lengthOfWeaponProf = weaponProf.size();
		String weapon;	//das Ergebnis was zurückgegeben wird
	
		//rndWeaponProf = rndNumber.nextInt(lengthOfWeaponProf);

		String rndWeapon = weaponProf.get(rndNumber.nextInt(lengthOfWeaponProf)); // aus der Prof Liste wird ein Eintrag zufällig ausgewählt

		if(rndWeapon.contains("weapon")){ //hier wird gescheckt, ob es direkt eine Waffe ist oder eine Prof in einem Waffenbereich

			ArrayList<String> armorList = DBMS.getRowWithPref("weapon", rndWeapon);
			weapon = armorList.get(1);
		}else {
			weapon=rndWeapon;
		}
		//String weapon = "NO WEAPON";

		return weapon;
	}
	
	/* TO DO @Lenard */
	public static String randDeity(String alignment) {
		String deity = "NO DEITY";
		
		ArrayList<String> newDeity = new ArrayList<String>();
		
		newDeity = DBMS.getRowWithPref("deity", alignment);
		
		while (alignment != newDeity.get(2)) {
			
			newDeity = DBMS.getRowWithPref("deity", alignment);
		}
		
		deity = newDeity.get(1);
		
		return deity;
	}

	//UNGETESTET
	/* @Luca */
	public static String randLanguages() {
		ArrayList<String>rndLang = DBMS.getRandRow("language");

		return rndLang.get(1);
	}
	
	/* TO DO @Lenard */
	public static String randName(String gender, String race) {
		String name = "NO NAME";
		
		String firstName = "NO FIRSTNAME";
		String secondName = "NO SECONDNAME";
		
		ArrayList<String> newName = new ArrayList<String>();
		newName = DBMS.getRowWithPref("name", race);
		
		while (gender != newName.get(4)) {
				
			newName = DBMS.getRowWithPref("name", race);
			firstName = newName.get(1);
			
		}
		
		newName = DBMS.getRowWithPref("name", race);
		secondName = newName.get(2);
		
		name = firstName + secondName;
		
		return name;
	}

	//UNGETESTET
	/* @Luca */
	public static int randAge(String race) {
		ArrayList<String> raceList;
		raceList = DBMS.getRow("race", race);
		int ageAdult = Integer.parseInt(raceList.get(4));
		int ageMax = Integer.parseInt(raceList.get(5));

		Random rndAge = new Random();

		return rndAge.nextInt(ageMax) + ageAdult;
	}

	//Schroedingers Funktion
	/* @Luca */
	public static List<String> randSkillProf(ArrayList<String> possibleSkills, Integer numberSkill, ArrayList<String> knownSkillList) {
		Random rndSkill= new Random();

		//alle bereits bekannten Skills werden entfernt und wenn dann keine möglichen mehr frei sind, wird die SkillList wieder zurückgegeben
		if(possibleSkills.isEmpty()){
			return knownSkillList;
		}
		//es werden so lange skills zufällig ausgewählt und hinzugefügt, bis die richtige Anzahl an Skills hinzugefügt wurde
		for (int currentNumberOfNewSkills = 0; currentNumberOfNewSkills < numberSkill; currentNumberOfNewSkills++ ){

			int lengthListPossibleSkills = possibleSkills.size();
			Integer skillIndex = rndSkill.nextInt(lengthListPossibleSkills);
			knownSkillList.add(possibleSkills.get(skillIndex));
			possibleSkills.remove(skillIndex);
		}
		Set<String> removeDuplicate = new HashSet<>(knownSkillList); // die ArrayList wird in ein Set gestopft, um Duplicate innerhalb, zu entfernen
		knownSkillList.clear();
		knownSkillList.addAll(removeDuplicate);

		return knownSkillList;
}
	
	/* TO DO @Luca */
	public static String randPlotHook() {
		String plotHook = "NO PLOT HOOK";
		
		return plotHook;
	}
	
	/* TO DO @Lenard */
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
		return gender;
	}
	
	/* TO DO @Luca */
	public static List<String> randSpells(String charClass, int numbSpells) {
		List<String> spells = new ArrayList<>();
		//PAIN
		spells.add("NO SPELLS");
		
		return spells;
	}
	
}