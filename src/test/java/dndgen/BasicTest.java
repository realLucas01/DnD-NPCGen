package dndgen;

import dndgen.database.DBMS;
import dndgen.tswp1.NpcClass;
import dndgen.tswp1.RandomNpc;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;

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
    @DisplayName("test um Test zu testen")
    void testTestFunktionUmTestsZuTesten() {
        int test = randomNpc.test();
        Assertions.assertEquals(1, test);
    }
}
