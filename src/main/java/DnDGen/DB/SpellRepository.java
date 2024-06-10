/* @author Lucas
 * 
 * Definiert die (Custom) Funktionen die über der dazugehörigen DB bzw iherem Model
 * ausgeführt werden können.
 *
 */

package DnDGen.DB;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SpellRepository extends CrudRepository<SpellModel, Integer>{
	
	long count();
	ArrayList<SpellModel> findBySpellId(Integer id);
	ArrayList<SpellModel> findBySpellName(String name);
	ArrayList<SpellModel> findBySpellLevel(String level);
	ArrayList<SpellModel> findBySpellSchool(String school);
	ArrayList<SpellModel> findBySpellCastingTime(String castingTime);
	ArrayList<SpellModel> findBySpellRange(String range);
	ArrayList<SpellModel> findBySpellComponents(String components);
	ArrayList<SpellModel> findBySpellDuration(String duration);
	ArrayList<SpellModel> findBySpellDescription(String description);
	ArrayList<SpellModel> findBySpellLevelUpScale(String levelUpScale);
	ArrayList<SpellModel> findBySpellAvailability(String Availability);
	@Query("SELECT l FROM SpellModel l WHERE l.spellAvailability Like %?1% AND l.spellLevel LIKE %?2%")
	ArrayList<SpellModel> findByMultPrefsize(String spellAvailability,String spellLevel);
	@Query("SELECT m FROM SpellModel m WHERE m.spellAvailability Like %?1% AND m.spellLevel LIKE %?2%")
	Page<SpellModel> findByMultPrefdata(String spellAvailability,String spellLevel,Pageable pageable1);
	@Query("SELECT n.spellName FROM SpellModel n WHERE  n.spellAvailability Like %?1%")
	ArrayList<String> getSpellCol(String spellAvailability);
	@Query("SELECT p FROM SpellModel p WHERE p.spellName Like %?1%")
	Page<SpellModel> getSpellOnePref(String spellName,Pageable pageable2);
}
