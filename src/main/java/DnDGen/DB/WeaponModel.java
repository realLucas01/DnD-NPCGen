/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Weapon Datenbank zu erstellen. 
 *
 */

package DnDGen.DB;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class WeaponModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer weaponId;
	private String weaponName;
	private String weaponProperty;
	private String weaponDamageType;
	private String weaponDamageDice;
	private String weaponKind;
	
	public WeaponModel() {
		
	}
	public WeaponModel(String weaponName, String weaponProperty, String weaponDamageType, String weaponDamageDice,
			String weaponKind) {
		this.weaponName = weaponName;
		this.weaponProperty = weaponProperty;
		this.weaponDamageType = weaponDamageType;
		this.weaponDamageDice = weaponDamageDice;
		this.weaponKind = weaponKind;
	}
	public Integer getWeaponId() {
		return weaponId;
	}
	public void setWeaponId(Integer weaponId) {
		this.weaponId = weaponId;
	}
	public String getWeaponName() {
		return weaponName;
	}
	public void setWeaponName(String weaponName) {
		this.weaponName = weaponName;
	}
	public String getWeaponProperty() {
		return weaponProperty;
	}
	public void setWeaponProperty(String weaponProperty) {
		this.weaponProperty = weaponProperty;
	}
	public String getWeaponDamageType() {
		return weaponDamageType;
	}
	public void setWeaponDamageType(String weaponDamageType) {
		this.weaponDamageType = weaponDamageType;
	}
	public String getWeaponDamageDice() {
		return weaponDamageDice;
	}
	public void setWeaponDamageDice(String weaponDamageDice) {
		this.weaponDamageDice = weaponDamageDice;
	}
	public String getWeaponKind() {
		return weaponKind;
	}
	public void setWeaponKind(String weaponKind) {
		this.weaponKind = weaponKind;
	}
	@Override
	public String toString() {
		return "WeaponModel [weaponId=" + weaponId + ", weaponName=" + weaponName + ", weaponProperty=" + weaponProperty
				+ ", weaponDamageType=" + weaponDamageType + ", weaponDamageDice=" + weaponDamageDice + ", weaponKind="
				+ weaponKind + "]";
	}
}
