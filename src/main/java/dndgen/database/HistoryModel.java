/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der alignment Datenbank zu erstellen. 
 * Aktuell noch WIP da die Atrribute der database noch nicht feststehen.
 * 
 */

package dndgen.database;

import java.time.LocalDateTime;
import java.util.ArrayList;

import dndgen.database.Sortierung.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class HistoryModel {
	@Id
	private Integer historyId;
	private Integer runVar;
	private String name;
	private Integer level;
	private String genClass;
	private String race;
	private String job;
	private Integer armorClass;
	private Integer healthPoints;
	private ArrayList<String> movementSpeedList;
	private ArrayList<String> languages;
	private ArrayList<Integer> abilityScores;
	private ArrayList<String> savingThrows;
	private String armor;
	private ArrayList<String> weaponList;
	private ArrayList<String> skillList;
	private String senses;
	@Lob
	@Column(name="spellList", length=51200)
	private ArrayList<String> spellList;
	private ArrayList<String> spellAbility;
	@Lob
	@Column(name="description", length=51200)
	private ArrayList<String> description;
	@Lob
	@Column(name="actionList", length=51200)
	private ArrayList<String> actionList;
	private ArrayList<String> bonusActionList;
	private Integer profBonus;
	private ArrayList<Integer> abilityModifier;
	private String gender;
	private Integer age;
	private String alignment;
	private String deity;
	@Lob
	@Column(name="bonusTextList", length=51200)
	private ArrayList<String> bonusTextList;
	private LocalDateTime entryage;

	
	public HistoryModel() {
	}

	public HistoryModel(Integer historyId, Integer runVar, String name, Integer level,
			String genClass, String race, String job, Integer armorClass, Integer healthPoints,
			ArrayList<String> movementSpeedList, ArrayList<String> languages, ArrayList<Integer> abilityScores,
			ArrayList<String> savingThrows, String armor, ArrayList<String> weaponList,
			ArrayList<String> skillList, String senses, ArrayList<String> spellList,
			ArrayList<String> spellAbility, ArrayList<String> description, ArrayList<String> actionList,
			ArrayList<String> bonusActionList, Integer profBonus, ArrayList<Integer> abilityModifier,
			String gender, Integer age, String alignment, String deity,
			ArrayList<String> bonusTextList, LocalDateTime entryage) {
		super();
		this.historyId = historyId;
		this.runVar = runVar;
		this.name = name;
		this.level = level;
		this.genClass = genClass;
		this.race = race;
		this.job = job;
		this.armorClass = armorClass;
		this.healthPoints = healthPoints;
		this.movementSpeedList = movementSpeedList;
		this.languages = languages;
		this.abilityScores = abilityScores;
		this.savingThrows = savingThrows;
		this.armor = armor;
		this.weaponList = weaponList;
		this.skillList = skillList;
		this.senses = senses;
		this.spellList = spellList;
		this.spellAbility = spellAbility;
		this.description = description;
		this.actionList = actionList;
		this.bonusActionList = bonusActionList;
		this.profBonus = profBonus;
		this.abilityModifier = abilityModifier;
		this.gender = gender;
		this.age = age;
		this.alignment = alignment;
		this.deity = deity;
		this.bonusTextList = bonusTextList;
		this.entryage = entryage;
	}

	@Order(value = 30)
	public Integer getId() {
		return historyId;
	}
	public void setId(Integer historyId) {
		this.historyId = historyId;
	}
	@Order(value = 31)
	public Integer getRunVar() {
		return runVar;
	}
	public void setRunVar(Integer runVar) {
		this.runVar = runVar;
	}
	@Order(value = 32)
	public String getName() {
		return name;
	}
	@Order(value = 1)
	public void setName(String name) {
		this.name = name;
	}
	@Order(value = 33)
	public Integer getLevel() {
		return level;
	}
	@Order(value = 2)
	public void setLevel(Integer level) {
		this.level = level;
	}
	@Order(value = 34)
	public String getGenClass() {
		return genClass;
	}
	@Order(value = 3)
	public void setGenClass(String genClass) {
		this.genClass = genClass;
	}
	@Order(value = 35)
	public String getRace() {
		return race;
	}
	@Order(value = 4)
	public void setRace(String race) {
		this.race = race;
	}
	@Order(value = 36)
	public String getJob() {
		return job;
	}
	@Order(value = 5)
	public void setJob(String job) {
		this.job = job;
	}
	@Order(value = 37)
	public Integer getArmorClass() {
		return armorClass;
	}
	@Order(value = 6)
	public void setArmorClass(Integer armorClass) {
		this.armorClass = armorClass;
	}
	@Order(value = 38)
	public Integer getHealthPoints() {
		return healthPoints;
	}
	@Order(value = 7)
	public void setHealthPoints(Integer healthPoints) {
		this.healthPoints = healthPoints;
	}
	@Order(value = 39)
	public ArrayList<String> getMovementSpeedList() {
		return movementSpeedList;
	}
	@Order(value = 8)
	public void setMovementSpeedList(ArrayList<String> movementSpeedList) {
		this.movementSpeedList = movementSpeedList;
	}
	@Order(value = 40)
	public ArrayList<String> getLanguages() {
		return languages;
	}
	@Order(value = 9)
	public void setLanguages(ArrayList<String> languages) {
		this.languages = languages;
	}
	@Order(value = 41)
	public ArrayList<Integer> getAbilityScores() {
		return abilityScores;
	}
	@Order(value = 10)
	public void setAbilityScores(ArrayList<Integer> abilityScores) {
		this.abilityScores = abilityScores;
	}
	@Order(value = 42)
	public ArrayList<String> getSavingThrows() {
		return savingThrows;
	}
	@Order(value = 11)
	public void setSavingThrows(ArrayList<String> savingThrows) {
		this.savingThrows = savingThrows;
	}
	@Order(value = 43)
	public String getArmor() {
		return armor;
	}
	@Order(value = 12)
	public void setArmor(String armor) {
		this.armor = armor;
	}
	@Order(value = 44)
	public ArrayList<String> getWeaponList() {
		return weaponList;
	}
	@Order(value = 13)
	public void setWeaponList(ArrayList<String> weaponList) {
		this.weaponList = weaponList;
	}
	@Order(value = 45)
	public ArrayList<String> getSkillList() {
		return skillList;
	}
	@Order(value = 14)
	public void setSkillList(ArrayList<String> skillList) {
		this.skillList = skillList;
	}
	@Order(value = 46)
	public String getSenses() {
		return senses;
	}
	@Order(value = 15)
	public void setSenses(String senses) {
		this.senses = senses;
	}
	@Order(value = 47)
	public ArrayList<String> getSpellList() {
		return spellList;
	}
	@Order(value = 16)
	public void setSpellList(ArrayList<String> spellList) {
		this.spellList = spellList;
	}
	@Order(value = 48)
	public ArrayList<String> getSpellAbility() {
		return spellAbility;
	}
	@Order(value = 17)
	public void setSpellAbility(ArrayList<String> spellAbility) {
		this.spellAbility = spellAbility;
	}
	@Order(value = 49)
	public ArrayList<String> getDescription() {
		return description;
	}
	@Order(value = 18)
	public void setDescription(ArrayList<String> description) {
		this.description = description;
	}
	@Order(value = 50)
	public ArrayList<String> getActionList() {
		return actionList;
	}
	@Order(value = 19)
	public void setActionList(ArrayList<String> actionList) {
		this.actionList = actionList;
	}
	@Order(value = 51)
	public ArrayList<String> getBonusActionList() {
		return bonusActionList;
	}
	@Order(value = 20)
	public void setBonusActionList(ArrayList<String> bonusActionList) {
		this.bonusActionList = bonusActionList;
	}
	@Order(value = 52)
	public Integer getProfBonus() {
		return profBonus;
	}
	@Order(value = 21)
	public void setProfBonus(Integer profBonus) {
		this.profBonus = profBonus;
	}
	@Order(value = 53)
	public ArrayList<Integer> getAbilityModifier() {
		return abilityModifier;
	}
	@Order(value = 22)
	public void setAbilityModifier(ArrayList<Integer> abilityModifier) {
		this.abilityModifier = abilityModifier;
	}
	@Order(value = 54)
	public String getGender() {
		return gender;
	}
	@Order(value = 23)
	public void setGender(String gender) {
		this.gender = gender;
	}
	@Order(value = 55)
	public Integer getAge() {
		return age;
	}
	@Order(value = 24)
	public void setAge(Integer age) {
		this.age = age;
	}
	@Order(value = 56)
	public String getAlignment() {
		return alignment;
	}
	@Order(value = 25)
	public void setAlignment(String alignment) {
		this.alignment = alignment;
	}
	@Order(value = 57)
	public String getDeity() {
		return deity;
	}
	@Order(value = 26)
	public void setDeity(String deity) {
		this.deity = deity;
	}
	@Order(value = 58)
	public ArrayList<String> getBonusTextList() {
		return bonusTextList;
	}
	@Order(value = 27)
	public void setBonusTextList(ArrayList<String> bonusTextList) {
		this.bonusTextList = bonusTextList;
	}
	@Order(value = 59)
	public LocalDateTime getEntryAge() {
		return entryage;
	}
	public void setEntryAge(LocalDateTime entryage) {
		this.entryage = entryage;
	}
	@Override
	public String toString() {
		return "HistoryModel [historyId=" + historyId + ", runVar=" + runVar + ", name=" + name
				+ ", level=" + level + ", genClass=" + genClass + ", race=" + race
				+ ", job=" + job + ", armorClass=" + armorClass + ", healthPoints=" + healthPoints
				+ ", movementSpeedList=" + movementSpeedList + ", languages=" + languages + ", abilityScores="
				+ abilityScores + ", savingThrows=" + savingThrows + ", armor=" + armor
				+ ", weaponList=" + weaponList + ", skillList=" + skillList + ", senses="
				+ senses + ", spellList=" + spellList + ", spellAbility=" + spellAbility
				+ ", description=" + description + ", actionList=" + actionList + ", bonusActionList="
				+ bonusActionList + ", profBonus=" + profBonus + ", entryage=" + entryage
				+ "]";
	}
}
