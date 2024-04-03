package DnDGen.TSWP1;

import java.util.*;

import org.springframework.context.ApplicationContext;

import DnDGen.DB.DBMS;


public class RandomGenerator {

	/* @author Lucas
	 *
	 * Aufrufen der GENAUEN DBMS bzw. SpringApplication die gestartet wurde, jede
	 * neu-konstruktion von DBMS wäre sonst leer.
	 * Ich musste es etwas anpassen, weil ihr aus irgendeinem Grund nur   nutzt,
	 * ich hoffe/denke das es auch so funktionieren sollte, übernehme aber keine Verantwortung dafür.
	 */
	ApplicationContext appCtx = DBMS.getApplicationContext();
	DBMS dbms = appCtx.getBean(DBMS.class);

	public static void main(String []args) {

		System.out.println("Hier ist die Main");

	}

	//Aufteilung eines großen String in kleinere
	public static ArrayList<String> bigStringToDividedString(String bigString) {
		ArrayList<String> dividedString = new ArrayList<>(); //rückgabe, des aufgeteilten Strings als eine Liste
		String[] partsOfTheBigString = bigString.split(", "); //Teilung des großen Strings an den Stellen wo ein Komma ist

		for (String s : partsOfTheBigString) { //jeder Teilstring wird der Liste hinzugefügt
			if (dividedString.contains(s)) {

			} else {
				dividedString.add(s);
			}
		}
		return dividedString;
	}

	public   int diceThrower(Integer diceCount, Integer diceNumber) {

		int addedRolls = 0;
		Random rand = new Random();

		for (int i = 0; i < diceCount; i++) {
			int value = rand.nextInt(diceNumber) + 1;
			addedRolls = addedRolls + value;
		}

		return addedRolls;
	}

	public   ArrayList<Integer> randAbilityScore() {

		ArrayList<Integer> value = new ArrayList<>();
		ArrayList<Integer> finishedSetForAbilityScores = new ArrayList<>();

		for (int i = 0; i < 6; i++) {
			value.add(diceThrower(1, 6));
			value.add(diceThrower(1, 6));
			value.add(diceThrower(1, 6));
			value.add(diceThrower(1, 6));
			Collections.sort(value, Collections.reverseOrder());

			int number = value.get(0) + value.get(1) + value.get(2);

			finishedSetForAbilityScores.add(number);

			value.clear();
		}
		Collections.sort(finishedSetForAbilityScores, Collections.reverseOrder());
		return finishedSetForAbilityScores;
	}

	/* @Luca */
	public   String randCharClass() {
		ArrayList<String> rndClass = dbms.getRandomRow("class");

		return rndClass.get(1);
	}

	/* TO DO @Lenard */
	public   String randRace() {
		String race = "NO RACE";

        ArrayList<String> newRace = dbms.getRandomRow("race");
		race = newRace.get(1);

		return race;
	}

	/* TO DO @Lenard */
	public   String randJob() {
		String job = "NO JOB";

		ArrayList<String> newJob = new ArrayList<String>();
		newJob = dbms.getRandomRow("job");

		job = newJob.get(1);

		return job;
	}

	/* @Luca */
	public int randLevel() {
		Random rndLevel = new Random();
		//Achtung, Level 0 ist möglich und ein Sonderfall der später beachtet werden muss
		return rndLevel.nextInt(21);
	}

	/* TO DO @Lenard */
	public String randDescription(String race) {
		String description = "NO DESCRIPTION";

		return description;
	}

	/* TO DO @Luca */
	public List<String> randTBF() {
		List<String> tbf = new ArrayList<>();

		tbf.add("NO TBF");

		return tbf;
	}

	//UNGETESTET
	// Funktion um alle gewünschten Prof aus Klasse, Rasse und Features zu bekommen
	/* @Luca */
	public   ArrayList<String> figureOutProfList(String neededProfList, String charClass, String race) {
		ArrayList<String> profList = new ArrayList<>(); // Liste mit Ergebnissen die zurückgegeben wird
		ArrayList<String> databaseReturn; // Liste die von der DB zurückkommt
		ArrayList<String> featureList; // Rassen Features
		//ArrayList<String> dividedString = new ArrayList<>(); // Liste in der die SubStrings einer Spalte aus der DB enthalten
		String kindOfFeature; // letzte Spalte aus der DB um Festzustellen um was es sich hier handelt

		switch (neededProfList) {    // Entscheidung welche ProfListe man benötigt
			case "armor":
				/*databaseReturn = dbms.getRow("class", charClass);
				profList = bigStringToDividedString(databaseReturn.get(4));*/
				profList = bigStringToDividedString(dbms.getRow("class", charClass).get(4));

				databaseReturn = dbms.getRow("race", race);
				featureList = bigStringToDividedString(databaseReturn.get(3));    //Datensätze werden aus der DB genommen, die richtigen Spalten ausgewählt und die Strings auseinander genommen
				if (featureList !=null){
					for (String feature : featureList) {    // die erhaltenen Rassen Features werden durchgecheckt, ob welche dabei sind die Armor Prof geben
						databaseReturn = dbms.getRow("features", feature);
						kindOfFeature = databaseReturn.get(3);

						if (kindOfFeature.contains("armor:")) {    // der Teil des Features, an dem man Feststellt, ob es sich um einen Armor Prof handelt wird entfernt
							kindOfFeature.replace("armor:", "");
							profList.addAll(bigStringToDividedString(kindOfFeature));
							break;
						}
					}
				}
				break;
			case "weapon":    //siehe "armor" nur jetzt mit den waffen
				databaseReturn = dbms.getRow("class", charClass);
				profList = bigStringToDividedString(databaseReturn.get(3));

				databaseReturn = dbms.getRow("race", race);
				featureList = bigStringToDividedString(databaseReturn.get(3));
				if(featureList != null) {
					for (String feature : featureList) {
						databaseReturn = dbms.getRow("features", feature);
						kindOfFeature = databaseReturn.get(3);

						if (kindOfFeature.contains("weapon:")) {
							kindOfFeature.replace("weapon:", " ");
							profList.addAll(bigStringToDividedString(kindOfFeature));
							break;
						}
						//jederzeit erweiterbar mit weiteren cases
					}
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
	public   String randArmor(ArrayList<String> armorProf) {
		String armor; //return value
		Random rndNumber = new Random(); //Zufallszahlen
		armorProf.remove("Shield");
		int lengthOfArmorProfList = armorProf.size();
		String rndArmor = armorProf.get(rndNumber.nextInt(lengthOfArmorProfList));
		ArrayList<String> armorList = dbms.getRowPref("armor", rndArmor);
		armor = armorList.get(1);

		return armor;
	}

	/* @Luca */
	public   String randWeapon(ArrayList<String> weaponProf) {
		Random rndNumber = new Random();    // für zufallszahlen
		//int rndWeaponProf  = 0;
		int lengthOfWeaponProf = weaponProf.size()-1;
		String weapon;    //das Ergebnis was zurückgegeben wird

		//rndWeaponProf = rndNumber.nextInt(lengthOfWeaponProf);
		String rndWeapon = weaponProf.get(rndNumber.nextInt(lengthOfWeaponProf)); // aus der Prof Liste wird ein Eintrag zufällig ausgewählt

		ArrayList<String> armorList = dbms.getRowPref("weapon", rndWeapon);
		weapon = armorList.get(1);

		return weapon;
	}

	/* TO DO @Lenard */
	public String randDeity(String alignment) {
		String deity = "NO DEITY";

		ArrayList<String> newDeity = new ArrayList<String>();

		newDeity = dbms.getRowPref("deity", alignment);

		deity = newDeity.get(1);

		return deity;
	}

	/* @Luca */
	public   String randLanguages() {
		ArrayList<String> rndLang = dbms.getRandomRow("language");

		return rndLang.get(1);
	}

	/* TO DO @Lenard */
	public   String randName(String gender, String race) {
		String name = "NO NAME";

		String firstName = "NO FIRSTNAME";
		String secondName = "NO SECONDNAME";
		
		ArrayList<String> newName = new ArrayList<String>();
		newName = dbms.getRowPref("name", race);
		
		firstName = newName.get(1);
		
		while (!(gender.equals(newName.get(4)))) {

			newName = dbms.getRowPref("name", race);
			firstName = newName.get(1);

		}

		newName = dbms.getRowPref("name", race);
		secondName = newName.get(2);

		name = firstName + " " + secondName;

		return name;
	}


	/* @Luca */
	public   int randAge(String race) {
		ArrayList<String> raceList;
		raceList = dbms.getRow("race", race);
		int ageAdult = Integer.parseInt(raceList.get(4));
		int ageMax = Integer.parseInt(raceList.get(5));

		Random rndAge = new Random();

		return rndAge.nextInt(ageMax) + ageAdult;
	}

	//Schroedingers Funktion
	/* @Luca */
	public   List<String> randSkillProf(ArrayList<String> possibleSkills, Integer numberSkill, ArrayList<String> knownSkillList) {
		Random rndSkill = new Random();

		if (possibleSkills.isEmpty()) {
			return knownSkillList;
		}
		//es werden so lange skills zufällig ausgewählt und hinzugefügt, bis die richtige Anzahl an Skills hinzugefügt wurde
		for (int currentNumberOfNewSkills = 0; currentNumberOfNewSkills < numberSkill; currentNumberOfNewSkills++) {
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
	public   String randPlotHook() {
		String plotHook = "NO PLOT HOOK";

		return plotHook;
	}

	/* TO DO @Lenard */
	public   String randGender() {

		String gender;
		Random rand = new Random();
		int value = rand.nextInt(2);

		if (value == 0) {
			gender = "female";
		} else {
			gender = "male";
		}
		return gender;
	}

	public   String randAlignment() {
		Random randNumber = new Random();
		int rndNum = randNumber.nextInt(9);
		ArrayList<String> Alignments = new ArrayList<>();
		Alignments.add("CG");
		Alignments.add("CN");
		Alignments.add("CE");
		Alignments.add("NE");
		Alignments.add("LN");
		Alignments.add("LG");
		Alignments.add("LN");
		Alignments.add("NG");
		Alignments.add("N");

		return Alignments.get(rndNum);
	}

	/* TO DO @Kristina */

	public ArrayList<String> randSpells(String className, int cantripNumber, int level, int spellMod, int spellLvl1) {
		ArrayList<String> spells = new ArrayList<>();
		ArrayList<String> cantrip = new ArrayList<>();
		//PAIN
		//CAN CONFIRM

		/* @author Lucas
		 *
		 * Wisst ihr was wirklich Pain ist? der gleiche Kopierfehler auf 500+ Zeilen....
		 *
		 * Ich hab einfach mal die ersten 152 Zeilen, aka alles das was zu "if 'Bard'" gehört(Zeile 364 - 516), als beispiel korrigiert aber
		 * weder die Lust noch das Durchhaltevermögen die restlichen 461(517-978) Zeilen für sie zu korrigieren, mal abgesehen von den Fehlern
		 * beim einsatz von If-Sätzen was direkt mal 98% aller If-Sätze inaccessible macht, das werde ich heute abend nochmalmit ihr besprechen so das
		 * es(hoffentlich) bis morgen funktionstüchtig ist und sie es versteht.
		 * (Zeilenangaben stimmen natürlich nur solange niemand oben drüber, also quasi alle anderen, noch was ändern, die Anzahl an Zeilen ist ja trotzdem richtig XD)
		 *
		 * Ganz unten ist auch noch ein Kommentar von mir falls ihr euch wundert warum eurer Debug/Fehler Menü laggt, gar nicht mehr geht oder falsche/zu wenige anzeigen macht.
		 *
		 */
		int spellCount = 0;
		int levelHalb = 0;
		int spellUpCount = 0;
		int cantripCount = 0;
		int levelCantrip = 0;
		// BARD
		if (className.equals("Bard")){

			levelCantrip = 0;
			if (level >= 4){
				levelCantrip = 1;
			}
			else if ((level<= 20) && (level>= 10)){
				levelCantrip = 2;
			}
			cantripCount = cantripNumber + levelCantrip;
			cantrip.addAll(dbms.getMultRowMultPref("spells", "Bard", "Cantrip", cantripCount));


			if (level == 1){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 4));
			}
			else if (level == 2){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 5));
			}
			else if(level == 3){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 5));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "2nd Level", 1));
			}
			else if(level == 4){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 5));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "2nd Level", 2));
			}
			else if(level == 5){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 5));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "3rd Level", 1));
			}
			else if(level == 6){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 5));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "3rd Level", 2));
			}
			else if(level == 7){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 5));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "3rd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "4th Level", 1));
			}
			else if(level == 8){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 5));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "4th Level", 2));
			}
			else if(level == 9){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 5));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "3rd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "4th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "5th Level", 1));
			}
			else if(level == 10){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 5));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "3rd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "4th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "5th Level", 3));
			}
			else if(level == 11){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 5));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "3rd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "4th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "5th Level", 3));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "6th Level", 1));
			}
			else if(level == 12){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 5));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "3rd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "4th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "5th Level", 3));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "6th Level", 1));
			}
			else if(level == 13){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 5));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "3rd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "4th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "5th Level", 3));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "6th Level", 1));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "7th Level", 1));
			}
			else if(level == 14){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 5));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "3rd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "4th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "5th Level", 3));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "6th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "7th Level", 1));
			}
			else if(level == 15){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 5));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "3rd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "4th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "5th Level", 3));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "6th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "7th Level", 1));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "8th Level", 1));
			}
			else if(level == 16){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 5));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "3rd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "4th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "5th Level", 3));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "6th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "7th Level", 1));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "8th Level", 1));
			}
			else if(level == 17){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 5));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "3rd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "4th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "5th Level", 3));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "6th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "7th Level", 1));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "8th Level", 1));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "9th Level", 1));
			}
			else if(level == 18){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 5));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "3rd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "4th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "5th Level", 3));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "6th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "7th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "8th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "9th Level", 1));
			}
			else if(level == 19){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 5));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "3rd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "4th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "5th Level", 3));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "6th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "7th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "8th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "9th Level", 1));
			}
			else if(level == 20){
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "1st Level", 5));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "3rd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "4th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "5th Level", 3));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "6th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "7th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "8th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", "Bard", "9th Level", 1));
			}
		}
		// SORCERER
		else if(className.equals("Sorcerer")){

			levelCantrip = 0;
			if (level >= 4){
				levelCantrip = 1;
			}
			else if ((level<= 20) && (level>= 10)){
				levelCantrip = 2;
			}
			cantripCount = cantripNumber + levelCantrip;
			cantrip.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "Cantrip", cantripCount));

			spellCount = spellLvl1 + (level - 1);
			spellUpCount = 2;
			if (level <= 2){
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "1st Level", spellCount));
			}
			else if ((level == 3) || (level == 4)){
				spellCount = spellCount - spellUpCount;
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "2nd Level", spellUpCount));
			}
			else if ((level == 5) || (level == 6)){
				spellCount = spellCount - (spellUpCount * 2);
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "2nd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "3rd Level", spellUpCount));
			}
			else if ((level == 7) || (level == 8)){
				spellCount = spellCount - (spellUpCount * 3);
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "2nd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "3rd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "4th Level", spellUpCount));
			}
			else if ((level == 9) || (level == 10)){
				spellCount = spellCount - (spellUpCount * 3);
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "2nd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "3rd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "4th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "5th Level", spellUpCount));
			}
			else if ((level == 11) || (level == 12)){
				spellCount = 4;
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "2nd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "3rd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "4th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "5th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "6th Level", spellUpCount));
			}
			else if ((level == 13) || (level == 14)){
				spellCount = 4;
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "2nd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "3rd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "4th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "5th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "6th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "7th Level", 1));
			}
			else if ((level == 15) || (level == 16)){
				spellCount = spellCount - (spellUpCount * 3);
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "2nd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "3rd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "4th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "5th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "6th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "7th Level", 1));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "8th Level", 1));
			}
			else if ((level >= 17) && (level <= 20)){
				spellCount = spellCount - (spellUpCount * 3);
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "2nd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "3rd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "4th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "5th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "6th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "7th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "8th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Sorcerer", "9th Level", 1));
			}
		}


		// WIZARD CLERIC DRUID
		else if((className.equals("Wizard")) || (className.equals("Cleric")) || (className.equals("Druid"))){

			if (level >= 4){
				levelCantrip = 1;
			}
			else if ((level<= 20) && (level>= 10)){
				levelCantrip = 2;
			}
			cantripCount = cantripNumber + levelCantrip;
			cantrip.addAll(dbms.getMultRowMultPref("spells", className, "Cantrip", cantripCount));

			spellUpCount = 2;
			spellCount = spellMod + level;
			if (level <= 2){
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
			}
			else if ((level == 3) || (level == 4)){
				spellCount = spellCount - spellUpCount;
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", spellUpCount));
			}
			else if ((level == 5) || (level == 6)){
				spellCount = spellCount - (spellUpCount * 2);
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", (spellUpCount)));
			}
			else if ((level == 7) || (level == 8)){
				spellCount = spellCount - (spellUpCount * 3);
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", (spellUpCount)));
			}
			else if ((level == 9) || (level == 10)){
				spellCount = spellCount - (spellUpCount * 4);
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "5th Level", (spellUpCount)));
			}
			else if ((level == 11) || (level == 12)){
				spellCount = spellCount - (spellUpCount * 5);
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "5th Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "6th Level", (spellUpCount)));
			}
			else if ((level == 13) || (level == 14)){
				spellCount = spellCount - (spellUpCount * 6);
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "5th Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "6th Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "7th Level", (spellUpCount)));
			}
			else if ((level == 15) || (level == 16)){
				spellCount = spellCount - (spellUpCount * 7);
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "5th Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "6th Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "7th Level", (spellUpCount)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "8th Level", (spellUpCount)));
			}
			else if ((level == 17) || (level == 18)){
				spellCount = spellCount - (spellUpCount * 8);
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", (spellUpCount + 1)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "5th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "6th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "7th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "8th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "9th Level", 1));
			}
			else if ((level == 19) || (level == 20)){
				spellCount = spellCount - (spellUpCount * 9);
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", (spellUpCount + 1)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", (spellUpCount + 1)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", (spellUpCount + 1)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "5th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "6th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "7th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "8th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "9th Level", 1));
			}
		}


		// ARTIFICER
		else if(className.equals("Artificer")){
			levelCantrip = 0;
			if (level >= 10){
				levelCantrip = 1;
			}
			else if ((level<= 20) && (level>= 14)){
				levelCantrip = 2;
			}
			cantripCount = cantripNumber + levelCantrip;
			cantrip.addAll(dbms.getMultRowMultPref("spells", "Artificer", "Cantrip", cantripCount));

			spellUpCount = 2;
			levelHalb =(int) Math.floor(level/2);
			if (levelHalb == 0){
				levelHalb = 1;
			}
			spellCount = spellMod + levelHalb;
			if (level <= 4){
				spells.addAll(dbms.getMultRowMultPref("spells", "Artificer", "1et Level", spellCount));
			}
			else if ((level >= 5) && (level <= 8)){
				spellCount = spellCount - (spellUpCount);
				spells.addAll(dbms.getMultRowMultPref("spells", "Artificer", "1et Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Artificer", "2nd Level", spellUpCount));
			}
			else if ((level >= 9) && (level <= 12)){
				spellCount = spellCount - (spellUpCount * 2);
				spells.addAll(dbms.getMultRowMultPref("spells", "Artificer", "1et Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Artificer", "2nd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Artificer", "3rd Level", spellUpCount));
			}
			else if ((level >= 13) && (level <= 16)){
				spellCount = spellCount - (spellUpCount * 3);
				spells.addAll(dbms.getMultRowMultPref("spells", "Artificer", "1et Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Artificer", "2nd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Artificer", "3rd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Artificer", "4th Level", spellUpCount));
			}
			else if ((level >= 17) && (level <= 20)){
				spellCount = spellCount - (spellUpCount * 4);
				spells.addAll(dbms.getMultRowMultPref("spells", "Artificer", "1et Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Artificer", "2nd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Artificer", "3rd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Artificer", "4th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", "Artificer", "5th Level", spellUpCount));
			}
		}


		// PALADIN
		else if(className.equals("Paladin")){
			cantrip.add("NO CANTRIPS");

			spellUpCount = 2;
			levelHalb =(int) Math.floor(level/2);
			if (levelHalb == 0){
				levelHalb = 1;
			}
			spellCount = spellMod + levelHalb;
			if (level <= 4){
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1et Level", spellCount));
			}
			else if ((level >= 5) && (level <= 8)){
				spellCount = spellCount - (spellUpCount);
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1et Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", spellUpCount));
			}
			else if ((level >= 9) && (level <= 12)){
				spellCount = spellCount - (spellUpCount * 2);
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1et Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", spellUpCount));
			}
			else if ((level >= 13) && (level <= 16)){
				spellCount = spellCount - (spellUpCount * 3);
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1et Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", spellUpCount));
			}
			else if ((level >= 17) && (level <= 20)){
				spellCount = spellCount - (spellUpCount * 4);
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1et Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "5th Level", spellUpCount));
			}
		}



		// RANGER
		else if(className.equals("Ranger")){
			cantrip.add("NO CANTRIPS");

			spellCount = 3;
			if (level == 1){
				spells.add("NO SPELLS");
			}
			else if (level == 2){
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", (spellCount - 1)));
			}
			else if ((level == 3) || (level == 4)){
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
			}
			else if ((level == 5) || (level == 6)){
				spellCount = spellCount - 1;
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", 1));
			}
			else if ((level == 7) || (level == 8)){
				spellCount = spellCount - 2;
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", 2));
			}
			else if ((level == 9) || (level == 10)){
				spellCount = spellCount - 3;
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", 1));
			}
			else if ((level == 11) || (level == 12)){
				spellCount = spellCount - 4;
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", 2));
			}
			else if ((level == 13) || (level == 14)){
				spellCount = spellCount - 5;
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", 1));
			}
			else if ((level == 15) || (level == 16)){
				spellCount = spellCount - 6;
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", 2));
			}
			else if ((level == 17) || (level == 18)){
				spellCount = spellCount - 7;
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "5th Level", 1));
			}
			else if ((level == 19) || (level == 20)){
				spellCount = spellCount - 8;
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", 2));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "5th Level", 2));
			}
		}


		//WARLOCK
		else if(className.equals("Warlock")){
			levelCantrip = 0;
			if (level >= 4){
				levelCantrip = 1;
			}
			else if ((level<= 20) && (level>= 10)){
				levelCantrip = 2;
			}
			cantripCount = cantripNumber + levelCantrip;
			cantrip.addAll(dbms.getMultRowMultPref("spells", className, "Cantrip", cantripCount));

			spellUpCount = 2;
			if ((level >= 1) && (level <= 8)){
				spellCount = level + 1;
			}
			else if ((level == 1) || (level == 2)){
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
			}
			else if ((level == 3) || (level == 4)){
				spellCount = spellCount - spellUpCount;
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", spellUpCount));
			}
			else if ((level == 5) || (level == 6)){
				spellCount = spellCount - (spellUpCount * 2);
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", spellUpCount));
			}
			else if ((level == 7) || (level == 8)){
				spellCount = spellCount - (spellUpCount * 3);
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", spellUpCount));
			}
			else if ((level == 9) || (level == 10)){
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", 4));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", (spellUpCount - 1)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "5th Level", (spellUpCount - 1)));
			}
			else if ((level == 11) || (level == 12)){
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", 4));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "5th Level", (spellUpCount - 1)));
			}
			else if ((level == 13) || (level == 14)){
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", 4));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "5th Level", spellUpCount));
			}
			else if ((level == 15) || (level == 16)){
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", 4));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", (spellUpCount + 1)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "5th Level", spellUpCount));
			}
			else if ((level == 17) || (level == 18)){
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", (spellUpCount + 1)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", (spellUpCount + 1)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", spellUpCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "5th Level", spellUpCount));
			}
			else if ((level == 19) || (level == 20)){
				spells.addAll(dbms.getMultRowMultPref("spells", className, "1st Level", spellCount));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "2nd Level", (spellUpCount + 1)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "3rd Level", (spellUpCount + 1)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "4th Level", (spellUpCount + 1)));
				spells.addAll(dbms.getMultRowMultPref("spells", className, "5th Level", spellUpCount));
			}
		}
		else{
			cantrip.add("NO CANTRIP");
			spells.add("NO SPELLS");
		}
		return spells;
	}
}
//@autor Lucas
//sie hat mehrmals irgendwo vergessen if's zu schließen, und ich hab keine Lust zu suchen wo, deswegen hier ein paar extra klammern,
//damit mein eclipse mir die anderen fehler anzeigt :')
//
//edit: nvm, mein compiler hat die Anzahl an Fehlern gesehen und nach den ersten 115 Zeilen von 500+ aufgehört zu zählen und sie anzuzeigen :'(
	


