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
 * Als Info: Ich teste alle meine Funktionen bevor sie auf Main gehen und
 * übernehme keine Haftung für falsche Methodeneingaben.
 *
 */

package DnDGen.DB;

import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import DnDGen.DB.Sortierung.Order;
import DnDGen.TSWP1.RandomGenerator;


@SpringBootApplication
public class DBMS implements ApplicationContextAware{
	@SuppressWarnings("unused")
	private static ConfigurableApplicationContext ctx;
    private static ApplicationContext atx;
	
	/* @author Lucas
	 *
	 * Verbindet alle Datenbanken mit dem DBMS
	 */
	@Autowired
	AbiliScRepository abiliScRepository;
	@Autowired
	ActionRepository actionRepository;
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
	@Autowired
	SpellKnownRepository spellKnownRepository; 
	@Autowired
	SpellSlotsRepository spellSlotsRepository; 
    Object r;
	
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
	 * Finden und setzen des ApplicationContextes damit andere Klassen in der
	 * Lage sind genau diese Version der Klasse DBMS aufzurufen um auch wirklich Daten zu erhalten.
	 */
    public void setApplicationContext(@NonNull ApplicationContext appContext) {
      atx = appContext;
    }
    public static ApplicationContext getApplicationContext() {
      return atx;
    }
	
	/* @author Lucas
	 * 
	 * Macht aktuell das gleiche wie die Main, ist aber für spätere
	 * verwendungszwecke hier.
	 */
	public static void dbmsStarter(String[] args) {
		ctx = SpringApplication.run(DBMS.class, args);
	}
	
	/* @author Lucas
	 *
	 * Fährt das DBMS herunter und schließt damit auch die Verbidung
	 * zur MySQL DB.
	 */
	public static void dbmsClose(ConfigurableApplicationContext ctx) {
		ctx.close();
	}
	
	/* @author Lucas
	 *
	 * Implementiert einen genrellen ExceptionThrower für sneaky
	 */
	public interface RunnableThrowingException<T extends Exception> {
	    void run() throws T;
	}
	
	/* @author Lucas
	 *
	 * Verallgemeinert alle Try-Catch statements im Code um die LOC in der Klasse
	 * so weit zu reduzieren wie möglich
	 */
	public static void sneaky(RunnableThrowingException<?> runnableThrowing) {
		try {
	        runnableThrowing.run();
	    } catch (Exception e) {
	        throw new RuntimeException(e);
	    }
	}
	
	/* @author Lucas
	 *
	 * Ordnet alle Methoden innerhalb der Datenbankmodellen in der Reihenfolge in der sie deklariert wurden.
	 * 
	 * @param clazz Klasse deren Methoden geordnet werden sollen
	 */
	public Method[] ordner(Class<?> clazz) {
		Method[] methods = clazz.getMethods();
        Arrays.sort(methods, new Comparator<Method>() {
            @Override
            public int compare(Method o1, Method o2) {
                Order or1 = o1.getAnnotation(Order.class);
                Order or2 = o2.getAnnotation(Order.class);
                // nulls last
                if (or1 != null && or2 != null) {
                    return or1.value() - or2.value();
                } else
                if (or1 != null && or2 == null) {
                    return -1;
                } else
                if (or1 == null && or2 != null) {
                    return 1;
                }
                return o1.getName().compareTo(o2.getName());
            }
        });
        return methods;
	}
	
	/* @author Lucas
	 *
	 * Gibt die Namensspalte einer Datenbank für UI zwecke aus.
	 * 
	 * @param dbname: der Name der DB von der Daten gewünscht sind, als 
	 * 					kleingeschriebener String.
	 * @return Eine ArrayList gefüllt mit Strings, jeder String ein eigener Index.
	 */
	public ArrayList<String> onlyColumn(String dbname) {
		ArrayList<String> onlycol = new ArrayList<String>();
		//deity, alignment, languange, Weapon, Armor, Spells, actions
		switch(dbname) {
		case "race":
			onlycol = raceRepository.getallRaceCol();
			break;
		case "class":
			onlycol = classRepository.getallClassCol();
			break;
		case "job":
			onlycol = jobRepository.getallJobCol();
			break;
		case "abilisc":
			onlycol = abiliScRepository.getallAbiliScCol();
			break;
		case "deity":
			onlycol = deityRepository.getallDeityCol();
			break;
		case "alignment":
			onlycol = alignmentRepository.getallAlignmentCol();
			break;
		case "language":
			onlycol = languageRepository.getallLangCol();
			break;
		case "armor":
			onlycol = armorRepository.getallArmCol();
			break;
		case "weapon":
			onlycol = weaponRepository.getallWeapCol();
			break;
		default:
			onlycol = null;
			break;
		}
		return onlycol;
	}
	
	/* @author Lucas
	 *
	 *Gibt die nach einer Präferenz sortierten Namensspalte einer Datenbank für UI zwecke aus.
	 * 
	 * @param dbname: der Name der DB von der Daten gewünscht sind, als 
	 * 					kleingeschriebener String.
	 * @param Clazz: KlassenName für den die Funktion ausgeführt werden soll(z.B.: "Fighter")
	 */
	public ArrayList<String> getColWithPref(String dbname,String Clazz){
		ArrayList<String> col = new ArrayList<String>();
		RandomGenerator randGen = new RandomGenerator();
		//Liste für Spells nach Klasse, Level, Spelllevel. Für Waffen+Rüstung nach Prof in Klasse in InnerJoin mit der jeweiligen Klasse
		//-deity, -alignment, -languange, -Weapon, -Armor, -Spells, -actions
		switch(dbname) {
		case "weapon":
			ArrayList<String> weaponprefs = (ArrayList<String>) randGen.bigStringToDividedString(classRepository.getWeaponPref(Clazz));
			for(String weaponpref : weaponprefs) {
				col.addAll(weaponRepository.getWeaponCol(weaponpref));
			}
			break;
		case "armor":
			ArrayList<String> armorprefs = (ArrayList<String>) randGen.bigStringToDividedString(classRepository.getArmorPref(Clazz));
			for(String armorpref : armorprefs) {
				col.addAll(armorRepository.getArmorCol(armorpref));
			}
			break;
		case "spells":
			col = spellRepository.getSpellCol(Clazz);
			break;
		case "actions":
			col = actionRepository.getActionsCol(Clazz);
			break;
		case "deity":
			col = deityRepository.getDeityByAlignmentCol(Clazz);
			break;
		default:
			col = null;
			break;
		}
		return col;
	}
	
	public ArrayList<String> bobMakesSortedList(Object model){
		ArrayList<String> sl = new ArrayList<String>();
		for (Method m : ordner(model.getClass())) {
			if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {
				sneaky(() -> r = m.invoke(model)); 
				sl.add(String.valueOf(r));
    		}
    	}
		return sl;
	}
	
	/* @author Lucas
	 *
	 * Gibt eine spezifische Zeile einer ausgewählten DB aus.
	 * Achtung: da diese Funktion nur eine Zeile erwartet muss der
	 * searchstring unique sein, damit auch nur ein Ergebniss gefunden wird.
	 * Wenn der searchstring nicht unique ist bitte getRowPref() nutzen.
	 * 
	 * @param dbname: der Name der DB von der Daten gewünscht sind, als 
	 * 					kleingeschriebener String.
	 * @param searchstring: String nach dem eine Zeile ausgewählt werden soll, ist
	 * 					ein vorbestimmtes Atrribut.(Race,Job,Class,Features: Name)
	 * @return ArrayList gefüllt mit den Attributen der Zeile in form von Strings, 
	 * 				jedes Attribut ein eigener Index, beginend mit der ID auf idx 0
	 */
	public ArrayList<String> getRow(String dbName,String searchstring) {
		 ArrayList<String> row = new ArrayList<String>();
		 
			switch(dbName) {
			case "race":
				RaceModel entryrace = raceRepository.findByRaceName(searchstring);
				row = bobMakesSortedList(entryrace);
				break;
			case "job":
				JobModel entryjob = jobRepository.findByJobName(searchstring);
				row = bobMakesSortedList(entryjob);
				break;
			case "class":
				ClassModel entryclass = classRepository.findByClassName(searchstring);
				row = bobMakesSortedList(entryclass);
				break;
			case "features":
				FeatureModel entryfeature = featureRepository.findByFeatureName(searchstring);
				row = bobMakesSortedList(entryfeature);
				break;
			case "armor":
			    ArmorModel entryarmor = armorRepository.findByArmorName(searchstring);
			    row = bobMakesSortedList(entryarmor);
				break;
			case "weapon":
			    WeaponModel entryweapon = weaponRepository.findByWeaponName(searchstring);
			    row = bobMakesSortedList(entryweapon);
				break;
			case "level":
			    LevelModel entrylevel = levelRepository.findByLevelNumber(Integer.parseInt(searchstring));
			    row = bobMakesSortedList(entrylevel);
				break;
			case "skills":
				SkillModel entryskill = skillRepository.findBySkillName(searchstring);
				row = bobMakesSortedList(entryskill);
				break;
			case "dracances":
				DracAncesModel entrydracances = dracAncesRepository.findByDraconicDragon(searchstring);
				row = bobMakesSortedList(entrydracances);
				break;
			case "spellslots":
				SpellSlotsModel entryspellslots = spellSlotsRepository.findBySpellSlotsClass(searchstring);
				row = bobMakesSortedList(entryspellslots);
				break;
			case "spells":
			    SpellModel entryspells = spellRepository.findBySpellName(searchstring);
			    row = bobMakesSortedList(entryspells);
				break;
			case "deity":
		    	DeityModel entrydeity = deityRepository.findByDeityName(searchstring);
		    	row = bobMakesSortedList(entrydeity);
			break;
			default:
				row = null;
			}
			return row;
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
	public ArrayList<String> getRandomRow(String dbname) {
	    long qty;
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
		    	randRow = bobMakesSortedList(randclass);
		    }
			break;
		case "language":
			qty = languageRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = languageRepository.findAll(PageRequest.of(idx, 1));
		    if (questionPage.hasContent()) {
		    	LanguageModel randlanuguage = (LanguageModel) questionPage.getContent().get(0);
		    	randRow = bobMakesSortedList(randlanuguage);
		    }
			break;
		case "job":
			qty = jobRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = jobRepository.findAll(PageRequest.of(idx, 1));
		    if (questionPage.hasContent()) {
		    	JobModel randjob =  (JobModel) questionPage.getContent().get(0);
		    	randRow = bobMakesSortedList(randjob);
		    }
			break;
		case "race":
			qty = raceRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = raceRepository.findAll(PageRequest.of(idx, 1));
		    if (questionPage.hasContent()) {
		    	RaceModel randrace = (RaceModel) questionPage.getContent().get(0);
		    	randRow = bobMakesSortedList(randrace);
		    }
			break;
		case "description":
			qty = descriptionRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = descriptionRepository.findAll(PageRequest.of(idx, 1));
		    if (questionPage.hasContent()) {
		    	DescriptionModel randesc = (DescriptionModel) questionPage.getContent().get(0);
		    	randRow = bobMakesSortedList(randesc);
		    }
			break;
		case "alignment":
			qty = alignmentRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = alignmentRepository.findAll(PageRequest.of(idx, 1));
		    if (questionPage.hasContent()) {
		    	AlignmentModel randalig = (AlignmentModel) questionPage.getContent().get(0);
		    	randRow = bobMakesSortedList(randalig);
		    }
			break;
		case "dracances":
			qty = dracAncesRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = dracAncesRepository.findAll(PageRequest.of(idx, 1));
		    if (questionPage.hasContent()) {
		    	DracAncesModel randdracances = (DracAncesModel) questionPage.getContent().get(0);
		    	randRow = bobMakesSortedList(randdracances);
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
	public ArrayList<String> getRowPref(String dbname, String pref){
	    int qty;
	    Page<?> questionPage2;
	    int idx;
	    ArrayList<String> randRowPref = new ArrayList<String>();
	    
		switch(dbname) {
		case "armor":
			qty = armorRepository.findByArmorKind(pref).size();
			idx = (int)(Math.random() * qty);
		    questionPage2 = armorRepository.getArmorPref(pref,PageRequest.of(idx,1));
		    if (questionPage2.hasContent()) {
		    	ArmorModel randarmorpref = (ArmorModel) questionPage2.getContent().get(0);
		    	randRowPref = bobMakesSortedList(randarmorpref);
		    }
			break;
		case "weapon":
			qty = weaponRepository.findByWeaponKind(pref).size();
			idx = (int)(Math.random() * qty);
		    questionPage2 = weaponRepository.getWeaponPref(pref,PageRequest.of(idx,1));
		    if (questionPage2.hasContent()) {
		    	WeaponModel randweaponpref = (WeaponModel) questionPage2.getContent().get(0);
		    	randRowPref = bobMakesSortedList(randweaponpref);
		    }
			break;
		case "deity":
			qty = deityRepository.findByDeityAlignment(pref).size();
			idx = (int)(Math.random() * qty);
		    questionPage2 = deityRepository.getDeityPref(pref,PageRequest.of(idx,1));
		    if (questionPage2.hasContent()) {
		    	DeityModel randdeitypref = (DeityModel) questionPage2.getContent().get(0);
		    	randRowPref = bobMakesSortedList(randdeitypref);
		    }
			break;
		case "name":
			qty = nameRepository.findByNameRace(pref).size();
			idx = (int)(Math.random() * qty);
		    questionPage2 = nameRepository.getNamePref(pref,PageRequest.of(idx,1));
		    if (questionPage2.hasContent()) {
		    	NameModel randnamepref = (NameModel) questionPage2.getContent().get(0);
		    	randRowPref = bobMakesSortedList(randnamepref);
		    }
			break;
		case "abilisc":
			qty = abiliScRepository.findByAbiliScGenerationMethod(pref).size();
			idx = (int)(Math.random() * qty);
		    questionPage2 = abiliScRepository.getAbiliScPref(pref,PageRequest.of(idx,1));
		    if (questionPage2.hasContent()) {
		    	AbiliScModel randabilipref = (AbiliScModel) questionPage2.getContent().get(0);
		    	randRowPref = bobMakesSortedList(randabilipref);
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
	 * @return ArrayList gefüllt mit den Attributen der Zeilen, jedes Attribut 
	 * 					ein eigener Index, beginend mit der ID auf idx 0. Die Zeilen
	 * 					werden in der ArrayList hintereinnader gehangen.
	 */
	public ArrayList<String> getMultRowPref(String dbname, String pref, Integer numberofrow){
		ArrayList<String> rand = new ArrayList<String>();
		ArrayList<String> multRowPref = new ArrayList<String>();
		
		switch(dbname) {
		default:
			rand= null;
			multRowPref= null;
		}
		return multRowPref;
	}
	
	/* @author Lucas
	 *
	 * (• ᴗ •)
	 * Die Funkiton ist nun glücklich, da sie einen nutzen bekommen hat. :D
	 *
	 * Gibt eine zufällige Zeile einer ausgewählten DB aus die vorher nach zwei
	 * Präferenzen sortiert wurde.
	 * 
	 * @param dbname: der Name der DB von der Daten gewünscht sind, als 
	 * 					kleingeschriebener String.
	 * @param pref1: String nach dem die DB vor der Randomauswahl sortiert werden soll.
	 * 
	 * @param pref2: String nach dem die DB vor der Randomauswahl sortiert werden soll.
	 * 
	 * @return ArrayList gefüllt mit den Attributen der Zeile, jedes Attribut 
	 * 					ein eigener Index, beginend mit der ID auf idx 0
	 */
	public ArrayList<String> getRowMultPref(String dbname, String pref1, String pref2){
	    int qty;
	    Page<?> questionPage;
	    int idx;
	    ArrayList<String> rowMultPref = new ArrayList<String>();
	    
		switch(dbname) {
		case "spells":
			qty = spellRepository.findByMultPrefsize(pref1, pref2).size();
			idx = (int)(Math.random() * qty);
		    questionPage = spellRepository.findByMultPrefdata(pref1, pref2,PageRequest.of(idx,1));
		    if (questionPage.hasContent()) {
		    	SpellModel spellmultpref = (SpellModel) questionPage.getContent().get(0);
		    	rowMultPref = bobMakesSortedList(spellmultpref);
		    }
		    break;
		case "spellknown":
			qty = spellKnownRepository.findByMultPrefsize(pref1, pref2).size();
			idx = (int)(Math.random() * qty);
		    questionPage = spellKnownRepository.findByMultPrefdata(pref1, pref2,PageRequest.of(idx,1));
		    if (questionPage.hasContent()) {
		    	SpellKnownModel spellknownmultpref = (SpellKnownModel) questionPage.getContent().get(0);
		    	rowMultPref = bobMakesSortedList(spellknownmultpref);
		    }
		    break;
		default:
			rowMultPref = null;
			break;
		}
		return rowMultPref;
	}
	
	/* @author Lucas
	 *
	 * Gibt eine gewünschte Anzahl an zufälligen Zeilen einer ausgewählten DB aus die vorher nach zwei
	 * Präferenzen sortiert wurde.
	 * 
	 * @param dbname: der Name der DB von der Daten gewünscht sind, als 
	 * 					kleingeschriebener String.
	 * @param pref1: String nach dem die DB vor der Randomauswahl sortiert werden soll.
	 * 
	 * @param pref2: String nach dem die DB vor der Randomauswahl sortiert werden soll.
	 * 
	 * @param rownumber: Anzahl der Zeilen die Ausgegeben werden sollen.
	 * 
	 * @return ArrayList gefüllt mit den Attributen der Zeilen, jedes Attribut 
	 * 					ein eigener Index, beginend mit der ID auf idx 0. Die Zeilen
	 * 					werden in der ArrayList hintereinnader gehangen.
	 */
	public ArrayList<String> getMultRowMultPref(String dbname, String pref, String pref2, Integer rownumber){
		ArrayList<String> rand = new ArrayList<String>();
		ArrayList<String> multRowMultPref = new ArrayList<String>();
		Integer i = 2;
		
		switch(dbname) {
		case "spells":
			multRowMultPref.addAll(getRowMultPref(dbname, pref, pref2));
			while (i <= rownumber) {
				rand.addAll(getRowMultPref(dbname, pref, pref2));
				if (multRowMultPref.containsAll(rand)) {
					rand.clear();
				} else {
					multRowMultPref.addAll(rand);
					rand.clear();
					i++;
				}
			}
			break;
		case "actions":
			for (ActionModel actions : actionRepository.findByAllActionsWithPref(pref, rownumber, pref2)) {
				rand.add(actions.getActionName());
				rand.add(actions.getActionDescription());
				multRowMultPref.addAll(rand);
				rand.clear();
			}
			break;
		default:
			multRowMultPref=null;
		}
		return multRowMultPref;
	}
	
	/* @author Lucas
	 *
	 * Gibt den Inhalt der HistoryDB als ArrayList aus. Jeder Index
	 * in der ArrayList ist ein NPC, gespeichert als "HistoryModel". 
	 * Der genaue Aufbau ist der Datei "HistoryModel.java" zu entnehmen, alle Attribute besitzen getter und setter.
	 */
	public ArrayList<HistoryModel> getHistory() {
		ArrayList<HistoryModel> historycontent = new ArrayList<HistoryModel>();
		for(HistoryModel i : historyRepository.findEverythingExceptCounter()) {
			historycontent.add(i);
		}
		Collections.sort(historycontent, new Comparator<HistoryModel>() {
		    @Override
		    public int compare(HistoryModel o1, HistoryModel o2) {
		        return o2.getHistoryEntryAge().compareTo(o1.getHistoryEntryAge());
		    }
		});
		return historycontent;
	 }
	
	/* @author Lucas
	 *
	 * Gibt eine bestimmte Zeile der History DB anhand der ID aus.
	 * 
	 * @param ID: ID der Zeile die Ausgegeben werden soll.
	 * @return als "HistoryModel". 
	 * Der genaue Aufbau ist der Datei "HistoryModel.java" zu entnehmen, alle Attribute besitzen getter und setter.
	 */
	public HistoryModel getHistoryEntry(Integer ID){
		HistoryModel Entry = historyRepository.findByHistoryId(ID);
		/*for (Method m : ordner(Entry.getClass())) {
    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
    			 sneaky(() -> r = m.invoke(Entry));
    			 FinalEntry.add(String.valueOf(r));
    		}
    	}*/
		return Entry;
	}
	
	
	/* @author Lucas
	 *
	 * Iniziert das speichern einer neuen Zeile über das DBMS, da die
	 * genaue Struktur der History DB aktuell noch unbekannt ist, ist diese funktion
	 * was Eingaben und teilweise Struktur betrift noch WIP.
	 */
	public void saveEntry(ArrayList<Object> NPC) {
		HistoryModel newentry = new HistoryModel();
	    	for (Method m : ordner(newentry.getClass())) {
	    		if (m.getName().startsWith("set") && m.getParameterTypes().length == 1 && !m.getName().equals("setHistoryId")&& 
	    				!m.getName().equals("setRunVar")&& !m.getName().equals("setHistoryEntryAge")) {	
	    			 sneaky(() -> r = m.invoke(newentry, NPC.getFirst()));
	    			 NPC.removeFirst();
	    		}
	    	}
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
	@Modifying
	public void manageHistory(String typeOfChange, HistoryModel histchange) {
		 switch(typeOfChange) {
		 case "save":
			 if (historyRepository.count() == 0) {
				 historyRepository.findById(11).get().setRunVar(1);
				 break;	 
			 }else if (historyRepository.findById(11).get().getRunVar() == 11) {
				 historyRepository.setRuVaManuel(1, 11);;
				 break;
			 }
			 histchange.setHistoryId(historyRepository.findById(11).get().getRunVar());
			 histchange.setHistoryEntryAge(LocalDateTime.now());
			 historyRepository.save(histchange);
			 historyRepository.setRuVaManuel((historyRepository.findById(11).get().getRunVar())+1, 11);
			 break;
		 case "update":
			 //historyRepository.setNewInfoById(histentry.getHistoryFirstName(),histentry.getHistoryLastName(),histentry.getHistoryOccurance(),histentry.getHistoryId());
			 break;
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