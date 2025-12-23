package dndgen.tswp1;

import dndgen.database.DBMS;
import org.springframework.context.ApplicationContext;

import javax.swing.filechooser.FileSystemView;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;


/**
 * The type Npc class.
 */
public class NpcClass {
    /**
     * The Logger.
     */
    static final Logger logger = Logger.getLogger(NpcClass.class.getName());
    /**
     * The App ctx.
     */
    /* @author Lucas
     *
     * Aufrufen der GENAUEN DBMS bzw. SpringApplication die gestartet wurde, jede
     * neu-konstruktion von DBMS wäre sonst leer.
     */
    final ApplicationContext appCtx = DBMS.getApplicationContext();
    /**
     * The Dbms.
     */
    DBMS dbms = appCtx.getBean(DBMS.class);
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
    private List<Integer> abilityModifier;
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
    private Integer id;

    /**
     * Gets name.
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets name.
     *
     * @param name the name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets level.
     *
     * @return the level
     */
    public int getLevel() {
        return level;
    }

    /**
     * Sets level.
     *
     * @param level the level
     */
    public void setLevel(int level) {
        this.level = level;
    }

    /**
     * Gets gen class.
     *
     * @return the gen class
     */
    public String getGenClass() {
        return genClass;
    }

    /**
     * Sets gen class.
     *
     * @param genClass the gen class
     */
    public void setGenClass(String genClass) {
        this.genClass = genClass;
    }

    /**
     * Gets race.
     *
     * @return the race
     */
    public String getRace() {
        return race;
    }

    /**
     * Sets race.
     *
     * @param race the race
     */
    public void setRace(String race) {
        this.race = race;
    }

    /**
     * Gets job.
     *
     * @return the job
     */
    public String getJob() {
        return job;
    }

    /**
     * Sets job.
     *
     * @param job the job
     */
    public void setJob(String job) {
        this.job = job;
    }

    /**
     * Gets armor class.
     *
     * @return the armor class
     */
    public Integer getArmorClass() {
        return armorClass;
    }

    /**
     * Sets armor class.
     *
     * @param armorClass the armor class
     */
    public void setArmorClass(Integer armorClass) {
        this.armorClass = armorClass;
    }

    /**
     * Gets health points.
     *
     * @return the health points
     */
    public int getHealthPoints() {
        return healthPoints;
    }

    /**
     * Sets health points.
     *
     * @param healthPoints the health points
     */
    public void setHealthPoints(int healthPoints) {
        this.healthPoints = healthPoints;
    }

    /**
     * Gets movement speed list.
     *
     * @return the movement speed list
     */
    public List<String> getMovementSpeedList() {
        return movementSpeedList;
    }

    /**
     * Sets movement speed list.
     *
     * @param movementSpeedList the movement speed list
     */
    public void setMovementSpeedList(List<String> movementSpeedList) {
        this.movementSpeedList = movementSpeedList;
    }

    /**
     * Gets languages.
     *
     * @return the languages
     */
    public List<String> getLanguages() {
        return languages;
    }

    /**
     * Sets languages.
     *
     * @param languages the languages
     */
    public void setLanguages(List<String> languages) {
        this.languages = languages;
    }

    /**
     * Gets ability scores.
     *
     * @return the ability scores
     */
    public List<Integer> getAbilityScores() {
        return abilityScores;
    }

    /**
     * Sets ability scores.
     *
     * @param abilityScores the ability scores
     */
    public void setAbilityScores(List<Integer> abilityScores) {
        this.abilityScores = abilityScores;
    }

    /**
     * Gets saving throws.
     *
     * @return the saving throws
     */
    public List<String> getSavingThrows() {
        return savingThrows;
    }

    /**
     * Sets saving throws.
     *
     * @param savingThrows the saving throws
     */
    public void
        setSavingThrows(List<String> savingThrows) {
        this.savingThrows = savingThrows;
    }

    /**
     * Gets armor.
     *
     * @return the armor
     */
    public String getArmor() {
        return armor;
    }

    /**
     * Sets armor.
     *
     * @param armor the armor
     */
    public void setArmor(String armor) {
        this.armor = armor;
    }

    /**
     * Gets weapon list.
     *
     * @return the weapon list
     */
    public List<String> getWeaponList() {
        return this.weaponList;
    }

    /**
     * Sets weapon list.
     *
     * @param weaponList the weapon list
     */
    public void setWeaponList(List<String> weaponList) {
        this.weaponList = weaponList;
    }

    /**
     * Gets skill list.
     *
     * @return the skill list
     */
    public List<String> getSkillList() {
        return skillList;
    }

    /**
     * Sets skill list.
     *
     * @param skillList the skill list
     */
    public void setSkillList(List<String> skillList) {
        this.skillList = skillList;
    }

    /**
     * Gets senses.
     *
     * @return the senses
     */
    public String getSenses() {
        return senses;
    }

    /**
     * Sets senses.
     *
     * @param senses the senses
     */
    public void setSenses(String senses) {
        this.senses = senses;
    }

    /**
     * Gets spell list.
     *
     * @return the spell list
     */
    public List<Object> getSpellList() {
        spellList = new ArrayList<>();
        spellList.add(0, cantrips);
        spellList.add(1, firstLevelSpell);
        spellList.add(2, secondLevelSpell);
        spellList.add(3, thirdLevelSpell);
        spellList.add(4, fourthLevelSpell);
        spellList.add(5, fifthLevelSpell);
        spellList.add(6, sixthLevelSpell);
        spellList.add(7, seventhLevelSpell);
        spellList.add(8, eightLevelSpell);
        spellList.add(9, ninthLevelSpell);
        return spellList;
    }
    /*
    füllt die Spellliste und die Spells mit null
     */
    public void initSpellList(){
        this.cantrips = null;
        this.firstLevelSpell = null;
        this.secondLevelSpell = null;
        this.thirdLevelSpell = null;
        this.fourthLevelSpell= null;
        this.fifthLevelSpell = null;
        this.sixthLevelSpell = null;
        this.seventhLevelSpell = null;
        this.eightLevelSpell = null;
        this.ninthLevelSpell = null;
    }



    /**
     * Sets spell list.
     *
     * @param spellList the spell list
     */

    public void setSpellList(List<Object> spellList) {
        this.setSpellCantrips((String) spellList.get(0));
        this.setFirstLevelSpell((String) spellList.get(1));
        this.setSecondLevelSpell((String) spellList.get(2));
        this.setThirdLevelSpell((String) spellList.get(3));
        this.setFourthLevelSpell((String) spellList.get(4));
        this.setFifthLevelSpell((String) spellList.get(5));
        this.setSixthLevelSpell((String) spellList.get(6));
        this.setSeventhLevelSpell((String) spellList.get(7));
        this.setEightLevelSpell((String) spellList.get(8));
        this.setNinthLevelSpell((String) spellList.get(9));
        this.spellList = spellList;
    }

    /**
     * Insert spell level list into spell list.
     *
     * @param spellLevelList the spell level list
     * @param spellLevel     the spell level
     */
    public void insertSpellLevelListIntoSpellList(List<Object> spellLevelList, int spellLevel) {
        spellList.set(spellLevel, spellLevelList);
    }

    /**
     * Gets spell ability.
     *
     * @return the spell ability
     */
    public List<String> getSpellAbility() {
        return spellAbility;
    }

    /**
     * Sets spell ability.
     *
     * @param spellAbility the spell ability
     */
    public void setSpellAbility(List<String> spellAbility) {
        this.spellAbility = spellAbility;
    }

    /**
     * Gets description.
     *
     * @return the description
     */
    public List<String> getDescription() {
        return description;
    }

    /**
     * Sets description.
     *
     * @param description the description
     */
    public void setDescription(List<String> description) {
        this.description = description;
    }

    /**
     * Gets action list.
     *
     * @return the action list
     */
    public List<String> getActionList() {
        return actionList;
    }

    /**
     * Sets action list.
     *
     * @param actionList the action list
     */
    public void setActionList(List<String> actionList) {
        this.actionList = actionList;
    }

    /**
     * Gets bonus action list.
     *
     * @return the bonus action list
     */
    public List<String> getBonusActionList() {
        return bonusActionList;
    }

    /**
     * Sets bonus action list.
     *
     * @param bonusActionList the bonus action list
     */
    public void setBonusActionList(List<String> bonusActionList) {
        this.bonusActionList = bonusActionList;
    }

    /**
     * Gets prof bonus.
     *
     * @return the prof bonus
     */
    public int getProfBonus() {
        return profBonus;
    }

    /**
     * Sets prof bonus.
     *
     * @param profBonus the prof bonus
     */
    public void setProfBonus(int profBonus) {
        this.profBonus = profBonus;
    }

    /**
     * Gets ability modifier.
     *
     * @return the ability modifier
     */
    public List<Integer> getAbilityModifier() {
        return abilityModifier;
    }

    /**
     * Sets ability modifier.
     *
     * @param abilityModifier the ability modifier
     */
    public void setAbilityModifier(List<Integer> abilityModifier) {
        this.abilityModifier = abilityModifier;
    }

    /**
     * Gets gender.
     *
     * @return the gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * Sets gender.
     *
     * @param gender the gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * Gets age.
     *
     * @return the age
     */
    public int getAge() {
        return age;
    }

    /**
     * Sets age.
     *
     * @param age the age
     */
    public void setAge(int age) {
        this.age = age;
    }

    /**
     * Gets alignment.
     *
     * @return the alignment
     */
    public String getAlignment() {
        return alignment;
    }

    /**
     * Sets alignment.
     *
     * @param alignment the alignment
     */
    public void setAlignment(String alignment) {
        this.alignment = alignment;
    }

    /**
     * Gets deity.
     *
     * @return the deity
     */
    public String getDeity() {
        return deity;
    }

    /**
     * Sets deity.
     *
     * @param deity the deity
     */
    public void setDeity(String deity) {
        this.deity = deity;
    }

    /**
     * Gets bonus text list.
     *
     * @return the bonus text list
     */
    public List<String> getBonusTextList() {
        return bonusTextList;
    }

    /**
     * Sets bonus text list.
     *
     * @param bonusTextList the bonus text list
     */
    public void setBonusTextList(List<String> bonusTextList) {
        this.bonusTextList = bonusTextList;
    }

    /**
     * Get spell cantrips string.
     *
     * @return the string
     */
    public String getSpellCantrips() {
        return cantrips;
    }

    /**
     * Set spell cantrips.
     *
     * @param spells the spells
     */
    public void setSpellCantrips(String spells) {
        this.cantrips = spells;
    }

    /**
     * Add spell cantrip.
     *
     * @param spells the spells
     */
    public void addSpellCantrip(String spells) {
        this.cantrips = addToSpellList(this.cantrips, spells);
    }

    /**
     * Remove spell cantrip.
     *
     * @param spells the spells
     */
    public void removeSpellCantrip(String spells) {
        // Check for null or empty
        if (this.cantrips == null || spells == null || spells.isEmpty()) {
            return;
        }

        // Check for spells existence
        if (!this.cantrips.contains(spells)) {
            return;
        }

        // remove spell
        this.cantrips = removeFromSpellList(this.cantrips, spells);
    }

    /*
     * Get first level spell string.
     *
     * @return the string
     */
    public String getFirstLevelSpell() {
        return firstLevelSpell;
    }

    /**
     * Set first level spell.
     *
     * @param spells the spells
     */
    public void setFirstLevelSpell(String spells) {
        this.firstLevelSpell = spells;
    }

    /**
     * Add first level spell.
     *
     * @param spells the spells
     */
    public void addFirstLevelSpell(String spells) {
        this.firstLevelSpell = addToSpellList(this.firstLevelSpell, spells);
    }

    /**
     * Remove first level spell.
     *
     * @param spells the spells
     */
    public void removeFirstLevelSpell(String spells) {
        // Check for null or empty
        if (this.firstLevelSpell == null || spells == null || spells.isEmpty()) {
            return;
        }

        // Check for spells existence
        if (!this.firstLevelSpell.contains(spells)) {
            return;
        }

        // remove spell
       this.firstLevelSpell = removeFromSpellList(this.firstLevelSpell, spells);
    }

    /**
     * Get second level spell string.
     *
     * @return the string
     */
    public String getSecondLevelSpell() {
        return secondLevelSpell;
    }

    /**
     * Set second level spell.
     *
     * @param spells the spells
     */
    public void setSecondLevelSpell(String spells) {
        this.secondLevelSpell = spells;
    }

    /**
     * Add second level spell.
     *
     * @param spells the spells
     */
    public void addSecondLevelSpell(String spells) {
        this.secondLevelSpell = addToSpellList(this.secondLevelSpell, spells);
    }

    /**
     * Remove second level spell.
     *
     * @param spells the spells
     */
    public void removeSecondLevelSpell(String spells) {
        // Check for null or empty
        if (this.secondLevelSpell == null || spells == null || spells.isEmpty()) {
            return;
        }

        // Check for spells existence
        if (!this.secondLevelSpell.contains(spells)) {
            return;
        }

        // remove spell
        this.secondLevelSpell = removeFromSpellList(this.secondLevelSpell, spells);
    }

    /**
     * Get third level spell string.
     *
     * @return the string
     */
    public String getThirdLevelSpell() {
        return thirdLevelSpell;
    }

    /**
     * Set third level spell.
     *
     * @param spells the spells
     */
    public void setThirdLevelSpell(String spells) {
        this.thirdLevelSpell = spells;
    }

    /**
     * Add third level spell.
     *
     * @param spells the spells
     */
    public void addThirdLevelSpell(String spells) {
        this.thirdLevelSpell = addToSpellList(this.thirdLevelSpell, spells);
    }

    /**
     * Remove third level spell.
     *
     * @param spells the spells
     */
    public void removeThirdLevelSpell(String spells) {
        // Check for null or empty
        if (this.thirdLevelSpell == null || spells == null || spells.isEmpty()) {
            return;
        }

        // Check for spells existence
        if (!this.thirdLevelSpell.contains(spells)) {
            return;
        }

        // remove spell
        this.thirdLevelSpell = removeFromSpellList(this.thirdLevelSpell, spells);
    }

    /**
     * Get fourth level spell string.
     *
     * @return the string
     */
    public String getFourthLevelSpell() {
        return fourthLevelSpell;
    }

    /**
     * Set fourth level spell.
     *
     * @param spells the spells
     */
    public void setFourthLevelSpell(String spells) {
        this.fourthLevelSpell = spells;
    }

    /**
     * Add fourth level spell.
     *
     * @param spells the spells
     */
    public void addFourthLevelSpell(String spells) {
        this.fourthLevelSpell = addToSpellList(this.fourthLevelSpell, spells);
    }

    /**
     * Remove fourth level spell.
     *
     * @param spells the spells
     */
    public void removeFourthLevelSpell(String spells) {
        // Check for null or empty
        if (this.fourthLevelSpell == null || spells == null || spells.isEmpty()) {
            return;
        }

        // Check for spells existence
        if (!this.fourthLevelSpell.contains(spells)) {
            return;
        }

        // remove spell
        this.fourthLevelSpell = removeFromSpellList(this.fourthLevelSpell, spells);
    }

    /**
     * Get fifth level spell string.
     *
     * @return the string
     */
    public String getFifthLevelSpell() {
        return fifthLevelSpell;
    }

    /**
     * Set fifth level spell.
     *
     * @param spells the spells
     */
    public void setFifthLevelSpell(String spells) {
        this.fifthLevelSpell = spells;
    }

    /**
     * Add fifth level spell.
     *
     * @param spells the spells
     */
    public void addFifthLevelSpell(String spells) {
        this.fifthLevelSpell = addToSpellList(this.fifthLevelSpell, spells);
    }

    /**
     * Remove fifth level spell.
     *
     * @param spells the spells
     */
    public void removeFifthLevelSpell(String spells) {
        // Check for null or empty
        if (this.fifthLevelSpell == null || spells == null || spells.isEmpty()) {
            return;
        }

        // Check for spells existence
        if (!this.fifthLevelSpell.contains(spells)) {
            return;
        }

        // remove spell
        this.fifthLevelSpell = removeFromSpellList(this.fifthLevelSpell, spells);
    }

    /**
     * Get sixth level spell string.
     *
     * @return the string
     */
    public String getSixthLevelSpell() {
        return sixthLevelSpell;
    }

    /**
     * Set sixth level spell.
     *
     * @param spells the spells
     */
    public void setSixthLevelSpell(String spells) {
        this.sixthLevelSpell = spells;
    }

    /**
     * Add sixth level spell.
     *
     * @param spells the spells
     */
    public void addSixthLevelSpell(String spells) {
        this.sixthLevelSpell = addToSpellList(this.sixthLevelSpell, spells);
    }

    /**
     * Remove sixth level spell.
     *
     * @param spells the spells
     */
    public void removeSixthLevelSpell(String spells) {
        // Check for null or empty
        if (this.sixthLevelSpell == null || spells == null || spells.isEmpty()) {
            return;
        }

        // Check for spells existence
        if (!this.sixthLevelSpell.contains(spells)) {
            return;
        }

        // remove spell
        this.sixthLevelSpell = removeFromSpellList(this.sixthLevelSpell, spells);
    }

    /**
     * Get seventh level spell string.
     *
     * @return the string
     */
    public String getSeventhLevelSpell() {
        return seventhLevelSpell;
    }

    /**
     * Set seventh level spell.
     *
     * @param spells the spells
     */
    public void setSeventhLevelSpell(String spells) {
        this.seventhLevelSpell = spells;
    }

    /**
     * Add seventh level spell.
     *
     * @param spells the spells
     */
    public void addSeventhLevelSpell(String spells) {
        this.seventhLevelSpell = addToSpellList(this.seventhLevelSpell, spells);
    }

    /**
     * Remove seventh level spell.
     *
     * @param spells the spells
     */
    public void removeSeventhLevelSpell(String spells) {
        // Check for null or empty
        if (this.seventhLevelSpell == null || spells == null || spells.isEmpty()) {
            return;
        }

        // Check for spells existence
        if (!this.seventhLevelSpell.contains(spells)) {
            return;
        }

        // remove spell
        this.seventhLevelSpell = removeFromSpellList(this.seventhLevelSpell, spells);
    }

    /**
     * Get eight level spell string.
     *
     * @return the string
     */
    public String getEightLevelSpell() {
        return eightLevelSpell;
    }

    /**
     * Set eight level spell.
     *
     * @param spells the spells
     */
    public void setEightLevelSpell(String spells) {
        this.eightLevelSpell = spells;
    }

    /**
     * Add eight level spell.
     *
     * @param spells the spells
     */
    public void addEightLevelSpell(String spells) {
        this.eightLevelSpell = addToSpellList(this.eightLevelSpell, spells);
    }

    /**
     * Remove eight level spells.
     *
     * @param spells the spells
     */
    public void removeEightLevelSpells(String spells) {
        // Check for null or empty
        if (this.eightLevelSpell == null || spells == null || spells.isEmpty()) {
            return;
        }

        // Check for spells existence
        if (!this.eightLevelSpell.contains(spells)) {
            return;
        }

        // remove spell
        this.eightLevelSpell = removeFromSpellList(this.eightLevelSpell, spells);
    }

    /**
     * Get ninth level spell string.
     *
     * @return the string
     */
    public String getNinthLevelSpell() {
        return ninthLevelSpell;
    }

    /**
     * Set ninth level spell.
     *
     * @param spells the spells
     */
    public void setNinthLevelSpell(String spells) {
        this.ninthLevelSpell = spells;
    }

    /**
     * Add ninth level spell.
     *
     * @param spells the spells
     */
    public void addNinthLevelSpell(String spells) {
        this.ninthLevelSpell = addToSpellList(this.ninthLevelSpell, spells);
    }

    /**
     * Remove ninth level spell.
     *
     * @param spells the spells
     */
    public void removeNinthLevelSpell(String spells) {
        // Check for null or empty
        if (this.ninthLevelSpell == null || spells == null || spells.isEmpty()) {
            return;
        }

        // Check for spells existence
        if (!this.ninthLevelSpell.contains(spells)) {
            return;
        }

        // remove spell
        this.ninthLevelSpell = removeFromSpellList(this.ninthLevelSpell, spells);
    }

    /**
     * Funktion wird verwendet um für Testzwecke den NPC vollständig zu printen
     */
    public void printNpc() {
        for (String s : Arrays.asList("name: " + name, "level: " + level, "class: " + genClass, "race: " + race, "job: " + job, "AC: " + armorClass,
                "HP: " + healthPoints, "Movement: " + movementSpeedList, "languages: " + languages, "AbilityScore: " + abilityScores, "SavingThrows: "
                        + savingThrows, "Armor: " + armor, "weaponList: " + weaponList, "Skills: " + skillList, "senses: " + senses, "SpellList: " +
                        spellList, "SpellAbility: " + spellAbility, "Description: " + description, "ActionList: " + actionList, "bonusActionList: " +
                        bonusActionList, "ProfBonus: " + profBonus, "AbilityModifier: " + abilityModifier, "Gender: " + gender, "Age: " + age, "Alignment: "
                        + alignment, "Deity: " + deity, "BonusTextList: " + bonusTextList, "Cantrips: " + cantrips, "firstLevelSpell: " + firstLevelSpell,
                "secondLevelSpell: " + secondLevelSpell, "thirdLevelSpell: " + thirdLevelSpell, "fourthLevelSpell: " + fourthLevelSpell, "fifthLevelSpell: "
                        + fifthLevelSpell, "sixthLevelSpell: " + sixthLevelSpell, "seventhLevelSpell: " + seventhLevelSpell, "eightLevelSpell: " + eightLevelSpell,
                "ninthLevelSpell: " + ninthLevelSpell)) {
            logger.info(s);
        }
    }

    /**
     * Export npc.
     */
/*
        export des NPCs als txt Datei
     */
    public void exportNPC() {
        System.out.println("Exported: " + name);
        File desktopPath = FileSystemView.getFileSystemView().getHomeDirectory();
        String fileName = name + ".txt";
        File file = new File(desktopPath,fileName);
        try(BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8)))  {

            for (String s : Arrays.asList("name: " + name, "level: " + level, "class: " + genClass, "race: " + race, "job: " + job, "AC: " + armorClass,
                    "HP: " + healthPoints, "Movement: " + movementSpeedList, "languages: " + languages, "AbilityScore: " + abilityScores,"AbilityModifier: " + abilityModifier, "SavingThrows: "
                            + savingThrows, "Armor: " + armor, "weaponList: " + weaponList, "Skills: " + skillList, "senses: " + senses, "SpellList: " +
                            spellList, "SpellAbility: " + spellAbility/*, "Description: " + description*/, "ActionList: " + actionList, "bonusActionList: " +
                            bonusActionList, "ProfBonus: " + profBonus, "Gender: " + gender, "Age: " + age, "Alignment: "
                            + alignment, "Deity: " + deity, "BonusTextList: " + bonusTextList, "Cantrips: " + cantrips, "firstLevelSpell: " + firstLevelSpell,
                    "secondLevelSpell: " + secondLevelSpell, "thirdLevelSpell: " + thirdLevelSpell, "fourthLevelSpell: " + fourthLevelSpell, "fifthLevelSpell: "
                            + fifthLevelSpell, "sixthLevelSpell: " + sixthLevelSpell, "seventhLevelSpell: " + seventhLevelSpell, "eightLevelSpell: " + eightLevelSpell,
                    "ninthLevelSpell: " + ninthLevelSpell)) {

                writer.write(s + "\n");

            }
            /*
            von Marie ge...liehen
             */
            List<String> descriptionTitles = Arrays.asList("Eyes", "Hair Color", "Hairstyle", "Skin", "Trait", "Mannerism",
                    "Bond", "Flaw", "Ideal", "Voice", "Clothing", "Attitude", "Size", "Height");

            List<String> npcDescription = getDescription();

            for (int i = 0; i < npcDescription.size(); i++) {
                writer.write(descriptionTitles.get(i) + ": " + npcDescription.get(i) + "\n");
            }
        } catch (IOException e) {
            System.out.println("An error occurred.");
            e.printStackTrace();

        }
        System.out.println("Dateipfad: " + file.getAbsolutePath());

    }

    /**
     * Gets id.
     *
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets id.
     *
     * @param id the id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * removes a spell from a spell list and cleans the list up
     *
     * @param npcSpellList is the String of spells -> the List of the Spells
     * @param removeSpell the spell to remove
     * @return a String -> SpellList without the spell
     * @author Marie
     */
    private String removeFromSpellList(String npcSpellList, String removeSpell) {
        npcSpellList = npcSpellList.replace(removeSpell, "");

        // clean spell list
        String cleanSpellList = "";
        String[] spellListSplit = npcSpellList.split(",");
        for (String spell : spellListSplit) {
            String trimSpell = spell.trim();
            if (!trimSpell.isEmpty()) {
                cleanSpellList = cleanSpellList.concat(trimSpell + ", ");
            }
        }

        logger.info(STR."removed spell: {}\{removeSpell}");
        // if there are no spells, the list will be NULL
        if(cleanSpellList.isEmpty()) {
            return null;
        } else {
            cleanSpellList = cleanSpellList.substring(0, cleanSpellList.length() - 2);
            return cleanSpellList;
        }
    }


    /**
     * Adds a spell to the NPC's spell list if it is not already present.
     * If the spell list is null, the provided spell becomes the new list.
     *
     * @param npcSpellList The current spell list of the NPC as a comma-separated string, or null if no spells are present.
     * @param addSpell The spell to add to the NPC's spell list.
     * @return The updated spell list as a comma-separated string.
     * @author Marie
     */
    private String addToSpellList(String npcSpellList, String addSpell){
        if(npcSpellList == null) {
            logger.info(STR."added spell: \{addSpell}");
            return addSpell;
        } else if (npcSpellList.contains(addSpell)) {
            return npcSpellList;
        } else {
            logger.info(STR."added spell: \{addSpell}");
            return npcSpellList + ", " + addSpell;
        }
    }
}

