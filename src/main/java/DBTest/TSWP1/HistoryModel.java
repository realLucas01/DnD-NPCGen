package DBTest.TSWP1;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class HistoryModel {
	
	@Id
	private Integer runVar;
	private Integer historyId;
	private String historyFirstName;
	private String historyLastName;
	private String historyOccurance;
	
	public HistoryModel() {
		
	}
	public HistoryModel(Integer historyId,String historyFirstName, String historyLastName, String historyOccurance) {
		this.historyId=historyId;
		this.historyFirstName= historyFirstName;
		this.historyLastName= historyLastName;
		this.historyOccurance= historyOccurance;
		
	}
	public Integer runVar() {
		return runVar;
	}
	public void runVar(Integer runVar) {
		this.runVar = runVar;
	}
	public Integer getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}
	public String getHistoryFirstName() {
		return historyFirstName;
	}
	public void setHistoryFirstName(String historyFirstName) {
		this.historyFirstName = historyFirstName;
	}
	public String getHistoryLastName() {
		return historyLastName;
	}
	public void setHistoryLastName(String historyLastName) {
		this.historyLastName = historyLastName;
	}
	public String getHistoryOccurance() {
		return historyOccurance;
	}
	public void setHistoryOccurance(String historyOccurance) {
		this.historyOccurance = historyOccurance;
	}
	@Override
	public String toString() {
		return "HistoryModel [historyId=" + historyId + ", historyFirstName=" + historyFirstName + ", historyLastName="
				+ historyLastName + ", historyOccurance=" + historyOccurance + "]";
	}
}