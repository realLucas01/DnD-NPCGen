/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der AbilityScores Datenbank zu erstellen. 
 * Aktuell noch WIP da die Atrribute der database noch aktiv geändert/bearbeitet werden.
 *
 */


package dndgen.database;

import dndgen.database.Sortierung.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AbiliScModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer abiliScId;
	private String abiliScName;
	private String abiliScKind;
	private String abiliScGenerationMethod;
	
	public AbiliScModel() {
		
	}
	public AbiliScModel(String abiliScName, String abiliScKind, String abiliScGenerationMethod) {
		this.abiliScName = abiliScName;
		this.abiliScKind = abiliScKind;
		this.abiliScGenerationMethod = abiliScGenerationMethod;
	}
	@Order(value = 1)
	public Integer getAbiliScId() {
		return abiliScId;
	}
	public void setAbiliScId(Integer abiliScId) {
		this.abiliScId = abiliScId;
	}
	@Order(value = 2)
	public String getAbiliScName() {
		return abiliScName;
	}
	public void setAbiliScName(String abiliScName) {
		this.abiliScName = abiliScName;
	}
	@Order(value = 3)
	public String getAbiliScKind() {
		return abiliScKind;
	}
	public void setAbiliScKind(String abiliScKind) {
		this.abiliScKind = abiliScKind;
	}
	@Order(value = 4)
	public String getAbiliScGenerationMethod() {
		return abiliScGenerationMethod;
	}
	public void setAbiliScGenerationMethod(String abiliScGenerationMethod) {
		this.abiliScGenerationMethod = abiliScGenerationMethod;
	}
	@Override
	public String toString() {
		return "AbiliScModel [abiliScId=" + abiliScId + ", abiliScName=" + abiliScName + ", abiliScKind=" + abiliScKind
				+ ", abiliScGenerationMethod=" + abiliScGenerationMethod + "]";
	}
}
