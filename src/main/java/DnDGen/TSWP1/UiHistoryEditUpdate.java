package DnDGen.TSWP1;

import DnDGen.DB.DBMS;
import org.springframework.context.ApplicationContext;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
/*
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
public class UiHistoryEditUpdate {
    /* @author Lucas
     *
     * Aufrufen der GENAUEN DBMS bzw. SpringApplication die gestartet wurde, jede
     * neu-konstruktion von DBMS wäre sonst leer.
     */
    ApplicationContext appCtx = DBMS.getApplicationContext();
    DBMS dbms = appCtx.getBean(DBMS.class);
    Random rndNumb = new SecureRandom();
    RandomGenerator randGen = new RandomGenerator();
    RandomNPC randNPC = new RandomNPC();
    EditNPC editNPC = new EditNPC();

    /*
    changes = {race, class, alignment}
     */
    public List<Object> updateHistoryEdit (List<String> changes, Integer iD ){
        List<Object> validAndInvalidList = new ArrayList<>();
        List<Object> currentNPC = dbms.getHistoryEntry(iD);
        String newRace = changes.get(0);
        String newCharClass = changes.get(1);
        String newAlignment = changes.get(2);


        //Übergangs Variablen werden wahrscheinlich später entfernt oder geändert
        List<Integer> abilityScore = new ArrayList<>();
        String oldRace = null;



        if((changes.getFirst() == null) || (changes.getLast() != null )){
            // class geändert
            // Änderungen: HP, profWeapon, profArmor(+AC), SavingThrows(ersetzen mit den neuen, nicht durch striche), Skills(überprüfen welche bleiben können, welche nicht), AbilityScoreDistripution(bedenken race Score improvement), Spells(überprüfen welche bleiben können, welche nicht), features(class features entfernen)

            abilityScore = editNPC.removeRaceAbilityAddition(oldRace, abilityScore);
            abilityScore = randNPC.distributeAbilityScore(abilityScore,newCharClass);
            abilityScore = randNPC.addRaceModifierToAbilityScore(oldRace,abilityScore);

        } else if ((changes.getFirst() != null) || (changes.getLast() == null )) {
            //race geändert
            // Änderungen: RaceAbilityScoreAddierung, Features, Skills, Language, Speed, Description:(Age, height, size, creature Type), Senses

            abilityScore = editNPC.removeRaceAbilityAddition(oldRace, abilityScore);
            abilityScore = randNPC.addRaceModifierToAbilityScore(newRace, abilityScore);

        } else if ((changes.getFirst() != null) || (changes.getLast() != null )) {
            //alles hat sich geändert

        } else if ((newAlignment!=null)){
            //Deity

        }else {
            //nichts ändert sich
            return new ArrayList<>();
        }
        //geänderten wird dem UI zurückgegeben
        return validAndInvalidList;
    }

    /*
    Option um nicht die HP manuell ausrechnen zu müssen, kann die Option angeboten werden diese auch wieder zufällig generieren zu lassen
     */
    private int newRolledHP(int level, String genClass, List<Integer> abilityModifier){
        return randNPC.calculateHP(genClass, level, abilityModifier);
    }








}
