/* @author Lucas
 * 
 * Wichtig: ALLE DATENBANKINTERAKTIONEN, egal welchen Grades, werden AUSSCHLIEßLICH
 * ÜBER DIESE KLASSE AUSGEFÜHRT!
 * 
 * Das ist die Haupt- und Verwaltungsklasse der gesamten Datenbankkomponente.
 * Sie verwaltet und kuemmert sich um alle Datenabrufe und Bewgungen und achtet
 * darauf das die für die Funktion essentiellen Grundvoraussetzungen wie Daten-
 * sowie auch Datenbankintegrität erhalten bleiben.
 * 
 * 
 *
 */

package DnDGen.DB;


import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

@SpringBootApplication
public class DBMS {
	private static ConfigurableApplicationContext ctx;
	
	/* @author Lucas
	 *
	 * Verbindet alle Datenbanken mit dem DBMS
	 */
	@Autowired
	AbiliScRepository abiliScRepository;
	@Autowired
	AlignmentRepository alignmentRepository;
	@Autowired
	ArmorRepository armorRepository;
	@Autowired
	ClassRepository classRepository;
	@Autowired
	DeityRepository deityRepository;
	@Autowired
	DescriptionRepository descriptionRepository;
	@Autowired
	DracAncesRepository dracAncesRepository;
	@Autowired
	FeatureRepository featureRepository;
	@Autowired
	HistoryRepository historyRepository;
	@Autowired
	JobRepository jobRepository;
	@Autowired
	LanguageRepository languageRepository;
	@Autowired
	LevelRepository levelRepository;
	@Autowired
	NameRepository nameRepository;
	@Autowired
	RaceRepository raceRepository;
	@Autowired
	SkillRepository skillRepository;
	@Autowired
	SpellRepository spellRepository;
	@Autowired
	WeaponRepository weaponRepository; 
	
	/* @author Lucas
	 * 
	 * Main des DBMS, startet die Springkomponente und damit auch die
	 * verbindung zur MySQL DB.
	 */
	public static void main(String[] args) {
		ctx = SpringApplication.run(DBMS.class, args);
	}
	
	/* @author Lucas
	 * 
	 * Macht aktuell das gleiche wie die Main, ist aber für spätere
	 * verwendungszwecke hier.
	 */
	public static void dbmsStarter(String[] args) {
		ctx = SpringApplication.run(DBMS.class, args);
        //System.out.println(ctx);
		return;
	}
	
	/* @author Lucas
	 *
	 * Fährt das DBMS herunter und schließt damit auch die Verbidung
	 * zur MySQL DB.
	 */
	public static void dbmsClose(String[] args,ConfigurableApplicationContext ctx) {
		ctx.close();
	}
	
	/* @author Lucas
	 *
	 * Gibt lediglich die Namensspalte einer DB wieder um die Vorauswahl 
	 * der GUI füllen zu können.
	 * 
	 * @param dbname: der Name der DB von der Daten gewünscht sind, als 
	 * 					kleingeschriebener String.
	 * @return Eine ArrayList gefüllt mit Strings, jeder String ein eigener Index.
	 */
	public ArrayList<String> onlyColumn(String dbname) {
		ArrayList<String> onlycol = new ArrayList<String>();
		
		switch(dbname) {
		case "race":
			onlycol = raceRepository.getallRaceCol();
		case "class":
			onlycol = classRepository.getallClassCol();
		case "job":
			onlycol = jobRepository.getallJobCol();
		default:
			onlycol = null;
		}
		return onlycol;
	}
	
	/* @author Lucas
	 *
	 * Gibt eine spezifische Zeile einer ausgewählten DB aus.
	 * 
	 * @param dbname: der Name der DB von der Daten gewünscht sind, als 
	 * 					kleingeschriebener String.
	 * @param searchstring: String nach dem eine Zeile ausgewählt werden soll, ist
	 * 					ein vorbestimmtes Atrribut.(Race,Job,Class,Features: Name)
	 * @return ArrayList gefüllt mit den Attributen der Zeile in form von Strings, 
	 * 				jedes Attribut ein eigener Index, beginend mit der ID auf idx 0
	 */
	public ArrayList<String> getRow(String DBNAME,String searchstring) {	
		 ArrayList<String> CD = new ArrayList<String>();
		 
			switch(DBNAME) {
			case "race":
				RaceModel Entryrace = raceRepository.findByRaceName(searchstring);
				CD.add(0, Entryrace.getRaceId().toString());
				CD.add(1, Entryrace.getRaceName());
				CD.add(2, Entryrace.getRaceAbilityScore());
				CD.add(3, Entryrace.getRaceFeatures());
				CD.add(4, Entryrace.getRaceAgeAdult().toString());
				CD.add(5, Entryrace.getRaceAgeMax().toString());
				CD.add(6, Entryrace.getRaceSkills());
				CD.add(7, Entryrace.getRaceHeight());
				CD.add(8, Entryrace.getRaceSize());
				CD.add(9, Entryrace.getRaceSpeed());
				CD.add(10, Entryrace.getRaceLanguages());
				CD.add(11, Entryrace.getRaceCreatureType());
				break;
			case "job":
				JobModel Entryjob = jobRepository.findByJobName(searchstring);
				CD.add(0, Entryjob.getJobId().toString());
				CD.add(1, Entryjob.getJobName());
				CD.add(2, Entryjob.getJobSkills());
				CD.add(3, Entryjob.getJobLanguage());
				break;
			case "class":
				ClassModel Entryclass = classRepository.findByClassName(searchstring);
				CD.add(0, Entryclass.getClassId().toString());
				CD.add(1, Entryclass.getClassName());
				CD.add(2, Entryclass.getClassHitDice());
				CD.add(3, Entryclass.getClassProfWeapon());
				CD.add(4, Entryclass.getClassProfArmor());
				CD.add(5, Entryclass.getClassSavingThrows());
				CD.add(6, Entryclass.getClassSkills());
				CD.add(7, Entryclass.getClassSkillNumber().toString());
				CD.add(8, Entryclass.getClassAbilityScoreDistribution());
				CD.add(9, Entryclass.getClassCantrips());
				CD.add(10, Entryclass.getClassSpells());
				CD.add(11, Entryclass.getClassSpellAbilityScore());
				break;
			case "features":
				FeatureModel Entryfeature = featureRepository.findByFeatureName(searchstring);
				CD.add(0, Entryfeature.getFeatureId().toString());
				CD.add(1, Entryfeature.getFeatureName());
				CD.add(2, Entryfeature.getFeatureDescription());
				CD.add(3, Entryfeature.getFeatureKindOf());
				break;
			default:
				CD = null;
			}
			return CD;
	}
	
	/* @author Lucas
	 *
	 * Gibt eine zufällige Zeile einer ausgewählten DB aus.
	 * 
	 * @param dbname: der Name der DB von der Daten gewünscht sind, als 
	 * 					kleingeschriebener String.
	 * @return ArrayList gefüllt mit den Attributen der Zeile in form von Strings, 
	 * 				jedes Attribut ein eigener Index, beginend mit der ID auf idx 0
	 */
	public ArrayList<String> getrandomRow(String dbname) {
	    Long qty;
	    Page<?> questionPage;
	    int idx;
	    ArrayList<String> randRow = new ArrayList<String>();
	    
	    switch(dbname) {
		case "class":
			qty = classRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = classRepository.findAll(PageRequest.of(idx, 1));
		    if (questionPage.hasContent()) {
		    	ClassModel randclass = (ClassModel) questionPage.getContent().get(0);
		    	randRow.add(0, randclass.getClassId().toString());
		    	randRow.add(1, randclass.getClassName());
		    	randRow.add(2, randclass.getClassHitDice());
		    	randRow.add(3, randclass.getClassProfWeapon());
		    	randRow.add(4, randclass.getClassProfArmor());
		    	randRow.add(5, randclass.getClassSavingThrows());
		    	randRow.add(6, randclass.getClassSkills());
		    	randRow.add(7, randclass.getClassSkillNumber().toString());
		    	randRow.add(8, randclass.getClassAbilityScoreDistribution());
		    	randRow.add(9, randclass.getClassCantrips());
		    	randRow.add(10, randclass.getClassSpells());
		    	randRow.add(11, randclass.getClassSpellAbilityScore());
		    }
			break;
		case "language":
			qty = languageRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = languageRepository.findAll(PageRequest.of(idx, 1));
		    System.out.println(questionPage.getSize());
		    if (questionPage.hasContent()) {
		    	LanguageModel randlanuguage = (LanguageModel) questionPage.getContent().get(0);
		    	randRow.add(0, randlanuguage.getLanguageId().toString());
		    	randRow.add(1, randlanuguage.getLanguageName());
		    }
			break;
		case "job":
			qty = jobRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = jobRepository.findAll(PageRequest.of(idx, 1));
		    if (questionPage.hasContent()) {
		    	JobModel randjob = (JobModel) questionPage.getContent().get(0);
		    	randRow.add(0, randjob.getJobId().toString());
		    	randRow.add(1, randjob.getJobName());
		    	randRow.add(2, randjob.getJobSkills());
		    	randRow.add(3, randjob.getJobLanguage());
		    }
			break;
		default:
			randRow = null;
		}
	    return randRow;
	}
	
	/* @author Lucas
	 *
	 * Gibt eine zufällige Zeile einer ausgewählten DB aus die vorher nach einer
	 * Präferenz sortiert wurde.
	 * 
	 * @param dbname: der Name der DB von der Daten gewünscht sind, als 
	 * 					kleingeschriebener String.
	 * @param pref: String nach dem die DB vor der Randomauswahl sortiert werden soll.
	 * 					(Armor,Weapon: Name; Deity: Alignment; Name: Race)
	 * @return ArrayList gefüllt mit den Attributen der Zeile in form von Strings, 
	 * 				jedes Attribut ein eigener Index, beginend mit der ID auf idx 0
	 */
	public ArrayList<String> getrowPref(String dbname, String pref){
	    int qty;
	    Page<?> questionPage2;
	    int idx;
	    ArrayList<String> randRowPref = new ArrayList<String>();
	    
		switch(dbname) {
		case "armor":
			qty = armorRepository.findByArmorName(pref).size();
			idx = (int)(Math.random() * qty);
		    questionPage2 = armorRepository.getArmorPref(pref,PageRequest.of(idx,1));
		    if (questionPage2.hasContent()) {
		    	ArmorModel randarmorpref = (ArmorModel) questionPage2.getContent().get(0);
		    	randRowPref.add(0, randarmorpref.getArmorId().toString());
		    	randRowPref.add(1, randarmorpref.getArmorName());
		    	randRowPref.add(2, randarmorpref.getArmorKind());
		    	randRowPref.add(3, randarmorpref.getArmorAc());
		    	randRowPref.add(4, randarmorpref.getArmorMax());
		    	randRowPref.add(5, randarmorpref.getArmorBonus());
		    	randRowPref.add(6, randarmorpref.getArmorStrength());
		    	randRowPref.add(7, randarmorpref.getArmorStealth().toString());
		    }
			break;
		case "weapon":
			qty = weaponRepository.findByWeaponName(pref).size();
			idx = (int)(Math.random() * qty);
		    questionPage2 = weaponRepository.getWeaponPref(pref,PageRequest.of(idx,1));
		    if (questionPage2.hasContent()) {
		    	WeaponModel randweaponpref = (WeaponModel) questionPage2.getContent().get(0);
		    	randRowPref.add(0, randweaponpref.getWeaponId().toString());
		    	randRowPref.add(1, randweaponpref.getWeaponName());
		    	randRowPref.add(2, randweaponpref.getWeaponProperty());
		    	randRowPref.add(3, randweaponpref.getWeaponDamageType());
		    	randRowPref.add(4, randweaponpref.getWeaponDamageDice());
		    	randRowPref.add(5, randweaponpref.getWeaponKind());
		    }
			break;
		case "deity":
			qty = deityRepository.findByDeityAlignment(pref).size();
			idx = (int)(Math.random() * qty);
		    questionPage2 = deityRepository.getDeityPref(pref,PageRequest.of(idx,1));
		    if (questionPage2.hasContent()) {
		    	DeityModel randdeitypref = (DeityModel) questionPage2.getContent().get(0);
		    	randRowPref.add(0, randdeitypref.getDeityId().toString());
		    	randRowPref.add(1, randdeitypref.getDeityName());
		    	randRowPref.add(2, randdeitypref.getDeityAlignment());
		    }
			break;
		case "name":
			qty = nameRepository.findByNameRace(pref).size();
			idx = (int)(Math.random() * qty);
		    questionPage2 = nameRepository.getNamePref(pref,PageRequest.of(idx,1));
		    if (questionPage2.hasContent()) {
		    	NameModel randnamepref = (NameModel) questionPage2.getContent().get(0);
		    	randRowPref.add(0, randnamepref.getNameId().toString());
		    	randRowPref.add(1, randnamepref.getNameFirstName());
		    	randRowPref.add(2, randnamepref.getNameLastName());
		    	randRowPref.add(3, randnamepref.getNameRace());
		    	randRowPref.add(4, randnamepref.getNameGender());
		    }
			break;
		default:
		
		}
		return randRowPref;
	}
	
	/* @author Lucas
	 *
	 * (◉ ᴖ ◉)
	 * Die Funkiton ist traurig da sie von Luca angefordert wurde und
	 * keinen Nutzen hat.
	 *
	 * Gibt mehrere zufällige Zeilen einer ausgewählten DB aus die vorher nach einer
	 * Präferenz sortiert wurden.
	 * 
	 * @param dbname: der Name der DB von der Daten gewünscht sind, als 
	 * 					kleingeschriebener String.
	 * @param pref: String nach dem die DB vor der Randomauswahl sortiert werden soll.
	 *
	 * @return ArrayList gefüllt mit den Attributen der Zeile in form von Strings, 
	 * 				jedes Attribut ein eigener Index, beginend mit der ID auf idx 0
	 */
	public ArrayList<String> getmultRowPref(String dbname, String pref, Integer numberofrow){
		ArrayList<String> rand1 = new ArrayList<String>();
		ArrayList<String> rand2 = new ArrayList<String>();
		ArrayList<String> randresult = new ArrayList<String>();
		boolean unterschied;
		
		switch(dbname) {
		default:
		}
		return randresult;
	}
	
	/* @author Lucas
	 *
	 * (◉ ᴖ ◉)
	 * Die Funkiton ist auch traurig da sie von Luca angefordert wurde und
	 * keinen Nutzen hat.
	 *
	 * Gibt eine zufällige Zeile einer ausgewählten DB aus die vorher nach zwei
	 * Präferenzen sortiert wurde.
	 * 
	 * @param dbname: der Name der DB von der Daten gewünscht sind, als 
	 * 					kleingeschriebener String.
	 * @param pref1: String nach dem die DB vor der Randomauswahl sortiert werden soll.
	 * 
	 * @param pref1: String nach dem die DB vor der Randomauswahl sortiert werden soll.
	 * 
	 * @return ArrayList gefüllt mit den Attributen der Zeile, jedes Attribut 
	 * 					ein eigener Index, beginend mit der ID auf idx 0
	 */
	public ArrayList<String> getrowMultPref(String dbname, String pref1,String pref2){
	    int qty;
	    Page<?> questionPage2;
	    int idx;
	    ArrayList<String> rowMultPref = new ArrayList<String>();
	    
		switch(dbname) {
		default:
		}
		return rowMultPref;
	}
	
	/* @author Lucas
	 *
	 * Iniziert das speichern einer neuen Zeile über das DBMS, da die
	 * genaue Struktur der History DB aktuell noch unbekannt ist, ist diese funktion
	 * was Eingaben und teilweise Struktur betrift noch WIP.
	 */
	public void saveEntry() {
		 HistoryModel newentry = new HistoryModel();
		 //hier würden die übertragenen Daten in das Model newentry eingetragen werden.
		 
		 manageHistory("save",newentry);
		 return;
	 }
	
	/* @author Lucas
	 *
	 * Iniziert das updaten/bearbeiten einer neuen Zeile über das DBMS, da die
	 * genaue Struktur der History DB aktuell noch unbekannt ist, ist diese funktion
	 * was Eingaben und teilweise Struktur betrift noch WIP.
	 */
	public void updateEntry(Integer HistID) {
		 HistoryModel updatedentry = new HistoryModel();
		 updatedentry.setHistoryId(HistID);
		//hier würden die weitern übertragenen Daten in das Model 
		//newentry eingetragen werden.
		 manageHistory("update",updatedentry);
		 return;
	 }

	/* @author Lucas
	 *
	 * Verwaltet die ausführung der speicher und update funktion der History
	 * um unteranderem sicherstellen zu können das es nie mehr als 10 Einträge
	 * gibt und immer der älteste gelöscht wird.
	 */
	@Transactional
	public void manageHistory(String typeOfChange, HistoryModel histchange) {
		 HistoryModel histentry = histchange;
		 
		 switch(typeOfChange) {
		 case "save":
			 if (historyRepository.count() == 0) {
				 historyRepository.findById(11).get().setRunVar(1);
			 }else if (historyRepository.findById(11).get().getRunVar() == 11) {
				 historyRepository.findById(11).get().setRunVar(1);
			 }
			 histentry.setHistoryId(historyRepository.findById(11).get().getRunVar());
			 historyRepository.save(histentry);
			 historyRepository.findById(11).get().setRunVar(historyRepository.findById(11).get().getRunVar()+1);
		 case "update":
			 //historyRepository.setNameInfoById(histentry.getHistoryFirstName(),histentry.getHistoryLastName(),histentry.getHistoryOccurance(),histentry.getHistoryId());
		 }
	}
	
	/* @author Lucas
	 *
	 * Ein überbleibsel meines früheren Versuches die Switch-cases der Hauptfunktionen
	 * der DBMS zu vermeiden, was aber an den Custom funktionen der Interfaces
	 * gescheitert ist, hier zu weiteren Testzwecken.
	 */
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
}