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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface LanguageRepository extends CrudRepository<LanguageModel, Integer>{
	
	long count();
	ArrayList<LanguageModel> findByLanguageId(Integer id);
	ArrayList<LanguageModel> findByLanguageName(String name);
	Page<LanguageModel> findAll(Pageable pageable);
	@Query("SELECT s.languageName FROM LanguageModel s")
	ArrayList<String> getallLangCol();
}
