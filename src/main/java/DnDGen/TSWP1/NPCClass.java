package DnDGen.TSWP1;

import java.util.ArrayList;
import java.util.List;

public class NPCClass {

    // Attributes
    private String name;
    private int level;
    private String genClass;
    private String race;
    private String job;
    private int armorClass;
    private int healthPoints;
    private List<String> movementSpeedList;
    private List<String> languages;
    private List<Integer> abilityScores;
    private List<String> savingThrows;
    private String armor;
    private List<String> weaponList;
    private List<String> skillList;
    private String senses;
    private List<Object> spellList;
    private List<String> spellAbility;
    private List<String> description;
    private List<String> actionList;
    private List<String> bonusActionList;
    private int profBonus;
    private List <Integer> abilityModifier;
    private String gender;
    private int age;
    private String alignment;
    private String deity;
    private List<String> bonusTextList;
    private String cantrips;
    private String firstLevelSpell;
    private String secondLevelSpell;
    private String thirdLevelSpell;
    private String fourthLevelSpell;
    private String fifthLevelSpell;
    private String sixthLevelSpell;
    private String seventhLevelSpell;
    private String eightLevelSpell;
    private String ninthLevelSpell;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getGenClass() {
        return genClass;
    }

    public void setGenClass(String genClass) {
        this.genClass = genClass;
    }

    public String getRace() {
        return race;
    }

    public void setRace(String race) {
        this.race = race;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public int getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(int armorClass) {
        this.armorClass = armorClass;
    }

    public int getHealthPoints() {
        return healthPoints;
    }

    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    public List<String> getMovementSpeedList() {
        return movementSpeedList;
    }

    public void setMovementSpeedList(List<String> movementSpeedList) {
        this.movementSpeedList = movementSpeedList;
    }

    public List<String> getLanguages() {
        return languages;
    }

    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    public List <Integer> getAbilityScores() {
        return abilityScores;
    }

    public void setAbilityScores(List <Integer> abilityScores) {
        this.abilityScores = abilityScores;
    }

    public List<String> getSavingThrows() {
        return savingThrows;
    }

    public void setSavingThrows(List<String> savingThrows) {
        this.savingThrows = savingThrows;
    }

    public String getArmor() {
        return armor;
    }

    public void setArmor(String armor) {
        this.armor = armor;
    }

    public List<String> getWeaponList() {
        return weaponList;
    }

    public void setWeaponList(List<String> weaponList) {
        this.weaponList = weaponList;
    }

    public List<String> getSkillList() {
        return skillList;
    }

    public void setSkillList(List<String> skillList) {
        this.skillList = skillList;
    }

    public String getSenses() {
        return senses;
    }

    public void setSenses(String senses) {
        this.senses = senses;
    }

    public List<Object> getSpellList() {
        if (spellList == null){
            spellList = new ArrayList<>();
            spellList.add(0,cantrips);
            spellList.add(1,firstLevelSpell);
            spellList.add(2,secondLevelSpell);
            spellList.add(3,thirdLevelSpell);
            spellList.add(4,fourthLevelSpell);
            spellList.add(5,fifthLevelSpell);
            spellList.add(6,sixthLevelSpell);
            spellList.add(7,seventhLevelSpell);
            spellList.add(8,eightLevelSpell);
            spellList.add(9,ninthLevelSpell);
        }
        return spellList;
    }
    public void insertSpellLevelListIntoSpellList(List<Object> spellLevelList, int spellLevel){
        spellList.set(spellLevel,spellLevelList);
    }

    public void setSpellList(List<Object> spellList) {
        this.spellList = spellList;
    }

    public List<String> getSpellAbility() {
        return spellAbility;
    }

    public void setSpellAbility(List<String> spellAbility) {
        this.spellAbility = spellAbility;
    }

    public List<String> getDescription() {
        return description;
    }

    public void setDescription(List<String> description) {
        this.description = description;
    }

    public List<String> getActionList() {
        return actionList;
    }

    public void setActionList(List<String> actionList) {
        this.actionList = actionList;
    }

    public List<String> getBonusActionList() {
        return bonusActionList;
    }

    public void setBonusActionList(List<String> bonusActionList) {
        this.bonusActionList = bonusActionList;
    }

    public int getProfBonus() {
        return profBonus;
    }

    public void setProfBonus(int profBonus) {
        this.profBonus = profBonus;
    }

    public List <Integer> getAbilityModifier() {
        return abilityModifier;
    }

    public void setAbilityModifier(List <Integer> abilityModifier) {
        this.abilityModifier = abilityModifier;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getAlignment() {
        return alignment;
    }

    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    public String getDeity() {
        return deity;
    }

    public void setDeity(String deity) {
        this.deity = deity;
    }

    public List<String> getBonusTextList() {
        return bonusTextList;
    }

    public void setBonusTextList(List<String> bonusTextList) {
        this.bonusTextList = bonusTextList;
    }

    // NPC zu List und vis versa
    public List<Object> convertNPCToList(){
        List<Object> listOfTheNPC = new ArrayList<>();
        listOfTheNPC.add(getRace());
        listOfTheNPC.add(getLevel());
        listOfTheNPC.add(getJob());
        listOfTheNPC.add(getGenClass());
        listOfTheNPC.add(getAbilityScores());
        listOfTheNPC.add(getAbilityModifier());
        listOfTheNPC.add(getHealthPoints());
        listOfTheNPC.add(getArmor());
        listOfTheNPC.add(getArmorClass());
        listOfTheNPC.add(getWeaponList());
        listOfTheNPC.add(getMovementSpeedList());
        listOfTheNPC.add(getSavingThrows());
        listOfTheNPC.add(getSkillList());
        listOfTheNPC.add(getGender());
        listOfTheNPC.add(getAge());
        listOfTheNPC.add(getName());
        listOfTheNPC.add(getAlignment());
        listOfTheNPC.add(getDescription());
        listOfTheNPC.add(getDeity());
        listOfTheNPC.add(getLanguages());
        listOfTheNPC.add(getSenses());
        listOfTheNPC.add(getSpellList());
        listOfTheNPC.add(getActionList());
        listOfTheNPC.add(getBonusActionList());
        listOfTheNPC.add(getBonusTextList());
        listOfTheNPC.add(getSpellAbility());

        return listOfTheNPC;
    }

    public void convertListToNPC(List<Object> listOfTheNPC){

    }

    public void setSpellCantrips(String spells){
        this.cantrips = spells;
    }

    public String getSpellCantrips(){
        return cantrips;
    }

    public void addSpellCantrip(String spells){
        this.cantrips = this.cantrips + spells;
    }

    public void removeSpellCantrip(String spells){
        this.cantrips = this.cantrips.replace(spells, "");
    }

    public void setFirstLevelSpell(String spells){
        this.firstLevelSpell = spells;
    }

    public String getFirstLevelSpell(){
        return firstLevelSpell;
    }

    public void addFirstLevelSpell(String spells){
        this.firstLevelSpell = this.firstLevelSpell + spells;
    }

    public void removeFirstLevelSpell(String spells){
        this.firstLevelSpell = this.firstLevelSpell.replace(spells, "");
    }

    public void setSecondLevelSpell(String spells){
        this.secondLevelSpell = spells;
    }

    public String getSecondLevelSpell(){
        return secondLevelSpell;
    }

    public void addSecondLevelSpell(String spells){
        this.secondLevelSpell = this.secondLevelSpell + spells;
    }

    public void removeSecondLevelSpell(String spells){
        this.secondLevelSpell = this.secondLevelSpell.replace(spells, "");
    }

    public void setThirdLevelSpell(String spells){
        this.thirdLevelSpell = spells;
    }

    public String getThirdLevelSpell(){
        return thirdLevelSpell;
    }

    public void addThirdLevelSpell(String spells){
        this.thirdLevelSpell = this.thirdLevelSpell + spells;
    }

    public void removeThirdLevelSpell(String spells){
        this.thirdLevelSpell = this.thirdLevelSpell.replace(spells, "");
    }

    public void setFourthLevelSpell(String spells){
        this.fourthLevelSpell = spells;
    }

    public String getFourthLevelSpell(){
        return fourthLevelSpell;
    }

    public void addFourthLevelSpell(String spells){
        this.fourthLevelSpell = this.fourthLevelSpell + spells;
    }

    public void removeFourthLevelSpell(String spells){
        this.fourthLevelSpell = this.fourthLevelSpell.replace(spells, "");
    }

    public void setFifthLevelSpell(String spells){
        this.fifthLevelSpell = spells;
    }

    public String getFifthLevelSpell(){
        return fifthLevelSpell;
    }

    public void addFifthLevelSpell(String spells){
        this.fifthLevelSpell = this.fifthLevelSpell + spells;
    }

    public void removeFifthLevelSpell(String spells){
        this.fifthLevelSpell = this.fifthLevelSpell.replace(spells, "");
    }

    public void setSixthLevelSpell(String spells){
        this.sixthLevelSpell = spells;
    }

    public String getSixthLevelSpell(){
        return sixthLevelSpell;
    }

    public void addSixthLevelSpell(String spells){
        this.sixthLevelSpell = this.sixthLevelSpell + spells;
    }

    public void removeSixthLevelSpell(String spells){
        this.sixthLevelSpell = this.sixthLevelSpell.replace(spells, "");
    }

    public void setSeventhLevelSpell(String spells){
        this.seventhLevelSpell = spells;
    }

    public String getSeventhLevelSpell(){
        return seventhLevelSpell;
    }

    public void addSeventhLevelSpell(String spells){
        this.seventhLevelSpell = this.seventhLevelSpell + spells;
    }

    public void removeSeventhLevelSpell(String spells){
        this.seventhLevelSpell = this.seventhLevelSpell.replace(spells, "");
    }

    public void setEightLevelSpell(String spells){
        this.eightLevelSpell = spells;
    }

    public String getEightLevelSpell(){
        return eightLevelSpell;
    }

    public void addEightLevelSpell(String spells){
        this.eightLevelSpell = this.eightLevelSpell + spells;
    }

    public void removeEightLevelSpells(String spells){
        this.eightLevelSpell = this.eightLevelSpell.replace(spells, "");
    }

    public void setNinthLevelSpell(String spells){
        this.ninthLevelSpell = spells;
    }

    public String getNinthLevelSpell(){
        return ninthLevelSpell;
    }

    public void addNinthLevelSpell(String spells){
        this.ninthLevelSpell = this.ninthLevelSpell + spells;
    }

    public void removeNinthLevelSpell(String spells){
        this.ninthLevelSpell = this.ninthLevelSpell.replace(spells, "");
    }


}
