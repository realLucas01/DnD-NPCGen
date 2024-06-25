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
	String placeholderVariableForSonarCloud = "placeholder";
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
		List<String> profList = new ArrayList<>(); // Liste mit Ergebnissen die zurückgegeben wird

		switch (neededProfList) {    // Entscheidung welche ProfListe man benötigt
			case "armor":
				profList.addAll(figureOutArmorProfList(charClass,race));
				break;
			case "weapon":    //siehe "armor" nur jetzt mit den waffen
				profList.addAll(figureOutWeaponProfList(charClass,race));
				break;
				default:
					break;
		}
		Set<String> removeDuplicate = new HashSet<>(profList); // die ArrayList wird in ein Set gestopft, um Duplicate innerhalb, zu entfernen
		profList.clear();
		profList.addAll(removeDuplicate); // diese wird hier wieder zu einer ArrayList
		return profList;
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
		for(String feature: bigStringToDividedString(dbms.getRow("race", race).get(3))){
			if(feature.equals("null") ){
				break;
			}
			databaseReturn = dbms.getRow(nameDBFeatures, feature);
			kindOfFeature = databaseReturn.get(3);
			if(kindOfFeature.contains("vision")){
				visions = visions + kindOfFeature;
			}
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

	//Schroedingers Funktion
	/* @Luca */
	public List<String> randSkillProf(List<String> possibleSkills, Integer numberSkill, List<String> knownSkillList) {
		if (possibleSkills.isEmpty()) {
			return knownSkillList;
		}
		//es werden so lange skills zufällig ausgewählt und hinzugefügt, bis die richtige Anzahl an Skills hinzugefügt wurde
		for (int currentNumberOfNewSkills = 0; currentNumberOfNewSkills < numberSkill;currentNumberOfNewSkills++) {
			int lengthListPossibleSkills = possibleSkills.size();
			int skillIndex = rand.nextInt(lengthListPossibleSkills);
			if(!knownSkillList.contains(possibleSkills.get(skillIndex))){
				knownSkillList.add(possibleSkills.get(skillIndex));
				possibleSkills.remove(skillIndex);
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

		return dbms.getRandomRow("alignment").get(2);
	}
	/*
		Hier wird alles bestimmt was eine Klasse für Werte hat um Spells zu bestimmen
		0 : spellSlots;
		1: highestSpellSlot;
		2: classSpellKnownNumber;
		3: cantripsNumber;
	 */
	public List<Object> classSpellStuff (String genClass, int level, List<Integer> abilityModifier ){
		String classSpellKnownCol = dbms.getRow(nameDBClass, genClass).get(9);
		int classSpellKnownNumber;
		int cantripsNumber;
		int highestSpellSlot = 0;
		boolean spezialCase = false;
		List<Integer> spellSlots = new ArrayList<>();

		if (Objects.equals(classSpellKnownCol, "null")){
			return new ArrayList<>();
		}
		switch (classSpellKnownCol){
			case "null":
				return new ArrayList<>();
			case "tabelle":
				classSpellKnownNumber = Integer.parseInt(bigStringToDividedString(dbms.getRowMultPref(nameDBDSpellKnown, genClass, "Spell Known").getLast()).get(level - 1));
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
				classSpellKnownNumber = determineNumberOfKnownSpell(genClass,level, abilityModifier);
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

		List<Object> spellStuff = new ArrayList<>();
		spellStuff.add(spellSlots);
		spellStuff.add(highestSpellSlot);
		spellStuff.add(classSpellKnownNumber);
		spellStuff.add(cantripsNumber);

		return spellStuff;
	}
	/*
	Funktion die zufällige Zauber und eine zufällige Anzahl ausgibt. Da noch Spells fehlen, und aus Sicherheitsgründen wird jeder DB Aufruf überwacht und nach 3 sekunden automatisch abgebrochen.
	Dies hat zur Folge, dass der abgebrochene DB Aufruf keinerlei Rückgaben/Ergebnisse produziert

	Eine Beispiel Ausgabe:
	[Cantrips: [Prestidigitation, Message, BladeWard], 1st Level: [TashasCausticBrew, FogCloud, FloatingDisk]]
	 */
	public List<Object> randSpellList (String genClass, List<Integer> abilityModifier, int level ){
		List<Object> spellList = new ArrayList<>();
		int classSpellKnownNumber ;
		int cantripsNumber ;
		int highestSpellSlot;

		List<Object> spellStuff = classSpellStuff(genClass,level,abilityModifier);
		if(spellStuff.isEmpty()){
			return new ArrayList<>();
		}
		highestSpellSlot = (int) spellStuff.get(1);
		classSpellKnownNumber = (int) spellStuff.get(2);
		cantripsNumber = (int) spellStuff.get(3);

		if(genClass.contains("(")){
			genClass = genClass.substring(0, genClass.indexOf("("));
		}
		String finalGenClass = genClass;


		spellList.add("Cantrips: " + determineSpellNames(determineCantrips(cantripsNumber,finalGenClass)));
		spellList.addAll(determineSpells(finalGenClass,highestSpellSlot,classSpellKnownNumber));
		return spellList;
	}
	/*
	gibt ein Integer Liste wieder, mit den Spell Slots für das Level
	 */
	private List<Integer>determineSpellSlots(String genClass, int level){
		List<Integer> spellSlots = new ArrayList<>();
		List<String> dbSpellSlot = dbms.getRow(nameDBSpellSlots, genClass);
		for(int index=0; index<9; index ++){
			spellSlots.add(Integer.parseInt(bigStringToDividedString(dbSpellSlot.get(3+index)).get(level-1)));
		}
		return spellSlots;
	}
	/*
	Anzahl der möglichen Spells für die Klasse
	 */
	private int determineNumberOfKnownSpell(String genClass, int level, List<Integer> abilityModifier) {
		String colClassForSpell = dbms.getRow(nameDBClass, genClass).get(9);
		String abilityModifierForSpells = colClassForSpell.substring(0, 3);
		int spellModNumber = 0;
		int knownSpells;

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
	private List<String> determineSpellNames (List<String> spells ){
		List<String> spellNames = new ArrayList<>();
		int lengthSpells;
		int positionSpellName = 1;
		lengthSpells = spells.size()/11;
		for(int indexName=0;indexName<lengthSpells;indexName++ ){
			spellNames.add(spells.get(positionSpellName));
			positionSpellName= positionSpellName+11;
		}
		return spellNames;
	}

	private List<String> determineCantrips(int cantripsNumber, String finalGenClass ){

        return new ArrayList<>(dbms.getMultRowMultPref(nameDBSpells, finalGenClass, "Cantrip", cantripsNumber));
	}

	private List<String> determineSpells(String finalGenClass, int highestSpellSlot, int classSpellKnownNumber){
		List<String> spellList = new ArrayList<>();
		String spellLeveName = null;


		for(int spellLevel=1; (spellLevel<=highestSpellSlot) && (classSpellKnownNumber>0); spellLevel++ ) {
			int numberSpells  = rand.nextInt(classSpellKnownNumber)+1;

			if (numberSpells > 10 ){
				numberSpells = 10;
			}

			classSpellKnownNumber = classSpellKnownNumber - numberSpells;
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
			int finalNumberSpells = numberSpells;
				spellList.add(finalSpellLeveName + ": " + determineSpellNames(dbms.getMultRowMultPref(nameDBSpells, finalGenClass, Integer.toString(spellLevel), finalNumberSpells)));
		}
		return spellList;
	}
	public List<Object> addRaceCantripsAndSpells(String race, List<Object> spellList, int level){
		List<String> featureList = bigStringToDividedString(dbms.getRow("race", race).get(3));
		List<String> featureSpellList = new ArrayList<>();

		for (String features : featureList){
			if ((Objects.equals(features, "null")||(features==null))){
				return new ArrayList<>();
			}
			if ((dbms.getRow(nameDBFeatures,features).get(3).contains("cantrip")||(dbms.getRow(nameDBFeatures,features).get(3).contains("spell")))){
				featureSpellList.add(features);
			}
		}
		for (String spellFeature : featureSpellList){
			String kindOfFeature = dbms.getRow(nameDBFeatures,spellFeature).get(3);

			if(kindOfFeature.startsWith("cantrip:")) {

				spellList=aufspaltungRaceCantrip(kindOfFeature, spellList,level);
			} else if (kindOfFeature.startsWith("spell:")) {

				spellList=aufspaltungRaceSpell(kindOfFeature, spellList,level);
			}

		}

		return spellList;
	}
	private List<Object> aufspaltungRaceCantrip(String spellFeature, List<Object> spellList, int level){
		String inhalt = spellFeature.substring(8);
		List<String> cantripListe = getSpellListFromSpellLevelToEditSpells(spellList,0);
		if(inhalt.contains("spell")) {//"cantrip"

			String[] cantrip = inhalt.split(" spell");

			cantripListe.add(cantrip[0].trim());
			spellList= aufspaltungRaceSpell("spell:" + cantrip[1].trim(), spellList, level);
		}else{
			String[] featureSubStrings = spellFeature.split(":");
				if(featureSubStrings.length==3){//cantrip: 1: Wizard
					int numberCantrips = Integer.parseInt(featureSubStrings[1].trim());
					String spellClass = featureSubStrings[2].trim();
					//Cantrips zufällig auswählen zu der Klasse und hinzufügen
					cantripListe.addAll(determineSpellNames(determineCantrips(numberCantrips,spellClass)));
				}else{
					cantripListe.add(featureSubStrings[1].trim()); // ganz normaler Cantrip
				}
		}
		spellList =  putSingleSpellLevelListBackToBigSpellList(0, cantripListe, spellList);
		return spellList;

	}

	private List<Object> aufspaltungRaceSpell(String spellFeature, List<Object> spellList, int level) {
		String[] featureSpellAufgeteilt = spellFeature.substring(7).split(", ");

		int spellLevel;
		String spellName = null;
		for (String spell: featureSpellAufgeteilt){
			if(spell.contains("[")){
				spell = spell.replace("[","");
				spell = spell.replace("]","");

				int levelFromSpell = Integer.parseInt(spell.substring(0,2).trim());
					if (level >= levelFromSpell){
						spellName = spell.substring(2).trim();
					}
			} else {
				spellName = spell.substring(2).trim();
			}
			if(spellName==null){
				return spellList;
			}
			spellLevel = Integer.parseInt(dbms.getRow("spells", spellName.trim()).get(2));
			List<String> currentSpellList = getSpellListFromSpellLevelToEditSpells(spellList,spellLevel);
				currentSpellList.add(spellName);
			spellList = putSingleSpellLevelListBackToBigSpellList(spellLevel,currentSpellList,spellList);
		}

		return spellList;
	}


	/*
	Aufteilung der großen Spell Liste in eine kleinere die nur die Spells enthält welche man nun bearbeiten möchte
	 */
	public List<String> getSpellListFromSpellLevelToEditSpells(List<Object> spellList, int spellLevel){
		Object spellFromSpellLevel ;
		if(spellList.isEmpty()) {
			return new ArrayList<>();
		}
		int indexNumberKlammer = 12;
		spellList = new ArrayList<>();
		spellList.add(placeholderVariableForSonarCloud);
		spellList.add(placeholderVariableForSonarCloud);
		spellList.add(placeholderVariableForSonarCloud);
		spellList.add(placeholderVariableForSonarCloud);
		spellList.add(placeholderVariableForSonarCloud);
		spellList.add(placeholderVariableForSonarCloud);
		spellList.add(placeholderVariableForSonarCloud);
		spellList.add(placeholderVariableForSonarCloud);
		spellList.add(placeholderVariableForSonarCloud);
		spellList.add(placeholderVariableForSonarCloud);
		switch (spellLevel){
			case 0:
				spellFromSpellLevel = spellList.getFirst();
				indexNumberKlammer = 10;
				break;
			case 1:
				spellFromSpellLevel = spellList.get(1);
				break;
			case 2:
				spellFromSpellLevel = spellList.get(2);
				break;
			case 3:
				spellFromSpellLevel = spellList.get(3);
				break;
			case 4:
				spellFromSpellLevel = spellList.get(4);
				break;
			case 5:
				spellFromSpellLevel = spellList.get(5);
				break;
			case 6:
				spellFromSpellLevel = spellList.get(6);
				break;
			case 7:
				spellFromSpellLevel = spellList.get(7);
				break;
			case 8:
				spellFromSpellLevel = spellList.get(8);
				break;
			case 9:
				spellFromSpellLevel = spellList.get(9);
				break;
			default:
				return new ArrayList<>();
		}
		Set<Object> removeDuplicate = new HashSet<>(spellList); // die ArrayList wird in ein Set gestopft, um Duplicate innerhalb, zu entfernen
		spellList.clear();
		spellList.addAll(removeDuplicate); // diese wird hier wieder zu einer ArrayList
		spellList.remove(placeholderVariableForSonarCloud);
		if (((String)spellFromSpellLevel).contains(placeholderVariableForSonarCloud)){
			return new ArrayList<>();
		}

		String spells = ((String) spellFromSpellLevel).substring(indexNumberKlammer,((String) spellFromSpellLevel).length()-1);
        return new ArrayList<>(bigStringToDividedString(spells));
	}
	/*
		Wenn ein SpellLevel abgearbeitet wurde, kann diese Funktion dazu genutzt werden damit diese veränderte Liste in die SpellList eingefügt werden kann um den alten Datensatz
		zu ersetzen
		Parameter:
		int spellLevelName: LEvel welches der Spell besitzt: 0 = Cantrip, 1= Lvl1,....
		ArrayList<String> die einzelne veränderte Spell Liste
		ArrayList<Object> die gesamte generierte Spell Liste

	 */
	public List<Object> putSingleSpellLevelListBackToBigSpellList(int spellLevelName, List<String> singleSpellList, List<Object> spellList){
		String stuffToPutIntoSpellList;
		if(singleSpellList.isEmpty()){
			return spellList;
		} else if (spellList.isEmpty()) {
			spellList = new ArrayList<>();
			spellList.add(placeholderVariableForSonarCloud);
			spellList.add(placeholderVariableForSonarCloud);
			spellList.add(placeholderVariableForSonarCloud);
			spellList.add(placeholderVariableForSonarCloud);
			spellList.add(placeholderVariableForSonarCloud);
			spellList.add(placeholderVariableForSonarCloud);
			spellList.add(placeholderVariableForSonarCloud);
			spellList.add(placeholderVariableForSonarCloud);
			spellList.add(placeholderVariableForSonarCloud);
			spellList.add(placeholderVariableForSonarCloud);
		}
		if(spellList.size()<=spellLevelName){
			spellList.add(placeholderVariableForSonarCloud);
			spellList.add(placeholderVariableForSonarCloud);
			spellList.add(placeholderVariableForSonarCloud);
			spellList.add(placeholderVariableForSonarCloud);
			spellList.add(placeholderVariableForSonarCloud);
			spellList.add(placeholderVariableForSonarCloud);
			spellList.add(placeholderVariableForSonarCloud);
			spellList.add(placeholderVariableForSonarCloud);
		}
		switch (spellLevelName){
			case 0:
				stuffToPutIntoSpellList = "Cantrip: " + singleSpellList ;
				spellList.set(0, stuffToPutIntoSpellList);
				break;
			case 1:
				stuffToPutIntoSpellList = "1st Level: " + singleSpellList  ;
				spellList.set(1, stuffToPutIntoSpellList);
				break;
			case 2:
				stuffToPutIntoSpellList = "2nd Level: " + singleSpellList ;
				spellList.set(2, stuffToPutIntoSpellList);
				break;
			case 3:
				stuffToPutIntoSpellList = "3th Level: " + singleSpellList ;
				spellList.set(3, stuffToPutIntoSpellList);
				break;
			case 4:
				stuffToPutIntoSpellList = "4th Level: " + singleSpellList ;
				spellList.set(4, stuffToPutIntoSpellList);
				break;
			case 5:
				stuffToPutIntoSpellList = "5th Level: " + singleSpellList ;
				spellList.set(5, stuffToPutIntoSpellList);
				break;
			case 6:
				stuffToPutIntoSpellList = "6th Level: " + singleSpellList ;
				spellList.set(6, stuffToPutIntoSpellList);
				break;
			case 7:
				stuffToPutIntoSpellList = "Cantrip: " + singleSpellList ;
				spellList.set(7, stuffToPutIntoSpellList);
				break;
			case 8:
				stuffToPutIntoSpellList = "8th Level: " + singleSpellList ;
				spellList.set(8, stuffToPutIntoSpellList);
				break;
			case 9:
				stuffToPutIntoSpellList = "9th Level: " + singleSpellList ;
				spellList.set(9, stuffToPutIntoSpellList);
				break;
			default:
				break;
		}
		Set<Object> removeDuplicate = new HashSet<>(spellList); // die ArrayList wird in ein Set gestopft, um Duplicate innerhalb, zu entfernen
		spellList.clear();
		spellList.addAll(removeDuplicate); // diese wird hier wieder zu einer ArrayList
		spellList.remove(placeholderVariableForSonarCloud);
		return spellList;
	}


	public List<Object> arrangeSpellListInTheRightOrder (List<Object> spellList){
		List<Object> arrangedSpellList = new ArrayList<>();
		arrangedSpellList.add(placeholderVariableForSonarCloud);
		arrangedSpellList.add(placeholderVariableForSonarCloud);
		arrangedSpellList.add(placeholderVariableForSonarCloud);
		arrangedSpellList.add(placeholderVariableForSonarCloud);
		arrangedSpellList.add(placeholderVariableForSonarCloud);
		arrangedSpellList.add(placeholderVariableForSonarCloud);
		arrangedSpellList.add(placeholderVariableForSonarCloud);
		arrangedSpellList.add(placeholderVariableForSonarCloud);
		arrangedSpellList.add(placeholderVariableForSonarCloud);
		arrangedSpellList.add(placeholderVariableForSonarCloud);

		for (Object lists: spellList ){
			String listName = ((String) lists).substring(0,1) ;

			switch (listName) {
				case "C":
					arrangedSpellList.set(0,lists);
					break;
				case "1":
					arrangedSpellList.set(1,lists);
					break;
				case "2":
					arrangedSpellList.set(2,lists);
					break;
				case "3":
					arrangedSpellList.set(3,lists);
					break;
				case "4":
					arrangedSpellList.set(4,lists);
					break;
				case "5":
					arrangedSpellList.set(5,lists);
					break;
				case "6":
					arrangedSpellList.set(6,lists);
					break;
				case "7":
					arrangedSpellList.set(7,lists);
					break;
				case "8":
					arrangedSpellList.set(8,lists);
					break;
				case "9":
					arrangedSpellList.set(9,lists);
					break;
				default:
					break;
			}
		}
		if ( arrangedSpellList.contains(placeholderVariableForSonarCloud)){
            arrangedSpellList.removeIf(ob -> ob.equals(placeholderVariableForSonarCloud));
		}
		return arrangedSpellList;
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



