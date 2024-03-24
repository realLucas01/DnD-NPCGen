package DBTest.TSWP1;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface ArmorRepository extends CrudRepository<ArmorModel, Integer>{
	
	ArrayList<ArmorModel> findByArmorName(String name);
	ArrayList<ArmorModel> findByArmorType(String type);
	ArrayList<ArmorModel> findByArmorAc(Integer ac);
	ArrayList<ArmorModel> findByArmorRmax(String rMax);
	ArrayList<ArmorModel> findByArmorId(Integer id);
	@Query("SELECT s.armorName AS ArmorName FROM ArmorModel s")
	List<ColOnly> getallArmortNames();
	long count();
	Page<ArmorModel> findAll(Pageable pageable);
	@Query("SELECT p FROM ArmorModel p WHERE p.armorType = ?1")
	Page<ArmorModel> findAllByArmorType(String armorType,Pageable pageable2);
	@Query("SELECT m FROM ArmorModel m WHERE m.armorType = ?1 AND m.armorAc = ?2")
	Page<ArmorModel> findAllByArmorTypeAndArmorAc(String armorType,Integer armorAc,Pageable pageable3);
	@Query("SELECT l FROM ArmorModel l WHERE l.armorType = ?1 AND l.armorAc = ?2")
	ArrayList<ArmorModel> findByMultPref(String armorType,Integer armorAc);
}
//WHERE armorType = 'Medium'
//p WHERE p.armorType = 'Medium'
//p.armorId, p.armorName, p.armorType, p.armorAC, p.armorRmax