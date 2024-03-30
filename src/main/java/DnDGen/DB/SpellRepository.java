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
import org.springframework.data.jpa.repository.Modifying;
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
}
