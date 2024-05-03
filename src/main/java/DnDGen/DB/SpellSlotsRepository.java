/* @author Lucas
 * 
 * Definiert die (Custom) Funktionen die über der dazugehörigen DB bzw iherem Model
 * ausgeführt werden können.
 *
 */

package DnDGen.DB;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import jakarta.persistence.TypedQuery;

public interface SpellSlotsRepository extends CrudRepository<SpellSlotsModel, Integer>{
	
	long count();
	ArrayList<SpellSlotsModel> findBySpellSlotsId(Integer id);
	SpellSlotsModel findBySpellSlotsClass(String klasse);
	ArrayList<SpellSlotsModel> findBySpellSlotsCantrips(String cantrip);
	ArrayList<SpellSlotsModel> findBySpellSlots1Level(String level1);
	ArrayList<SpellSlotsModel> findBySpellSlots2Level(String level2);
	ArrayList<SpellSlotsModel> findBySpellSlots3Level(String level3);
	ArrayList<SpellSlotsModel> findBySpellSlots4Level(String level4);
	ArrayList<SpellSlotsModel> findBySpellSlots5Level(String level5);
	ArrayList<SpellSlotsModel> findBySpellSlots6Level(String level6);
	ArrayList<SpellSlotsModel> findBySpellSlots7Level(String level7);
	ArrayList<SpellSlotsModel> findBySpellSlots8Level(String level8);
	ArrayList<SpellSlotsModel> findBySpellSlots9Level(String level9);
	Page<SpellSlotsModel> findAll(Pageable pageable);
}

