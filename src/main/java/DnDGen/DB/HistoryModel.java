/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Alignment Datenbank zu erstellen. 
 * Aktuell noch WIP da die Atrribute der DB noch nicht feststehen.
 * 
 */

package DnDGen.DB;

import java.util.ArrayList;

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
	private String historySpeed;
	private String historyLanguages;
	private ArrayList<Integer> historyAbiliSc;
	private ArrayList<Integer> historySavingTh;
	private String historyArmor;
	private String historyWeapon;
	private String historySp;
	private String historySpells;
	private String historySpellAb;
	private String historyDesc;
	private String historyActions;
	private String historyBonusAct;
	private ArrayList<Integer> historyProfBonus;
	
	public HistoryModel() {
		
	}
	public HistoryModel(Integer historyId, Integer runVar, String historyName, Integer historyLevel,
			String historyClass, String historyRace, String historyJob, Integer historyAc, Integer historyHp,
			String historySpeed, String historyLanguages, ArrayList<Integer> historyAbiliSc,
			ArrayList<Integer> historySavingTh, String historyArmor, String historyWeapon, String historySp,
			String historySpells, String historySpellAb, String historyDesc, String historyActions,
			String historyBonusAct, ArrayList<Integer> historyProfBonus) {
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
		this.historySp = historySp;
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
	public void setHistoryName(String historyName) {
		this.historyName = historyName;
	}
	public Integer getHistoryLevel() {
		return historyLevel;
	}
	public void setHistoryLevel(Integer historyLevel) {
		this.historyLevel = historyLevel;
	}
	public String getHistoryClass() {
		return historyClass;
	}
	public void setHistoryClass(String historyClass) {
		this.historyClass = historyClass;
	}
	public String getHistoryRace() {
		return historyRace;
	}
	public void setHistoryRace(String historyRace) {
		this.historyRace = historyRace;
	}
	public String getHistoryJob() {
		return historyJob;
	}
	public void setHistoryJob(String historyJob) {
		this.historyJob = historyJob;
	}
	public Integer getHistoryAc() {
		return historyAc;
	}
	public void setHistoryAc(Integer historyAc) {
		this.historyAc = historyAc;
	}
	public Integer getHistoryHp() {
		return historyHp;
	}
	public void setHistoryHP(Integer historyHp) {
		this.historyHp = historyHp;
	}
	public String getHistorySpeed() {
		return historySpeed;
	}
	public void setHistorySpeed(String historySpeed) {
		this.historySpeed = historySpeed;
	}
	public String getHistoryLanguages() {
		return historyLanguages;
	}
	public void setHistoryLanguages(String historyLanguages) {
		this.historyLanguages = historyLanguages;
	}
	public ArrayList<Integer> getHistoryAbiliSc() {
		return historyAbiliSc;
	}
	public void setHistoryAbiliSc(ArrayList<Integer> historyAbiliSc) {
		this.historyAbiliSc = historyAbiliSc;
	}
	public ArrayList<Integer> getHistorySavingTh() {
		return historySavingTh;
	}
	public void setHistorySavingTh(ArrayList<Integer> historySavingTh) {
		this.historySavingTh = historySavingTh;
	}
	public String getHistoryArmor() {
		return historyArmor;
	}
	public void setHistoryArmor(String historyArmor) {
		this.historyArmor = historyArmor;
	}
	public String getHistoryWeapon() {
		return historyWeapon;
	}
	public void setHistoryWeapon(String historyWeapon) {
		this.historyWeapon = historyWeapon;
	}
	public String getHistorySp() {
		return historySp;
	}
	public void setHistorySp(String historySp) {
		this.historySp = historySp;
	}
	public String getHistorySpells() {
		return historySpells;
	}
	public void setHistorySpells(String historySpells) {
		this.historySpells = historySpells;
	}
	public String getHistorySpellAb() {
		return historySpellAb;
	}
	public void setHistorySpellAb(String historySpellAb) {
		this.historySpellAb = historySpellAb;
	}
	public String getHistoryDesc() {
		return historyDesc;
	}
	public void setHistoryDesc(String historyDesc) {
		this.historyDesc = historyDesc;
	}
	public String getHistoryActions() {
		return historyActions;
	}
	public void setHistoryActions(String historyActions) {
		this.historyActions = historyActions;
	}
	public String getHistoryBonusAct() {
		return historyBonusAct;
	}
	public void setHistoryBonusAct(String historyBonusAct) {
		this.historyBonusAct = historyBonusAct;
	}
	public ArrayList<Integer> getHistoryProfBonus() {
		return historyProfBonus;
	}
	public void setHistoryProfBonus(ArrayList<Integer> historyProfBonus) {
		this.historyProfBonus = historyProfBonus;
	}
	@Override
	public String toString() {
		return "HistoryModel [historyId=" + historyId + ", runVar=" + runVar + ", historyName=" + historyName
				+ ", historyLevel=" + historyLevel + ", historyClass=" + historyClass + ", historyRace=" + historyRace
				+ ", historyJob=" + historyJob + ", historyAC=" + historyAc + ", historyHP=" + historyHp
				+ ", historySpeed=" + historySpeed + ", historyLanguages=" + historyLanguages + ", historyAbiliSc="
				+ historyAbiliSc + ", historySavingT=" + historySavingTh + ", historyArmor=" + historyArmor
				+ ", historyWeapon=" + historyWeapon + ", historySP=" + historySp + ", historySpells=" + historySpells
				+ ", historySpellAb=" + historySpellAb + ", historyDesc=" + historyDesc + ", historyActions="
				+ historyActions + ", historyBonusAct=" + historyBonusAct + ", historyProfBonus=" + historyProfBonus
				+ "]";
	}
}
