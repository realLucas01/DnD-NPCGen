/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Armor Datenbank zu erstellen. 
 *
 */

package dndgen.database;

import dndgen.database.Sortierung.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ArmorModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer armorId;
	private String armorName;
	private String armorKind;
	private String armorAc;
	private String armorMax;
	private String armorBonus;
	private String armorStrength;
	private String armorStealth;

	public ArmorModel() {
		
	}
	public ArmorModel(String armorName, String armorKind, String armorAc, String armorMax, String armorBonus,
			String armorStrength, String armorStealth) {
		super();
		this.armorName = armorName;
		this.armorKind = armorKind;
		this.armorAc = armorAc;
		this.armorMax = armorMax;
		this.armorBonus = armorBonus;
		this.armorStrength = armorStrength;
		this.armorStealth = armorStealth;
	}
	@Order(value = 1)
	public Integer getArmorId() {
		return armorId;
	}
	public void setArmorId(Integer armorId) {
		this.armorId = armorId;
	}
	@Order(value = 2)
	public String getArmorName() {
		return armorName;
	}
	public void setArmorName(String armorName) {
		this.armorName = armorName;
	}
	@Order(value = 3)
	public String getArmorKind() {
		return armorKind;
	}
	public void setArmorKind(String armorKind) {
		this.armorKind = armorKind;
	}
	@Order(value = 4)
	public String getArmorAc() {
		return armorAc;
	}
	public void setArmorAc(String armorAc) {
		this.armorAc = armorAc;
	}
	@Order(value = 5)
	public String getArmorMax() {
		return armorMax;
	}
	public void setArmorMax(String armorMax) {
		this.armorMax = armorMax;
	}
	@Order(value = 6)
	public String getArmorBonus() {
		return armorBonus;
	}
	public void setArmorBonus(String armorBonus) {
		this.armorBonus = armorBonus;
	}
	@Order(value = 7)
	public String getArmorStrength() {
		return armorStrength;
	}
	public void setArmorStrength(String armorStrength) {
		this.armorStrength = armorStrength;
	}
	@Order(value = 8)
	public String getArmorStealth() {
		return armorStealth;
	}
	public void setArmorStealth(String armorStealth) {
		this.armorStealth = armorStealth;
	}
	@Override
	public String toString() {
		return "ArmorModel [armorId=" + armorId + ", armorName=" + armorName + ", armorKind=" + armorKind + ", armorAc="
				+ armorAc + ", armorMax=" + armorMax + ", armorBonus=" + armorBonus + ", armorStrength=" + armorStrength
				+ ", armorStealth=" + armorStealth + "]";
	}
}
