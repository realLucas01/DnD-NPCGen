/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Features Datenbank zu erstellen. 
 *
 */

package DnDGen.DB;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class FeatureModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer featureId;
	private String featureName;
	@Column(columnDefinition="TEXT")
	private String featureDescription;
	private String featureKindOf;
	
	public FeatureModel() {
		
	}
	public FeatureModel(String featureName, String featureDescription, String featureKindOf) {
		super();
		this.featureName = featureName;
		this.featureDescription = featureDescription;
		this.featureKindOf = featureKindOf;
	}
	public Integer getFeatureId() {
		return featureId;
	}
	public void setFeatureId(Integer featureId) {
		this.featureId = featureId;
	}
	public String getFeatureName() {
		return featureName;
	}
	public void setFeatureName(String featureName) {
		this.featureName = featureName;
	}
	public String getFeatureDescription() {
		return featureDescription;
	}
	public void setFeatureDescription(String featureDescription) {
		this.featureDescription = featureDescription;
	}
	public String getFeatureKindOf() {
		return featureKindOf;
	}
	public void setFeatureKindOf(String featureKindOf) {
		this.featureKindOf = featureKindOf;
	}
	@Override
	public String toString() {
		return "FeatureModel [featureId=" + featureId + ", featureName=" + featureName + ", featureDescription="
				+ featureDescription + ", featureKindOf=" + featureKindOf + "]";
	}
}
