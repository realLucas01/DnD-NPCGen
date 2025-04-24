/* @author Lucas
 * 
 * Definiert die (Custom) Funktionen die über der dazugehörigen database bzw iherem Model
 * ausgeführt werden können.
 *
 */

package dndgen.database;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface DracAncesRepository extends CrudRepository<DracAncesModel, Integer>{
	
	long count();
	ArrayList<DracAncesModel> findByDraconicId(Integer id);
	DracAncesModel findByDraconicDragon(String dragon);
	ArrayList<DracAncesModel> findByDraconicDamageType(String damageType);
	ArrayList<DracAncesModel> findByDraconicBreathWeapon(String breathWeapon);
	Page<DracAncesModel> findAll(Pageable pageable);
}
