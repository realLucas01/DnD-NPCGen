/* @author Lucas
 * 
 * Definiert die (Custom) Funktionen die über der dazugehörigen database bzw iherem Model
 * ausgeführt werden können.
 *
 */

package dndgen.database;

import java.util.ArrayList;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface SpellKnownRepository extends CrudRepository<SpellKnownModel, Integer>{
	
	long count();
	ArrayList<SpellKnownModel> findBySpellKnownId(Integer id);
	SpellKnownModel findBySpellKnownClass(String classe);
	ArrayList<SpellKnownModel> findBySpellKnownKind(String kind);
	ArrayList<SpellKnownModel> findBySpellKnownStuff(String stuff);
	Page<SpellKnownModel> findAll(Pageable pageable);
	@Query("SELECT l FROM SpellKnownModel l WHERE l.spellKnownClass = ?1 AND l.spellKnownKind = ?2")
	ArrayList<SpellKnownModel> findByMultPrefsize(String spellKnownClass,String spellKnownKind);
	@Query("SELECT m FROM SpellKnownModel m WHERE m.spellKnownClass = ?1 AND m.spellKnownKind = ?2")
	Page<SpellKnownModel> findByMultPrefdata(String spellKnownClass,String spellKnownKind,Pageable pageable1);
}
