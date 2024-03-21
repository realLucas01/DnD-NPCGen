package DBTest.TSWP1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface NameRepository extends CrudRepository<NameModel, Integer>{
	
	ArrayList<NameModel> findByNameFirstName(String firstName);
	ArrayList<NameModel> findByNameLastName(String lastName);
	ArrayList<NameModel> findByNameOccurance(String occurance);
	@Query("SELECT s.nameFirstName AS FirstName FROM NameModel s")
	List<ColOnly> getallFirstNames();
	long count();
	Page<NameModel> findAll(Pageable pageable);
	@Modifying
	@Query("update NameModel u set u.nameFirstName = ?1, u.nameLastName = ?2, u.nameOccurance = ?3 where u.nameId = ?4")
	void setNameInfoById(String nameFirstName, String nameLastName, String nameOccurance, Integer nameId);
}