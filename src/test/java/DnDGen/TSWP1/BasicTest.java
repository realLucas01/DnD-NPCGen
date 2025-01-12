package DnDGen.TSWP1;

import DnDGen.DB.DBMS;
import DnDGen.UI.UI;
import DnDGen.UI.UIStarter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

@SpringBootTest
class BasicTest {
    //TODO Funkionen mit Datenbankaufruf funktionieren derzeit noch nicht

    RandomNpc randomNpc = new RandomNpc();
    //ApplicationContext appCtx = DBMS.getApplicationContext();
    /**
     * The Dbms.
     */
    //DBMS dbms = appCtx.getBean(DBMS.class);



    /*
    @Test
    void setAgeTest() {
        NpcClass tPC = new NpcClass();
        tPC.setAge(22);
        Assertions.assertEquals(22, tPC.getAge());
    }

    @Test
    void profTest(){
        int result = randomNpc.determineProfBonus(1);
        Assertions.assertEquals(2,result);
    }
    */


    @Test
    @DisplayName("test um Test zu testen")
    void testTestFunktionUmTestsZuTesten(){

        int test = randomNpc.test();
        Assertions.assertEquals(1,test);
    }

}
