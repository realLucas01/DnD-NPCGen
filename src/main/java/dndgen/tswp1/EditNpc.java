package dndgen.tswp1;

import dndgen.database.DBMS;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

import java.security.SecureRandom;
import java.time.LocalDateTime;
import java.util.*;

/**
 * The type Edit npc.
 */
public class EditNpc {
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
    final DBMS dbms = appCtx.getBean(DBMS.class);

    /**
     * The Rnd numb.
     */
    Random rndNumb = new SecureRandom();
    /**
     * The Rand gen.
     */
    final RandomGenerator randGen = new RandomGenerator();
    /**
     * The Rand npc.
     */
    @Autowired
    private RandomNpc randNpc;
    //RandomNpc randNpc = new RandomNpc();

/*
    eine ArrayList, diese ist wie folgt aufgebaut:
            * (race, level, job, class, AbilityScore, AbilityModifier, HP, Armor, AC, Weapon(), walkingSpeed, SavingThrows, Skills(), Gender, Age, Name, Description, Deity, Languages, Senses, spellList)
    *   0: ID //muss auch wieder zurückgegeben werden
        1: irgendwas von Lucas

        2: name ;
        3: level ;
        4: genClass ;
        5: race ;
        6: job ;
        7: armorClass ;
        8: healthPoints ;
        9: movementSpeedList ;
        10: languages ;
        11: abilityScores ;
        12: savingThrows ;
        13: armor ;
        14: weaponList ;
        15: skillList ;
        16: senses ;
        17: spellList ;
        18: spellAbility ;
        19: description ;
        20: actionList ;
        21: bonusActionList ;
        22: profBonus ;

        23: abilityModifier ;
        24: gender ;
        25: age ;
        26: alignment ;
        27: deity ;
        28: bonusTextList ;

        length - 1: Datetime


 */


    /*
        Legende:
            ST: Saving Throws,
            PB: ProfBonus,
            AS: Ability Score,
            AC: Armor Class

           was passiert, wenn man was ändert:

           Level:
            HP, Action, AS(HP,ST,Skills, Spells) , Spells, PB(-> Skills, SavingThrows)

           Class:
            HP, AS Verteilung, ProfWeapon, ProfArmor, ST, Skills, Spells, features, (BonusAction, Action)

           Race:
            AS, features, Age, Skills, description, Language,(BonusAction, Action)

           Job:
            Skills, Language

           AS:
            HP, AC, Skills(Berechnung), Spells(Anzahl), ST(Berechnung)

           Armor:
            AC

           Skills:
            neue Skills berechnen


           Pseudocode:
            Erst checken was sich alles geändert hat, Namen der Änderungen in Liste speichern, in Variablen neue und alte werte speichern

            for(String changes : changeList){

            switch(changes):

                case "class":
                    do stuff;
                    break;

                case "race"

            }

         */

        /*
            was nach dem neuen Stand berechnet werden möchte, wenn sich dies ändert:
              Class:
                - profArmor:
                       - AC
                - saving throws
                - Skills (zahlen)
                - AbilityScoreDistribution
                - Spells (Anzahl)

              Race:
                -

         */

    /**
     * Remove plus number in a string list.
     *
     * @param stringList the string list
     * @return the list
     */
    public List<String> removePlusNumberInAStringList(List<String> stringList) {
        List<String> stringListWithoutNumbers = new ArrayList<>();
        for (String skills : stringList) {
            stringListWithoutNumbers.add(skills.substring(0, skills.indexOf("+") - 1));
        }
        return stringListWithoutNumbers;
    }

    public void editTheNPC(NpcClass changedNPC, NpcClass oldNPC, Integer historyID, LocalDateTime dateTime) {
        NpcClass editedNPC = oldNPC;
        editedNPC.setName(changedNPC.getName());
        editedNPC.setAbilityScores(changedNPC.getAbilityScores());
        editedNPC.setAbilityModifier(changedNPC.getAbilityModifier());
        editedNPC.setLevel(changedNPC.getLevel());
        setLevelChanges(changedNPC, oldNPC, editedNPC); //level Änderungen
        editedNPC.setGenClass(changedNPC.getGenClass());
        editedNPC.setRace(changedNPC.getRace());
        editedNPC.setJob(changedNPC.getJob());
        if(!Objects.equals(oldNPC.getJob(), changedNPC.getJob())){ setJobChanges(changedNPC,oldNPC,editedNPC);}
        editedNPC.setArmor(changedNPC.getArmor());
        setNewArmorClass(changedNPC,editedNPC);
        editedNPC.setHealthPoints(changedNPC.getHealthPoints());
        editedNPC.setMovementSpeedList(randNpc.determineMovementSpeed(editedNPC.getRace()));
        editedNPC.setLanguages(changedNPC.getLanguages());
        editedNPC.setSavingThrows(randNpc.determineSavingThrows(editedNPC.getGenClass(),editedNPC.getAbilityModifier(),editedNPC.getProfBonus()));
        editedNPC.setWeaponList(changedNPC.getWeaponList()); //scheint bei History edit Update er ledig worden zu sein
        editedNPC.setSkillList(changedNPC.getSkillList());
        editedNPC.setSenses(changedNPC.getSenses());
        editedNPC.setSpellList(changedNPC.getSpellList());
        editedNPC.setSpellAbility(changedNPC.getSpellAbility());
        editedNPC.setDescription(changedNPC.getDescription());
        editedNPC.setActionList(changedNPC.getActionList());
        editedNPC.setBonusActionList(changedNPC.getBonusActionList());
        editedNPC.setProfBonus(randNpc.determineProfBonus(editedNPC.getLevel()));
        editedNPC.setGender(changedNPC.getGender());
        editedNPC.setAge(changedNPC.getAge());
        editedNPC.setAlignment(changedNPC.getAlignment());
        editedNPC.setDeity(changedNPC.getDeity());
        editedNPC.setBonusTextList(changedNPC.getBonusTextList());
        editedNPC.setSpellCantrips(changedNPC.getSpellCantrips());
        editedNPC.setFirstLevelSpell(changedNPC.getFirstLevelSpell());
        editedNPC.setSecondLevelSpell(changedNPC.getSecondLevelSpell());
        editedNPC.setThirdLevelSpell(changedNPC.getThirdLevelSpell());
        editedNPC.setFourthLevelSpell(changedNPC.getFourthLevelSpell());
        editedNPC.setFifthLevelSpell(changedNPC.getFifthLevelSpell());
        editedNPC.setSixthLevelSpell(changedNPC.getSixthLevelSpell());
        editedNPC.setSeventhLevelSpell(changedNPC.getSeventhLevelSpell());
        editedNPC.setEightLevelSpell(changedNPC.getEightLevelSpell());
        editedNPC.setEightLevelSpell(changedNPC.getEightLevelSpell());
        editedNPC.setNinthLevelSpell(changedNPC.getNinthLevelSpell());

        dbms.updateHistoryEntry(editedNPC, historyID, dateTime);
    }


    private void setLevelChanges(NpcClass changedNPC, NpcClass oldNPC, NpcClass editedNPC){
        editedNPC.setLevel(changedNPC.getLevel());
        if(changedNPC.getHealthPoints() == oldNPC.getHealthPoints()){ // wenn die HP unverändert sind, werden sie neu generiert
            editedNPC.setHealthPoints(randNpc.calculateHP(changedNPC.getGenClass(), editedNPC.getLevel(), editedNPC.getAbilityModifier()));
        } else { // wenn sie manuell geändert wurden, werden diese übernommen
            editedNPC.setHealthPoints(changedNPC.getHealthPoints());
        }
        editedNPC.setProfBonus(randNpc.determineProfBonus(editedNPC.getLevel()));
        editedNPC.setSkillList(randNpc.addAbilityScoreToSkillList(editedNPC.getAbilityModifier(),removePlusNumberInAStringList(changedNPC.getSkillList()),editedNPC.getProfBonus()));
        editedNPC.setSavingThrows(randNpc.determineSavingThrows(changedNPC.getGenClass(), editedNPC.getAbilityModifier(),editedNPC.getProfBonus()));
    }
    private void setJobChanges(NpcClass changedNPC, NpcClass oldNPC, NpcClass editedNPC){
        List<String> oldJobSkills = randGen.bigStringToDividedString(dbms.getRowWithPref("job", oldNPC.getJob()).get(2));
        List<String> newJobSkills = randGen.bigStringToDividedString(dbms.getRowWithPref("job", changedNPC.getJob()).get(2));
        List<String> currentSkills = changedNPC.getSkillList();
        currentSkills.removeAll(oldJobSkills);
        currentSkills.addAll(newJobSkills);
        editedNPC.setSkillList(currentSkills);
        editedNPC.setJob(changedNPC.getJob());
    }

    private void setNewArmorClass(NpcClass changedNPC, NpcClass editedNPC) {
        boolean haseHeAlreadyAShield = false;
        String armor;
        String changedArmor = changedNPC.getArmor();
        if (changedArmor.contains("-")) {
            armor = randGen.randArmor(randGen.figureOutArmorProfList(editedNPC.getGenClass(), editedNPC.getRace()));
            editedNPC.setArmor(armor);
        } else {
            armor = changedNPC.getArmor();
        }
        if(changedArmor.contains(("Shield"))){
            haseHeAlreadyAShield = true;
        }
        if (armor == null) {
            armor = "None";
        }
        randNpc.calculateArmorClass(armor,changedNPC,haseHeAlreadyAShield);
        }

    }

