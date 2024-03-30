/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Language Datenbank zu erstellen. 
 *
 */

package DnDGen.DB;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class LanguageModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer languageId;
	private String languageName;
	
	public LanguageModel() {
		
	}
	public LanguageModel(String languageName) {
		super();
		this.languageName = languageName;
	}
	public Integer getLanguageId() {
		return languageId;
	}
	public void setLanguageId(Integer languageId) {
		this.languageId = languageId;
	}
	public String getLanguageName() {
		return languageName;
	}
	public void setLanguageName(String languageName) {
		this.languageName = languageName;
	}
	@Override
	public String toString() {
		return "LanguageModel [languageId=" + languageId + ", languageName=" + languageName + "]";
	}
}
