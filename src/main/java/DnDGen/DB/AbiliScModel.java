/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der AbilityScores Datenbank zu erstellen. 
 * Aktuell noch WIP da die Atrribute der DB noch aktiv geändert/bearbeitet werden.
 *
 */


package DnDGen.DB;

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
	public Integer getAbiliScId() {
		return abiliScId;
	}
	public void setAbiliScId(Integer abiliScId) {
		this.abiliScId = abiliScId;
	}
	public String getAbiliScName() {
		return abiliScName;
	}
	public void setAbiliScName(String abiliScName) {
		this.abiliScName = abiliScName;
	}
	public String getAbiliScKind() {
		return abiliScKind;
	}
	public void setAbiliScKind(String abiliScKind) {
		this.abiliScKind = abiliScKind;
	}
	public String getAbiliScGenerationMethod() {
		return abiliScGenerationMethod;
	}
	public void setAbiliScGenerationMethod(String abiliScGenerationMethod) {
		this.abiliScGenerationMethod = abiliScGenerationMethod;
	}
}
