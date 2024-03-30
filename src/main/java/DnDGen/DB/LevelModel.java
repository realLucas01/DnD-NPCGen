/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Level Datenbank zu erstellen. 
 *
 */

package DnDGen.DB;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class LevelModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer levelId;
	private Integer levelNumber;
	private Integer levelProfBonus;
	
	public LevelModel() {
		
	}
	public LevelModel(Integer levelNumber, Integer levelProfBonus) {
		super();
		this.levelNumber = levelNumber;
		this.levelProfBonus = levelProfBonus;
	}
	public Integer getLevelId() {
		return levelId;
	}
	public void setLevelId(Integer levelId) {
		this.levelId = levelId;
	}
	public Integer getLevelNumber() {
		return levelNumber;
	}
	public void setLevelNumber(Integer levelNumber) {
		this.levelNumber = levelNumber;
	}
	public Integer getLevelProfBonus() {
		return levelProfBonus;
	}
	public void setLevelProfBonus(Integer levelProfBonus) {
		this.levelProfBonus = levelProfBonus;
	}
	@Override
	public String toString() {
		return "LevelModel [levelId=" + levelId + ", levelNumber=" + levelNumber + ", levelProfBonus=" + levelProfBonus
				+ "]";
	}
}
