/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Deity Datenbank zu erstellen. 
 *
 */

package DnDGen.DB;

import DnDGen.DB.Sortierung.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DeityModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer deityId;
	private String deityName;
	private String deityAlignment;
	
	public DeityModel() {
		
	}
	public DeityModel(String deityName, String deityAlignment) {
		super();
		this.deityName = deityName;
		this.deityAlignment = deityAlignment;
	}
	@Order(value = 1)
	public Integer getDeityId() {
		return deityId;
	}
	public void setDeityId(Integer deityId) {
		this.deityId = deityId;
	}
	@Order(value = 2)
	public String getDeityName() {
		return deityName;
	}
	public void setDeityName(String deityName) {
		this.deityName = deityName;
	}
	@Order(value = 3)
	public String getDeityAlignment() {
		return deityAlignment;
	}
	public void setDeityAlignment(String deityAlignment) {
		this.deityAlignment = deityAlignment;
	}
	@Override
	public String toString() {
		return "DeityModel [deityId=" + deityId + ", deityName=" + deityName + ", deityAlignment=" + deityAlignment
				+ "]";
	}
}
