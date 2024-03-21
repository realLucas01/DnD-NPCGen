package DBTest.TSWP1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class DBMS implements CommandLineRunner{
	
	@Autowired
	NameRepository nameRepository;
	@Autowired
	ArmorRepository armorRepository;

	public static void main(String[] args) {
		String entscheidung;
		Scanner Event = new Scanner(System.in);
		
		entscheidung = Event.next();
		if (entscheidung.equals("NEIN")) {
			System.out.println("Ok dann nicht");
		}else {
			SpringApplication.run(DBMS.class, args);
		}
	}
	
	@Override
	@Transactional
	public void run(String... args) {
		
		TestInitiallizeNameDB();
		TestInitiallizeArmorDB();
		
		NameModel q2;	
		Scanner Event2 = new Scanner(System.in);
		
		System.out.println("Welche Operation soll ausgeführt werden?(rand/col/querry/save/update)");
		String entscheidung2 = Event2.next();
		if (entscheidung2.equals("rand")) {
			Integer i=0;
			
			while (i<5) {
			q2 = randomQuestion();
			System.out.println(q2);
			i++;
			}
		}else if (entscheidung2.equals("col")) {
			onlyColumn();
		}
		else if (entscheidung2.equals("querry")){
			System.out.println("Für welche Datenbank soll gesucht werden?(NAME/WAFFE)");
			String entscheidung3 = Event2.next();
			carryOutSearch(entscheidung3);
		}
		else if (entscheidung2.equals("save")) {
			System.out.println("Wie ist der Vorname des Charakters?");
			String vorname = Event2.next();
			System.out.println("Wie ist der Nachname des Charakters?");
			String nachname = Event2.next();
			System.out.println("Welcher Rasse gehört der Charakter an?");
			String occurance = Event2.next();
			nameRepository.save(new NameModel(vorname,nachname,occurance));
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
			nameRepository.setNameInfoById(vorname,nachname,occurance,ID);
		}
	}

	
		
	public void TestInitiallizeNameDB() {
		
		if (nameRepository.findAll().equals(null)) {
		NameModel NameInformation1 = new NameModel("Adrik","Balderk","Dwarf");
		NameModel NameInformation2 = new NameModel("Faen","Carric","Elf");
		NameModel NameInformation3 = new NameModel("Cora","Leagallow","Halfling");
		NameModel NameInformation4 = new NameModel("Daar","Kerrhylon","Dragonborn");
			
		nameRepository.save(NameInformation1);
		nameRepository.save(NameInformation2);
		nameRepository.save(NameInformation3);
		nameRepository.save(NameInformation4);
		}
	}
	
	public void TestInitiallizeArmorDB() {
		
		if (nameRepository.findAll().equals(null)) {
		ArmorModel ArmorInformation1 = new ArmorModel("Padded","Light",11,"?");
		ArmorModel ArmorInformation2 = new ArmorModel("Hide","Medium",12,"?");
		ArmorModel ArmorInformation3 = new ArmorModel("Half Plate","Medium",15,"?");
		ArmorModel ArmorInformation4 = new ArmorModel("Plate","Heavy",18,"?");
		
		armorRepository.save(ArmorInformation1);
		armorRepository.save(ArmorInformation2);
		armorRepository.save(ArmorInformation3);
		armorRepository.save(ArmorInformation4);
		}
	}




	public NameModel randomQuestion() {
	    Long qty = nameRepository.count();
	    int idx = (int)(Math.random() * qty);
	    Page<NameModel> questionPage = nameRepository.findAll(PageRequest.of(idx, 1));
	    NameModel q = null;
	    if (questionPage.hasContent()) {
	        q = questionPage.getContent().get(0);
	    }
	    return q;
	}
	
	public List<ColOnly> onlyColumn() {
		String onlycol = nameRepository.getallFirstNames().toString();
		System.out.println(onlycol.toString());
		return null;
	}
	
	public ArrayList<String> carryOutSearch(String DBNAME) {
		ArrayList <String> Dataset = null;
		
		switch (DBNAME) {
			case "NAME":
				//Dataset = (ArrayList<String>) Arrays.asList(nameRepository.findById(3).toString());
				nameRepository.findById(3).ifPresent(val -> System.out.println(val));
				break;
			case "WAFFE":
				Dataset = (ArrayList<String>) Arrays.asList(armorRepository.findById(3).toString());
				nameRepository.findById(3).ifPresent(val -> System.out.println(val));
				break;
		}
	return Dataset;
	}
			
		/* nameRepository.findByNameFirstName("Adrik").forEach(
				val -> System.out.println(val)
				);
		nameRepository.findByNameLastName("Carric").forEach(
				val -> System.out.println(val)
				);
		nameRepository.findByNameOccurance("Dragonborn").forEach(
				val -> System.out.println(val)
				); 
		nameRepository.findById(3).ifPresent(
				val -> System.out.println(val)
				);	*/
		//nameRepository.getallFirstNames().forEach(val -> System.out.println(val));
}