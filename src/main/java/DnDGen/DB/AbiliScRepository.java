/* @author Lucas
 * 
 * Definiert die (Custom) Funktionen die über der dazugehörigen DB bzw iherem Model
 * ausgeführt werden können.
 * So wie der Aufbau der DB ist auch dieses Interface noch WIP.
 *
 */

package DnDGen.DB;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface AbiliScRepository extends CrudRepository<AbiliScModel, Integer>{
	
	long count();
	ArrayList<AbiliScModel> findByAbiliScId(Integer id);
	ArrayList<AbiliScModel> findByAbiliScName(String name);
	ArrayList<AbiliScModel> findByAbiliScKind(String kind);
	ArrayList<AbiliScModel> findByAbiliScGenerationMethod(String generationMethod);
	@Query("SELECT s.abiliScGenerationMethod FROM AbiliScModel s")
	ArrayList<String> getallAbiliScCol();

}
