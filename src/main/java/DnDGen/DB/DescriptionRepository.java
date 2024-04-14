/* @author Lucas
 * 
 * Definiert die (Custom) Funktionen die über der dazugehörigen DB bzw iherem Model
 * ausgeführt werden können.
 * Da die Strucktur der DB noch nicht existiert ist auch dieses Modell der DB extrem WIP.
 *
 */

package DnDGen.DB;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface DescriptionRepository extends CrudRepository<DescriptionModel, Integer>{
	
	long count();
	ArrayList<DescriptionModel> findByDescriptionId(Integer id);
	ArrayList<DescriptionModel> findByDescriptionEyes(String eyes);
	ArrayList<DescriptionModel> findBydescriptionHairColor(String hairColor);
	ArrayList<DescriptionModel> findBydescriptionHairStyle(String hairStyle);
	ArrayList<DescriptionModel> findBydescriptionSkin(String skin);
	ArrayList<DescriptionModel> findBydescriptionTrait(String trait);
	ArrayList<DescriptionModel> findBydescriptionMannerism(String mannerism);
	ArrayList<DescriptionModel> findBydescriptionBond(String bond);
	Page<RaceModel> findAll(Pageable pageable);
}
