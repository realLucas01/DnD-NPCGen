/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Description Datenbank zu erstellen. 
 * Da die Strucktur der database noch nicht existiert ist auch dieses Modell der database extrem WIP.
 *
 */

package dndgen.database;


import dndgen.database.Sortierung.Order;
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
	@Order(value = 1)
	public Integer getDescriptionId() {
		return descriptionId;
	}
	public void setDescriptionId(Integer descriptionId) {
		this.descriptionId = descriptionId;
	}
	@Order(value = 2)
	public String getDescriptionEyes() {
		return descriptionEyes;
	}
	public void setDescriptionEyes(String descriptionEyes) {
		this.descriptionEyes = descriptionEyes;
	}
	@Order(value = 3)
	public String getDescriptionHairColor() {
		return descriptionHairColor;
	}
	public void setDescriptionHairColor(String descriptionHairColor) {
		this.descriptionHairColor = descriptionHairColor;
	}
	@Order(value = 4)
	public String getDescriptionHairStyle() {
		return descriptionHairStyle;
	}
	public void setDescriptionHairStyle(String descriptionHairStyle) {
		this.descriptionHairStyle = descriptionHairStyle;
	}
	@Order(value = 5)
	public String getDescriptionSkin() {
		return descriptionSkin;
	}
	public void setDescriptionSkin(String descriptionSkin) {
		this.descriptionSkin = descriptionSkin;
	}
	@Order(value = 6)
	public String getDescriptionTrait() {
		return descriptionTrait;
	}
	public void setDescriptionTrait(String descriptionTrait) {
		this.descriptionTrait = descriptionTrait;
	}
	@Order(value = 7)
	public String getDescriptionMannerism() {
		return descriptionMannerism;
	}
	public void setDescriptionMannerism(String descriptionMannerism) {
		this.descriptionMannerism = descriptionMannerism;
	}
	@Order(value = 8)
	public String getDescriptionBond() {
		return descriptionBond;
	}
	public void setDescriptionBond(String descriptionBond) {
		this.descriptionBond = descriptionBond;
	}
	@Order(value = 9)
	public String getDescriptionFlaw() {
		return descriptionFlaw;
	}
	public void setDescriptionFlaw(String descriptionFlaw) {
		this.descriptionFlaw = descriptionFlaw;
	}
	@Order(value = 10)
	public String getDescriptionIdeal() {
		return descriptionIdeal;
	}
	public void setDescriptionIdeal(String descriptionIdeal) {
		this.descriptionIdeal = descriptionIdeal;
	}
	@Order(value = 11)
	public String getDescriptionVoice() {
		return descriptionVoice;
	}
	public void setDescriptionVoice(String descriptionVoice) {
		this.descriptionVoice = descriptionVoice;
	}
	@Order(value = 12)
	public String getDescriptionClothing() {
		return descriptionClothing;
	}
	public void setDescriptionClothing(String descriptionClothing) {
		this.descriptionClothing = descriptionClothing;
	}
	@Order(value = 13)
	public String getDescriptionAttitute() {
		return descriptionAttitute;
	}
	public void setDescriptionAttitute(String descriptionAttitute) {
		this.descriptionAttitute = descriptionAttitute;
	}
	@Order(value = 14)
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
