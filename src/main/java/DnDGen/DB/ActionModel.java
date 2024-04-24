package DnDGen.DB;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class ActionModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer actionId;
	private String actionName;
	private String actionDescription;
	private String actionClass;
	private Integer actionLevel;
	private String actionType;
	
	public ActionModel() {
		
	}
	public ActionModel(String actionName, String actionDescription, String actionClass, Integer actionLevel,
			String actionType) {
		super();
		this.actionName = actionName;
		this.actionDescription = actionDescription;
		this.actionClass = actionClass;
		this.actionLevel = actionLevel;
		this.actionType = actionType;
	}
	public Integer getActionId() {
		return actionId;
	}
	public void setActionId(Integer actionId) {
		this.actionId = actionId;
	}
	public String getActionName() {
		return actionName;
	}
	public void setActionName(String actionName) {
		this.actionName = actionName;
	}
	public String getActionDescription() {
		return actionDescription;
	}
	public void setActionDescription(String actionDescription) {
		this.actionDescription = actionDescription;
	}
	public String getActionClass() {
		return actionClass;
	}
	public void setActionClass(String actionClass) {
		this.actionClass = actionClass;
	}
	public Integer getActionLevel() {
		return actionLevel;
	}
	public void setActionLevel(Integer actionLevel) {
		this.actionLevel = actionLevel;
	}
	public String getActionType() {
		return actionType;
	}
	public void setActionType(String actionType) {
		this.actionType = actionType;
	}
	@Override
	public String toString() {
		return "ActionModel [actionId=" + actionId + ", actionName=" + actionName + ", actionBeschreibung="
				+ actionDescription + ", actionKlasse=" + actionClass + ", actionLevel=" + actionLevel
				+ ", actionArt=" + actionType + "]";
	}
}
