/* @author Lucas
 * 
 * Definiert die (Custom) Funktionen die über der dazugehörigen DB bzw iherem Model
 * ausgeführt werden können und definiert teilweise auch die dafür nötigen Spring Data JPA Querrys.
 *
 */

package DnDGen.DB;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NameRepository extends CrudRepository<NameModel, Integer>{
	
	long count();
	ArrayList<NameModel> findByNameId(Integer id);
	ArrayList<NameModel> findByNameFirstName(String firstName);
	ArrayList<NameModel> findByNameLastName(String lastName);
	ArrayList<NameModel> findByNameRace(String race);
	ArrayList<NameModel> findByNameGender(String gender);

	Page<NameModel> findAll(Pageable pageable);
	@Query("SELECT p FROM NameModel p WHERE p.nameRace = ?1")
	Page<NameModel> getNamePref(String nameRace,Pageable pageable2);
}
