package DnDGen.TSWP1;

import DnDGen.DB.DBMS;
import DnDGen.DB.HistoryModel;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


public class NpcClass {
    /* @author Lucas
     *
     * Aufrufen der GENAUEN DBMS bzw. SpringApplication die gestartet wurde, jede
     * neu-konstruktion von DBMS wäre sonst leer.
     */
    ApplicationContext appCtx = DBMS.getApplicationContext();
    DBMS dbms = appCtx.getBean(DBMS.class);
    
    static Logger logger = Logger.getLogger(NpcClass.class.getName());

    // Attributes
    private String name;
    private Integer level;
    private String genClass;
    private String race;
    private String job;
    private Integer armorClass;
    private Integer healthPoints;
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
    private Integer profBonus;
    private List <Integer> abilityModifier;
    private String gender;
    private Integer age;
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

    public Integer getArmorClass() {
        return armorClass;
    }

    public void setArmorClass(Integer armorClass) {
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

    public void historyEntryToNpc(HistoryModel entry){
        setRace(entry.getHistoryRace());
        setGenClass(entry.getHistoryClass());
        setAlignment(entry.getHistoryAlignment());
        setDeity(entry.getHistoryDeity());
        setName(entry.getHistoryName());
        setLevel(entry.getHistoryLevel());
        setJob(entry.getHistoryJob());
        setArmorClass(entry.getHistoryAc());
        setHealthPoints(entry.getHistoryHp());
        setMovementSpeedList(entry.getHistorySpeed());
        setLanguages(entry.getHistoryLanguages());
        setAbilityScores(entry.getHistoryAbiliSc());
        setAbilityModifier(entry.getHistoryAbiliMod());
        setSavingThrows(entry.getHistorySavingTh());
        setArmor(entry.getHistoryArmor());
        setWeaponList(entry.getHistoryWeapon());
        setSkillList(entry.getHistorySkills());
        setSpellAbility(entry.getHistorySpellAb());
        setDescription(entry.getHistoryDesc());
        setActionList(entry.getHistoryActions());
        setBonusActionList(entry.getHistoryBonusTextList());
        setProfBonus(entry.getHistoryProfBonus());
        setGender(entry.getHistoryGender());
        setAge(entry.getHistoryAge());
        setBonusTextList(entry.getHistoryBonusTextList());
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

    /**
     * Funktion wird verwendet um für Testzwecke den NPC vollständig zu printen
     */
    public void printNpc(){
        for (String s : Arrays.asList("name: " + name, "level: " + level, "class: " + genClass, "race: " + race, "job: " + job, "AC: " + armorClass,
                "HP: " + healthPoints, "Movement: " + movementSpeedList, "languages: " + languages, "AbilityScore: " + abilityScores, "SavingThrows: "
                        + savingThrows, "Armor: " + armor, "weaponList: " + weaponList, "Skills: " + skillList, "senses: " + senses, "Spelllist: " +
                        spellList, "SpellAbility: " + spellAbility, "Description: " + description, "ActionList: " + actionList, "bonusActionList: " +
                        bonusActionList, "ProfBonus: " + profBonus, "AbilityModifier: " + abilityModifier, "Gender: " + gender, "Age: " + age, "Alignment: "
                        + alignment, "Deity: " + deity, "BonusTextList: " + bonusTextList, "Cantrips: " + cantrips, "firstLevelSpell: " + firstLevelSpell,
                        "secondLevelSpell: " + secondLevelSpell, "thirdLevelSpell: " + thirdLevelSpell, "fourthLevelSpell: " + fourthLevelSpell, "fifthLevelSpell: "
                        + fifthLevelSpell, "sixthLevelSpell: " + sixthLevelSpell, "seventhLevelSpell: " + seventhLevelSpell, "eightLevelSpell: " + eightLevelSpell,
                        "ninthLevelSpell: " + ninthLevelSpell)) {
            logger.info(s);
        }
    }

    public void saveNpcInHistory() {
        ArrayList<Object> history = new ArrayList<>();

        history.add(name);
        history.add(level);
        history.add(genClass);
        history.add(race);
        history.add(job);
        history.add(armorClass);
        history.add(healthPoints);
        history.add(movementSpeedList);
        history.add(languages);
        history.add(abilityScores);
        history.add(savingThrows);
        history.add(armor);
        history.add(weaponList);
        history.add(skillList);
        history.add(senses);
        history.add(spellList);
        history.add(spellAbility);
        history.add(description);
        history.add(actionList);
        history.add(bonusActionList);
        history.add(profBonus);

        history.add(abilityModifier);
        history.add(gender);
        history.add(age);
        history.add(alignment);
        history.add(deity);
        history.add(bonusTextList);

        dbms.saveEntry(history);
    }
}
