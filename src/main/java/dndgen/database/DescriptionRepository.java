/* @author Lucas
 * 
 * Definiert die (Custom) Funktionen die über der dazugehörigen database bzw iherem Model
 * ausgeführt werden können.
 * Da die Strucktur der database noch nicht existiert ist auch dieses Modell der database extrem WIP.
 *
 */

package dndgen.database;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

public interface DescriptionRepository extends CrudRepository<DescriptionModel, Integer>{
	
	long count();
	ArrayList<DescriptionModel> findByDescriptionId(Integer id);
	ArrayList<DescriptionModel> findByDescriptionEyes(String eyes);
	ArrayList<DescriptionModel> findByDescriptionHairColor(String hairColor);
	ArrayList<DescriptionModel> findByDescriptionHairStyle(String hairStyle);
	ArrayList<DescriptionModel> findByDescriptionSkin(String skin);
	ArrayList<DescriptionModel> findByDescriptionTrait(String trait);
	ArrayList<DescriptionModel> findByDescriptionMannerism(String mannerism);
	ArrayList<DescriptionModel> findByDescriptionFlaw(String flaw);
	ArrayList<DescriptionModel> findBydescriptionIdeal(String ideal);
	ArrayList<DescriptionModel> findBydescriptionVoice(String voice);
	ArrayList<DescriptionModel> findBydescriptionClothing(String clothing);
	ArrayList<DescriptionModel> findBydescriptionAttitute(String attitute);
	ArrayList<DescriptionModel> findBydescriptionPlothook(String plothook);
	Page<DescriptionModel> findAll(Pageable pageable);
}
