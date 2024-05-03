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
import java.util.ArrayList;
import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.repository.CrudRepository;
import org.springframework.lang.NonNull;
import org.springframework.transaction.annotation.Transactional;

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
				CD.add(9, Entryclass.getClassSpellKnown());
				CD.add(10, Entryclass.getClassFeatures());
				break;
			case "features":
				FeatureModel Entryfeature = featureRepository.findByFeatureName(searchstring);
				CD.add(0, Entryfeature.getFeatureId().toString());
				CD.add(1, Entryfeature.getFeatureName());
				CD.add(2, Entryfeature.getFeatureDescription());
				CD.add(3, Entryfeature.getFeatureKindOf());
				break;
			case "armor":
			    ArmorModel Entryarmor = armorRepository.findByArmorName(searchstring);
			    CD.add(0, Entryarmor.getArmorId().toString());
			    CD.add(1, Entryarmor.getArmorName());
			    CD.add(2, Entryarmor.getArmorKind());
			    CD.add(3, Entryarmor.getArmorAc());
			    CD.add(4, Entryarmor.getArmorMax());
			    CD.add(5, Entryarmor.getArmorBonus());
			    CD.add(6, Entryarmor.getArmorStrength());
			    CD.add(7, Entryarmor.getArmorStealth());
				break;
			case "weapon":
			    WeaponModel Entryweapon = weaponRepository.findByWeaponName(searchstring);
			    CD.add(0, Entryweapon.getWeaponId().toString());
			    CD.add(1, Entryweapon.getWeaponName());
			    CD.add(2, Entryweapon.getWeaponProperty());
			    CD.add(3, Entryweapon.getWeaponDamageType());
			    CD.add(4, Entryweapon.getWeaponDamageDice());
			    CD.add(5, Entryweapon.getWeaponKind());
				break;
			case "level":
			    LevelModel Entrylevel = levelRepository.findByLevelNumber(Integer.parseInt(searchstring));
			    CD.add(0, Entrylevel.getLevelId().toString());
			    CD.add(1, Entrylevel.getLevelNumber().toString());
			    CD.add(2, Entrylevel.getLevelProfBonus().toString());
				break;
			case "skills":
				SkillModel Entryskill = skillRepository.findBySkillName(searchstring);
				CD.add(0, Entryskill.getSkillId().toString());
				CD.add(1, Entryskill.getSkillName());
				CD.add(2, Entryskill.getSkillAbilityScore());
				break;
			case "dracances":
				DracAncesModel Entrydracances = dracAncesRepository.findByDraconicDragon(searchstring);
				CD.add(0, Entrydracances.getDraconicId().toString());
				CD.add(1, Entrydracances.getDraconicDragon());
				CD.add(2, Entrydracances.getDraconicDamageType());
				CD.add(3, Entrydracances.getDraconicBreathWeapon());
				break;
			case "spellslots":
				SpellSlotsModel Entryspellslots = spellSlotsRepository.findBySpellSlotsClass(searchstring);
				CD.add(0, Entryspellslots.getSpellSlotsId().toString());
				CD.add(1, Entryspellslots.getSpellSlotsClass());
				CD.add(2, Entryspellslots.getSpellSlotsCantrips().toString());
				CD.add(3, Entryspellslots.getSpellSlots1Level().toString());
				CD.add(4, Entryspellslots.getSpellSlots2Level().toString());
				CD.add(5, Entryspellslots.getSpellSlots3Level().toString());
				CD.add(6, Entryspellslots.getSpellSlots4Level().toString());
				CD.add(7, Entryspellslots.getSpellSlots5Level().toString());
				CD.add(8, Entryspellslots.getSpellSlots6Level().toString());
				CD.add(9, Entryspellslots.getSpellSlots7Level().toString());
				CD.add(10, Entryspellslots.getSpellSlots8Level().toString());
				CD.add(11, Entryspellslots.getSpellSlots9Level().toString());
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
		    	randRow.add(0, randclass.getClassId().toString());
		    	randRow.add(1, randclass.getClassName());
		    	randRow.add(2, randclass.getClassHitDice());
		    	randRow.add(3, randclass.getClassProfWeapon());
		    	randRow.add(4, randclass.getClassProfArmor());
		    	randRow.add(5, randclass.getClassSavingThrows());
		    	randRow.add(6, randclass.getClassSkills());
		    	randRow.add(7, randclass.getClassSkillNumber().toString());
		    	randRow.add(8, randclass.getClassAbilityScoreDistribution());
		    	randRow.add(9, randclass.getClassSpellKnown());
		    	randRow.add(10, randclass.getClassFeatures());
		    }
			break;
		case "language":
			qty = languageRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = languageRepository.findAll(PageRequest.of(idx, 1));
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
		    	JobModel randjob =  (JobModel) questionPage.getContent().get(0);
		    	randRow.add(0, randjob.getJobId().toString());
		    	randRow.add(1, randjob.getJobName());
		    	randRow.add(2, randjob.getJobSkills());
		    	randRow.add(3, randjob.getJobLanguage());
		    }
			break;
		case "race":
			qty = raceRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = raceRepository.findAll(PageRequest.of(idx, 1));
		    if (questionPage.hasContent()) {
		    	RaceModel randrace = (RaceModel) questionPage.getContent().get(0);
		    randRow.add(0, randrace.getRaceId().toString());
		    randRow.add(1, randrace.getRaceName());
		    randRow.add(2, randrace.getRaceAbilityScore());
		    randRow.add(3, randrace.getRaceFeatures());
		    randRow.add(4, randrace.getRaceAgeAdult().toString());
		    randRow.add(5, randrace.getRaceAgeMax().toString());
		    randRow.add(6, randrace.getRaceSkills());
		    randRow.add(7, randrace.getRaceHeight());
		    randRow.add(8, randrace.getRaceSize());
		    randRow.add(9, randrace.getRaceSpeed());
		    randRow.add(10, randrace.getRaceLanguages());
		    randRow.add(11, randrace.getRaceCreatureType());
		    }
			break;
		case "description":
			qty = descriptionRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = descriptionRepository.findAll(PageRequest.of(idx, 1));
		    if (questionPage.hasContent()) {
		    	DescriptionModel randesc = (DescriptionModel) questionPage.getContent().get(0);
		    randRow.add(0, randesc.getDescriptionId().toString());
		    randRow.add(1, randesc.getDescriptionEyes());
		    randRow.add(2, randesc.getDescriptionHairColor());
		    randRow.add(3, randesc.getDescriptionHairStyle());
		    randRow.add(4, randesc.getDescriptionSkin());
		    randRow.add(5, randesc.getDescriptionTrait());
		    randRow.add(6, randesc.getDescriptionMannerism());
		    randRow.add(7, randesc.getDescriptionBond());
		    randRow.add(8, randesc.getDescriptionFlaw());
		    randRow.add(9, randesc.getDescriptionIdeal());
		    randRow.add(10, randesc.getDescriptionVoice());
		    randRow.add(11, randesc.getDescriptionClothing());
		    randRow.add(12, randesc.getDescriptionAttitute());
		    randRow.add(13, randesc.getDescriptionPlothook());
		    }
			break;
		case "alignment":
			qty = alignmentRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = alignmentRepository.findAll(PageRequest.of(idx, 1));
		    if (questionPage.hasContent()) {
		    	AlignmentModel randalig = (AlignmentModel) questionPage.getContent().get(0);
		    randRow.add(0, randalig.getAlignmentId().toString());
		    randRow.add(1, randalig.getAlignmentName());
		    randRow.add(2, randalig.getAlignmentShorted());
		    }
			break;
		case "dracances":
			qty = dracAncesRepository.count();
			idx = (int)(Math.random() * qty);
		    questionPage = dracAncesRepository.findAll(PageRequest.of(idx, 1));
		    if (questionPage.hasContent()) {
		    	DracAncesModel randdracances = (DracAncesModel) questionPage.getContent().get(0);
			randRow.add(0, randdracances.getDraconicId().toString());
			randRow.add(1, randdracances.getDraconicDragon());
			randRow.add(2, randdracances.getDraconicDamageType());
			randRow.add(3, randdracances.getDraconicBreathWeapon());
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
		    	randRowPref.add(0, randarmorpref.getArmorId().toString());
		    	randRowPref.add(1, randarmorpref.getArmorName());
		    	randRowPref.add(2, randarmorpref.getArmorKind());
		    	randRowPref.add(3, randarmorpref.getArmorAc());
		    	randRowPref.add(4, randarmorpref.getArmorMax());
		    	randRowPref.add(5, randarmorpref.getArmorBonus());
		    	randRowPref.add(6, randarmorpref.getArmorStrength());
		    	randRowPref.add(7, randarmorpref.getArmorStealth());
		    }
			break;
		case "weapon":
			qty = weaponRepository.findByWeaponKind(pref).size();
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
		case "abilisc":
			qty = abiliScRepository.findByAbiliScGenerationMethod(pref).size();
			idx = (int)(Math.random() * qty);
		    questionPage2 = abiliScRepository.getAbiliScPref(pref,PageRequest.of(idx,1));
		    if (questionPage2.hasContent()) {
		    	AbiliScModel randabilipref = (AbiliScModel) questionPage2.getContent().get(0);
		    	randRowPref.add(0, randabilipref.getAbiliScId().toString());
		    	randRowPref.add(1, randabilipref.getAbiliScName());
		    	randRowPref.add(2, randabilipref.getAbiliScKind());
		    	randRowPref.add(3, randabilipref.getAbiliScGenerationMethod());
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
		    	rowMultPref.add(0, spellmultpref.getSpellId().toString());
		    	rowMultPref.add(1, spellmultpref.getSpellName());
		    	rowMultPref.add(2, spellmultpref.getSpellLevel().toString());
		    	rowMultPref.add(3, spellmultpref.getSpellSchool());
		    	rowMultPref.add(4, spellmultpref.getSpellCastingTime());
		    	rowMultPref.add(5, spellmultpref.getSpellRange());
		    	rowMultPref.add(6, spellmultpref.getSpellComponents());
		    	rowMultPref.add(7, spellmultpref.getSpellDuration());
		    	rowMultPref.add(8, spellmultpref.getSpellDescription());
		    	rowMultPref.add(9, spellmultpref.getSpellLevelUpScale());
		    	rowMultPref.add(10, spellmultpref.getSpellAvailability());
		    }
		case "spellknown":
			qty = spellKnownRepository.findByMultPrefsize(pref1, pref2).size();
			idx = (int)(Math.random() * qty);
		    questionPage = spellKnownRepository.findByMultPrefdata(pref1, pref2,PageRequest.of(idx,1));
		    if (questionPage.hasContent()) {
		    	SpellKnownModel spellknownmultpref = (SpellKnownModel) questionPage.getContent().get(0);
		    	rowMultPref.add(0, spellknownmultpref.getSpellKnownId().toString());
		    	rowMultPref.add(1, spellknownmultpref.getSpellKnownClass());
		    	rowMultPref.add(2, spellknownmultpref.getSpellKnownKind());
		    	rowMultPref.add(3, spellknownmultpref.getSpellKnownStuff());
		    }
		default:
			//Aus irgendeinem Grund geht er wenn der case "spellkown" ausgeführt wird direkt dannach
			//in den default case und setzt die ArrayList direkt wieder auf null, ka warum....
			//rowMultPref = null;
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