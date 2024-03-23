package DBTest.TSWP1;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

import org.aspectj.weaver.patterns.TypePatternQuestions.Question;
import org.hibernate.sql.results.graph.entity.EntityFetch;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import DBTest.Allesandere.Test2;

@SpringBootApplication
public class DBMS implements CommandLineRunner{
	
	/*
	private Test2 test;
	
	public DBMS(Test2 test2){
		this.test=test2;
	} */
	
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
		tester();
		}
	
	public void tester() {
		Scanner Event2 = new Scanner(System.in);
		System.out.println("Für welche Datenbank soll eine Operation ausgeführt werden?(name/armor)");
		String dbname = Event2.next();
		System.out.println("Welche Operation soll ausgeführt werden?(row/randrow/rowpref/multrowpref/col/save/update)");
		String entscheidung2 = Event2.next();
		if (entscheidung2.equals("row")) {
			System.out.println(getRow(dbname));
		}
		else if (entscheidung2.equals("randrow")){
			System.out.println(randomRow(dbname));
		}
		else if (entscheidung2.equals("rowpref")){
			System.out.println(getRow(dbname));
		}
		else if (entscheidung2.equals("multrowpref")){
			System.out.println(getRow(dbname));
		}
		else if (entscheidung2.equals("col")) {
			System.out.println(onlyColumn());
		}

		else if (entscheidung2.equals("save")) {
			System.out.println("Wie ist der Vorname des Charakters?");
			String vorname = Event2.next();
			System.out.println("Wie ist der Nachname des Charakters?");
			String nachname = Event2.next();
			System.out.println("Welcher Rasse gehört der Charakter an?");
			String occurance = Event2.next();
			saveEntry(vorname, nachname, occurance);
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
			updateEntry(vorname, nachname, occurance, ID);
		}
	}
		
	public void TestInitiallizeNameDB() {
		
		Long Zahl = nameRepository.count();
		if (Zahl.toString().equals("0")) {
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
		
		Long Zahl = armorRepository.count();
		if (Zahl.toString().equals("0")) {
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

	 public ArrayList<String> getRow(String DBNAME) {	
		 ArrayList<?> Dataset;
		 ArrayList<String> ConvertetDataset = new ArrayList<String>();
		 
			switch(DBNAME) {
			case "name":
				Dataset = nameRepository.findByNameId(3);

				nameRepository.findById(3).ifPresent(val -> System.out.println(val));
				NameModel Entryname = (NameModel) Dataset.get(0);
				System.out.println("Datensatz als ArrayList:" + Entryname.getNameFirstName());
				ConvertetDataset.add(0, Entryname.getNameId().toString());
				ConvertetDataset.add(1, Entryname.getNameFirstName());
				ConvertetDataset.add(2, Entryname.getNameLastName());
				ConvertetDataset.add(3, Entryname.getNameOccurance());
				System.out.println(ConvertetDataset.get(2));
				break;
			case "armor":
				Dataset = armorRepository.findByArmorId(3);

				armorRepository.findById(3).ifPresent(val -> System.out.println(val));
				ArmorModel Entryarmor = (ArmorModel) Dataset.get(0);
				System.out.println("Datensatz als ArrayList:" + Entryarmor.getArmorName());
				ConvertetDataset.add(0, Entryarmor.getArmorId().toString());
				ConvertetDataset.add(1, Entryarmor.getArmorName());
				ConvertetDataset.add(2, Entryarmor.getArmorType());
				ConvertetDataset.add(3, Entryarmor.getArmorAC().toString());
				ConvertetDataset.add(4, Entryarmor.getArmorRmax());
				System.out.println(ConvertetDataset.get(2));
				break;
			default:
				Dataset = nameRepository.findByNameId(3);
				ConvertetDataset = null;
			}
			

			return ConvertetDataset;
		 
		 //Funktioniert irgendwie nicht ganz aufgrund der bennenung der Querrys in den Interfaces die ich nicht "automatisieren" kann
		/* 	CrudRepository<?, Integer> Repo = chooseDB(DBNAME);
			ArrayList<?> Dataset = Repo.findByID(3);

			Repo.findById(3).ifPresent(val -> System.out.println(val));
			NameModel firstEntry = Dataset.get(0);
			System.out.println("Datensatz als ArrayList:" + firstEntry.getNameFirstName()); */
	}


	public ArrayList<?> randomRow(String dbname) {
	    Long qty;
	    Page<?> questionPage;
	    int idx;
	    ArrayList<String> randRow = new ArrayList<String>();
	    
	    switch(dbname) {
		case "name":
			qty = nameRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = nameRepository.findAll(PageRequest.of(idx, 1));
		    Question q = null;
		    if (questionPage.hasContent()) {
		    	NameModel randname = (NameModel) questionPage.getContent().get(0);
		    	randRow.add(0, randname.getNameId().toString());
		    	randRow.add(1, randname.getNameFirstName());
		    	randRow.add(2, randname.getNameLastName());
		    	randRow.add(3, randname.getNameOccurance());
		    }
			break;
		case "armor":
			qty = armorRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = armorRepository.findAll(PageRequest.of(idx, 1));
		    if (questionPage.hasContent()) {
		    	ArmorModel randname = (ArmorModel) questionPage.getContent().get(0);
		    	randRow.add(0, randname.getArmorId().toString());
		    	randRow.add(1, randname.getArmorName());
		    	randRow.add(2, randname.getArmorType());
		    	randRow.add(3, randname.getArmorAC().toString());
		    	randRow.add(4, randname.getArmorRmax());
		    }
			break;
		default:

		}
	    
	    return randRow;
	}
	
	public void rowPref(String dbname, String pref){
	    
		switch(dbname) {
		case "name":

			break;
		case "armor":

			break;
		default:
		
		}
	}
	
	public List<ColOnly> onlyColumn() {
		String onlycol = nameRepository.getallFirstNames().toString();
		System.out.println(onlycol.toString());
		return null;
	}
	
	//Funktioniert halbwegs allerdings ist das Problem bei den weiteren "automatisierungen"
	public CrudRepository<?, Integer> chooseDB(String DB) {
		CrudRepository<?, Integer> Repo;
		
		switch(DB) {
		case "name":
			Repo = nameRepository;
			break;
		case "armor":
			Repo = armorRepository;
			break;
		default: 
			Repo = nameRepository;
		}
		
		return Repo;
	}
	 
	 public void saveEntry(String vorname, String nachname, String occurance) {
			nameRepository.save(new NameModel(vorname,nachname,occurance));
	 }
	 
	 public void updateEntry(String vorname,String nachname,String occurance,Integer ID) {
		 	nameRepository.setNameInfoById(vorname,nachname,occurance,ID);
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
}