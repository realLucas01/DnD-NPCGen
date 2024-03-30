/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Skill Datenbank zu erstellen. 
 *
 */

package DnDGen.DB;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class SkillModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer skillId;
	private String skillName;
	private String skillAbilityScore;
	
	public SkillModel() {
		
	}
	public SkillModel(String skillName, String skillAbilityScore) {
		super();
		this.skillName = skillName;
		this.skillAbilityScore = skillAbilityScore;
	}
	public Integer getSkillId() {
		return skillId;
	}
	public void setSkillId(Integer skillId) {
		this.skillId = skillId;
	}
	public String getSkillName() {
		return skillName;
	}
	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}
	public String getSkillAbilityScore() {
		return skillAbilityScore;
	}
	public void setSkillAbilityScore(String skillAbilityScore) {
		this.skillAbilityScore = skillAbilityScore;
	}
	@Override
	public String toString() {
		return "SkillModel [skillId=" + skillId + ", skillName=" + skillName + ", skillAbilityScore="
				+ skillAbilityScore + "]";
	}
}
