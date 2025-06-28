/* @author Lucas
 * 
 * Definiert die (Custom) Funktionen die über der dazugehörigen database bzw iherem Model
 * ausgeführt werden können.
 * So wie der Aufbau der database ist auch dieses Interface noch stark WIP.
 *
 */

package dndgen.database;

import java.util.ArrayList;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface HistoryRepository extends CrudRepository<HistoryModel, Integer>{
	
	long count();
	HistoryModel findByHistoryId(Integer id);
	ArrayList<HistoryModel> findByRunVar(Integer runVar);
	@Transactional
	@Modifying
	@Query("update HistoryModel u set u.runVar = ?1 where u.Id = ?2")
	void setRuVaManuel(Integer runVar, Integer Id);
	@Query("SELECT n FROM HistoryModel n WHERE NOT n.Id=13")
	ArrayList<HistoryModel> findEverythingExceptCounter();
}
