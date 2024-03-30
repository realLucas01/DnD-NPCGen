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

public interface JobRepository extends CrudRepository<JobModel, Integer>{
	
	long count();
	ArrayList<JobModel> findByJobId(Integer id);
	JobModel findByJobName(String name);
	ArrayList<JobModel> findByJobSkills(String skills);
	ArrayList<JobModel> findByJobLanguage(String language);
	Page<JobModel> findAll(Pageable pageable);
	@Query("SELECT s.jobName FROM JobModel s")
	ArrayList<String> getallJobCol();
}
