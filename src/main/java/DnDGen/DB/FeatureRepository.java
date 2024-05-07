/* @author Lucas
 * 
 * Definiert die (Custom) Funktionen die über der dazugehörigen DB bzw iherem Model
 * ausgeführt werden können.
 *
 */

package DnDGen.DB;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

public interface FeatureRepository extends CrudRepository<FeatureModel, Integer>{
	
	long count();
	ArrayList<FeatureModel> findByFeatureId(Integer id);
	FeatureModel findByFeatureName(String name);
	ArrayList<FeatureModel> findByFeatureDescription(String description);
}
