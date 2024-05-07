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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface RaceRepository extends CrudRepository<RaceModel, Integer>{
	
	long count();
	ArrayList<RaceModel> findByRaceId(Integer id);
	RaceModel findByRaceName(String name);
	ArrayList<RaceModel> findByRaceAbilityScore(String abilityScore);
	ArrayList<RaceModel> findByRaceFeatures(String features);
	ArrayList<RaceModel> findByRaceAgeAdult(Integer ageAdult);
	ArrayList<RaceModel> findByRaceAgeMax(Integer ageMax);
	ArrayList<RaceModel> findByRaceSkills(String skills);
	ArrayList<RaceModel> findByRaceHeight(String height);
	ArrayList<RaceModel> findByRaceSize(String size);
	ArrayList<RaceModel> findByRaceSpeed(String speed);
	ArrayList<RaceModel> findByRaceLanguages(String languages);
	ArrayList<RaceModel> findByRaceCreatureType(String creatureType);
	@Query("SELECT s.raceName FROM RaceModel s")
	ArrayList<String> getallRaceCol();
	@Query("SELECT p FROM RaceModel p WHERE p.raceName = ?1")
	Page<RaceModel> getRacePref(String raceName,Pageable pageable2);
	Page<RaceModel> findAll(Pageable pageable);
	
}
