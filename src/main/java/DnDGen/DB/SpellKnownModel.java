/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der SpellKnown Datenbank zu erstellen. 
 *
 */

package DnDGen.DB;

import DnDGen.DB.Sortierung.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SpellKnownModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer spellKnownId;
	private String spellKnownClass;
	private String spellKnownKind;
	private String spellKnownStuff;
	
	public SpellKnownModel() {
		
	}
	public SpellKnownModel(String spellKnownClass, String spellKnownKind, String spellKnownStuff) {
		super();
		this.spellKnownClass = spellKnownClass;
		this.spellKnownKind = spellKnownKind;
		this.spellKnownStuff = spellKnownStuff;
	}
	@Order(value = 1)
	public Integer getSpellKnownId() {
		return spellKnownId;
	}
	public void setSpellKnownId(Integer spellKnownId) {
		this.spellKnownId = spellKnownId;
	}
	@Order(value = 2)
	public String getSpellKnownClass() {
		return spellKnownClass;
	}
	public void setSpellKnownClass(String spellKnownClass) {
		this.spellKnownClass = spellKnownClass;
	}
	@Order(value = 3)
	public String getSpellKnownKind() {
		return spellKnownKind;
	}
	public void setSpellKnownKind(String spellKnownKind) {
		this.spellKnownKind = spellKnownKind;
	}
	@Order(value = 4)
	public String getSpellKnownStuff() {
		return spellKnownStuff;
	}
	public void setSpellKnownStuff(String spellKnownStuff) {
		this.spellKnownStuff = spellKnownStuff;
	}
	@Override
	public String toString() {
		return "SpellKnownModel [spellKnownId=" + spellKnownId + ", spellKnownClass=" + spellKnownClass
				+ ", spellKnownKind=" + spellKnownKind + ", spellKnownStuff=" + spellKnownStuff + "]";
	}
}
