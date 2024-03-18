package DBTest.TSWP1;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class NameController implements CommandLineRunner{
	
	@Autowired
	NameRepository nameRepository;

	public static void main(String[] args) {
		SpringApplication.run(NameController.class, args);
	}
	
	@Override
	public void run(String... args) {
		
		NameModel storeInformationOne = new NameModel("Adrik","Balderk","Dwarf");
		NameModel storeInformationTwo = new NameModel("Faen","Carric","Elf");
		NameModel storeInformationThree = new NameModel("Cora","Leagallow","Halfling");
		NameModel storeInformationFour = new NameModel("Daar","Kerrhylon","Dragonborn");
		
		nameRepository.save(storeInformationOne);
		nameRepository.save(storeInformationTwo);
		nameRepository.save(storeInformationThree);
		nameRepository.save(storeInformationFour);
		
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
