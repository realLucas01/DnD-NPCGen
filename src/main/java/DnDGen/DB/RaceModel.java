/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Race Datenbank zu erstellen. 
 *
 */

package DnDGen.DB;

import DnDGen.DB.Sortierung.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class RaceModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer raceId;
	private String raceName;
	private String raceAbilityScore;
	private String raceFeatures;
	private Integer raceAgeAdult;
	private Integer raceAgeMax;
	private String raceSkills;
	private String raceHeight;
	private String raceSize;
	private String raceSpeed;
	private String raceLanguages;
	private String raceCreatureType;

	public RaceModel() {
		
	}
	public RaceModel(String raceName, String raceAbilityScore, String raceFeatures, Integer raceAgeAdult,
			Integer raceAgeMax, String raceSkills, String raceHeight, String raceSize, String raceSpeed,
			String raceLanguages, String raceCreatureType) {
		this.raceName = raceName;
		this.raceAbilityScore = raceAbilityScore;
		this.raceFeatures = raceFeatures;
		this.raceAgeAdult = raceAgeAdult;
		this.raceAgeMax = raceAgeMax;
		this.raceSkills = raceSkills;
		this.raceHeight = raceHeight;
		this.raceSize = raceSize;
		this.raceSpeed = raceSpeed;
		this.raceLanguages = raceLanguages;
		this.raceCreatureType = raceCreatureType;
	}
	@Order(value = 1)
	public Integer getRaceId() {
		return raceId;
	}
	public void setRaceId(Integer raceId) {
		this.raceId = raceId;
	}
	@Order(value = 2)
	public String getRaceName() {
		return raceName;
	}
	public void setRaceName(String raceName) {
		this.raceName = raceName;
	}
	@Order(value = 3)
	public String getRaceAbilityScore() {
		return raceAbilityScore;
	}
	public void setRaceAbilityScore(String raceAbilityScore) {
		this.raceAbilityScore = raceAbilityScore;
	}
	@Order(value = 4)
	public String getRaceFeatures() {
		return raceFeatures;
	}
	public void setRaceFeatures(String raceFeatures) {
		this.raceFeatures = raceFeatures;
	}
	@Order(value = 5)
	public Integer getRaceAgeAdult() {
		return raceAgeAdult;
	}
	public void setRaceAgeAdult(Integer raceAgeAdult) {
		this.raceAgeAdult = raceAgeAdult;
	}
	@Order(value = 6)
	public Integer getRaceAgeMax() {
		return raceAgeMax;
	}
	public void setRaceAgeMax(Integer raceAgeMax) {
		this.raceAgeMax = raceAgeMax;
	}
	@Order(value = 7)
	public String getRaceSkills() {
		return raceSkills;
	}
	public void setRaceSkills(String raceSkills) {
		this.raceSkills = raceSkills;
	}
	@Order(value = 8)
	public String getRaceHeight() {
		return raceHeight;
	}
	public void setRaceHeight(String raceHeight) {
		this.raceHeight = raceHeight;
	}
	@Order(value = 9)
	public String getRaceSize() {
		return raceSize;
	}
	public void setRaceSize(String raceSize) {
		this.raceSize = raceSize;
	}
	@Order(value = 10)
	public String getRaceSpeed() {
		return raceSpeed;
	}
	public void setRaceSpeed(String raceSpeed) {
		this.raceSpeed = raceSpeed;
	}
	@Order(value = 11)
	public String getRaceLanguages() {
		return raceLanguages;
	}
	public void setRaceLanguages(String raceLanguages) {
		this.raceLanguages = raceLanguages;
	}
	@Order(value = 12)
	public String getRaceCreatureType() {
		return raceCreatureType;
	}
	public void setRaceCreatureType(String raceCreatureType) {
		this.raceCreatureType = raceCreatureType;
	}
	@Override
	public String toString() {
		return "RaceModel [raceId=" + raceId + ", raceName=" + raceName + ", raceAbilityScore=" + raceAbilityScore
				+ ", raceFeatures=" + raceFeatures + ", raceAgeAdult=" + raceAgeAdult + ", raceAgeMax=" + raceAgeMax
				+ ", raceSkills=" + raceSkills + ", raceHeight=" + raceHeight + ", raceSize=" + raceSize
				+ ", raceSpeed=" + raceSpeed + ", raceLanguages=" + raceLanguages + ", raceCreatureType="
				+ raceCreatureType + "]";
	}
}