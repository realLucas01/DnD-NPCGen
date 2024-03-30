/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Description Datenbank zu erstellen. 
 * Da die Strucktur der DB noch nicht existiert ist auch dieses Modell der DB extrem WIP.
 *
 */

package DnDGen.DB;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class DescriptionModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer descriptionId;
	
	public DescriptionModel() {
		
	}
}
