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
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface WeaponRepository extends CrudRepository<WeaponModel, Integer>{
	
	long count();
	ArrayList<WeaponModel> findByWeaponId(Integer id);
	WeaponModel findByWeaponName(String name);
	ArrayList<WeaponModel> findByWeaponProperty(String property);
	ArrayList<WeaponModel> findByWeaponDamageType(String damageType);
	ArrayList<WeaponModel> findByWeaponDamageDice(String damageDice);
	ArrayList<WeaponModel> findByWeaponKind(String kind);
	@Query("SELECT p FROM WeaponModel p WHERE p.weaponKind = ?1")
	Page<WeaponModel> getWeaponPref(String weaponKind,Pageable pageable);
	@Query("SELECT l.weaponName FROM WeaponModel l WHERE l.weaponKind Like %?1%")
	ArrayList<String> getWeaponCol(String weaponKind);
}
