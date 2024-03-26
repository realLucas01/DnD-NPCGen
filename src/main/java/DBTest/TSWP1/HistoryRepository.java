package DBTest.TSWP1;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface HistoryRepository extends CrudRepository<HistoryModel, Integer>{
	ArrayList<HistoryModel> findByHistoryFirstName(String firstName);
	ArrayList<HistoryModel> findByHistoryLastName(String lastName);
	ArrayList<HistoryModel> findByHistoryOccurance(String occurance);
	ArrayList<HistoryModel> findByRunVar(Integer runVar);
	@Modifying
	@Query("update HistoryModel u set u.historyFirstName = ?1, u.historyLastName = ?2, u.historyOccurance = ?3 where u.historyId = ?4")
	void setNameInfoById(String historyFirstName, String historyLastName, String historyOccurance, Integer historyId);
	long count();
}
