/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Draconian Ancestry Datenbank zu erstellen. 
 *
 */

package DnDGen.DB;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DracAncesModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer draconicId;
	private String draconicDragon;
	private String draconicDamageType;
	private String draconicBreathWeapon;
	
	public DracAncesModel() {
		
	}
	public DracAncesModel(String draconicDragon, String draconicDamageType, String draconicBreathWeapon) {
		super();
		this.draconicDragon = draconicDragon;
		this.draconicDamageType = draconicDamageType;
		this.draconicBreathWeapon = draconicBreathWeapon;
	}
	public Integer getDraconicId() {
		return draconicId;
	}
	public void setDraconicId(Integer draconicId) {
		this.draconicId = draconicId;
	}
	public String getDraconicDragon() {
		return draconicDragon;
	}
	public void setDraconicDragon(String draconicDragon) {
		this.draconicDragon = draconicDragon;
	}
	public String getDraconicDamageType() {
		return draconicDamageType;
	}
	public void setDraconicDamageType(String draconicDamageType) {
		this.draconicDamageType = draconicDamageType;
	}
	public String getDraconicBreathWeapon() {
		return draconicBreathWeapon;
	}
	public void setDraconicBreathWeapon(String draconicBreathWeapon) {
		this.draconicBreathWeapon = draconicBreathWeapon;
	}
	@Override
	public String toString() {
		return "DraconicAncestryModel [draconicId=" + draconicId + ", draconicDragon=" + draconicDragon
				+ ", draconicDamageType=" + draconicDamageType + ", draconicBreathWeapon=" + draconicBreathWeapon + "]";
	}
}
