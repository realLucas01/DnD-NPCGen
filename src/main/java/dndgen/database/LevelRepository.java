/* @author Lucas
 * 
 * Definiert die (Custom) Funktionen die über der dazugehörigen database bzw iherem Model
 * ausgeführt werden können.
 *
 */

package dndgen.database;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

public interface LevelRepository extends CrudRepository<LevelModel, Integer>{
	
	long count();
	ArrayList<LevelModel> findByLevelId(Integer id);
	LevelModel findByLevelNumber(Integer number);
	ArrayList<LevelModel> findByLevelProfBonus(Integer profBonus);
	
}
