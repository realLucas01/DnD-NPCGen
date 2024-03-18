package DBTest.TSWP1;

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
	private String nameOccurance;
	
	public NameModel() {
		
	}
	public NameModel(String nameFirstName, String nameLastName, String nameOccurance) {
		this.nameFirstName= nameFirstName;
		this.nameLastName= nameLastName;
		this.nameOccurance= nameOccurance;
		
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
	public String getNameOccurance() {
		return nameOccurance;
	}
	public void setNameOccurance(String nameOccurance) {
		this.nameOccurance = nameOccurance;
	}
	@Override
	public String toString() {
		return "NameModel [nameId=" + nameId + ", nameFirstName=" + nameFirstName + ", nameLastName=" + nameLastName
				+ ", nameOccurance=" + nameOccurance + "]";
	}
}