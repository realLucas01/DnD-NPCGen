package DnDGen.DB;

import java.util.ArrayList;

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
}
