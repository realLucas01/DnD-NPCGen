package DBTest.TSWP1;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface HistoryRepository extends CrudRepository<HistoryModel, Integer>{
	ArrayList<HistoryModel> findByHistoryFirstName(String firstName);
	ArrayList<HistoryModel> findByHistoryLastName(String lastName);
	ArrayList<HistoryModel> findByHistoryOccurance(String occurance);
	ArrayList<HistoryModel> findByHistoryId(Integer id);
	@Modifying
	@Query("update NameModel u set u.nameFirstName = ?1, u.nameLastName = ?2, u.nameOccurance = ?3 where u.nameId = ?4")
	void setNameInfoById(String nameFirstName, String nameLastName, String nameOccurance, Integer nameId);
	long count();
}
