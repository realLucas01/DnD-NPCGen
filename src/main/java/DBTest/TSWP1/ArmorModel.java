package DBTest.TSWP1;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ArmorModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer armorId;
	private String armorName;
	private String armorType;
	private Integer armorAc;
	private String armorRmax;
	
	public ArmorModel() {
		
	}
	public ArmorModel(String armorName, String armorType, Integer armorAc, String armorRmax) {
		this.armorName= armorName;
		this.armorType= armorType;
		this.armorAc = armorAc;
		this.armorRmax= armorRmax;
	}
	
	public Integer getArmorId() {
		return armorId;
	}
	public void setArmorId(Integer armorId) {
		this.armorId = armorId;
	}
	public String getArmorName() {
		return armorName;
	}
	public void setArmorName(String armorName) {
		this.armorName = armorName;
	}
	public String getArmorType() {
		return armorType;
	}
	public void setArmorType(String armorType) {
		this.armorType = armorType;
	}
	public Integer getArmorAc() {
		return armorAc;
	}
	public void setArmorAc(Integer armorAc) {
		this.armorAc = armorAc;
	}
	public String getArmorRmax() {
		return armorRmax;
	}
	public void setArmorRmax(String armorRmax) {
		this.armorRmax = armorRmax;
	}
	@Override
	public String toString() {
		return "ArmorModel [armorId=" + armorId + ", armorName=" + armorName + ", armorType=" + armorType + ", armorAC="
				+ armorAc + ", armorRmax=" + armorRmax + "]";
	}
}