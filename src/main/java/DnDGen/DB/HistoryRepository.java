/* @author Lucas
 * 
 * Definiert die (Custom) Funktionen die über der dazugehörigen DB bzw iherem Model
 * ausgeführt werden können.
 * So wie der Aufbau der DB ist auch dieses Interface noch stark WIP.
 *
 */

package DnDGen.DB;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

public interface HistoryRepository extends CrudRepository<HistoryModel, Integer>{
	
	long count();
	HistoryModel findByHistoryId(Integer id);
	ArrayList<HistoryModel> findByRunVar(Integer runVar);
	@Modifying
	@Transactional
	@Query("update HistoryModel u set u.runVar = ?1 where u.historyId = ?2")
	void setRuVaManuel(Integer runVar, Integer historyId);
}
