/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Alignment Datenbank zu erstellen. 
 * Aktuell noch WIP da die Atrribute der DB noch nicht feststehen.
 * 
 */

package DnDGen.DB;

import java.time.LocalDateTime;
import java.util.ArrayList;

import DnDGen.DB.Sortierung.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;

@Entity
public class HistoryModel {
	@Id
	private Integer historyId;
	private Integer runVar;
	private String historyName;
	private Integer historyLevel;
	private String historyClass;
	private String historyRace;
	private String historyJob;
	private Integer historyAc;
	private Integer historyHp;
	private ArrayList<String> historySpeed;
	private ArrayList<String> historyLanguages;
	private ArrayList<Integer> historyAbiliSc;
	private ArrayList<String> historySavingTh;
	private String historyArmor;
	private ArrayList<String> historyWeapon;
	private ArrayList<String> historySkills;
	private String historySenses;
	@Lob
	@Column(name="historySpells", length=51200)
	private ArrayList<String> historySpells;
	private ArrayList<String> historySpellAb;
	@Lob
	@Column(name="historyDesc", length=51200)
	private ArrayList<String> historyDesc;
	@Lob
	@Column(name="historyActions", length=51200)
	private ArrayList<String> historyActions;
	private ArrayList<String> historyBonusAct;
	private Integer historyProfBonus;
	private ArrayList<Integer> historyAbiliMod;
	private String historyGender;
	private Integer historyAge;
	private String historyAlignment;
	private String historyDeity;
	@Lob
	@Column(name="historyBonusTextList", length=51200)
	private ArrayList<String> historyBonusTextList;
	private LocalDateTime historyEntryAge;

	
	public HistoryModel() {
	}

	public HistoryModel(Integer historyId, Integer runVar, String historyName, Integer historyLevel,
			String historyClass, String historyRace, String historyJob, Integer historyAc, Integer historyHp,
			ArrayList<String> historySpeed, ArrayList<String> historyLanguages, ArrayList<Integer> historyAbiliSc,
			ArrayList<String> historySavingTh, String historyArmor, ArrayList<String> historyWeapon,
			ArrayList<String> historySkills, String historySenses, ArrayList<String> historySpells,
			ArrayList<String> historySpellAb, ArrayList<String> historyDesc, ArrayList<String> historyActions,
			ArrayList<String> historyBonusAct, Integer historyProfBonus, ArrayList<Integer> historyAbiliMod,
			String historyGender, Integer historyAge, String historyAlignment, String historyDeity,
			ArrayList<String> historyBonusTextList, LocalDateTime historyEntryAge) {
		super();
		this.historyId = historyId;
		this.runVar = runVar;
		this.historyName = historyName;
		this.historyLevel = historyLevel;
		this.historyClass = historyClass;
		this.historyRace = historyRace;
		this.historyJob = historyJob;
		this.historyAc = historyAc;
		this.historyHp = historyHp;
		this.historySpeed = historySpeed;
		this.historyLanguages = historyLanguages;
		this.historyAbiliSc = historyAbiliSc;
		this.historySavingTh = historySavingTh;
		this.historyArmor = historyArmor;
		this.historyWeapon = historyWeapon;
		this.historySkills = historySkills;
		this.historySenses = historySenses;
		this.historySpells = historySpells;
		this.historySpellAb = historySpellAb;
		this.historyDesc = historyDesc;
		this.historyActions = historyActions;
		this.historyBonusAct = historyBonusAct;
		this.historyProfBonus = historyProfBonus;
		this.historyAbiliMod = historyAbiliMod;
		this.historyGender = historyGender;
		this.historyAge = historyAge;
		this.historyAlignment = historyAlignment;
		this.historyDeity = historyDeity;
		this.historyBonusTextList = historyBonusTextList;
		this.historyEntryAge = historyEntryAge;
	}

	@Order(value = 30)
	public Integer getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Integer historyId) {
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
	public String getHistoryName() {
		return historyName;
	}
	@Order(value = 1)
	public void setHistoryName(String historyName) {
		this.historyName = historyName;
	}
	@Order(value = 33)
	public Integer getHistoryLevel() {
		return historyLevel;
	}
	@Order(value = 2)
	public void setHistoryLevel(Integer historyLevel) {
		this.historyLevel = historyLevel;
	}
	@Order(value = 34)
	public String getHistoryClass() {
		return historyClass;
	}
	@Order(value = 3)
	public void setHistoryClass(String historyClass) {
		this.historyClass = historyClass;
	}
	@Order(value = 35)
	public String getHistoryRace() {
		return historyRace;
	}
	@Order(value = 4)
	public void setHistoryRace(String historyRace) {
		this.historyRace = historyRace;
	}
	@Order(value = 36)
	public String getHistoryJob() {
		return historyJob;
	}
	@Order(value = 5)
	public void setHistoryJob(String historyJob) {
		this.historyJob = historyJob;
	}
	@Order(value = 37)
	public Integer getHistoryAc() {
		return historyAc;
	}
	@Order(value = 6)
	public void setHistoryAc(Integer historyAc) {
		this.historyAc = historyAc;
	}
	@Order(value = 38)
	public Integer getHistoryHp() {
		return historyHp;
	}
	@Order(value = 7)
	public void setHistoryHp(Integer historyHp) {
		this.historyHp = historyHp;
	}
	@Order(value = 39)
	public ArrayList<String> getHistorySpeed() {
		return historySpeed;
	}
	@Order(value = 8)
	public void setHistorySpeed(ArrayList<String> historySpeed) {
		this.historySpeed = historySpeed;
	}
	@Order(value = 40)
	public ArrayList<String> getHistoryLanguages() {
		return historyLanguages;
	}
	@Order(value = 9)
	public void setHistoryLanguages(ArrayList<String> historyLanguages) {
		this.historyLanguages = historyLanguages;
	}
	@Order(value = 41)
	public ArrayList<Integer> getHistoryAbiliSc() {
		return historyAbiliSc;
	}
	@Order(value = 10)
	public void setHistoryAbiliSc(ArrayList<Integer> historyAbiliSc) {
		this.historyAbiliSc = historyAbiliSc;
	}
	@Order(value = 42)
	public ArrayList<String> getHistorySavingTh() {
		return historySavingTh;
	}
	@Order(value = 11)
	public void setHistorySavingTh(ArrayList<String> historySavingTh) {
		this.historySavingTh = historySavingTh;
	}
	@Order(value = 43)
	public String getHistoryArmor() {
		return historyArmor;
	}
	@Order(value = 12)
	public void setHistoryArmor(String historyArmor) {
		this.historyArmor = historyArmor;
	}
	@Order(value = 44)
	public ArrayList<String> getHistoryWeapon() {
		return historyWeapon;
	}
	@Order(value = 13)
	public void setHistoryWeapon(ArrayList<String> historyWeapon) {
		this.historyWeapon = historyWeapon;
	}
	@Order(value = 45)
	public ArrayList<String> getHistorySkills() {
		return historySkills;
	}
	@Order(value = 14)
	public void setHistorySkills(ArrayList<String> historySkills) {
		this.historySkills = historySkills;
	}
	@Order(value = 46)
	public String getHistorySenses() {
		return historySenses;
	}
	@Order(value = 15)
	public void setHistorySenses(String historySenses) {
		this.historySenses = historySenses;
	}
	@Order(value = 47)
	public ArrayList<String> getHistorySpells() {
		return historySpells;
	}
	@Order(value = 16)
	public void setHistorySpells(ArrayList<String> historySpells) {
		this.historySpells = historySpells;
	}
	@Order(value = 48)
	public ArrayList<String> getHistorySpellAb() {
		return historySpellAb;
	}
	@Order(value = 17)
	public void setHistorySpellAb(ArrayList<String> historySpellAb) {
		this.historySpellAb = historySpellAb;
	}
	@Order(value = 49)
	public ArrayList<String> getHistoryDesc() {
		return historyDesc;
	}
	@Order(value = 18)
	public void setHistoryDesc(ArrayList<String> historyDesc) {
		this.historyDesc = historyDesc;
	}
	@Order(value = 50)
	public ArrayList<String> getHistoryActions() {
		return historyActions;
	}
	@Order(value = 19)
	public void setHistoryActions(ArrayList<String> historyActions) {
		this.historyActions = historyActions;
	}
	@Order(value = 51)
	public ArrayList<String> getHistoryBonusAct() {
		return historyBonusAct;
	}
	@Order(value = 20)
	public void setHistoryBonusAct(ArrayList<String> historyBonusAct) {
		this.historyBonusAct = historyBonusAct;
	}
	@Order(value = 52)
	public Integer getHistoryProfBonus() {
		return historyProfBonus;
	}
	@Order(value = 21)
	public void setHistoryProfBonus(Integer historyProfBonus) {
		this.historyProfBonus = historyProfBonus;
	}
	@Order(value = 53)
	public ArrayList<Integer> getHistoryAbiliMod() {
		return historyAbiliMod;
	}
	@Order(value = 22)
	public void setHistoryAbiliMod(ArrayList<Integer> historyAbiliMod) {
		this.historyAbiliMod = historyAbiliMod;
	}
	@Order(value = 54)
	public String getHistoryGender() {
		return historyGender;
	}
	@Order(value = 23)
	public void setHistoryGender(String historyGender) {
		this.historyGender = historyGender;
	}
	@Order(value = 55)
	public Integer getHistoryAge() {
		return historyAge;
	}
	@Order(value = 24)
	public void setHistoryAge(Integer historyAge) {
		this.historyAge = historyAge;
	}
	@Order(value = 56)
	public String getHistoryAlignment() {
		return historyAlignment;
	}
	@Order(value = 25)
	public void setHistoryAlignment(String historyAlignment) {
		this.historyAlignment = historyAlignment;
	}
	@Order(value = 57)
	public String getHistoryDeity() {
		return historyDeity;
	}
	@Order(value = 26)
	public void setHistoryDeity(String historyDeity) {
		this.historyDeity = historyDeity;
	}
	@Order(value = 58)
	public ArrayList<String> getHistoryBonusTextList() {
		return historyBonusTextList;
	}
	@Order(value = 27)
	public void setHistoryBonusTextList(ArrayList<String> historyBonusTextList) {
		this.historyBonusTextList = historyBonusTextList;
	}
	@Order(value = 59)
	public LocalDateTime getHistoryEntryAge() {
		return historyEntryAge;
	}
	public void setHistoryEntryAge(LocalDateTime historyEntryAge) {
		this.historyEntryAge = historyEntryAge;
	}
	@Override
	public String toString() {
		return "HistoryModel [historyId=" + historyId + ", runVar=" + runVar + ", historyName=" + historyName
				+ ", historyLevel=" + historyLevel + ", historyClass=" + historyClass + ", historyRace=" + historyRace
				+ ", historyJob=" + historyJob + ", historyAc=" + historyAc + ", historyHp=" + historyHp
				+ ", historySpeed=" + historySpeed + ", historyLanguages=" + historyLanguages + ", historyAbiliSc="
				+ historyAbiliSc + ", historySavingTh=" + historySavingTh + ", historyArmor=" + historyArmor
				+ ", historyWeapon=" + historyWeapon + ", historySkills=" + historySkills + ", historySenses="
				+ historySenses + ", historySpells=" + historySpells + ", historySpellAb=" + historySpellAb
				+ ", historyDesc=" + historyDesc + ", historyActions=" + historyActions + ", historyBonusAct="
				+ historyBonusAct + ", historyProfBonus=" + historyProfBonus + ", historyEntryAge=" + historyEntryAge
				+ "]";
	}
}
