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
import org.springframework.boot.ExitCodeGenerator;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;

import DBTest.Allesandere.Test2;

@SpringBootApplication
public class DBMS implements CommandLineRunner{
	
	private static ConfigurableApplicationContext ctx;
	
	/*
	private Test2 test;
	
	public DBMS(Test2 test2){
		this.test=test2;
	} */
	
	@Autowired
	NameRepository nameRepository;
	@Autowired
	ArmorRepository armorRepository;
	@Autowired
	HistoryRepository historyRepository;

	public static void main(String[] args) {
		String entscheidung;
		Scanner Event = new Scanner(System.in);
		
		System.out.println("Spring starten?(ja/nein)");
		entscheidung = Event.next();
		if (entscheidung.equals("nein")) {
			System.out.println("Ok dann nicht");
		}else if (entscheidung.equals("ja")){
	        ctx = dbmsStarter(args);
	    }
	}
	
	@Override
	@Transactional
	public void run(String... args) {
		Scanner Event = new Scanner(System.in);

			TestInitiallizeNameDB();
			TestInitiallizeArmorDB();
			TestInitiallizeHistoryDB();
			tester();
		System.out.println("Spring schließen?(ja/nein)");
		String entsch = Event.next();
		System.out.println(ctx);
		if (entsch.equals("ja")) {
			dbmsClose(args, ctx);
		}else if (entsch.equals("nein")){
			tester();
		}
		
		/*System.out.println("Spring schließen?(ja/nein)");
		Scanner event = new Scanner(System.in);
		String entsch = event.next();
		if (entsch.equals("ja")) {
			SpringApplication.exit(DBMS.class, args);
		}else if (entsch.equals("nein")){
			tester();
		}*/
		}
	
	public static ConfigurableApplicationContext dbmsStarter(String[] args) {
		ctx = SpringApplication.run(DBMS.class, args);
        System.out.println(ctx);
		return ctx;
	}
	public static void dbmsClose(String[] args,ConfigurableApplicationContext ctx) {
		ctx.close();
	}
	
	public void tester() {
		Scanner Event2 = new Scanner(System.in);
		System.out.println("Für welche Datenbank soll eine Operation ausgeführt werden?(name/armor)");
		String dbname = Event2.next();
		System.out.println("Welche Operation soll ausgeführt werden?(row/randrow/rowpref/rowmultpref/multrowpref/col/save/update)");
		String entscheidung2 = Event2.next();
		if (entscheidung2.equals("row")) {
			System.out.println(getRow(dbname));
		}
		else if (entscheidung2.equals("randrow")){
			System.out.println(randomRow(dbname));
		}
		else if (entscheidung2.equals("rowpref")){
			System.out.println(rowPref(dbname,"Medium"));
		}
		else if (entscheidung2.equals("rowmultpref")){
			System.out.println(rowMultPref(dbname,"Medium",12));
		}
		else if (entscheidung2.equals("multrowpref")){
			System.out.println(multRowPref(dbname,"Medium"));
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
			//System.out.println("Id des zu veränderten Charakter?");
			//int ID = Event2.nextInt();
			System.out.println("Wie ist der neue Vorname des Charakters?");
			String vorname = Event2.next();
			System.out.println("Wie ist der neue Nachname des Charakters?");
			String nachname = Event2.next();
			System.out.println("Welcher neuen Rasse gehört der Charakter an?");
			String occurance = Event2.next();
			System.out.println("Welcher Eintrag soll geändert werden?(ID angeben)");
			Integer id = Event2.nextInt();
			updateEntry(vorname, nachname, occurance, id);
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
	
	public void TestInitiallizeHistoryDB() {
		
		Long Zahl = historyRepository.count();
		if (Zahl.toString().equals("0")) {
		HistoryModel hist = new HistoryModel(1,"Zaeler","Zaeler","Zaeler");	
		hist.setHistoryId(11);
		historyRepository.save(hist);
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
				ConvertetDataset.add(3, Entryarmor.getArmorAc().toString());
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


	public ArrayList<String> randomRow(String dbname) {
	    Long qty;
	    Page<?> questionPage;
	    int idx;
	    ArrayList<String> randRow = new ArrayList<String>();
	    
	    switch(dbname) {
		case "name":
			qty = nameRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = nameRepository.findAll(PageRequest.of(idx, 1));
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
		    System.out.println(questionPage.getSize());
		    if (questionPage.hasContent()) {
		    	ArmorModel randname = (ArmorModel) questionPage.getContent().get(0);
		    	randRow.add(0, randname.getArmorId().toString());
		    	randRow.add(1, randname.getArmorName());
		    	randRow.add(2, randname.getArmorType());
		    	randRow.add(3, randname.getArmorAc().toString());
		    	randRow.add(4, randname.getArmorRmax());
		    }
			break;
		default:

		}
	    
	    return randRow;
	}
	
	public ArrayList<String> rowPref(String dbname, String pref){
	    int qty;
	    Page<?> questionPage2;
	    int idx;
	    ArrayList<String> randRow = new ArrayList<String>();
	    
		switch(dbname) {
		case "name":
			qty = nameRepository.findByNameOccurance(pref).size();
			idx = (int)(Math.random() * qty);
		    questionPage2 = nameRepository.findAllByNameOccurance(pref,PageRequest.of(idx,1));
		    if (questionPage2.hasContent()) {
		    	NameModel randname = (NameModel) questionPage2.getContent().get(0);
		    	randRow.add(0, randname.getNameId().toString());
		    	randRow.add(1, randname.getNameFirstName());
		    	randRow.add(2, randname.getNameLastName());
		    	randRow.add(3, randname.getNameOccurance());
		    }
			break;
		case "armor":
			qty = armorRepository.findByArmorType(pref).size();
			//System.out.println(qty);
			idx = (int)(Math.random() * qty);
			//System.out.println(idx);
			//System.out.println(pref);
		    questionPage2 = armorRepository.findAllByArmorType(pref,PageRequest.of(idx,1));
		    //System.out.println(questionPage2.getSize());
		    if (questionPage2.hasContent()) {
		    	ArmorModel randname = (ArmorModel) questionPage2.getContent().get(0);
		    	randRow.add(0, randname.getArmorId().toString());
		    	randRow.add(1, randname.getArmorName());
		    	randRow.add(2, randname.getArmorType());
		    	randRow.add(3, randname.getArmorAc().toString());
		    	randRow.add(4, randname.getArmorRmax());
		    }
			break;
		default:
		
		}
		return randRow;
	}
	
	public ArrayList<String> rowMultPref(String dbname, String pref,Integer AC){
	    int qty;
	    Page<?> questionPage2;
	    int idx;
	    ArrayList<String> randRow = new ArrayList<String>();
	    
		switch(dbname) {
		case "name":
			break;
		case "armor":
			qty = armorRepository.findByMultPref(pref, AC).size();
			//System.out.println(qty);
			//System.out.println(armorRepository.findByMultPref(pref, AC).toString());
			idx = (int)(Math.random() * qty);
			//System.out.println(idx);
		    questionPage2 = armorRepository.findAllByArmorTypeAndArmorAc(pref,AC,PageRequest.of(idx,1));
		    //System.out.println(questionPage2.getSize());
		    if (questionPage2.hasContent()) {
		    	ArmorModel randname = (ArmorModel) questionPage2.getContent().get(0);
		    	randRow.add(0, randname.getArmorId().toString());
		    	randRow.add(1, randname.getArmorName());
		    	randRow.add(2, randname.getArmorType());
		    	randRow.add(3, randname.getArmorAc().toString());
		    	randRow.add(4, randname.getArmorRmax());
		    }
			break;
		default:
		
		}
		return randRow;
	}
	
	public ArrayList<?> multRowPref(String dbname, String pref){
		ArrayList<String> rand1 = new ArrayList<String>();
		ArrayList<String> rand2 = new ArrayList<String>();
		ArrayList<String> randresult = new ArrayList<String>();
		boolean unterschied;
		
		switch(dbname) {
		case "name":
			break;
		case "armor":
			unterschied = false;
		    rand1.addAll(rowPref(dbname, pref));
		    //System.out.println(rand1);
		    rand2.addAll(rowPref(dbname, pref));
			while (rand1.equals(rand2)) {
			    rand2.clear();
				rand2.addAll(rowPref(dbname, pref));
		    }
			//System.out.println(rand2);
			randresult.addAll(rand1);
			randresult.addAll(rand2);
			break;
		default:
		
		}
		return randresult;
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
		 HistoryModel newentry = new HistoryModel();
		 newentry.setHistoryFirstName(vorname);
		 newentry.setHistoryLastName(nachname);
		 newentry.setHistoryOccurance(occurance);
		 manageHistory("save",newentry);
		 return ;
	 }
	 
	 public void updateEntry(String vorname,String nachname,String occurance, Integer HistID) {
		 HistoryModel updatedentry = new HistoryModel();
		 updatedentry.setHistoryFirstName(vorname);
		 updatedentry.setHistoryLastName(nachname);
		 updatedentry.setHistoryOccurance(occurance);
		 updatedentry.setHistoryId(HistID);
		 manageHistory("update",updatedentry);
		 return ;
	 }
	 
	 @Transactional
	 public void manageHistory(String typeOfChange, HistoryModel histchange) {
		 HistoryModel histentry = histchange;
		 
		 switch(typeOfChange) {
		 case "save":
			 //System.out.println(historyRepository.findById(11).get().getrunVar());
			 if (historyRepository.count() == 0) {
				 historyRepository.findById(11).get().setrunVar(1);
			 }else if (historyRepository.findById(11).get().getrunVar() == 11) {
				 historyRepository.findById(11).get().setrunVar(1);
			 }
			 histentry.setHistoryId(historyRepository.findById(11).get().getrunVar());
			 //System.out.println(historyRepository.findById(11).get().getrunVar());
			 historyRepository.save(histentry);
			 historyRepository.findById(11).get().setrunVar(historyRepository.findById(11).get().getrunVar()+1);
		 case "update":
			 historyRepository.setNameInfoById(histentry.getHistoryFirstName(),histentry.getHistoryLastName(),histentry.getHistoryOccurance(),histentry.getHistoryId());
		 }
		 //switch (typeOfChange) {
		 //case "create":
		/*	 break;
		 case "update":
			 histentry.setrunVar(IDVAR);
			 historyRepository.save(histentry);
			 IDVAR++;
			 break;
		 }*/
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