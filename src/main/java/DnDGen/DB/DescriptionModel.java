/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Description Datenbank zu erstellen. 
 * Da die Strucktur der DB noch nicht existiert ist auch dieses Modell der DB extrem WIP.
 *
 */

package DnDGen.DB;

import jakarta.persistence.Column;
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
	private String descriptionFlaw;
	private String descriptionIdeal;
	private String descriptionVoice;
	private String descriptionClothing;
	private String descriptionAttitute;
	@Column(columnDefinition="TEXT")
	private String descriptionPlothook;
	
	public DescriptionModel() {
		
	}
	public DescriptionModel(String descriptionEyes, String descriptionHairColor, String descriptionHairStyle,
			String descriptionSkin, String descriptionTrait, String descriptionMannerism, String descriptionBond,
			String descriptionFlaw, String descriptionIdeal, String descriptionVoice, String descriptionClothing,
			String descriptionAttitute, String descriptionPlothook) {
		super();
		this.descriptionEyes = descriptionEyes;
		this.descriptionHairColor = descriptionHairColor;
		this.descriptionHairStyle = descriptionHairStyle;
		this.descriptionSkin = descriptionSkin;
		this.descriptionTrait = descriptionTrait;
		this.descriptionMannerism = descriptionMannerism;
		this.descriptionBond = descriptionBond;
		this.descriptionFlaw = descriptionFlaw;
		this.descriptionIdeal = descriptionIdeal;
		this.descriptionVoice = descriptionVoice;
		this.descriptionClothing = descriptionClothing;
		this.descriptionAttitute = descriptionAttitute;
		this.descriptionPlothook = descriptionPlothook;
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
	public String getDescriptionFlaw() {
		return descriptionFlaw;
	}
	public void setDescriptionFlaw(String descriptionFlaw) {
		this.descriptionFlaw = descriptionFlaw;
	}
	public String getDescriptionIdeal() {
		return descriptionIdeal;
	}
	public void setDescriptionIdeal(String descriptionIdeal) {
		this.descriptionIdeal = descriptionIdeal;
	}
	public String getDescriptionVoice() {
		return descriptionVoice;
	}
	public void setDescriptionVoice(String descriptionVoice) {
		this.descriptionVoice = descriptionVoice;
	}
	public String getDescriptionClothing() {
		return descriptionClothing;
	}
	public void setDescriptionClothing(String descriptionClothing) {
		this.descriptionClothing = descriptionClothing;
	}
	public String getDescriptionAttitute() {
		return descriptionAttitute;
	}
	public void setDescriptionAttitute(String descriptionAttitute) {
		this.descriptionAttitute = descriptionAttitute;
	}
	public String getDescriptionPlothook() {
		return descriptionPlothook;
	}
	public void setDescriptionPlothook(String descriptionPlothook) {
		this.descriptionPlothook = descriptionPlothook;
	}
	@Override
	public String toString() {
		return "DescriptionModel [descriptionId=" + descriptionId + ", descriptionEyes=" + descriptionEyes
				+ ", descriptionHairColor=" + descriptionHairColor + ", descriptionHairStyle=" + descriptionHairStyle
				+ ", descriptionSkin=" + descriptionSkin + ", descriptionTrait=" + descriptionTrait
				+ ", descriptionMannerism=" + descriptionMannerism + ", descriptionBond=" + descriptionBond
				+ ", descriptionFlaw=" + descriptionFlaw + ", descriptionIdeal=" + descriptionIdeal
				+ ", descriptionVoice=" + descriptionVoice + ", descriptionClothing=" + descriptionClothing
				+ ", descriptionAttitute=" + descriptionAttitute + ", descriptionPothook=" + descriptionPlothook + "]";
	}
}
