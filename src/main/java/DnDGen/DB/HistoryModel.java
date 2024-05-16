/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Alignment Datenbank zu erstellen. 
 * Aktuell noch WIP da die Atrribute der DB noch nicht feststehen.
 * 
 */

package DnDGen.DB;

import java.util.ArrayList;

import DnDGen.DB.Sortierung.Order;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;

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
	@Column(columnDefinition="TEXT")
	private ArrayList<String> historySpells;
	private ArrayList<String> historySpellAb;
	@Column(columnDefinition="MEDIUMTEXT")
	private ArrayList<String> historyDesc;
	private ArrayList<String> historyActions;
	private ArrayList<String> historyBonusAct;
	private Integer historyProfBonus;
	
	public HistoryModel() {
	}
	public HistoryModel(Integer historyId, Integer runVar, String historyName, Integer historyLevel,
			String historyClass, String historyRace, String historyJob, Integer historyAc, Integer historyHp,
			ArrayList<String> historySpeed, ArrayList<String> historyLanguages, ArrayList<Integer> historyAbiliSc,
			ArrayList<String> historySavingTh, String historyArmor, ArrayList<String> historyWeapon,
			ArrayList<String> historySkills, String historySenses, ArrayList<String> historySpells,
			ArrayList<String> historySpellAb, ArrayList<String> historyDesc, ArrayList<String> historyActions,
			ArrayList<String> historyBonusAct, Integer historyProfBonus) {
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
	}
	public Integer getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}
	public Integer getRunVar() {
		return runVar;
	}
	public void setRunVar(Integer runVar) {
		this.runVar = runVar;
	}
	public String getHistoryName() {
		return historyName;
	}
	@Order(value = 1)
	public void setHistoryName(String historyName) {
		this.historyName = historyName;
	}
	public Integer getHistoryLevel() {
		return historyLevel;
	}
	@Order(value = 2)
	public void setHistoryLevel(Integer historyLevel) {
		this.historyLevel = historyLevel;
	}
	public String getHistoryClass() {
		return historyClass;
	}
	@Order(value = 3)
	public void setHistoryClass(String historyClass) {
		this.historyClass = historyClass;
	}
	public String getHistoryRace() {
		return historyRace;
	}
	@Order(value = 4)
	public void setHistoryRace(String historyRace) {
		this.historyRace = historyRace;
	}
	public String getHistoryJob() {
		return historyJob;
	}
	@Order(value = 5)
	public void setHistoryJob(String historyJob) {
		this.historyJob = historyJob;
	}
	public Integer getHistoryAc() {
		return historyAc;
	}
	@Order(value = 6)
	public void setHistoryAc(Integer historyAc) {
		this.historyAc = historyAc;
	}
	public Integer getHistoryHp() {
		return historyHp;
	}
	@Order(value = 7)
	public void setHistoryHp(Integer historyHp) {
		this.historyHp = historyHp;
	}
	public ArrayList<String> getHistorySpeed() {
		return historySpeed;
	}
	@Order(value = 8)
	public void setHistorySpeed(ArrayList<String> historySpeed) {
		this.historySpeed = historySpeed;
	}
	public ArrayList<String> getHistoryLanguages() {
		return historyLanguages;
	}
	@Order(value = 9)
	public void setHistoryLanguages(ArrayList<String> historyLanguages) {
		this.historyLanguages = historyLanguages;
	}
	public ArrayList<Integer> getHistoryAbiliSc() {
		return historyAbiliSc;
	}
	@Order(value = 10)
	public void setHistoryAbiliSc(ArrayList<Integer> historyAbiliSc) {
		this.historyAbiliSc = historyAbiliSc;
	}
	public ArrayList<String> getHistorySavingTh() {
		return historySavingTh;
	}
	@Order(value = 11)
	public void setHistorySavingTh(ArrayList<String> historySavingTh) {
		this.historySavingTh = historySavingTh;
	}
	public String getHistoryArmor() {
		return historyArmor;
	}
	@Order(value = 12)
	public void setHistoryArmor(String historyArmor) {
		this.historyArmor = historyArmor;
	}
	public ArrayList<String> getHistoryWeapon() {
		return historyWeapon;
	}
	@Order(value = 13)
	public void setHistoryWeapon(ArrayList<String> historyWeapon) {
		this.historyWeapon = historyWeapon;
	}
	public ArrayList<String> getHistorySkills() {
		return historySkills;
	}
	@Order(value = 14)
	public void setHistorySkills(ArrayList<String> historySkills) {
		this.historySkills = historySkills;
	}
	public String getHistorySenses() {
		return historySenses;
	}
	@Order(value = 15)
	public void setHistorySenses(String historySenses) {
		this.historySenses = historySenses;
	}
	public ArrayList<String> getHistorySpells() {
		return historySpells;
	}
	@Order(value = 16)
	public void setHistorySpells(ArrayList<String> historySpells) {
		this.historySpells = historySpells;
	}
	public ArrayList<String> getHistorySpellAb() {
		return historySpellAb;
	}
	@Order(value = 17)
	public void setHistorySpellAb(ArrayList<String> historySpellAb) {
		this.historySpellAb = historySpellAb;
	}
	public ArrayList<String> getHistoryDesc() {
		return historyDesc;
	}
	@Order(value = 18)
	public void setHistoryDesc(ArrayList<String> historyDesc) {
		this.historyDesc = historyDesc;
	}
	public ArrayList<String> getHistoryActions() {
		return historyActions;
	}
	@Order(value = 19)
	public void setHistoryActions(ArrayList<String> historyActions) {
		this.historyActions = historyActions;
	}
	public ArrayList<String> getHistoryBonusAct() {
		return historyBonusAct;
	}
	@Order(value = 20)
	public void setHistoryBonusAct(ArrayList<String> historyBonusAct) {
		this.historyBonusAct = historyBonusAct;
	}
	public Integer getHistoryProfBonus() {
		return historyProfBonus;
	}
	@Order(value = 21)
	public void setHistoryProfBonus(Integer historyProfBonus) {
		this.historyProfBonus = historyProfBonus;
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
				+ historyBonusAct + ", historyProfBonus=" + historyProfBonus + "]";
	}
}
