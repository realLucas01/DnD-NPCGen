/* @author Lucas
 * 
 * Definiert die (Custom) Funktionen die über der dazugehörigen database bzw iherem Model
 * ausgeführt werden können.
 *
 */

package dndgen.database;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SkillRepository extends CrudRepository<SkillModel, Integer>{
	
	long count();
	ArrayList<SkillModel> findBySkillId(Integer id);
	@Query("SELECT m FROM SkillModel m WHERE m.skillName Like %?1%")
	SkillModel findBySkillName(String name);
	ArrayList<SkillModel> findBySkillAbilityScore(String abilityScore);
	@Query("SELECT s.skillName FROM SkillModel s")
	ArrayList<String> getallSkillCol();
}
