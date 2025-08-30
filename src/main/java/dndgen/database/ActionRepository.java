package dndgen.database;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ActionRepository extends CrudRepository<ActionModel, Integer>{

	long count();
	ArrayList<ActionModel> findByActionId(Integer id);
	ArrayList<ActionModel> findByActionName(String name);
	ArrayList<ActionModel> findByActionDescription(String beschreibung);
	ArrayList<ActionModel> findByActionClass(String klasse);
	ArrayList<ActionModel> findByActionLevel(Integer level);
	ArrayList<ActionModel> findByActionType(String art);
	@Query("SELECT l FROM ActionModel l WHERE l.actionClass = ?1 AND l.actionLevel = ?2 AND l.actionType = ?3")
	ArrayList<ActionModel> findByAllActionsWithPref(String actionClass,Integer actionLevel, String actionType);
	@Query("SELECT s.actionName FROM ActionModel s WHERE s.actionClass Like %?1%")
	ArrayList<String> getActionsCol(String Class);
	@Query("SELECT m FROM ActionModel m WHERE m.actionClass Like %?1% AND m.actionLevel = ?2")
	ArrayList<ActionModel> findByMultPrefsize(String actionClass,Integer actionLevel);
	@Query("SELECT n FROM ActionModel n WHERE n.actionClass Like %?1% AND n.actionLevel = ?2")
	Page<ActionModel> findByMultPrefdata(String actionClass,Integer actionLevel,Pageable pageable1);
}
