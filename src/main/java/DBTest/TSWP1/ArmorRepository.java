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
	ArrayList<ArmorModel> findByArmorAC(Integer ac);
	ArrayList<ArmorModel> findByArmorRmax(String rMax);
	@Query("SELECT s.armorName AS ArmorName FROM ArmorModel s")
	List<ColOnly> getallFirstNames();
	long count();
	Page<ArmorModel> findAll(Pageable pageable);
}
