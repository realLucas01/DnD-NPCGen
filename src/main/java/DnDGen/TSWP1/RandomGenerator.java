package DnDGen.TSWP1;

import java.security.SecureRandom;
import java.util.*;
import java.util.concurrent.*;

import ch.qos.logback.classic.Logger;
import org.springframework.context.ApplicationContext;

import DnDGen.DB.DBMS;


public class RandomGenerator {

	/* @author Lucas
	 *
	 * Aufrufen der GENAUEN DBMS bzw. SpringApplication die gestartet wurde, jede
	 * neu-konstruktion von DBMS wäre sonst leer.
	 * Ich musste es etwas anpassen, weil ihr aus irgendeinem Grund nur nutzt,
	 * ich hoffe/denke, dass es auch so funktionieren sollte, übernehme aber keine Verantwortung dafür.
	 */
	ApplicationContext appCtx = DBMS.getApplicationContext();
	DBMS dbms = appCtx.getBean(DBMS.class);

	Random rand = new SecureRandom();

	String nameDBClass = "class";
	String nameDBDSpellKnown = "spellknown";
	String nameDBSpells = "spells";
	String nameDBFeatures = "features";
	String nameDBSpellSlots = "spellslots";
	Logger logger;
	//Aufteilung eines großen String in kleinere
	public List<String> bigStringToDividedString(String bigString) {
        String[] partsOfTheBigString = bigString.split(", "); //Teilung des großen Strings an den Stellen wo ein Komma ist
        //rückgabe, des aufgeteilten Strings als eine Liste
        return new ArrayList<>(List.of(partsOfTheBigString));
	}

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

	public int diceThrower(Integer diceCount, Integer diceNumber) {

		int addedRolls = 0;

		for (int i = 0; i < diceCount; i++) {
			int value = rand.nextInt(diceNumber) + 1;
			addedRolls = addedRolls + value;
		}

		return addedRolls;
	}

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

	public double randHeight(String race) {
		ArrayList<String> rawHeight;
		ArrayList<Double> minMaxArray;
		String height;
		double min;
		double max;

		rawHeight = dbms.getRow("race", race);
		height = rawHeight.get(7);

		minMaxArray = (ArrayList<Double>) divideDoubles(height);
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
		newSize = (ArrayList<String>) bigStringToDividedString(size);

		if(newSize.size() == 2) {
			int value = rand.nextInt(2);
			size = newSize.get(value);
		} else {
			size = sizeArray.get(8);
		}

		return size;
	}

	/* TO DO @Lenard */
	public List<String> randDescription(String race) {
		ArrayList<String> desc = new ArrayList<>();
		ArrayList<String> dbData;

		String size = randSize(race);
		double height = randHeight(race);

		int i = 1;
		while(i < 13) {
			dbData = dbms.getRandomRow("description");
			desc.add(dbData.get(i));
			dbData.clear();
			i++;
		}
		desc.add(size);
		desc.add(Double.toString(height));

		//Eyes, HairColor, HairStyle, Skin, Trait, Mannerism, Bond, Flaw, Ideal, Voice, Clothing, Attitude, Plothook, size, height
		return desc;
	}

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
	public List<String> figureOutArmorProfList(String charClass, String race){
		List<String> profList; // Liste mit Ergebnissen die zurückgegeben wird
		List<String> databaseReturn; // Liste die von der DB zurückkommt
		String kindOfFeature; // letzte Spalte aus der DB um Festzustellen um was es sich hier handelt
		profList = bigStringToDividedString(dbms.getRow(nameDBClass, charClass).get(4));
		databaseReturn = dbms.getRow("race", race);
		if(!Objects.equals(databaseReturn.get(3), "null")) {
			for (String feature : bigStringToDividedString(databaseReturn.get(3))) {    // die erhaltenen Rassen Features werden durchgecheckt, ob welche dabei sind die Armor Prof geben
				databaseReturn = dbms.getRow(nameDBFeatures, feature);
				kindOfFeature = databaseReturn.get(3);
				if (kindOfFeature.contains("armor:")) {    // der Teil des Features, an dem man Feststellt, ob es sich um einen Armor Prof handelt wird entfernt
					kindOfFeature= kindOfFeature.replace("armor: ", "");
					profList.addAll(bigStringToDividedString(kindOfFeature));
				}
			}
		}
		return profList;
	}
	public List<String> figureOutWeaponProfList(String charClass, String race){
		List<String> profList = new ArrayList<>(); // Liste mit Ergebnissen die zurückgegeben wird
		List<String> databaseReturn; // Liste die von der DB zurückkommt
		String kindOfFeature; // letzte Spalte aus der DB um Festzustellen um was es sich hier handelt
		databaseReturn = dbms.getRow(nameDBClass, charClass);
		if(!Objects.equals(databaseReturn.get(3), "null")) {

			profList = bigStringToDividedString(databaseReturn.get(3));
			databaseReturn = dbms.getRow("race", race);
			if (!Objects.equals(databaseReturn.get(3), "null")) {
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
		return  profList;
	}

	public String getVision (String race){
		String visions = ""; // Liste mit Ergebnissen die zurückgegeben wird
		List<String> databaseReturn; // Liste die von der DB zurückkommt
		String kindOfFeature; // letzte Spalte aus der DB um Festzustellen um was es sich hier handelt
		StringBuilder visionStringBuilder = new StringBuilder();
		for(String feature: bigStringToDividedString(dbms.getRow("race", race).get(3))){
			if(feature.equals("null") ){
				break;
			}
			databaseReturn = dbms.getRow(nameDBFeatures, feature);
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

	/* TO DO @Luca */
	public String randArmor(List<String> armorProf) {
		String armor; //return value
		armorProf.remove("Shield");
		int lengthOfArmorProfList = armorProf.size();
		String rndArmor = armorProf.get(rand.nextInt(lengthOfArmorProfList));
		if((rndArmor.equals("Light Armor"))||(rndArmor.equals("Medium Armor"))||(rndArmor.equals("Heavy Armor"))){
			List<String> armorList = dbms.getRowPref("armor", rndArmor);
			armor = armorList.get(1);
		}else{	//Sonderfälle Rüstung
			armor = rndArmor;
		}
		return armor;
	}

	/* @Luca */
	public String randWeapon(List<String> weaponProf) {
		int lengthOfWeaponProf = weaponProf.size();
		String weapon;    //das Ergebnis was zurückgegeben wird
		if(weaponProf.isEmpty()){
			return "no Weapon";
		}
		String rndWeapon = weaponProf.get(rand.nextInt(lengthOfWeaponProf)); // aus der Prof Liste wird ein Eintrag zufällig ausgewählt
		if((rndWeapon.equals("Simple Weapon")||(rndWeapon.equals("Martial Weapon")))) {
			List<String> weaponList = dbms.getRowPref("weapon", rndWeapon);
			weapon = weaponList.get(1);
		}else {
			weapon = rndWeapon;
		}
		return weapon;

	}

	/* TO DO @Lenard */
	public String randDeity(String alignment) {
		String deity;

		List<String> newDeity;

		newDeity = dbms.getRowPref("deity", alignment);
		deity = newDeity.get(1);

		return deity;
	}

	/* @Luca */
	public   String randLanguages() {
		List<String> rndLang = dbms.getRandomRow("language");
		return rndLang.get(1);
	}

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
		List<String> raceList;
		raceList = dbms.getRow("race", race);
		int ageAdult = Integer.parseInt(raceList.get(4));
		int ageMax = Integer.parseInt(raceList.get(5));

		return rand.nextInt(ageMax) + ageAdult;
	}

	//Schroedingers Funktion(ehemals)
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

		return dbms.getRandomRow("alignment").get(2);
	}
	public int getHighestSpellLevel(NPCClass chosenNPC){
		int dbCounter = 3;
		int highestSpellLevel = 0 ;
		for(int i=1; i<10;i++){
			int spellLevel = Integer.parseInt(bigStringToDividedString(dbms.getRow(nameDBSpellSlots, chosenNPC.getGenClass()).get(dbCounter)).get(chosenNPC.getLevel()-1));
			if(spellLevel == 0){
				return highestSpellLevel;
			}
			highestSpellLevel++;
			dbCounter++;
		}
		return highestSpellLevel;
	}

	public NPCClass randomSpellsForClass(NPCClass chosenNPC){
		String classSpellKnownColDB = dbms.getRow(nameDBClass, chosenNPC.getGenClass()).get(9);
		int spellCount = 0 ;
		int highestSpellLevel= 0 ;
		int cantripNumber= 0;
		int numberOfRandomSpellsPerLevel = 0;
		int bracketIndex = chosenNPC.getGenClass().indexOf("(");
		String spellClass;


		switch (classSpellKnownColDB){
			case "null":
				return chosenNPC;
			case "tabelle":
				spellCount = Integer.parseInt(bigStringToDividedString(dbms.getRowMultPref(nameDBDSpellKnown,chosenNPC.getGenClass(),"Spell Known").get(3)).get(chosenNPC.getLevel()-1));
				highestSpellLevel = getHighestSpellLevel(chosenNPC);
				cantripNumber = Integer.parseInt(bigStringToDividedString(dbms.getRow(nameDBSpellSlots, chosenNPC.getGenClass()).get(2)).get(chosenNPC.getLevel()-1));
				break;
			case "spezial":
				spellCount = Integer.parseInt(bigStringToDividedString(dbms.getRowMultPref(nameDBDSpellKnown,chosenNPC.getGenClass(),"Spell Known").get(3)).get(chosenNPC.getLevel()-1));
				highestSpellLevel = Integer.parseInt(bigStringToDividedString(dbms.getRowMultPref(nameDBDSpellKnown,chosenNPC.getGenClass(),"Slot Level").get(3)).get(chosenNPC.getLevel()-1));
				cantripNumber = Integer.parseInt(bigStringToDividedString(dbms.getRowMultPref(nameDBDSpellKnown,chosenNPC.getGenClass(),"Cantrips").get(3)).get(chosenNPC.getLevel()-1));
				break;
			default:
				spellCount = determineNumberOfKnownSpell(chosenNPC);
				highestSpellLevel = getHighestSpellLevel(chosenNPC);
				cantripNumber = Integer.parseInt(bigStringToDividedString(dbms.getRow(nameDBSpellSlots, chosenNPC.getGenClass()).get(2)).get(chosenNPC.getLevel()-1));
				break;
		}
		if(bracketIndex!=-1) { //wenn innerhalb der Klassen Namen eine Klammer steht, um Varianten dieser zu kennzeichen, muss sie entfernt werden, um spells zu finden
			spellClass = chosenNPC.getGenClass().substring(0, bracketIndex);
		}else {
			spellClass = chosenNPC.getGenClass();
		}

		if (cantripNumber != 0 ){
			chosenNPC.setSpellCantrips(determineSpellNames(determineCantrips(cantripNumber,spellClass)));
		}
		if(spellCount != 0 ){
			for (int currentSpellLevel=1;currentSpellLevel <= highestSpellLevel;currentSpellLevel++){
				numberOfRandomSpellsPerLevel = rand.nextInt(spellCount)+1;
				List<String> spellsForLevel = dbms.getMultRowMultPref(nameDBSpells, spellClass, Integer.toString(currentSpellLevel), numberOfRandomSpellsPerLevel);
				assignSpellsToNPC(chosenNPC, currentSpellLevel, determineSpellNames(spellsForLevel));
				spellCount -= numberOfRandomSpellsPerLevel;

				if (spellCount == 0){
					break;
				}
			}
		}
		return chosenNPC;
	}
	public void assignSpellsToNPC(NPCClass chosenNPC, int spellLevel, String spellNames) {
		switch (spellLevel) {
			case 1:
				chosenNPC.setFirstLevelSpell(spellNames);
				break;
			case 2:
				chosenNPC.setSecondLevelSpell(spellNames);
				break;
			case 3:
				chosenNPC.setThirdLevelSpell(spellNames);
				break;
			case 4:
				chosenNPC.setFourthLevelSpell(spellNames);
				break;
			case 5:
				chosenNPC.setFifthLevelSpell(spellNames);
				break;
			case 6:
				chosenNPC.setSixthLevelSpell(spellNames);
				break;
			case 7:
				chosenNPC.setSeventhLevelSpell(spellNames);
				break;
			case 8:
				chosenNPC.setEightLevelSpell(spellNames);
				break;
			case 9:
				chosenNPC.setNinthLevelSpell(spellNames);
				break;
			default:
				logger.info("Zauber nicht verfügbar -> FEHLER");
		}
	}

	/*
	Anzahl der möglichen Spells für die Klasse
	 */
	private int determineNumberOfKnownSpell(NPCClass chosenNPC) {

		String colClassForSpell = dbms.getRow(nameDBClass, chosenNPC.getGenClass()).get(9);
		String abilityModifierForSpells = colClassForSpell.substring(0, 3);
		int spellModNumber = 0;
		int knownSpells;

		switch (abilityModifierForSpells) {
			case "Str":
				spellModNumber = chosenNPC.getAbilityModifier().getFirst();
				break;
			case "Dex":
				spellModNumber = chosenNPC.getAbilityModifier().get(1);
				break;
			case "Con":
				spellModNumber = chosenNPC.getAbilityModifier().get(2);
				break;
			case "Int":
				spellModNumber = chosenNPC.getAbilityModifier().get(3);
				break;
			case "Wis":
				spellModNumber = chosenNPC.getAbilityModifier().get(4);
				break;
			case "Cha":
				spellModNumber = chosenNPC.getAbilityModifier().get(5);
				break;
			default:
				break;
		}
		if (colClassForSpell.contains("1/2")){
			knownSpells = (int) (spellModNumber +  chosenNPC.getLevel()*0.5);
		}else {
			knownSpells = spellModNumber + chosenNPC.getLevel();
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
        return dbms.getMultRowMultPref(nameDBSpells, finalGenClass, "Cantrip", cantripsNumber);
	}


	public void addRaceCantripsAndSpells(NPCClass chosenNPC){
		List<String> featureList = bigStringToDividedString(dbms.getRow("race", chosenNPC.getRace()).get(3));
		List<String> featureSpellList = new ArrayList<>();

		for (String features : featureList){
			if ((Objects.equals(features, "null")||(features==null))){
				return;
			}
			String possibleSpellFeature =  dbms.getRow(nameDBFeatures,features).get(3);
			if ((possibleSpellFeature.contains("cantrip")||(possibleSpellFeature.contains("spell")))){
				featureSpellList.add(features);
			}
		}
		for (String spellFeature : featureSpellList){
			String kindOfFeature = dbms.getRow(nameDBFeatures,spellFeature).get(3);

			if(kindOfFeature.startsWith("cantrip:")) {
				aufspaltungRaceCantrip(kindOfFeature, chosenNPC);
			} else if (kindOfFeature.startsWith("spell:")) {
				aufspaltungRaceSpell(kindOfFeature, chosenNPC);
			}
		}
	}

	private void aufspaltungRaceCantrip(String spellFeature, NPCClass chosenNPC){
		String inhalt = spellFeature.substring(8);
		if(inhalt.contains("spell")) {//"cantrip"

			String[] cantrip = inhalt.split(" spell");

			chosenNPC.addSpellCantrip(cantrip[0].trim());
			aufspaltungRaceSpell("spell:" + cantrip[1].trim(), chosenNPC);
		}else{
			String[] featureSubStrings = spellFeature.split(":");
				if(featureSubStrings.length==3){//cantrip: 1: Wizard
					int numberCantrips = Integer.parseInt(featureSubStrings[1].trim());
					String spellClass = featureSubStrings[2].trim();
					//Cantrips zufällig auswählen und zu der Klasse und hinzufügen
					chosenNPC.addSpellCantrip(determineSpellNames(determineCantrips(numberCantrips,spellClass)));
				}else{
					chosenNPC.addSpellCantrip(featureSubStrings[1].trim()); // ganz normaler Cantrip
				}
		}
	}

	private void aufspaltungRaceSpell(String spellFeature, NPCClass chosenNPC) {
		String[] featureSpellAufgeteilt = spellFeature.substring(7).split(", ");

		int spellLevel;
		String spellName = null;
		for (String spell: featureSpellAufgeteilt){
			if(spell.contains("[")){
				spell = spell.replace("[","");
				spell = spell.replace("]","");

				int levelFromSpell = Integer.parseInt(spell.substring(0,2).trim());
					if (chosenNPC.getLevel() >= levelFromSpell){
						spellName = spell.substring(2).trim();
					}
			} else {
				spellName = spell.substring(2).trim();
			}
			if(spellName==null){
				return;
			}
			spellLevel = Integer.parseInt(dbms.getRow("spells", spellName.trim()).get(2));
			assignSpellsToNPC(chosenNPC,spellLevel,spellName);
		}
	}

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

	public int spellAttackMod(List<Integer> abilityModifier, int profBonus, String genClass){
		int spellAttackMod = 0;

		String spellMod = dbms.getRow("class", genClass).get(11);
		switch(spellMod){
			case "Cha":
				 spellAttackMod = abilityModifier.get(5) + profBonus;
				 break;
			case "Wis":
				spellAttackMod = abilityModifier.get(4) + profBonus;
				break;
			case "Int":
				spellAttackMod = abilityModifier.get(3) + profBonus;
				break;
			case "Int, Wis":
				int intelligence = abilityModifier.get(3);
				int wisdom = abilityModifier.get(4);
				if(intelligence > wisdom){
					spellAttackMod = intelligence + profBonus;
				}
				else{
					spellAttackMod = wisdom + profBonus;
				}
				break;
			default :
				break;
		}
		return spellAttackMod;
	}

	public int spellSaveDC(int spellAttackMod){
		int spellSaveDC = 0;

		if(spellAttackMod > 0){
			 spellSaveDC = 8 + spellAttackMod;
		}
		return spellSaveDC;
	}

}



