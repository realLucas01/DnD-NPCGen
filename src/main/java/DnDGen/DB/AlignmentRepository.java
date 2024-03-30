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

public interface AlignmentRepository extends CrudRepository<AlignmentModel, Integer>{
	
	long count();
	ArrayList<AlignmentModel> findByAlignmentId(Integer id);
	ArrayList<AlignmentModel> findByAlignmentName(String name);
	ArrayList<AlignmentModel> findByAlignmentShorted(String shorted);
}
