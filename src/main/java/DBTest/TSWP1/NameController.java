package DBTest.TSWP1;

import java.util.Scanner;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

@SpringBootApplication
public class NameController implements CommandLineRunner{
	
	@Autowired
	NameRepository nameRepository;

	public static void main(String[] args) {
		String entscheidung;
		Scanner Event = new Scanner(System.in);
		
		entscheidung = Event.next();
		if (entscheidung.equals("NEIN")) {
			System.out.println("Ok dann nicht");
		}else {
			SpringApplication.run(NameController.class, args);
		}

		
		

	}
	
	@Override
	public void run(String... args) {
		String entscheidung2;
		NameModel q2;
		
		NameModel storeInformationOne = new NameModel("Adrik","Balderk","Dwarf");
		NameModel storeInformationTwo = new NameModel("Faen","Carric","Elf");
		NameModel storeInformationThree = new NameModel("Cora","Leagallow","Halfling");
		NameModel storeInformationFour = new NameModel("Daar","Kerrhylon","Dragonborn");
		
		nameRepository.save(storeInformationOne);
		nameRepository.save(storeInformationTwo);
		nameRepository.save(storeInformationThree);
		nameRepository.save(storeInformationFour);
		
		Scanner Event2 = new Scanner(System.in);
		
		entscheidung2 = Event2.next();
		
		if (entscheidung2.equals("rand")) {
			Integer i=0;
			
			while (i<5) {
			q2 = randomQuestion();
			System.out.println(q2);
			i++;
			}
		}else {
		
		nameRepository.findByNameFirstName("Adrik").forEach(
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
				);
		nameRepository.getallFirstNames().forEach(val -> System.out.println(val));
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
	
}
