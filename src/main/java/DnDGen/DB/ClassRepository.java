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

public interface ClassRepository extends CrudRepository<ClassModel, Integer>{
	
	long count();
	ArrayList<ClassModel> findByClassId(Integer id);
	ClassModel findByClassName(String name);
	ArrayList<ClassModel> findByClassHitDice(Integer hitDice);
	ArrayList<ClassModel> findByClassProfWeapon(String profWeapon);
	ArrayList<ClassModel> findByClassProfArmor(String profArmor);
	ArrayList<ClassModel> findByClassSavingThrows(String savingThrows);
	ArrayList<ClassModel> findByClassSkills(String skills);
	ArrayList<ClassModel> findByClassSkillNumber(Integer skillNumber);
	ArrayList<ClassModel> findByClassAbilityScoreDistribution(String abilityScoreDistribution);
	ArrayList<ClassModel> findByClassSpellKnown(String spellKnown);
	ArrayList<ClassModel> findByClassFeatures(String features);
	@Query("SELECT s.className FROM ClassModel s")
	ArrayList<String> getallClassCol();
	Page<ClassModel> findAll(Pageable pageable);
	
}
