package dndgen.tswp1;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.*;

import ch.qos.logback.classic.Logger;
import org.springframework.context.ApplicationContext;

import dndgen.database.DBMS;


/**
 * The type Random generator.
 */
public class RandomGenerator {

	/**
	 * The App ctx.
	 */
	/* @author Lucas
	 *
	 * Aufrufen der GENAUEN DBMS bzw. SpringApplication die gestartet wurde, jede
	 * neu-konstruktion von DBMS wäre sonst leer.
	 * Ich musste es etwas anpassen, weil ihr aus irgendeinem Grund nur nutzt,
	 * ich hoffe/denke, dass es auch so funktionieren sollte, übernehme aber keine Verantwortung dafür.
	 */
	ApplicationContext appCtx = DBMS.getApplicationContext();
	/**
	 * The Dbms.
	 */
	DBMS dbms = appCtx.getBean(DBMS.class);

	/**
	 * The Rand.
	 */
	Random rand = new SecureRandom();

	/**
	 * The Name db class.
	 */
	String nameDBClass = "class";
	/**
	 * The Name dbd spell known.
	 */
	String nameDBDSpellKnown = "spellknown";
	/**
	 * The Name db spells.
	 */
	String nameDBSpells = "spells";
	/**
	 * The Name db features.
	 */
	String nameDBFeatures = "features";
	/**
	 * The Name db spell slots.
	 */
	String nameDBSpellSlots = "spellslots";
	/**
	 * The Logger.
	 */
	Logger logger;

	/**
	 * Big string to divided string list.
	 *
	 * @param bigString the big string
	 * @return the list
	 */
//Aufteilung eines großen String in kleinere
	public List<String> bigStringToDividedString(String bigString) {
        String[] partsOfTheBigString = bigString.split(", "); //Teilung des großen Strings an den Stellen wo ein Komma ist
        //rückgabe, des aufgeteilten Strings als eine Liste
        return new ArrayList<>(List.of(partsOfTheBigString));
	}

	/**
	 * Divide doubles list.
	 *
	 * @param bigNumber the big number
	 * @return the list
	 */
	public static List<Double> divideDoubles(String bigNumber) {
		List<String> dividedString = new ArrayList<>();
		List<Double> parsedToDouble = new ArrayList<>();
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

	/**
	 * Dice thrower int.
	 *
	 * @param diceCount  the dice count
	 * @param diceNumber the dice number
	 * @return the int
	 */
	public int diceThrower(Integer diceCount, Integer diceNumber) {

		int addedRolls = 0;

		for (int i = 0; i < diceCount; i++) {
			int value = rand.nextInt(diceNumber) + 1;
			addedRolls = addedRolls + value;
		}

		return addedRolls;
	}

	/**
	 * Rand ability score list.
	 *
	 * @param amountOfThrows the amount of throws
	 * @return the list
	 */
	public List<Integer> randAbilityScore(Integer amountOfThrows) {
		ArrayList<Integer> value = new ArrayList<>();
		ArrayList<Integer> finishedSetForAbilityScores = new ArrayList<>();

		for (int i = 0; i < 6; i++) {
			int j = 0;
			while(j < amountOfThrows + 1) {
				value.add(diceThrower(1, 6));
				j++;
			}

			value.sort(Collections.reverseOrder());
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
		finishedSetForAbilityScores.sort(Collections.reverseOrder());
		return finishedSetForAbilityScores;
	}

	/**
	 * Rand height double.
	 *
	 * @param race the race
	 * @return the double
	 */
	public double randHeight(String race) {
		ArrayList<String> rawHeight;
		ArrayList<Double> minMaxArray;
		String height;
		double min;
		double max;

		rawHeight = dbms.getRowWithPref("race", race);
		height = rawHeight.get(7);

		minMaxArray = (ArrayList<Double>) divideDoubles(height);
		min = minMaxArray.get(0);
		max = minMaxArray.get(1);

		double randomNum = ThreadLocalRandom.current().nextDouble(min, max);

        return Math.round(randomNum * 10.0) / 10.0;
	}

	/**
	 * Rand char class string.
	 *
	 * @return the string
	 */
	/* @Luca */
	public   String randCharClass() {
		ArrayList<String> rndClass = dbms.getRandRow(nameDBClass);

		return rndClass.get(1);
	}

	/**
	 * Rand race string.
	 *
	 * @return the string
	 */
	/* TO DO @Lenard */
	public String randRace() {
		String race;
        ArrayList<String> newRace = dbms.getRandRow("race");
		race = newRace.get(1);

		return race;
	}

	/**
	 * Rand job string.
	 *
	 * @return the string
	 */
	/* TO DO @Lenard */
	public   String randJob() {
		String job;

		ArrayList<String> newJob;
		newJob = dbms.getRandRow("job");

		job = newJob.get(1);

		return job;
	}

	/**
	 * Rand level int.
	 *
	 * @return the int
	 */
	/* @Luca */
	public int randLevel() {
		//Achtung, Level 0 ist möglich und ein Sonderfall der später beachtet werden muss
		return rand.nextInt(21);
	}

	/**
	 * Rand size string.
	 *
	 * @param race the race
	 * @return the string
	 */
	public String randSize(String race) {
		String size;
		ArrayList<String> sizeArray;
		ArrayList<String> newSize;

		sizeArray = dbms.getRowWithPref("race", race);
		size = sizeArray.get(8);
		newSize = (ArrayList<String>) bigStringToDividedString(size);

		if(newSize.size() == 2) {
			int value = rand.nextInt(2);
			size = newSize.get(value);
		} else {
			size = sizeArray.get(8);
		}

		return size;
	}

	/**
	 * Rand description list.
	 *
	 * @param race the race
	 * @return the list
	 */
	/* TO DO @Lenard */
	public List<String> randDescription(String race) {
		ArrayList<String> desc = new ArrayList<>();
		ArrayList<String> dbData;

		String size = randSize(race);
		double height = randHeight(race);

		int i = 1;
		while(i < 13) {
			dbData = dbms.getRandRow("description");
			desc.add(dbData.get(i));
			dbData.clear();
			i++;
		}
		desc.add(size);
		desc.add(Double.toString(height));

		//Eyes, HairColor, HairStyle, Skin, Trait, Mannerism, Bond, Flaw, Ideal, Voice, Clothing, Attitude, Plot hook, size, height
		return desc;
	}

	/**
	 * Figure out prof list.
	 *
	 * @param neededProfList the needed prof list
	 * @param charClass      the char class
	 * @param race           the race
	 * @return the list
	 */
/* Funktion um alle gewünschten Prof aus Klasse, Rasse und Features zu bekommen
		Derzeit ist es möglich mit dieser Funktion die Profs für Waffen, Rüstung und zusätzliche Sicht zu bekommen
	*/
	/* @Luca */
	public List<String> figureOutProfList(String neededProfList, String charClass, String race) {
		Set<String> profSet = new HashSet<>();// Liste mit Ergebnissen die zurückgegeben wird
		switch (neededProfList) {    // Entscheidung welche ProfListe man benötigt
			case "armor":
				profSet.addAll(figureOutArmorProfList(charClass,race));
				break;
			case "weapon":    //siehe "armor" nur jetzt mit den waffen
				profSet.addAll(figureOutWeaponProfList(charClass,race));
				break;
				default:
					break;
		}
		return new ArrayList<>(profSet);
	}

	/**
	 * Figure out armor prof list.
	 *
	 * @param charClass the char class
	 * @param race      the race
	 * @return the list
	 */
	public List<String> figureOutArmorProfList(String charClass, String race){
		List<String> profList; // Liste mit Ergebnissen die zurückgegeben wird
		List<String> databaseReturn; // Liste die von der database zurückkommt
		String kindOfFeature; // letzte Spalte aus der database um Festzustellen um was es sich hier handelt
		profList = bigStringToDividedString(dbms.getRowWithPref(nameDBClass, charClass).get(4));
		databaseReturn = dbms.getRowWithPref("race", race);
		if(!Objects.equals(databaseReturn.get(3), "null")) {
			for (String feature : bigStringToDividedString(databaseReturn.get(3))) {    // die erhaltenen Rassen Features werden durchgecheckt, ob welche dabei sind die Armor Prof geben
				databaseReturn = dbms.getRowWithPref(nameDBFeatures, feature);
				kindOfFeature = databaseReturn.get(3);
				if (kindOfFeature.contains("armor:")) {    // der Teil des Features, an dem man Feststellt, ob es sich um einen Armor Prof handelt wird entfernt
					kindOfFeature= kindOfFeature.replace("armor: ", "");
					profList.addAll(bigStringToDividedString(kindOfFeature));
				}
			}
		}
		return profList;
	}

	/**
	 * Figure out weapon prof list.
	 *
	 * @param charClass the char class
	 * @param race      the race
	 * @return the list
	 */
	public List<String> figureOutWeaponProfList(String charClass, String race){
		List<String> profList = new ArrayList<>(); // Liste mit Ergebnissen die zurückgegeben wird
		List<String> databaseReturn; // Liste die von der database zurückkommt
		String kindOfFeature; // letzte Spalte aus der database um Festzustellen um was es sich hier handelt
		databaseReturn = dbms.getRowWithPref(nameDBClass, charClass);
		if(!Objects.equals(databaseReturn.get(3), "null")) {

			profList = bigStringToDividedString(databaseReturn.get(3));
			databaseReturn = dbms.getRowWithPref("race", race);
			if (!Objects.equals(databaseReturn.get(3), "null")) {
				for (String feature : bigStringToDividedString(databaseReturn.get(3))) {
					databaseReturn = dbms.getRowWithPref(nameDBFeatures, feature);
					kindOfFeature = databaseReturn.get(3);
					if (kindOfFeature.contains("weapon:")) {
						kindOfFeature = kindOfFeature.replace("weapon:", " ");
						profList.addAll(bigStringToDividedString(kindOfFeature));
					}
				}
			}
		}
		return  profList;
	}

	/**
	 * Get vision string.
	 *
	 * @param race the race
	 * @return the string
	 */
	public String getVision (String race){
		String visions = ""; // Liste mit Ergebnissen die zurückgegeben wird
		List<String> databaseReturn; // Liste die von der database zurückkommt
		String kindOfFeature; // letzte Spalte aus der database um Festzustellen um was es sich hier handelt
		StringBuilder visionStringBuilder = new StringBuilder();
		for(String feature: bigStringToDividedString(dbms.getRowWithPref("race", race).get(3))){
			if(feature.equals("null") ){
				break;
			}
			databaseReturn = dbms.getRowWithPref(nameDBFeatures, feature);
			kindOfFeature = databaseReturn.get(3);
			if(kindOfFeature.contains("vision")){
				visionStringBuilder.append(kindOfFeature);
			}
			visions = String.valueOf(visionStringBuilder);
		}
		if(visions.isEmpty()){
			visions = "your blind in the dark... live with it";
		}
		return visions;
	}

	/**
	 * Rand armor string.
	 *
	 * @param armorProf the armor prof
	 * @return the string
	 */
	/* TO DO @Luca */
	public String randArmor(List<String> armorProf) {
		String armor; //return value
		armorProf.remove("Shield");
		int lengthOfArmorProfList = armorProf.size();
		String rndArmor = armorProf.get(rand.nextInt(lengthOfArmorProfList));
		if((rndArmor.equals("Light Armor"))||(rndArmor.equals("Medium Armor"))||(rndArmor.equals("Heavy Armor"))){
			List<String> armorList = dbms.getRandRowWithPref("armor", rndArmor);
			armor = armorList.get(1);
		}else{	//Sonderfälle Rüstung
			armor = rndArmor;
		}
		return armor;
	}

	/**
	 * Rand weapon string.
	 *
	 * @param weaponProf the weapon prof
	 * @return the string
	 */
	/* @Luca */
	public String randWeapon(List<String> weaponProf) {
		int lengthOfWeaponProf = weaponProf.size();
		String weapon;    //das Ergebnis was zurückgegeben wird
		if(weaponProf.isEmpty()){
			return "no Weapon";
		}
		String rndWeapon = weaponProf.get(rand.nextInt(lengthOfWeaponProf)); // aus der Prof Liste wird ein Eintrag zufällig ausgewählt
		if((rndWeapon.equals("Simple Weapon")||(rndWeapon.equals("Martial Weapon")))) {
			List<String> weaponList = dbms.getRandRowWithPref("weapon", rndWeapon);
			weapon = weaponList.get(1);
		}else {
			weapon = rndWeapon;
		}
		return weapon;

	}

	/**
	 * Rand deity string.
	 *
	 * @param alignment the alignment
	 * @return the string
	 */
	/* TO DO @Lenard */
	public String randDeity(String alignment) {
		String deity;

		List<String> newDeity;

		newDeity = dbms.getRandRowWithPref("deity", alignment);
		deity = newDeity.get(1);

		return deity;
	}

	/**
	 * Rand languages string.
	 *
	 * @return the string
	 */
	/* @Luca */
	public   String randLanguages() {
		List<String> rndLang = dbms.getRandRow("language");
		return rndLang.get(1);
	}

	/**
	 * Rand name string.
	 *
	 * @param gender the gender
	 * @param race   the race
	 * @return the string
	 */
	/* TO DO @Lenard */
	public String randName(String gender, String race) {
		String name;
		String firstName;
		String secondName;
		List<String> splitRaceArray = new ArrayList<>();
		String[] newRace = race.split(" ");

		for (String s : newRace) {
			if (!splitRaceArray.contains(s)) {
				splitRaceArray.add(s);
			}
		}
		String newRaceString = splitRaceArray.getLast();
		List<String> newName;

        do {
            newName = dbms.getRandRowWithPref("name", newRaceString);
            firstName = newName.get(1);
        } while (!(gender.equals(newName.get(4))));

		newName = dbms.getRandRowWithPref("name", newRaceString);
		secondName = newName.get(2);

		name = firstName + " " + secondName;

		return name;
	}


	/**
	 * Rand age int.
	 *
	 * @param race the race
	 * @return the int
	 */
	/* @Luca */
	public int randAge(String race) {
		List<String> raceList;
		raceList = dbms.getRowWithPref("race", race);
		int ageAdult = Integer.parseInt(raceList.get(4));
		int ageMax = Integer.parseInt(raceList.get(5));

		return rand.nextInt(ageMax) + ageAdult;
	}

	/**
	 * Rand skill prof list.
	 *
	 * @param possibleSkills the possible skills
	 * @param numberSkill    the number skill
	 * @param knownSkillList the known skill list
	 * @return the list
	 */
//Schrodinger Funktion(ehemals)
	/* @Luca */
	public List<String> randSkillProf(List<String> possibleSkills, int numberSkill, List<String> knownSkillList) {
		if (possibleSkills.isEmpty()) {
			return knownSkillList;
		}
		// Verwende ein Set, um von Anfang an Duplikate zu vermeiden
		Set<String> uniqueSkills = new HashSet<>(knownSkillList);

		// Kopiere die Liste der möglichen Skills, um sie nicht zu verändern
		List<String> remainingSkills = new ArrayList<>(possibleSkills);

		// Mische die Liste, um zufällige Auswahl zu erleichtern
		Collections.shuffle(remainingSkills);

		// Füge zufällig ausgewählte Skills zur Menge hinzu, bis die Anzahl erreicht ist
		for (int runtime=1;numberSkill>=runtime; runtime++) {
                uniqueSkills.add(remainingSkills.get(runtime));  // Duplikate werden durch das Set verhindert
		}
		return new ArrayList<>(uniqueSkills);
	}

	/**
	 * Rand gender string.
	 *
	 * @return the string
	 */
	/* TO DO @Lenard */
	public String randGender() {
		String gender;
		int value = rand.nextInt(2);

		if (value == 0) {
			gender = "female";
		} else {
			gender = "male";
		}
		return gender;
	}

	/**
	 * Rand alignment string.
	 *
	 * @return the string
	 */
	public String randAlignment() {

		return dbms.getRandRow("alignment").get(2);
	}

	/**
	 * Get the highest spell level int.
	 *
	 * @param chosenNpc the chosen npc
	 * @return the int
	 */
	public int getHighestSpellLevel(NpcClass chosenNpc){
		int dbCounter = 3;
		int highestSpellLevel = 0 ;
		for(int i=1; i<10;i++){
			int spellLevel = Integer.parseInt(bigStringToDividedString(dbms.getRowWithPref(nameDBSpellSlots, chosenNpc.getGenClass()).get(dbCounter)).get(chosenNpc.getLevel()-1));
			if(spellLevel == 0){
				return highestSpellLevel;
			}
			highestSpellLevel++;
			dbCounter++;
		}
		return highestSpellLevel;
	}

	/**
	 * Random spells for class npc class.
	 *
	 * @param chosenNpc the chosen npc
	 * @return the npc class
	 */
	public NpcClass randomSpellsForClass(NpcClass chosenNpc){
		String classSpellKnownColDB = dbms.getRowWithPref(nameDBClass, chosenNpc.getGenClass()).get(9);
		int spellCount ;
		int highestSpellLevel;
		int cantripNumber;
		int numberOfRandomSpellsPerLevel;
		int bracketIndex = chosenNpc.getGenClass().indexOf("("); // sollte in einem Klassennamen eine Klammer stehen, um Variationen zu kennzeichnen
		String spellClass;


		switch (classSpellKnownColDB){
			case "null":
				return chosenNpc;
			case "tabelle":
				spellCount = Integer.parseInt(bigStringToDividedString(dbms.getRandRowWithMultPref(nameDBDSpellKnown,chosenNpc.getGenClass(),"Spell Known").get(3)).get(chosenNpc.getLevel()-1));
				highestSpellLevel = getHighestSpellLevel(chosenNpc);
				cantripNumber = Integer.parseInt(bigStringToDividedString(dbms.getRowWithPref(nameDBSpellSlots, chosenNpc.getGenClass()).get(2)).get(chosenNpc.getLevel()-1));
				break;
			case "spezial":
				spellCount = Integer.parseInt(bigStringToDividedString(dbms.getRandRowWithMultPref(nameDBDSpellKnown,chosenNpc.getGenClass(),"Spell Known").get(3)).get(chosenNpc.getLevel()-1));
				highestSpellLevel = Integer.parseInt(bigStringToDividedString(dbms.getRandRowWithMultPref(nameDBDSpellKnown,chosenNpc.getGenClass(),"Slot Level").get(3)).get(chosenNpc.getLevel()-1));
				cantripNumber = Integer.parseInt(bigStringToDividedString(dbms.getRandRowWithMultPref(nameDBDSpellKnown,chosenNpc.getGenClass(),"Cantrips").get(3)).get(chosenNpc.getLevel()-1));
				break;
			default:
				spellCount = determineNumberOfKnownSpell(chosenNpc);
				highestSpellLevel = getHighestSpellLevel(chosenNpc);
				cantripNumber = Integer.parseInt(bigStringToDividedString(dbms.getRowWithPref(nameDBSpellSlots, chosenNpc.getGenClass()).get(2)).get(chosenNpc.getLevel()-1));
				break;
		}
		if(bracketIndex!=-1) { //wenn innerhalb der Klassen Namen eine Klammer steht, um Varianten dieser zu kennzeichen, muss sie entfernt werden, um spells zu finden
			spellClass = chosenNpc.getGenClass().substring(0, bracketIndex);
		}else {
			spellClass = chosenNpc.getGenClass();
		}
		if (cantripNumber != 0 ){
			chosenNpc.setSpellCantrips(determineSpellNames(determineCantrips(cantripNumber,spellClass)));
		}
		if(spellCount != 0 ){
			for (int currentSpellLevel=1;currentSpellLevel <= highestSpellLevel;currentSpellLevel++){
				numberOfRandomSpellsPerLevel = rand.nextInt(6-3)+3;
				List<String> spellsForLevel = dbms.getMultRowWithMultPref(nameDBSpells, spellClass, Integer.toString(currentSpellLevel), numberOfRandomSpellsPerLevel);
				assignSpellsToNpc(chosenNpc, currentSpellLevel, determineSpellNames(spellsForLevel));
				spellCount -= numberOfRandomSpellsPerLevel;

				if (spellCount == 0) break;
			}
		}
		return chosenNpc;
	}

	/**
	 * Assign spells to npc.
	 *
	 * @param chosenNpc  the chosen npc
	 * @param spellLevel the spell level
	 * @param spellNames the spell names
	 */
	public void assignSpellsToNpc(NpcClass chosenNpc, int spellLevel, String spellNames) {
        switch (spellLevel) {
            case 1 -> chosenNpc.setFirstLevelSpell(spellNames);
            case 2 -> chosenNpc.setSecondLevelSpell(spellNames);
            case 3 -> chosenNpc.setThirdLevelSpell(spellNames);
            case 4 -> chosenNpc.setFourthLevelSpell(spellNames);
            case 5 -> chosenNpc.setFifthLevelSpell(spellNames);
            case 6 -> chosenNpc.setSixthLevelSpell(spellNames);
            case 7 -> chosenNpc.setSeventhLevelSpell(spellNames);
            case 8 -> chosenNpc.setEightLevelSpell(spellNames);
            case 9 -> chosenNpc.setNinthLevelSpell(spellNames);
            default -> logger.info("Zauber nicht verfügbar -> FEHLER");
        }
	}

	/*
	Anzahl der möglichen Spells für die Klasse
	 */
	private Integer determineNumberOfKnownSpell(NpcClass chosenNpc) {

		String colClassForSpell = dbms.getRowWithPref(nameDBClass, chosenNpc.getGenClass()).get(9);
		String abilityModifierForSpells = colClassForSpell.substring(0, 3);
		int spellModNumber = 0;
		int knownSpells;

        switch (abilityModifierForSpells) {
            case "Str" -> spellModNumber = chosenNpc.getAbilityModifier().getFirst();
            case "Dex" -> spellModNumber = chosenNpc.getAbilityModifier().get(1);
            case "Con" -> spellModNumber = chosenNpc.getAbilityModifier().get(2);
            case "Int" -> spellModNumber = chosenNpc.getAbilityModifier().get(3);
            case "Wis" -> spellModNumber = chosenNpc.getAbilityModifier().get(4);
            case "Cha" -> spellModNumber = chosenNpc.getAbilityModifier().get(5);
            default -> {//hi
            }
        }
		if (colClassForSpell.contains("1/2")){
			knownSpells = (int) (spellModNumber +  chosenNpc.getLevel()*0.5);
		}else {
			knownSpells = spellModNumber + chosenNpc.getLevel();
		}
		return knownSpells;
	}
	/*
	Damit nicht die alles von den Spells ausgegeben wird, werden hier nur die Namen extrahiert
	 */

	private String determineSpellNames (List<String> spells ){
		StringBuilder spellNames = new StringBuilder(" ");

		for (int i = 1; i < spells.size(); i += 11) {  // Annahme: Zaubername ist an jeder 11. Stelle
			spellNames.append(spells.get(i)).append(", ");
		}
		return spellNames.toString();
	}

	private List<String> determineCantrips(int cantripsNumber, String finalGenClass ){
        return dbms.getMultRowWithMultPref(nameDBSpells, finalGenClass, "Cantrip", cantripsNumber);
	}

	/**
	 * Add race cantrips and spells.
	 *
	 * @param chosenNpc the chosen npc
	 */
	public void addRaceCantripsAndSpells(NpcClass chosenNpc){
		List<String> featureList = bigStringToDividedString(dbms.getRowWithPref("race", chosenNpc.getRace()).get(3));
		List<String> featureSpellList = new ArrayList<>();

		for (String features : featureList){
			if ((Objects.equals(features, "null")||(features==null))){
				return;
			}
			String possibleSpellFeature =  dbms.getRowWithPref(nameDBFeatures,features).get(3);
			if ((possibleSpellFeature.contains("cantrip")||(possibleSpellFeature.contains("spell")))){
				featureSpellList.add(features);
			}
		}
		for (String spellFeature : featureSpellList){
			String kindOfFeature = dbms.getRowWithPref(nameDBFeatures,spellFeature).get(3);

			if(kindOfFeature.startsWith("cantrip:")) {
				aufspaltungRaceCantrip(kindOfFeature, chosenNpc);
			} else if (kindOfFeature.startsWith("spell:")) {
				aufspaltungRaceSpell(kindOfFeature, chosenNpc);
			}
		}
	}

	private void aufspaltungRaceCantrip(String spellFeature, NpcClass chosenNpc){
		String inhalt = spellFeature.substring(8);
		if(inhalt.contains("spell")) {//"cantrip"

			String[] cantrip = inhalt.split(" spell");

			chosenNpc.addSpellCantrip(cantrip[0].trim());
			aufspaltungRaceSpell("spell:" + cantrip[1].trim(), chosenNpc);
		}else{
			String[] featureSubStrings = spellFeature.split(":");
				if(featureSubStrings.length==3){//cantrip: 1: Wizard
					int numberCantrips = Integer.parseInt(featureSubStrings[1].trim());
					String spellClass = featureSubStrings[2].trim();
					//Cantrips zufällig auswählen und zu der Klasse und hinzufügen
					chosenNpc.addSpellCantrip(determineSpellNames(determineCantrips(numberCantrips,spellClass)));
				}else{
					chosenNpc.addSpellCantrip(featureSubStrings[1].trim()); // ganz normaler Cantrip
				}
		}
	}

	private void aufspaltungRaceSpell(String spellFeature, NpcClass chosenNpc) {
		String[] featureSpellAufgeteilt = spellFeature.substring(7).split(", ");

		int spellLevel;
		String spellName = null;
		for (String spell: featureSpellAufgeteilt){
			if(spell.contains("[")){
				spell = spell.replace("[","");
				spell = spell.replace("]","");

				int levelFromSpell = Integer.parseInt(spell.substring(0,2).trim());
					if (chosenNpc.getLevel() >= levelFromSpell){
						spellName = spell.substring(2).trim();
					}
			} else {
				spellName = spell.substring(2).trim();
			}
			if(spellName==null){
				return;
			}
			spellLevel = Integer.parseInt(dbms.getRowWithPref("spells", spellName.trim()).get(2));
			assignSpellsToNpc(chosenNpc,spellLevel,spellName);
		}
	}

	/**
	 * Passive perception string.
	 *
	 * @param abilityModifier the ability modifier
	 * @param skillList       the skill list
	 * @param profBonus       the prof bonus
	 * @return the string
	 */
	public String passivePerception(List<Integer> abilityModifier, List<String> skillList, int profBonus){
		int perception;

		if(skillList.contains("Perception")){
			perception = 10 + abilityModifier.get(4) + profBonus;
		}
		else {
			perception = 10 + abilityModifier.get(4);
		}
        return String.valueOf(perception);
	}

	/**
	 * Spell attack mod int.
	 *
	 * @param abilityModifier the ability modifier
	 * @param profBonus       the prof bonus
	 * @param genClass        the gen class
	 * @return the int
	 */
	public int spellAttackMod(List<Integer> abilityModifier, int profBonus, String genClass){
		int spellAttackMod = 0;

		String spellMod = dbms.getRowWithPref("class", genClass).get(11);
        switch (spellMod) {
            case "Cha" -> spellAttackMod = abilityModifier.get(5) + profBonus;
            case "Wis" -> spellAttackMod = abilityModifier.get(4) + profBonus;
            case "Int" -> spellAttackMod = abilityModifier.get(3) + profBonus;
            case "Int, Wis" -> {
                int intelligence = abilityModifier.get(3);
                int wisdom = abilityModifier.get(4);
                if (intelligence > wisdom) {
                    spellAttackMod = intelligence + profBonus;
                } else {
                    spellAttackMod = wisdom + profBonus;
                }
            }
            default -> {//hi
            }
        }
		return spellAttackMod;
	}

	/**
	 * Spell save dc int.
	 *
	 * @param spellAttackMod the spell attack mod
	 * @return the int
	 */
	public int spellSaveDC(int spellAttackMod){
		int spellSaveDC = 0;

		if(spellAttackMod > 0){
			 spellSaveDC = 8 + spellAttackMod;
		}
		return spellSaveDC;
	}

}



