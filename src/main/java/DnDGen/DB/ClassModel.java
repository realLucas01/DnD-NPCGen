/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Class Datenbank zu erstellen. 
 *
 */

package DnDGen.DB;

import DnDGen.DB.Sortierung.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ClassModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer classId;
	private String className;
	private Integer classHitDice;
	private String classProfWeapon;
	private String classProfArmor;
	private String classSavingThrows;
	private String classSkills;
	private Integer classSkillNumber;
	private String classAbilityScoreDistribution;
	private String classSpellKnown;
	@Column(columnDefinition="TEXT")
	private String classFeatures;
	private String classSpellMod;
	
	public ClassModel() {
		
	}
	public ClassModel(String className, Integer classHitDice, String classProfWeapon, String classProfArmor,
			String classSavingThrows, String classSkills, Integer classSkillNumber,
			String classAbilityScoreDistribution, String classSpellKnown,
			String classFeatures,String classSpellMod) {
		this.className = className;
		this.classHitDice = classHitDice;
		this.classProfWeapon = classProfWeapon;
		this.classProfArmor = classProfArmor;
		this.classSavingThrows = classSavingThrows;
		this.classSkills = classSkills;
		this.classSkillNumber = classSkillNumber;
		this.classAbilityScoreDistribution = classAbilityScoreDistribution;
		this.classSpellKnown = classSpellKnown;
		this.classFeatures = classFeatures;
		this.classSpellMod = classSpellMod;
	}
	@Order(value = 1)
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	@Order(value = 2)
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	@Order(value = 3)
	public Integer getClassHitDice() {
		return classHitDice;
	}
	public void setClassHitDice(Integer classHitDice) {
		this.classHitDice = classHitDice;
	}
	@Order(value = 4)
	public String getClassProfWeapon() {
		return classProfWeapon;
	}
	public void setClassProfWeapon(String classProfWeapon) {
		this.classProfWeapon = classProfWeapon;
	}
	@Order(value = 5)
	public String getClassProfArmor() {
		return classProfArmor;
	}
	public void setClassProfArmor(String classProfArmor) {
		this.classProfArmor = classProfArmor;
	}
	@Order(value = 6)
	public String getClassSavingThrows() {
		return classSavingThrows;
	}
	public void setClassSavingThrows(String classSavingThrows) {
		this.classSavingThrows = classSavingThrows;
	}
	@Order(value = 7)
	public String getClassSkills() {
		return classSkills;
	}
	public void setClassSkills(String classSkills) {
		this.classSkills = classSkills;
	}
	@Order(value = 8)
	public Integer getClassSkillNumber() {
		return classSkillNumber;
	}
	public void setClassSkillNumber(Integer classSkillNumber) {
		this.classSkillNumber = classSkillNumber;
	}
	@Order(value = 9)
	public String getClassAbilityScoreDistribution() {
		return classAbilityScoreDistribution;
	}
	public void setClassAbilityScoreDistribution(String classAbilityScoreDistribution) {
		this.classAbilityScoreDistribution = classAbilityScoreDistribution;
	}
	@Order(value = 10)
	public String getClassSpellKnown() {
		return classSpellKnown;
	}
	public void setClassSpellKnown(String classSpellKnown) {
		this.classSpellKnown = classSpellKnown;
	}
	@Order(value = 11)
	public String getClassFeatures() {
		return classFeatures;
	}
	public void setClassFeatures(String classFeatures) {
		this.classFeatures = classFeatures;
	}
	@Order(value = 12)
	public String getClassSpellMod() {
		return classSpellMod;
	}
	public void setClassSpellMod(String classSpellMod) {
		this.classSpellMod = classSpellMod;
	}
	@Override
	public String toString() {
		return "ClassModel [classId=" + classId + ", className=" + className + ", classHitDice=" + classHitDice
				+ ", classProfWeapon=" + classProfWeapon + ", classProfArmor=" + classProfArmor + ", classSavingThrows="
				+ classSavingThrows + ", classSkills=" + classSkills + ", classSkillNumber=" + classSkillNumber
				+ ", classAbilityScoreDistribution=" + classAbilityScoreDistribution + ", classSpellKnown="
				+ classSpellKnown + ", classFeatures=" + classFeatures + ", classSpellMod=" + classSpellMod + "]";
	}
	
}
