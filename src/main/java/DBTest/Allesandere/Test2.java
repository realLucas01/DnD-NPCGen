package DBTest.Allesandere;

import java.util.Scanner;

import DBTest.TSWP1.DBMS;
import DBTest.TSWP1.NameModel;
import DBTest.TSWP1.NameRepository;


public class Test2 {
	private DBMS dbms;
	
	public Test2(DBMS dBMS){
		this.dbms = dBMS;
	}
	
		
		public void tester() {
			Scanner Event2 = new Scanner(System.in);
			System.out.println("Für welche Datenbank soll eine Operation ausgeführt werden?(name/armor)");
			String dbname = Event2.next();
			System.out.println("Welche Operation soll ausgeführt werden?(row/randrow/rowpref/multrowpref/col/save/update)");
			String entscheidung2 = Event2.next();
			if (entscheidung2.equals("row")) {
				System.out.println(dbms.getRow(dbname));
			}
			else if (entscheidung2.equals("randrow")){
				System.out.println(dbms.randomRow(dbname));
			}
			else if (entscheidung2.equals("rowpref")){
				System.out.println(dbms.getRow(dbname));
			}
			else if (entscheidung2.equals("multrowpref")){
				System.out.println(dbms.getRow(dbname));
			}
			else if (entscheidung2.equals("col")) {
				System.out.println(dbms.onlyColumn());
			}

			else if (entscheidung2.equals("save")) {
				System.out.println("Wie ist der Vorname des Charakters?");
				String vorname = Event2.next();
				System.out.println("Wie ist der Nachname des Charakters?");
				String nachname = Event2.next();
				System.out.println("Welcher Rasse gehört der Charakter an?");
				String occurance = Event2.next();
				dbms.saveEntry(vorname, nachname, occurance);
			}
			else if (entscheidung2.equals("update")) {
				System.out.println("Id des zu veränderten Charakter?");
				int ID = Event2.nextInt();
				System.out.println("Wie ist der neue Vorname des Charakters?");
				String vorname = Event2.next();
				System.out.println("Wie ist der neue Nachname des Charakters?");
				String nachname = Event2.next();
				System.out.println("Welcher neuen Rasse gehört der Charakter an?");
				String occurance = Event2.next();
				dbms.updateEntry(vorname, nachname, occurance, ID);
			}
		}
		
	
	
}
