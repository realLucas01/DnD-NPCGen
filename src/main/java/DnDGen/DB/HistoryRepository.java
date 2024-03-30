/* @author Lucas
 * 
 * Definiert die (Custom) Funktionen die über der dazugehörigen DB bzw iherem Model
 * ausgeführt werden können.
 * So wie der Aufbau der DB ist auch dieses Interface noch stark WIP.
 *
 */

package DnDGen.DB;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface HistoryRepository extends CrudRepository<HistoryModel, Integer>{
	
	long count();
	ArrayList<HistoryModel> findByHistoryId(Integer id);
	ArrayList<HistoryModel> findByRunVar(Integer runVar);

}
