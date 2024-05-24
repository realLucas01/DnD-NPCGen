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

import java.lang.reflect.Array;
import java.lang.reflect.Method;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
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
import org.springframework.transaction.annotation.Transactional;

import DnDGen.DB.Sortierung.Order;


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
    @Override
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
	 * Ordnet alle methoden innerhalb der Datenbankmodellen in der Reihenfolge in der sie deklariert wurden.
	 * 
	 * @param clazz Classe deren Methoden geordnet werden sollen
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
		default:
			onlycol = null;
			break;
		}
		return onlycol;
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
	public ArrayList<String> getRow(String DBNAME,String searchstring) {	
		 ArrayList<String> CD = new ArrayList<String>();
		 
			switch(DBNAME) {
			case "race":
				RaceModel Entryrace = raceRepository.findByRaceName(searchstring);
				for (Method m : ordner(Entryrace.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(Entryrace));
		    			 CD.add(String.valueOf(r));
		    		}
		    	}
				break;
			case "job":
				JobModel Entryjob = jobRepository.findByJobName(searchstring);
				for (Method m : ordner(Entryjob.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(Entryjob));
		    			 CD.add(String.valueOf(r));
		    		}
		    	}
				break;
			case "class":
				ClassModel Entryclass = classRepository.findByClassName(searchstring);
				for (Method m : ordner(Entryclass.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(Entryclass));
		    			 CD.add(String.valueOf(r));
		    		}
		    	}
				break;
			case "features":
				FeatureModel Entryfeature = featureRepository.findByFeatureName(searchstring);
				for (Method m : ordner(Entryfeature.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(Entryfeature));
		    			 CD.add(String.valueOf(r));
		    		}
		    	}
				break;
			case "armor":
			    ArmorModel Entryarmor = armorRepository.findByArmorName(searchstring);
			    for (Method m : ordner(Entryarmor.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(Entryarmor));
		    			 CD.add(String.valueOf(r));
		    		}
		    	}
				break;
			case "weapon":
			    WeaponModel Entryweapon = weaponRepository.findByWeaponName(searchstring);
			    for (Method m : ordner(Entryweapon.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(Entryweapon));
		    			 CD.add(String.valueOf(r));
		    		}
		    	}
				break;
			case "level":
			    LevelModel Entrylevel = levelRepository.findByLevelNumber(Integer.parseInt(searchstring));
			    for (Method m : ordner(Entrylevel.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(Entrylevel));
		    			 CD.add(String.valueOf(r));
		    		}
		    	}
				break;
			case "skills":
				SkillModel Entryskill = skillRepository.findBySkillName(searchstring);
				for (Method m : ordner(Entryskill.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(Entryskill));
		    			 CD.add(String.valueOf(r));
		    		}
		    	}
				break;
			case "dracances":
				DracAncesModel Entrydracances = dracAncesRepository.findByDraconicDragon(searchstring);
				for (Method m : ordner(Entrydracances.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(Entrydracances));
		    			 CD.add(String.valueOf(r));
		    		}
		    	}
				break;
			case "spellslots":
				SpellSlotsModel Entryspellslots = spellSlotsRepository.findBySpellSlotsClass(searchstring);
				for (Method m : ordner(Entryspellslots.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(Entryspellslots));
							CD.add(String.valueOf(r));
		    		}
		    	}
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
	public ArrayList<String> getRandomRow(String dbname) {
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
			    for (Method m : ordner(randclass.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(randclass));
		    			 randRow.add(String.valueOf(r));
		    		}
		    	}
		    }
			break;
		case "language":
			qty = languageRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = languageRepository.findAll(PageRequest.of(idx, 1));
		    if (questionPage.hasContent()) {
		    	LanguageModel randlanuguage = (LanguageModel) questionPage.getContent().get(0);
			    for (Method m : ordner(randlanuguage.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(randlanuguage));
		    			 randRow.add(String.valueOf(r));
		    		}
		    	}
		    }
			break;
		case "job":
			qty = jobRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = jobRepository.findAll(PageRequest.of(idx, 1));
		    if (questionPage.hasContent()) {
		    	JobModel randjob =  (JobModel) questionPage.getContent().get(0);
			    for (Method m : ordner(randjob.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(randjob));
		    			 randRow.add(String.valueOf(r));
		    		}
		    	}
		    }
			break;
		case "race":
			qty = raceRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = raceRepository.findAll(PageRequest.of(idx, 1));
		    if (questionPage.hasContent()) {
		    	RaceModel randrace = (RaceModel) questionPage.getContent().get(0);
			    for (Method m : ordner(randrace.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(randrace));
		    			 randRow.add(String.valueOf(r));
		    		}
		    	}
		    }
			break;
		case "description":
			qty = descriptionRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = descriptionRepository.findAll(PageRequest.of(idx, 1));
		    if (questionPage.hasContent()) {
		    	DescriptionModel randesc = (DescriptionModel) questionPage.getContent().get(0);
			    for (Method m : ordner(randesc.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(randesc));
		    			 randRow.add(String.valueOf(r));
		    		}
		    	}
		    }
			break;
		case "alignment":
			qty = alignmentRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = alignmentRepository.findAll(PageRequest.of(idx, 1));
		    if (questionPage.hasContent()) {
		    	AlignmentModel randalig = (AlignmentModel) questionPage.getContent().get(0);
			    for (Method m : ordner(randalig.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(randalig));
		    			 randRow.add(String.valueOf(r));
		    		}
		    	}
		    }
			break;
		case "dracances":
			qty = dracAncesRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = dracAncesRepository.findAll(PageRequest.of(idx, 1));
		    if (questionPage.hasContent()) {
		    	DracAncesModel randdracances = (DracAncesModel) questionPage.getContent().get(0);
			    for (Method m : ordner(randdracances.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(randdracances));
		    			 randRow.add(String.valueOf(r));
		    		}
		    	}
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
		    	for (Method m : ordner(randarmorpref.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0) {	
		    			 sneaky(() -> r = m.invoke(randarmorpref));
		    			 randRowPref.add(String.valueOf(r));
		    		}
		    	}
		    }
			break;
		case "weapon":
			qty = weaponRepository.findByWeaponKind(pref).size();
			idx = (int)(Math.random() * qty);
		    questionPage2 = weaponRepository.getWeaponPref(pref,PageRequest.of(idx,1));
		    if (questionPage2.hasContent()) {
		    	WeaponModel randweaponpref = (WeaponModel) questionPage2.getContent().get(0);
		    	for (Method m : ordner(randweaponpref.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(randweaponpref));
		    			 randRowPref.add(String.valueOf(r));
		    		}
		    	}
		    }
			break;
		case "deity":
			qty = deityRepository.findByDeityAlignment(pref).size();
			idx = (int)(Math.random() * qty);
		    questionPage2 = deityRepository.getDeityPref(pref,PageRequest.of(idx,1));
		    if (questionPage2.hasContent()) {
		    	DeityModel randdeitypref = (DeityModel) questionPage2.getContent().get(0);
		    	for (Method m : ordner(randdeitypref.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(randdeitypref));
		    			 randRowPref.add(String.valueOf(r));
		    		}
		    	}
		    }
			break;
		case "name":
			qty = nameRepository.findByNameRace(pref).size();
			idx = (int)(Math.random() * qty);
		    questionPage2 = nameRepository.getNamePref(pref,PageRequest.of(idx,1));
		    if (questionPage2.hasContent()) {
		    	NameModel randnamepref = (NameModel) questionPage2.getContent().get(0);
		    	for (Method m : ordner(randnamepref.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(randnamepref));
		    			 randRowPref.add(String.valueOf(r));
		    		}
		    	}
		    }
			break;
		case "abilisc":
			qty = abiliScRepository.findByAbiliScGenerationMethod(pref).size();
			idx = (int)(Math.random() * qty);
		    questionPage2 = abiliScRepository.getAbiliScPref(pref,PageRequest.of(idx,1));
		    if (questionPage2.hasContent()) {
		    	AbiliScModel randabilipref = (AbiliScModel) questionPage2.getContent().get(0);
		    	for (Method m : ordner(randabilipref.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(randabilipref));
		    			 randRowPref.add(String.valueOf(r));
		    		}
		    	}
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
		    	for (Method m : ordner(spellmultpref.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(spellmultpref));
		    			 rowMultPref.add(String.valueOf(r));
		    		}
		    	}
		    }
		    break;
		case "spellknown":
			qty = spellKnownRepository.findByMultPrefsize(pref1, pref2).size();
			idx = (int)(Math.random() * qty);
		    questionPage = spellKnownRepository.findByMultPrefdata(pref1, pref2,PageRequest.of(idx,1));
		    if (questionPage.hasContent()) {
		    	SpellKnownModel spellknownmultpref = (SpellKnownModel) questionPage.getContent().get(0);
		    	for (Method m : ordner(spellknownmultpref.getClass())) {
		    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
		    			 sneaky(() -> r = m.invoke(spellknownmultpref));
		    			 rowMultPref.add(String.valueOf(r));
		    		}
		    	}
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
	 * in der ArrayList ist ein NPC, gespeichert als eigene ArrayList.
	 */
	public ArrayList<Object> getHistory() {
		ArrayList<Object> historycontent = new ArrayList<Object>();
		//ArrayList<Object> historysingle = new ArrayList<Object>();
		for(HistoryModel i : historyRepository.findEverythingExceptCounter()) {
			ArrayList<Object> historysingle = new ArrayList<Object>();
			for (Method m : ordner(i.getClass())) {
	    		if (m.getName().startsWith("get") && m.getParameterTypes().length == 0 && !m.getName().equals("getClass")) {	
	    			 sneaky(() -> r = m.invoke(i));
	    			 historysingle.add(r);
	    		}
	    	}
			historycontent.add(historysingle);
		}
		return historycontent;
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