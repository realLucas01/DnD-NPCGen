/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Spell Datenbank zu erstellen. 
 *
 */

package DnDGen.DB;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SpellModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer spellId;
	private String spellName;
	private String spellLevel;
	private String spellSchool;
	private String spellCastingTime;
	private String spellRange;
	private String spellComponents;
	private String spellDuration;
	@Column(columnDefinition="TEXT")
	private String spellDescription;
	private String spellLevelUpScale;
	private String spellAvailability;
	
	public SpellModel() {
		
	}
	public SpellModel(String spellName, String spellLevel, String spellSchool, String spellCastingTime,
			String spellRange, String spellComponents, String spellDuration, String spellDescription,
			String spellLevelUpScale, String spellAvailability) {
		super();
		this.spellName = spellName;
		this.spellLevel = spellLevel;
		this.spellSchool = spellSchool;
		this.spellCastingTime = spellCastingTime;
		this.spellRange = spellRange;
		this.spellComponents = spellComponents;
		this.spellDuration = spellDuration;
		this.spellDescription = spellDescription;
		this.spellLevelUpScale = spellLevelUpScale;
		this.spellAvailability = spellAvailability;
	}
	public Integer getSpellId() {
		return spellId;
	}
	public void setSpellId(Integer spellId) {
		this.spellId = spellId;
	}
	public String getSpellName() {
		return spellName;
	}
	public void setSpellName(String spellName) {
		this.spellName = spellName;
	}
	public String getSpellLevel() {
		return spellLevel;
	}
	public void setSpellLevel(String spellLevel) {
		this.spellLevel = spellLevel;
	}
	public String getSpellSchool() {
		return spellSchool;
	}
	public void setSpellSchool(String spellSchool) {
		this.spellSchool = spellSchool;
	}
	public String getSpellCastingTime() {
		return spellCastingTime;
	}
	public void setSpellCastingTime(String spellCastingTime) {
		this.spellCastingTime = spellCastingTime;
	}
	public String getSpellRange() {
		return spellRange;
	}
	public void setSpellRange(String spellRange) {
		this.spellRange = spellRange;
	}
	public String getSpellComponents() {
		return spellComponents;
	}
	public void setSpellComponents(String spellComponents) {
		this.spellComponents = spellComponents;
	}
	public String getSpellDuration() {
		return spellDuration;
	}
	public void setSpellDuration(String spellDuration) {
		this.spellDuration = spellDuration;
	}
	public String getSpellDescription() {
		return spellDescription;
	}
	public void setSpellDescription(String spellDescription) {
		this.spellDescription = spellDescription;
	}
	public String getSpellLevelUpScale() {
		return spellLevelUpScale;
	}
	public void setSpellLevelUpScale(String spellLevelUpScale) {
		this.spellLevelUpScale = spellLevelUpScale;
	}
	public String getSpellAvailability() {
		return spellAvailability;
	}
	public void setSpellAvailability(String spellAvailability) {
		this.spellAvailability = spellAvailability;
	}
	@Override
	public String toString() {
		return "SpellModel [spellId=" + spellId + ", spellName=" + spellName + ", spellLevel=" + spellLevel
				+ ", spellSchool=" + spellSchool + ", spellCastingTime=" + spellCastingTime + ", spellRange="
				+ spellRange + ", spellComponents=" + spellComponents + ", spellDuration=" + spellDuration
				+ ", spellDescription=" + spellDescription + ", spellLevelUpScale=" + spellLevelUpScale
				+ ", spellAvailability=" + spellAvailability + "]";
	}
}
