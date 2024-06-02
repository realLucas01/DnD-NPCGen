package DnDGen.TSWP1;

import DnDGen.DB.DBMS;
import org.springframework.context.ApplicationContext;

import java.util.ArrayList;

public class UiHistoryEditUpdate {
    /* @author Lucas
     *
     * Aufrufen der GENAUEN DBMS bzw. SpringApplication die gestartet wurde, jede
     * neu-konstruktion von DBMS wäre sonst leer.
     */
    ApplicationContext appCtx = DBMS.getApplicationContext();
    DBMS dbms = appCtx.getBean(DBMS.class);

    /*
    changes = {race, class}
     */
    public ArrayList<Object> updateHistoryEdit (ArrayList<String> changes, Integer ID ){
        ArrayList<Object> validAndInvalidList = new ArrayList<>();
        ArrayList<Object> currentNPC = dbms.getHistory(); // Lucas ToDo



        if((changes.getFirst() == null) || (changes.getLast() != null )){
            // classe geändert
            // Änderungen: HP, profWeapon, profArmor, SavingThrows, Skills, AbilityScoreDistripution, Spells, features


        } else if ((changes.getFirst() != null) || (changes.getLast() == null )) {
            //race geändert
            // Änderungen: RaceAbilityScoreAddierung, Features, Skills, Language, Speed, Description:(Age, height, size, creature Type)
            
        } else if ((changes.getFirst() != null) || (changes.getLast() != null )) {
            //beides hat sich geändert

        }else {
            //nichts ändert sich
            return null;
        }
        //geänderten wird dem UI zurückgegeben
        return null;
    }








}
