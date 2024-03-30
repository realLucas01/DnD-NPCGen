/* @author Lucas
 * 
 * Definiert die (Custom) Funktionen die über der dazugehörigen DB bzw iherem Model
 * ausgeführt werden können und definiert teilweise auch die dafür nötigen Spring Data JPA Querrys.
 *
 */

package DnDGen.DB;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ArmorRepository extends CrudRepository<ArmorModel, Integer>{
	
	long count();
	ArrayList<ArmorModel> findByArmorId(Integer id);
	ArrayList<ArmorModel> findByArmorName(String name);
	ArrayList<ArmorModel> findByArmorKind(String kind);
	ArrayList<ArmorModel> findByArmorAc(String ac);
	ArrayList<ArmorModel> findByArmorMax(String max);
	ArrayList<ArmorModel> findByArmorBonus(String bonus);
	ArrayList<ArmorModel> findByArmorStrength(String strength);
	ArrayList<ArmorModel> findByArmorStealth(String stealth);
	@Query("SELECT p FROM ArmorModel p WHERE p.armorName = ?1")
	Page<ArmorModel> getArmorPref(String armorName,Pageable pageable2);
}
