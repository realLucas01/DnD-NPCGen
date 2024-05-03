/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der SpellSlots Datenbank zu erstellen. 
 *
 */

package DnDGen.DB;

import java.util.Arrays;
import java.util.List;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SpellSlotsModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer spellSlotsId;
	private String spellSlotsClass;
	private String spellSlotsCantrips;
	private String spellSlots1Level;
	private String spellSlots2Level;
	private String spellSlots3Level;
	private String spellSlots4Level;
	private String spellSlots5Level;
	private String spellSlots6Level;
	private String spellSlots7Level;
	private String spellSlots8Level;
	private String spellSlots9Level;
	
	public SpellSlotsModel() {
		
	}
	public SpellSlotsModel(String spellSlotsClass, String spellSlotsCantrips, String spellSlots1Level,
			String spellSlots2Level, String spellSlots3Level, String spellSlots4Level,
			String spellSlots5Level, String spellSlots6Level, String spellSlots7Level,
			String spellSlots8Level, String spellSlots9Level) {
		super();
		this.spellSlotsClass = spellSlotsClass;
		this.spellSlotsCantrips = spellSlotsCantrips;
		this.spellSlots1Level = spellSlots1Level;
		this.spellSlots2Level = spellSlots2Level;
		this.spellSlots3Level = spellSlots3Level;
		this.spellSlots4Level = spellSlots4Level;
		this.spellSlots5Level = spellSlots5Level;
		this.spellSlots6Level = spellSlots6Level;
		this.spellSlots7Level = spellSlots7Level;
		this.spellSlots8Level = spellSlots8Level;
		this.spellSlots9Level = spellSlots9Level;
	}
	public Integer getSpellSlotsId() {
		return spellSlotsId;
	}
	public void setSpellSlotsId(Integer spellSlotsId) {
		this.spellSlotsId = spellSlotsId;
	}
	public String getSpellSlotsClass() {
		return spellSlotsClass;
	}
	public void setSpellSlotsClass(String spellSlotsClass) {
		this.spellSlotsClass = spellSlotsClass;
	}
	public String getSpellSlotsCantrips() {
		return spellSlotsCantrips;
	}
	public void setSpellSlotsCantrips(String spellSlotsCantrips) {
		this.spellSlotsCantrips = spellSlotsCantrips;
	}
	public String getSpellSlots1Level() {
		return spellSlots1Level;
	}
	public void setSpellSlots1Level(String spellSlots1Level) {
		this.spellSlots1Level = spellSlots1Level;
	}
	public String getSpellSlots2Level() {
		return spellSlots2Level;
	}
	public void setSpellSlots2Level(String spellSlots2Level) {
		this.spellSlots2Level = spellSlots2Level;
	}
	public String getSpellSlots3Level() {
		return spellSlots3Level;
	}
	public void setSpellSlots3Level(String spellSlots3Level) {
		this.spellSlots3Level = spellSlots3Level;
	}
	public String getSpellSlots4Level() {
		return spellSlots4Level;
	}
	public void setSpellSlots4Level(String spellSlots4Level) {
		this.spellSlots4Level = spellSlots4Level;
	}
	public String getSpellSlots5Level() {
		return spellSlots5Level;
	}
	public void setSpellSlots5Level(String spellSlots5Level) {
		this.spellSlots5Level = spellSlots5Level;
	}
	public String getSpellSlots6Level() {
		return spellSlots6Level;
	}
	public void setSpellSlots6Level(String spellSlots6Level) {
		this.spellSlots6Level = spellSlots6Level;
	}
	public String getSpellSlots7Level() {
		return spellSlots7Level;
	}
	public void setSpellSlots7Level(String spellSlots7Level) {
		this.spellSlots7Level = spellSlots7Level;
	}
	public String getSpellSlots8Level() {
		return spellSlots8Level;
	}
	public void setSpellSlots8Level(String spellSlots8Level) {
		this.spellSlots8Level = spellSlots8Level;
	}
	public String getSpellSlots9Level() {
		return spellSlots9Level;
	}
	public void setSpellSlots9Level(String spellSlots9Level) {
		this.spellSlots9Level = spellSlots9Level;
	}
	@Override
	public String toString() {
		return "SpellSlotsModel [spellSlotsId=" + spellSlotsId + ", spellSlotsClass=" + spellSlotsClass
				+ ", spellSlotsCantrips=" + spellSlotsCantrips + ", spellSlots1Level=" + spellSlots1Level
				+ ", spellSlots2Level=" + spellSlots2Level + ", spellSlots3Level=" + spellSlots3Level
				+ ", spellSlots4Level=" + spellSlots4Level + ", spellSlots5Level=" + spellSlots5Level
				+ ", spellSlots6Level=" + spellSlots6Level + ", spellSlots7Level=" + spellSlots7Level
				+ ", spellSlots8Level=" + spellSlots8Level + ", spellSlots9Level=" + spellSlots9Level + "]";
	}
}
