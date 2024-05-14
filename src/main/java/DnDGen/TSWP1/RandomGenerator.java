package DnDGen.TSWP1;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.*;

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
	String nameDBDSpellKnown = "spellknown";
	String nameDBSpells = "spells";
	String nameDBFeatures = "features";
	String nameDBSpellSlots = "spellslots";
	//Aufteilung eines großen String in kleinere
	public static ArrayList<String> bigStringToDividedString(String bigString) {
        String[] partsOfTheBigString = bigString.split(", "); //Teilung des großen Strings an den Stellen wo ein Komma ist
        //rückgabe, des aufgeteilten Strings als eine Liste
        return new ArrayList<>(List.of(partsOfTheBigString));
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
			while(j < amountOfThrows + 1) {
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
		int lvl = rand.nextInt(21);
		return lvl;
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

	/* Funktion um alle gewünschten Prof aus Klasse, Rasse und Features zu bekommen
		Derzeit ist es möglich mit dieser Funktion die Profs für Waffen, Rüstung und zusätzliche Sicht zu bekommen
	*/
	/* @Luca */
	public ArrayList<String> figureOutProfList(String neededProfList, String charClass, String race) {
		ArrayList<String> profList = new ArrayList<>(); // Liste mit Ergebnissen die zurückgegeben wird
		ArrayList<String> databaseReturn; // Liste die von der DB zurückkommt
		String kindOfFeature; // letzte Spalte aus der DB um Festzustellen um was es sich hier handelt

		switch (neededProfList) {    // Entscheidung welche ProfListe man benötigt
			case "armor":
				profList = bigStringToDividedString(dbms.getRow(nameDBClass, charClass).get(4));
				databaseReturn = dbms.getRow("race", race);
				if(databaseReturn.get(3)!="null") {
					for (String feature : bigStringToDividedString(databaseReturn.get(3))) {    // die erhaltenen Rassen Features werden durchgecheckt, ob welche dabei sind die Armor Prof geben
						databaseReturn = dbms.getRow(nameDBFeatures, feature);
						kindOfFeature = databaseReturn.get(3);
						if (kindOfFeature.contains("armor:")) {    // der Teil des Features, an dem man Feststellt, ob es sich um einen Armor Prof handelt wird entfernt
							kindOfFeature= kindOfFeature.replace("armor: ", "");
							profList.addAll(bigStringToDividedString(kindOfFeature));
						}
					}
				}
				break;
			case "weapon":    //siehe "armor" nur jetzt mit den waffen
				databaseReturn = dbms.getRow(nameDBClass, charClass);
				if(databaseReturn.get(3)!="null") {

					profList = bigStringToDividedString(databaseReturn.get(3));
					databaseReturn = dbms.getRow("race", race);
					if (databaseReturn.get(3) != "null") {
						for (String feature : bigStringToDividedString(databaseReturn.get(3))) {
							databaseReturn = dbms.getRow(nameDBFeatures, feature);
							kindOfFeature = databaseReturn.get(3);
							if (kindOfFeature.contains("weapon:")) {
								kindOfFeature = kindOfFeature.replace("weapon:", " ");
								profList.addAll(bigStringToDividedString(kindOfFeature));
							}
						}
					}
				}
				break;
				default:
					break;
		}
		Set<String> removeDuplicate = new HashSet<>(profList); // die ArrayList wird in ein Set gestopft, um Duplicate innerhalb, zu entfernen
		profList.clear();
		profList.addAll(removeDuplicate); // diese wird hier wieder zu einer ArrayList
		return profList;
	}
	public List<String> getVision (String race){
		ArrayList<String> visions = new ArrayList<>(); // Liste mit Ergebnissen die zurückgegeben wird
		ArrayList<String> databaseReturn; // Liste die von der DB zurückkommt
		String kindOfFeature; // letzte Spalte aus der DB um Festzustellen um was es sich hier handelt
		for(String feature: bigStringToDividedString(dbms.getRow("race", race).get(3))){
			databaseReturn = dbms.getRow(nameDBFeatures, feature);
			kindOfFeature = databaseReturn.get(3);
			if(kindOfFeature.contains("vision")){
				visions.add(kindOfFeature);
			}
		}
		return visions;
	}

	/* TO DO @Luca */
	public String randArmor(ArrayList<String> armorProf) {
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
		for (int currentNumberOfNewSkills = 0; currentNumberOfNewSkills < numberSkill; ) {
			int lengthListPossibleSkills = possibleSkills.size();
			int skillIndex = rand.nextInt(lengthListPossibleSkills);
			if(!knownSkillList.contains(possibleSkills.get(skillIndex))){
				knownSkillList.add(possibleSkills.get(skillIndex));
				possibleSkills.remove(skillIndex);
				currentNumberOfNewSkills++;
			}
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
	/*
	Funktion die zufällige Zauber und eine zufällige Anzahl ausgibt. Da noch Spells fehlen, und aus Sicherheitsgründen wird jeder DB Aufruf überwacht und nach 3 sekunden automatisch abgebrochen.
	Dies hat zur Folge, dass der abgebrochene DB Aufruf keinerlei Rückgaben/Ergebnisse produziert

	Eine Beispiel Ausgabe:
	[Cantrips: [Prestidigitation, Message, BladeWard], 1st Level: [TashasCausticBrew, FogCloud, FloatingDisk]]
	 */
	public ArrayList<String> randSpellList (String genClass, ArrayList<Integer> abilityModifier, int level ){
		ExecutorService executor = Executors.newSingleThreadExecutor();
		String classSpellKnownCol = dbms.getRow(nameDBClass, genClass).get(9);
		ArrayList<String> spellList = new ArrayList<>();
		ArrayList<Integer> spellSlots = new ArrayList<>();
		int classSpellKnownNumber ;
		int cantripsNumber ;
		int highestSpellSlot = 0;
		boolean spezialCase = false;

		if (Objects.equals(classSpellKnownCol, "null")){
			return new ArrayList<>();
		}
		switch (classSpellKnownCol){
			case "null":
				return new ArrayList<>();
            case "tabelle":
				classSpellKnownNumber = Integer.parseInt(bigStringToDividedString(dbms.getRowMultPref(nameDBDSpellKnown,genClass, "Spell Known").getLast()).get(level-1));
				spellSlots = determineSpellSlots(genClass,level);
				cantripsNumber = Integer.parseInt(bigStringToDividedString(dbms.getRow(nameDBSpellSlots, genClass).get(2)).get(level-1));
				break;
			case "spezial":
				classSpellKnownNumber = Integer.parseInt((bigStringToDividedString(dbms.getRowMultPref(nameDBDSpellKnown,genClass, "Spell Known").getLast()).get(level-1)));
				cantripsNumber = Integer.parseInt(bigStringToDividedString(dbms.getRowMultPref(nameDBDSpellKnown,genClass, "Cantrips" ).getLast()).get(level-1));
				highestSpellSlot = Integer.parseInt(bigStringToDividedString(dbms.getRowMultPref(nameDBDSpellKnown,genClass, "Slot Level").getLast()).get(level-1));
				spezialCase = true;
				break;
			default:
				cantripsNumber = Integer.parseInt(bigStringToDividedString(dbms.getRow(nameDBSpellSlots, genClass).get(2)).get(level-1));
				spellSlots = determineSpellSlots(genClass,level);
				classSpellKnownNumber = determineNumberOfKnownSpell(genClass,level,  abilityModifier);
				break;
		}

		if ((!spezialCase)) {
			int lastLevelSpellSlot = 1;
			for (int index : spellSlots) {
				if(index == 0){
					break;
				} else {
					highestSpellSlot = lastLevelSpellSlot;
					lastLevelSpellSlot++;
				}
			}
		}
		if(genClass.contains("(")){
			genClass = genClass.substring(0, genClass.indexOf("("));
		}
		String finalGenClass = genClass;


		spellList.add("Cantrips: " + determineSpellNames(determineCantrips(cantripsNumber,finalGenClass)));
		spellList.addAll(determineSpells(finalGenClass,highestSpellSlot,classSpellKnownNumber));
		executor.shutdown();
		return spellList;
	}
	/*
	gibt ein Integer Liste wieder, mit den Spell Slots für das Level
	 */
	private ArrayList<Integer>determineSpellSlots(String genClass, int level){
		ArrayList<Integer> spellSlots = new ArrayList<>();
		ArrayList<String> dbSpellSlot = dbms.getRow(nameDBSpellSlots, genClass);
		for(int index=0; index<9; index ++){
			spellSlots.add(Integer.parseInt(bigStringToDividedString(dbSpellSlot.get(3+index)).get(level-1)));
		}
		return spellSlots;
	}
	/*
	Anzahl der möglichen Spells für die Klasse
	 */
	private int determineNumberOfKnownSpell(String genClass, int level, ArrayList<Integer> abilityModifier) {
		String colClassForSpell = dbms.getRow("class", genClass).get(9);
		String abilityModifierForSpells = colClassForSpell.substring(0, 3);
		int spellModNumber = 0;
		int knownSpells;
		//ArrayList<Integer> abilityModifier = new ArrayList<>();
		/*for (String element : abilityModifierString) {
			int value = Integer.parseInt(element);
			abilityModifier.add(value);
		}*/
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
			knownSpells = spellModNumber + level;
		}
		return knownSpells;
	}
	/*
	Damit nicht die alles von den Spells ausgegeben wird, werden hier nur die Namen extrahiert
	 */
	private ArrayList<String> determineSpellNames (ArrayList<String> spells ){
		ArrayList<String> spellNames = new ArrayList<>();
		int lengthSpells;
		int positionSpellName = 1;
		lengthSpells = spells.size()/11;
		for(int indexName=0;indexName<lengthSpells;indexName++ ){
			spellNames.add(spells.get(positionSpellName));
			positionSpellName= positionSpellName+11;
		}
		return spellNames;
	}

	private ArrayList<String> determineCantrips(int cantripsNumber, String finalGenClass ){
		ExecutorService executor = Executors.newSingleThreadExecutor();
		ArrayList<String> cantrips = new ArrayList<>();
		Future<?> future0 = executor.submit(() ->{
			cantrips.addAll(dbms.getMultRowMultPref(nameDBSpells, finalGenClass, "Cantrip", cantripsNumber));
		});try {
			future0.get(3, TimeUnit.SECONDS); // Timeout nach 5 Sekunden
		} catch (ExecutionException | TimeoutException | InterruptedException e) {
			future0.cancel(true); // Thread abbrechen, falls Timeout erreicht wurde
		}
		return cantrips;
	}

	private ArrayList<String> determineSpells(String finalGenClass, int highestSpellSlot, int classSpellKnownNumber){
		ExecutorService executor = Executors.newSingleThreadExecutor();
		ArrayList<String> spellList = new ArrayList<>();
		String spellLeveName = null;


		for(int spellLevel=1; (spellLevel<=highestSpellSlot) && (classSpellKnownNumber>0); spellLevel++ ) {
			int numberSpells  = rand.nextInt(classSpellKnownNumber)+1;
			classSpellKnownNumber = classSpellKnownNumber - numberSpells;
			int finalSpellLevel = spellLevel;
			switch (spellLevel){
				case 1 :
					spellLeveName = "1st Level";
					break;
				case 2 :
					spellLeveName = "2nd Level";
					break;
				case 3 :
					spellLeveName = "3th Level";
					break;
				case 4 :
					spellLeveName = "4th Level";
					break;
				case 5 :
					spellLeveName = "5th Level";
					break;
				case 6 :
					spellLeveName = "6th Level";
					break;
				case 7 :
					spellLeveName = "7th Level";
					break;
				case 8 :
					spellLeveName = "8th Level";
					break;
				case 9:
					spellLeveName = "9th Level";
					break;
				default:
						break;

			}
			String finalSpellLeveName = spellLeveName;
			Future<?> future1 = executor.submit(() ->{
				spellList.add(finalSpellLeveName + ": " + determineSpellNames(dbms.getMultRowMultPref(nameDBSpells, finalGenClass, Integer.toString(finalSpellLevel), numberSpells)));
			});
			try {
				future1.get(3, TimeUnit.SECONDS); // Timeout nach 5 Sekunden
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				future1.cancel(true); // Thread abbrechen, falls Timeout erreicht wurde
			}

		}
		return spellList;
	}

	public String passivePerception(ArrayList<Integer> abilityModifier, ArrayList<String> skillList, int profBonus){
		//System.out.println("Ability M.: " + abilityModifier.get(4));
		int perception = 0;

		if(skillList.contains("Perception")){
			perception = 10 + abilityModifier.get(4) + profBonus;
		}
		else {
			perception = 10 + abilityModifier.get(4);
		}
		//System.out.println("passive Perception: " + perception);
        return String.valueOf(perception);
	}

	/* TO DO @Kristina */
	/*
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
	/*
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
	}*/
}
//@autor Lucas
//sie hat mehrmals irgendwo vergessen if's zu schließen, und ich hab keine Lust zu suchen wo, deswegen hier ein paar extra klammern,
//damit mein eclipse mir die anderen fehler anzeigt :')
//
//edit: nvm, mein compiler hat die Anzahl an Fehlern gesehen und nach den ersten 115 Zeilen von 500+ aufgehört zu zählen und sie anzuzeigen :'(
	


