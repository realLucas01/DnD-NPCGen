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

public interface SkillRepository extends CrudRepository<SkillModel, Integer>{
	
	long count();
	ArrayList<SkillModel> findBySkillId(Integer id);
	ArrayList<SkillModel> findBySkillName(String name);
	ArrayList<SkillModel> findBySkillAbilityScore(String abilityScore);

}
