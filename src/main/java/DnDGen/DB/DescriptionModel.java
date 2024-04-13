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
	private String descriptionEyes;
	private String descriptionHairColor;
	private String descriptionHairStyle;
	private String descriptionSkin;
	private String descriptionTrait;
	private String descriptionMannerism;
	private String descriptionBond;
	
	public DescriptionModel() {
		
	}
	public DescriptionModel(String descriptionEyes, String descriptionHairColor, String descriptionHairStyle,
			String descriptionSkin, String descriptionTrait, String descriptionMannerism, String descriptionBond) {
		super();
		this.descriptionEyes = descriptionEyes;
		this.descriptionHairColor = descriptionHairColor;
		this.descriptionHairStyle = descriptionHairStyle;
		this.descriptionSkin = descriptionSkin;
		this.descriptionTrait = descriptionTrait;
		this.descriptionMannerism = descriptionMannerism;
		this.descriptionBond = descriptionBond;
	}
	public Integer getDescriptionId() {
		return descriptionId;
	}
	public void setDescriptionId(Integer descriptionId) {
		this.descriptionId = descriptionId;
	}
	public String getDescriptionEyes() {
		return descriptionEyes;
	}
	public void setDescriptionEyes(String descriptionEyes) {
		this.descriptionEyes = descriptionEyes;
	}
	public String getDescriptionHairColor() {
		return descriptionHairColor;
	}
	public void setDescriptionHairColor(String descriptionHairColor) {
		this.descriptionHairColor = descriptionHairColor;
	}
	public String getDescriptionHairStyle() {
		return descriptionHairStyle;
	}
	public void setDescriptionHairStyle(String descriptionHairStyle) {
		this.descriptionHairStyle = descriptionHairStyle;
	}
	public String getDescriptionSkin() {
		return descriptionSkin;
	}
	public void setDescriptionSkin(String descriptionSkin) {
		this.descriptionSkin = descriptionSkin;
	}
	public String getDescriptionTrait() {
		return descriptionTrait;
	}
	public void setDescriptionTrait(String descriptionTrait) {
		this.descriptionTrait = descriptionTrait;
	}
	public String getDescriptionMannerism() {
		return descriptionMannerism;
	}
	public void setDescriptionMannerism(String descriptionMannerism) {
		this.descriptionMannerism = descriptionMannerism;
	}
	public String getDescriptionBond() {
		return descriptionBond;
	}
	public void setDescriptionBond(String descriptionBond) {
		this.descriptionBond = descriptionBond;
	}
	@Override
	public String toString() {
		return "DescriptionModel [descriptionId=" + descriptionId + ", descriptionEyes=" + descriptionEyes
				+ ", descriptionHairColor=" + descriptionHairColor + ", descriptionHairStyle=" + descriptionHairStyle
				+ ", descriptionSkin=" + descriptionSkin + ", descriptionTrait=" + descriptionTrait
				+ ", descriptionMannerism=" + descriptionMannerism + ", descriptionBond=" + descriptionBond + "]";
	}
}
