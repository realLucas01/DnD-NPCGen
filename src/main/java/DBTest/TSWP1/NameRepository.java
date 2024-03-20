package DBTest.TSWP1;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NameRepository extends CrudRepository<NameModel, Integer>{
	
	List<NameModel> findByNameFirstName(String firstName);
	List<NameModel> findByNameLastName(String lastName);
	List<NameModel> findByNameOccurance(String occurance);
	@Query("SELECT s.nameFirstName AS FirstName FROM NameModel s")
	List<RowOnly> getallFirstNames();
	long count();
	Page<NameModel> findAll(Pageable pageable);
}