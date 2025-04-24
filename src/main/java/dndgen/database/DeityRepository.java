/* @author Lucas
 * 
 * Definiert die (Custom) Funktionen die über der dazugehörigen database bzw iherem Model
 * ausgeführt werden können und definiert teilweise auch die dafür nötigen Spring Data JPA Querrys.
 *
 */

package dndgen.database;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DeityRepository extends CrudRepository<DeityModel, Integer>{
	
	long count();
	ArrayList<DeityModel> findByDeityId(Integer id);
	DeityModel findByDeityName(String name);
	ArrayList<DeityModel> findByDeityAlignment(String alignment);
	@Query("SELECT p FROM DeityModel p WHERE p.deityAlignment = ?1")
	Page<DeityModel> getDeityPref(String deityAlignment,Pageable pageable2);
	@Query("SELECT s.deityName FROM DeityModel s")
	ArrayList<String> getallDeityCol();
	@Query("SELECT l.deityName FROM DeityModel l WHERE l.deityAlignment = ?1")
	ArrayList<String> getDeityByAlignmentCol(String deityAlignment);
}
