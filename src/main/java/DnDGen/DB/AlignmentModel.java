/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Alignment Datenbank zu erstellen. 
 *
 */

package DnDGen.DB;


import DnDGen.DB.Sortierung.Order;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class AlignmentModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer alignmentId;
	private String alignmentName;
	private String alignmentShorted;
	
	public AlignmentModel() {
		
	}
	public AlignmentModel(String alignmentName, String alignmentShorted) {
		this.alignmentName = alignmentName;
		this.alignmentShorted = alignmentShorted;
	}
	@Order(value = 1)
	public Integer getAlignmentId() {
		return alignmentId;
	}
	public void setAlignmentId(Integer alignmentId) {
		this.alignmentId = alignmentId;
	}
	@Order(value = 2)
	public String getAlignmentName() {
		return alignmentName;
	}
	public void setAlignmentName(String alignmentName) {
		this.alignmentName = alignmentName;
	}
	@Order(value = 3)
	public String getAlignmentShorted() {
		return alignmentShorted;
	}
	public void setAlignmentShorted(String alignmentShorted) {
		this.alignmentShorted = alignmentShorted;
	}
	@Override
	public String toString() {
		return "AlignmentModel [alignmentId=" + alignmentId + ", alignmentName=" + alignmentName + ", alignmentShort="
				+ alignmentShorted + "]";
	}
}
