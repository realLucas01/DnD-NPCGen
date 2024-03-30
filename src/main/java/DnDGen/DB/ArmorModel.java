/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Armor Datenbank zu erstellen. 
 *
 */

package DnDGen.DB;

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
	public Integer getArmorId() {
		return armorId;
	}
	public void setArmorId(Integer armorId) {
		this.armorId = armorId;
	}
	public String getArmorName() {
		return armorName;
	}
	public void setArmorName(String armorName) {
		this.armorName = armorName;
	}
	public String getArmorKind() {
		return armorKind;
	}
	public void setArmorKind(String armorKind) {
		this.armorKind = armorKind;
	}
	public String getArmorAc() {
		return armorAc;
	}
	public void setArmorAc(String armorAc) {
		this.armorAc = armorAc;
	}
	public String getArmorMax() {
		return armorMax;
	}
	public void setArmorMax(String armorMax) {
		this.armorMax = armorMax;
	}
	public String getArmorBonus() {
		return armorBonus;
	}
	public void setArmorBonus(String armorBonus) {
		this.armorBonus = armorBonus;
	}
	public String getArmorStrength() {
		return armorStrength;
	}
	public void setArmorStrength(String armorStrength) {
		this.armorStrength = armorStrength;
	}
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
