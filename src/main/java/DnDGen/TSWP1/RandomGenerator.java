package DnDGen.TSWP1;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

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

	Random rand = new SecureRandom();

	String nameDBClass = "class";

	//Aufteilung eines großen String in kleinere
	public static ArrayList<String> bigStringToDividedString(String bigString) {
		ArrayList<String> dividedString = new ArrayList<>(); //rückgabe, des aufgeteilten Strings als eine Liste
		String[] partsOfTheBigString = bigString.split(", "); //Teilung des großen Strings an den Stellen wo ein Komma ist

		for (String s : partsOfTheBigString) { //jeder Teilstring wird der Liste hinzugefügt
		//	if (!dividedString.contains(s)) {
				dividedString.add(s);
		//	}
		}
		return dividedString;
	}

	public static ArrayList<Double> divideDoubles(String bigNumber) {
		ArrayList<String> dividedString = new ArrayList<>();
		ArrayList<Double> parsedToDouble = new ArrayList<>();
		String[] partsOfTheBigString = bigNumber.split("-");

		for (String s : partsOfTheBigString) {
			if (!dividedString.contains(s)) {
				dividedString.add(s);
			}
		}
		for(int k = 0; k < 2; k++) {
			Double parsedInt = Double.parseDouble(dividedString.get(k));
			parsedToDouble.add(parsedInt);
		}
		return parsedToDouble;
	}
	
	public int diceThrower(Integer diceCount, Integer diceNumber) {

		int addedRolls = 0;

		for (int i = 0; i < diceCount; i++) {
			int value = rand.nextInt(diceNumber) + 1;
			addedRolls = addedRolls + value;
		}

		return addedRolls;
	}

	public ArrayList<Integer> randAbilityScore(Integer amountOfThrows) {
		ArrayList<Integer> value = new ArrayList<>();
		ArrayList<Integer> finishedSetForAbilityScores = new ArrayList<>();
		
		for (int i = 0; i < 6; i++) {
			int j = 0;
			while(j <= amountOfThrows + 1) {
				value.add(diceThrower(1, 6));
				j++;
			}
			
			Collections.sort(value, Collections.reverseOrder());
			value.removeLast();
			
			int k = 0;
			int number = 0;
			while(k <= amountOfThrows - 1) {
				number = number + value.get(k);
				k++;
			}

			finishedSetForAbilityScores.add(number);
			
			value.clear();
		}
		Collections.sort(finishedSetForAbilityScores, Collections.reverseOrder());
		return finishedSetForAbilityScores;
	}
	
	public double randHeight(String race) {
		ArrayList<String> rawHeight;
		ArrayList<Double> minMaxArray;
		String height;
		double min;
		double max;
		
		rawHeight = dbms.getRow("race", race);
		height = rawHeight.get(7);
		
		minMaxArray = divideDoubles(height);
		min = minMaxArray.get(0);
		max = minMaxArray.get(1);
		
		double randomNum = ThreadLocalRandom.current().nextDouble(min, max);

        return Math.round(randomNum * 10.0) / 10.0;
	}
	
	/* @Luca */
	public   String randCharClass() {
		ArrayList<String> rndClass = dbms.getRandomRow(nameDBClass);

		return rndClass.get(1);
	}

	/* TO DO @Lenard */
	public String randRace() {
		String race;
        ArrayList<String> newRace = dbms.getRandomRow("race");
		race = newRace.get(1);

		return race;
	}

	/* TO DO @Lenard */
	public   String randJob() {
		String job;

		ArrayList<String> newJob;
		newJob = dbms.getRandomRow("job");

		job = newJob.get(1);

		return job;
	}

	/* @Luca */
	public int randLevel() {
		//Achtung, Level 0 ist möglich und ein Sonderfall der später beachtet werden muss
		return rand.nextInt(21);
	}
	
	public String randSize(String race) {
		String size;
		ArrayList<String> sizeArray;
		ArrayList<String> newSize;
		
		sizeArray = dbms.getRow("race", race);
		size = sizeArray.get(8);
		newSize =  bigStringToDividedString(size);
		
		if(newSize.size() == 2) {
			int value = rand.nextInt(2);
			size = newSize.get(value);		
		} else {
			size = sizeArray.get(8);
		}

		return size;
	}
	
	/* TO DO @Lenard */
	public ArrayList<String> randDescription(String race, String alignment) {
		ArrayList<String> desc = new ArrayList<>();
		ArrayList<String> dbData;
		
		String job = randJob();
		String gender = randGender();
		int age = randAge(race);
		String deity = randDeity(alignment);
		String size = randSize(race);
		double height = randHeight(race);
		
		int i = 1;
		while(i < 13) {
			dbData = dbms.getRandomRow("description");
			desc.add(dbData.get(i));
			dbData.clear();
			i++;
		}
		
		desc.add(job);
		desc.add(gender);
		desc.add(Integer.toString(age));
		desc.add(deity);
		desc.add(size);
		desc.add(Double.toString(height));
		
		//Eyes, HairColor, HairStyle, Skin, Trait, Mannerism, DOnd, Flaw, Ideal, Voice, CLothing, Attitude, Plothook, Job, Gender, age, deity, size, height
		return desc;
	}

	//UNGETESTET
	// Funktion um alle gewünschten Prof aus Klasse, Rasse und Features zu bekommen
	/* @Luca */
	public ArrayList<String> figureOutProfList(String neededProfList, String charClass, String race) {
		ArrayList<String> profList = new ArrayList<>(); // Liste mit Ergebnissen die zurückgegeben wird
		ArrayList<String> databaseReturn; // Liste die von der DB zurückkommt
		ArrayList<String> featureList; // Rassen Features
		String kindOfFeature; // letzte Spalte aus der DB um Festzustellen um was es sich hier handelt

		switch (neededProfList) {    // Entscheidung welche ProfListe man benötigt
			case "armor":
				profList = bigStringToDividedString(dbms.getRow(nameDBClass, charClass).get(4));

				databaseReturn = dbms.getRow("race", race);
				if(databaseReturn.get(3)!=null) {
					featureList = bigStringToDividedString(databaseReturn.get(3));    //Datensätze werden aus der DB genommen, die richtigen Spalten ausgewählt und die Strings auseinander genommen
					for (String feature : featureList) {    // die erhaltenen Rassen Features werden durchgecheckt, ob welche dabei sind die Armor Prof geben
						if (feature != null) {
							databaseReturn = dbms.getRow("features", feature);
							kindOfFeature = databaseReturn.get(3);

							if (kindOfFeature.contains("armor:")) {    // der Teil des Features, an dem man Feststellt, ob es sich um einen Armor Prof handelt wird entfernt
								kindOfFeature= kindOfFeature.replace("armor: ", "");
								profList.addAll(bigStringToDividedString(kindOfFeature));
								break;
							}
						}
					}
				}
				break;
			case "weapon":    //siehe "armor" nur jetzt mit den waffen
				databaseReturn = dbms.getRow(nameDBClass, charClass);
				if(databaseReturn.get(3)!= null){
				profList = bigStringToDividedString(databaseReturn.get(3));
				databaseReturn = dbms.getRow("race", race);
				if(databaseReturn.get(3)!=null) {

					featureList = bigStringToDividedString(databaseReturn.get(3));
					for (String feature : featureList) {
						if (feature != null) {
							databaseReturn = dbms.getRow("features", feature);
							kindOfFeature = databaseReturn.get(3);

							if (kindOfFeature.contains("weapon:")) {
								kindOfFeature =kindOfFeature.replace("weapon:", " ");
								profList.addAll(bigStringToDividedString(kindOfFeature));
								break;
							}

							//jederzeit erweiterbar mit weiteren cases
						}
					}
				}
				}
				break;
			default:
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
		armorProf.remove("Shield");
		int lengthOfArmorProfList = armorProf.size();
		String rndArmor = armorProf.get(rand.nextInt(lengthOfArmorProfList));
		if((rndArmor.equals("Light Armor"))||(rndArmor.equals("Medium Armor"))||(rndArmor.equals("Heavy Armor"))){
			ArrayList<String> armorList = dbms.getRowPref("armor", rndArmor);
			armor = armorList.get(1);
		}else{	//Sonderfälle Rüstung
			armor = rndArmor;
		}
		return armor;
	}

	/* @Luca */
	public String randWeapon(ArrayList<String> weaponProf) {
		int lengthOfWeaponProf = weaponProf.size();
		String weapon;    //das Ergebnis was zurückgegeben wird
		if(weaponProf.isEmpty()){
			return "no Weapon";
		}
		String rndWeapon = weaponProf.get(rand.nextInt(lengthOfWeaponProf)); // aus der Prof Liste wird ein Eintrag zufällig ausgewählt
		if((rndWeapon.equals("Simple Weapon")||(rndWeapon.equals("Martial Weapon")))) {
			ArrayList<String> weaponList = dbms.getRowPref("weapon", rndWeapon);
			weapon = weaponList.get(1);
		}else {
			weapon = rndWeapon;
		}
		return weapon;

	}

	/* TO DO @Lenard */
	public String randDeity(String alignment) {
		String deity;

		ArrayList<String> newDeity;
		
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
	public String randName(String gender, String race) {
		String name;
		String firstName;
		String secondName;
		ArrayList<String> splitRaceArray = new ArrayList<>();
		String[] newRace = race.split(" ");
		
		for (String s : newRace) {
			if (!splitRaceArray.contains(s)) {
				splitRaceArray.add(s);
			}
		}
		String newRaceString = splitRaceArray.getLast();
		ArrayList<String> newName;
		newName = dbms.getRowPref("name", newRaceString);
		
		firstName = newName.get(1);
		
		while (!(gender.equals(newName.get(4)))) {

			newName = dbms.getRowPref("name", newRaceString);
			firstName = newName.get(1);

		}

		newName = dbms.getRowPref("name", newRaceString);
		secondName = newName.get(2);

		name = firstName + " " + secondName;

		return name;
	}


	/* @Luca */
	public int randAge(String race) {
		ArrayList<String> raceList;
		raceList = dbms.getRow("race", race);
		int ageAdult = Integer.parseInt(raceList.get(4));
		int ageMax = Integer.parseInt(raceList.get(5));

		return rand.nextInt(ageMax) + ageAdult;
	}

	//Schroedingers Funktion
	/* @Luca */
	public List<String> randSkillProf(ArrayList<String> possibleSkills, Integer numberSkill, ArrayList<String> knownSkillList) {
		if (possibleSkills.isEmpty()) {
			return knownSkillList;
		}
		//es werden so lange skills zufällig ausgewählt und hinzugefügt, bis die richtige Anzahl an Skills hinzugefügt wurde
		for (int currentNumberOfNewSkills = 0; currentNumberOfNewSkills < numberSkill; currentNumberOfNewSkills++) {
			int lengthListPossibleSkills = possibleSkills.size();
			Integer skillIndex = rand.nextInt(lengthListPossibleSkills);
			knownSkillList.add(possibleSkills.get(skillIndex));
			possibleSkills.remove(skillIndex);
		}
		Set<String> removeDuplicate = new HashSet<>(knownSkillList); // die ArrayList wird in ein Set gestopft, um Duplicate innerhalb, zu entfernen
		knownSkillList.clear();
		knownSkillList.addAll(removeDuplicate);

		return knownSkillList;
	}

	/* TO DO @Lenard */
	public   String randGender() {

		String gender;
		int value = rand.nextInt(2);

		if (value == 0) {
			gender = "female";
		} else {
			gender = "male";
		}
		return gender;
	}

	public String randAlignment() {
		int rndNum = rand.nextInt(9);
		ArrayList<String> alignments = new ArrayList<>();
		alignments.add("Chaotic Good");
		alignments.add("Chaotic Neutral");
		alignments.add("Chaotic Evil");
		alignments.add("Neutral Evil");
		alignments.add("Lawful Neutral");
		alignments.add("Lawful Good");
		alignments.add("Lawful Neutral");
		alignments.add("Neutral Good");
		alignments.add("Neutral");

		return alignments.get(rndNum);
	}
	public ArrayList<String> randSpellList (String genClass, ArrayList<Integer> abilityModifier, int level ){
		System.out.println("class: " + genClass);
		String classSpellKnownCol = dbms.getRow("class", genClass).get(9);
		ArrayList<String> spells = new ArrayList<>();
		ArrayList<Integer> spellSlots = new ArrayList<>();
		int classSpellKnownNumber = 0 ;
		int cantripsNumber = 0 ;
		int highestSpellSlot = 0;
		boolean spezialCase = false;

		if (classSpellKnownCol == null){
			return new ArrayList<>();
		}
		/*
		Liste :
		Warlock,Sorcerer, ranger, Wizard : funktioniert ??
		Paladin, Artificer: abgestürzt -> Endlosschleife?
		level ?
		Monk,Rogue,Fighter, ja

		Abstürze:
		Wizard Lvl 5
		BloodHunter lvl20
		bard lvl 10
		cleric lvl 15
		wizard lvl 10 - classSpellKnownNumber  = null
		warlock lvl 19
		 */
		System.out.println("level: " + level);

		switch (classSpellKnownCol){
			case "NULL":
				System.out.println("0");
				return new ArrayList<>();
            case "tabelle":
				System.out.println("tabelle");
				System.out.println("classSpellKnownNumber: " + dbms.getRowMultPref("spellknown",genClass,"Spell Known" ));
				System.out.println("classSpellKnownNumber2: " + Integer.parseInt(bigStringToDividedString(dbms.getRowMultPref("spellknown",genClass, "Spell Known").getLast()).get(level-1)));
				classSpellKnownNumber = Integer.parseInt(bigStringToDividedString(dbms.getRowMultPref("spellknown",genClass, "Spell Known").getLast()).get(level-1));
				spellSlots = determineSpellSlots(genClass,level);
				cantripsNumber = Integer.parseInt(bigStringToDividedString(dbms.getRow("spellslots", genClass).get(2)).get(level-1));
				break;
			case "spezial":
				System.out.println("spezial");
				System.out.println("classSpellKnownNumber: " + dbms.getRowMultPref("spellknown",genClass, "Spell Known"));
				System.out.println("classSpellKnownNumber2: " + Integer.parseInt(bigStringToDividedString(dbms.getRowMultPref("spellknown",genClass, "Spell Known").getLast()).get(level-1)));

				classSpellKnownNumber = Integer.parseInt((bigStringToDividedString(dbms.getRowMultPref("spellknown",genClass, "Spell Known").getLast()).get(level-1)));
				cantripsNumber = Integer.parseInt(bigStringToDividedString(dbms.getRowMultPref("spellknown",genClass, "Cantrips" ).getLast()).get(level-1));
				highestSpellSlot = Integer.parseInt(bigStringToDividedString(dbms.getRowMultPref("spellknown",genClass, "Slot Level").getLast()).get(level-1));
				spezialCase = true;
				break;
			default:
				System.out.println("default");
				System.out.println("classSpellKnownNumber: " + dbms.getRowMultPref("spellknown","Spell Known", genClass));
				cantripsNumber = Integer.parseInt(bigStringToDividedString(dbms.getRow("spellslots", genClass).get(2)).get(level-1));
				spellSlots = determineSpellSlots(genClass,level);
				classSpellKnownNumber = determineNumberOfKnownSpell(genClass,level,abilityModifier);
				break;
		}
			spells.add("Cantrips: " + dbms.getMultRowMultPref("spells", genClass, "Cantrip", cantripsNumber));
		if ((!spezialCase)) {
			int lastLevelSpellSlot = 1;
			for (int index : spellSlots) {
				if(index == 0){
					highestSpellSlot = lastLevelSpellSlot;
				} else {
					lastLevelSpellSlot++;
				}
			}
		}
		for(int spellLevel=1; (spellLevel<highestSpellSlot) && (classSpellKnownNumber>0); spellLevel++ ){
			int numberSpells  = rand.nextInt(classSpellKnownNumber)+1;
			classSpellKnownNumber = classSpellKnownNumber - numberSpells;
			switch (spellLevel){
				case 1:
					spells.add("1st Level: " + dbms.getMultRowMultPref("spells", genClass, Integer.toString(spellLevel), numberSpells));
					break;
				case 2:
					spells.add("2nd Level: " + dbms.getMultRowMultPref("spells", genClass, Integer.toString(spellLevel), numberSpells));
					break;
				case 3:
					spells.add("3rd Level: " + dbms.getMultRowMultPref("spells", genClass, Integer.toString(spellLevel), numberSpells));
					break;
				case 4:
					spells.add("4th Level: " + dbms.getMultRowMultPref("spells", genClass, Integer.toString(spellLevel), numberSpells));
					break;
				case 5:
					spells.add("5th Level: " + dbms.getMultRowMultPref("spells", genClass, Integer.toString(spellLevel), numberSpells));
					break;
				case 6:
					spells.add("6th Level: " + dbms.getMultRowMultPref("spells", genClass, Integer.toString(spellLevel), numberSpells));
					break;
				case 7:
					spells.add("7th Level: " + dbms.getMultRowMultPref("spells", genClass, Integer.toString(spellLevel), numberSpells));
					break;
				case 8:
					spells.add("8th Level: " + dbms.getMultRowMultPref("spells", genClass, Integer.toString(spellLevel), numberSpells));
					break;
				case 9:
					spells.add("9th Level: " + dbms.getMultRowMultPref("spells", genClass, Integer.toString(spellLevel), numberSpells));
					break;
				default:
					break;
			}
		}
		return spells;
	}
	/*
	gibt ein Integer Liste wieder, mit den Spell Slots für das Level
	 */
	public ArrayList<Integer>determineSpellSlots(String genClass, int level){
		ArrayList<Integer> spellSlots = new ArrayList<>();
		ArrayList<String> dbSpellSlot = dbms.getRow("spellslots", genClass);
		System.out.println("determine SpellSlots: "+ dbms.getRow("spellslots", genClass));
		for(int index=0; index<9; index ++){
			spellSlots.add(Integer.parseInt(bigStringToDividedString(dbSpellSlot.get(3+index)).get(level-1)));
		}
		System.out.println("SpellSlots: "+ spellSlots);
		return spellSlots;
	}
	public int determineNumberOfKnownSpell(String genClass, int level, ArrayList<Integer> abilityModifier) {
		String colClassForSpell = dbms.getRow("class", genClass).get(9);
		String abilityModifierForSpells = colClassForSpell.substring(0, 3);
		int spellModNumber = 0;
		int knownSpells;
		System.out.println("abilityModifierForSpells: "+ abilityModifierForSpells);
		switch (abilityModifierForSpells) {
			case "Str":
				spellModNumber = abilityModifier.getFirst();
				break;
			case "Dex":
				spellModNumber = abilityModifier.get(1);
				break;
			case "Con":
				spellModNumber = abilityModifier.get(2);
				break;
			case "Int":
				spellModNumber = abilityModifier.get(3);
				break;
			case "Wis":
				spellModNumber = abilityModifier.get(4);
				break;
			case "Cha":
				spellModNumber = abilityModifier.get(5);
				break;
			default:
				break;
		}
		if (colClassForSpell.contains("1/2")){
			knownSpells = (int) (spellModNumber +  level*0.5);
		}else {
			knownSpells = spellModNumber * level;
		}
		return knownSpells;
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
	


