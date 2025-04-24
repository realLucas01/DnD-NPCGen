/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Weapon Datenbank zu erstellen. 
 *
 */

package dndgen.database;

import dndgen.database.Sortierung.Order;
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
	@Order(value = 1)
	public Integer getWeaponId() {
		return weaponId;
	}
	public void setWeaponId(Integer weaponId) {
		this.weaponId = weaponId;
	}
	@Order(value = 2)
	public String getWeaponName() {
		return weaponName;
	}
	public void setWeaponName(String weaponName) {
		this.weaponName = weaponName;
	}
	@Order(value = 3)
	public String getWeaponProperty() {
		return weaponProperty;
	}
	public void setWeaponProperty(String weaponProperty) {
		this.weaponProperty = weaponProperty;
	}
	@Order(value = 4)
	public String getWeaponDamageType() {
		return weaponDamageType;
	}
	public void setWeaponDamageType(String weaponDamageType) {
		this.weaponDamageType = weaponDamageType;
	}
	@Order(value = 5)
	public String getWeaponDamageDice() {
		return weaponDamageDice;
	}
	public void setWeaponDamageDice(String weaponDamageDice) {
		this.weaponDamageDice = weaponDamageDice;
	}
	@Order(value = 6)
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
