/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Name Datenbank zu erstellen. 
 *
 */

package DnDGen.DB;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class NameModel {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer nameId;
	private String nameFirstName;
	private String nameLastName;
	private String nameRace;
	private String nameGender;
	
	public NameModel() {
		
	}
	public NameModel(String nameFirstName, String nameLastName, String nameRace, String nameGender) {
		this.nameFirstName= nameFirstName;
		this.nameLastName= nameLastName;
		this.nameRace= nameRace;
		this.nameGender= nameGender;
		
	}
	
	public Integer getNameId() {
		return nameId;
	}
	public void setNameId(Integer nameId) {
		this.nameId = nameId;
	}
	public String getNameFirstName() {
		return nameFirstName;
	}
	public void setNameFirstName(String nameFirstName) {
		this.nameFirstName = nameFirstName;
	}
	public String getNameLastName() {
		return nameLastName;
	}
	public void setNameLastName(String nameLastName) {
		this.nameLastName = nameLastName;
	}
	public String getNameRace() {
		return nameRace;
	}
	public void setNameRace(String nameRace) {
		this.nameRace = nameRace;
	}
	public String getNameGender() {
		return nameGender;
	}
	public void setNameGender(String nameGender) {
		this.nameGender = nameGender;
	}
	@Override
	public String toString() {
		return "NameModel [nameId=" + nameId + ", nameFirstName=" + nameFirstName + ", nameLastName=" + nameLastName
				+ ", nameRace=" + nameRace + ", nameGender=" + nameGender + "]";
	}
}