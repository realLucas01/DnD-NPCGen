package dndgen;

import dndgen.database.DBMS;
import dndgen.tswp1.NpcClass;
import dndgen.tswp1.RandomNpc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

/**
 * Test Funktionen können nun hier implementiert und ausgeführt werden
 * Tests müssen derzeit einzeln gestartet werden, alle zusammen in Reihe ist derzeit nicht möglich
 */
@SpringBootTest
class BasicTest {


    @Autowired
    private RandomNpc randomNpc;
    ApplicationContext appCtx = SpringApplication.run(MainApplication.class);
    DBMS dbmsInstance = appCtx.getBean(DBMS.class);

    @Test
    void profTest(){
        int result = randomNpc.determineProfBonus(1);
        Assertions.assertEquals(2,result);
    }
    @Test
    void setAgeTest() {
        NpcClass tPC = new NpcClass();
        tPC.setAge(22);
        Assertions.assertEquals(22, tPC.getAge());
    }
    @Test
    void testCreateRandomNPC() {
        NpcClass nPC = randomNpc.createRandomNPC(null, null, null, null, null, null, null);
        Assertions.assertNotNull(nPC);
    }

    /*
    determineSavingThrows tests,default noch ungetestet
     */
    @Test
    void testDetermineSavingThrows(){
        List<Integer> abiliMod = new ArrayList<>();
        abiliMod.add(2);
        abiliMod.add(2);
        abiliMod.add(2);
        abiliMod.add(2);
        abiliMod.add(2);
        abiliMod.add(2);
        List<String> classesToTest = new ArrayList<>();
        classesToTest.add("Fighter");
        classesToTest.add("Artificer");
        classesToTest.add("Bard");
        classesToTest.add("Paladin");
        classesToTest.add("Commoner");

        int profBonus = 2;
        List<String> idealoutput = new ArrayList<>();
        idealoutput.add("Str +4");
        idealoutput.add("Con +4");
        idealoutput.add("Con +4");
        idealoutput.add("Int +4");
        idealoutput.add("Dex +4");
        idealoutput.add("Cha +4");
        idealoutput.add("Wis +4");
        idealoutput.add("Cha +4");
        idealoutput.add("none");
        List<String> realOutputSaves = new ArrayList<>();

        for(String s : classesToTest){
            realOutputSaves.addAll(randomNpc.determineSavingThrows(s,abiliMod,profBonus));
        }
        Assertions.assertEquals(idealoutput, realOutputSaves);
    }
    /*
        fehlerhafte zu testende Funktion
        Ergebnisse:
        Expected :[19, 12, 16, 10, 13, 9]
        Actual   :[10, 16, 12, 19, 9, 13]
     */
    @Test
    void testAbilityScoreDistribution(){
        String testClass1 = "Paladin";
        String testClass2 = "Commoner";
        List<Integer> abilityScoreList = new ArrayList<>();
        abilityScoreList.add(10);
        abilityScoreList.add(12);
        abilityScoreList.add(9);
        abilityScoreList.add(16);
        abilityScoreList.add(13);
        abilityScoreList.add(19);
        List<Integer> firstResults = new ArrayList<>();
        List<Integer> secondResults = new ArrayList<>();
        List<Integer> exceptedResultsFighter = new ArrayList<>(); //Str, Dex, Con, Wis, Int, Cha
        exceptedResultsFighter.add(19);
        exceptedResultsFighter.add(12);
        exceptedResultsFighter.add(9);
        exceptedResultsFighter.add(10);
        exceptedResultsFighter.add(13);
        exceptedResultsFighter.add(16);

        firstResults = randomNpc.distributeAbilityScore(abilityScoreList,testClass1); //Str, Con, Wis, Dex, Int, Cha
        secondResults = randomNpc.distributeAbilityScore(abilityScoreList,testClass1);
        Assertions.assertEquals(exceptedResultsFighter,firstResults);
    }

    @Test
    void testDetermineAbilityMod(){
        List<Integer> abilityScoreList = new ArrayList<>();
        abilityScoreList.add(10);
        abilityScoreList.add(12);
        abilityScoreList.add(9);
        abilityScoreList.add(16);
        abilityScoreList.add(19);
        abilityScoreList.add(13);
        List<Integer> expectedResults = new ArrayList<>();
        expectedResults.add(0);
        expectedResults.add(1);
        expectedResults.add(-1);
        expectedResults.add(3);
        expectedResults.add(4);
        expectedResults.add(1);
        List<Integer> resultFromFunction;
        resultFromFunction = randomNpc.determineAbilityModifier(abilityScoreList);
        Assertions.assertEquals(expectedResults,resultFromFunction);
    }

    @Test
    void testProfBonus(){
        Integer value = randomNpc.determineProfBonus(2);
        Assertions.assertEquals(2,value);
    }
    @Test
    void testincreaseSecOrThird(){
        List<Integer> abilityScore = new ArrayList<>();
        List<Integer> returnAS;
        abilityScore.add(8);
        abilityScore.add(10);
        abilityScore.add(12);
        abilityScore.add(4);
        abilityScore.add(13);
        abilityScore.add(15);
        returnAS = randomNpc.increaseSecOrThirdAS(abilityScore);
        System.out.println(returnAS);
    }


}
