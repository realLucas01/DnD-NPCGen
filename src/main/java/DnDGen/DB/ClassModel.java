/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Class Datenbank zu erstellen. 
 *
 */

package DnDGen.DB;

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
	private String classHitDice;
	private String classProfWeapon;
	private String classProfArmor;
	private String classSavingThrows;
	private String classSkills;
	private Integer classSkillNumber;
	private String classAbilityScoreDistribution;
	private String classCantrips;
	private String classSpells;
	private String classSpellAbilityScore;
	
	public ClassModel() {
		
	}
	public ClassModel(String className, String classHitDice, String classProfWeapon, String classProfArmor,
			String classSavingThrows, String classSkills, Integer classSkillNumber,
			String classAbilityScoreDistribution, String classCantrips, String classSpells,
			String classSpellAbilityScore) {
		this.className = className;
		this.classHitDice = classHitDice;
		this.classProfWeapon = classProfWeapon;
		this.classProfArmor = classProfArmor;
		this.classSavingThrows = classSavingThrows;
		this.classSkills = classSkills;
		this.classSkillNumber = classSkillNumber;
		this.classAbilityScoreDistribution = classAbilityScoreDistribution;
		this.classCantrips = classCantrips;
		this.classSpells = classSpells;
		this.classSpellAbilityScore = classSpellAbilityScore;
	}
	public Integer getClassId() {
		return classId;
	}
	public void setClassId(Integer classId) {
		this.classId = classId;
	}
	public String getClassName() {
		return className;
	}
	public void setClassName(String className) {
		this.className = className;
	}
	public String getClassHitDice() {
		return classHitDice;
	}
	public void setClassHitDice(String classHitDice) {
		this.classHitDice = classHitDice;
	}
	public String getClassProfWeapon() {
		return classProfWeapon;
	}
	public void setClassProfWeapon(String classProfWeapon) {
		this.classProfWeapon = classProfWeapon;
	}
	public String getClassProfArmor() {
		return classProfArmor;
	}
	public void setClassProfArmor(String classProfArmor) {
		this.classProfArmor = classProfArmor;
	}
	public String getClassSavingThrows() {
		return classSavingThrows;
	}
	public void setClassSavingThrows(String classSavingThrows) {
		this.classSavingThrows = classSavingThrows;
	}
	public String getClassSkills() {
		return classSkills;
	}
	public void setClassSkills(String classSkills) {
		this.classSkills = classSkills;
	}
	public Integer getClassSkillNumber() {
		return classSkillNumber;
	}
	public void setClassSkillNumber(Integer classSkillNumber) {
		this.classSkillNumber = classSkillNumber;
	}
	public String getClassAbilityScoreDistribution() {
		return classAbilityScoreDistribution;
	}
	public void setClassAbilityScoreDistribution(String classAbilityScoreDistribution) {
		this.classAbilityScoreDistribution = classAbilityScoreDistribution;
	}
	public String getClassCantrips() {
		return classCantrips;
	}
	public void setClassCantrips(String classCantrips) {
		this.classCantrips = classCantrips;
	}
	public String getClassSpells() {
		return classSpells;
	}
	public void setClassSpells(String classSpells) {
		this.classSpells = classSpells;
	}
	public String getClassSpellAbilityScore() {
		return classSpellAbilityScore;
	}
	public void setClassSpellAbilityScore(String classSpellAbilityScore) {
		this.classSpellAbilityScore = classSpellAbilityScore;
	}
	@Override
	public String toString() {
		return "ClassModel [classId=" + classId + ", className=" + className + ", classHitDice=" + classHitDice
				+ ", classProfWeapon=" + classProfWeapon + ", classProfArmor=" + classProfArmor + ", classSavingThrows="
				+ classSavingThrows + ", classSkills=" + classSkills + ", classSkillNumber=" + classSkillNumber
				+ ", classAbilityScoreDistribution=" + classAbilityScoreDistribution + ", classCantrips="
				+ classCantrips + ", classSpells=" + classSpells + ", classSpellAbilityScore=" + classSpellAbilityScore
				+ "]";
	}
}
