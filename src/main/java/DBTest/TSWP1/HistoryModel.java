package DBTest.TSWP1;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class HistoryModel {
	
	@Id
	private Integer historyId;
	private Integer runVar;
	private String historyFirstName;
	private String historyLastName;
	private String historyOccurance;
	
	public HistoryModel() {
		
	}
	public HistoryModel(Integer runVar, String historyFirstName, String historyLastName, String historyOccurance) {
		this.runVar=runVar;
		this.historyFirstName= historyFirstName;
		this.historyLastName= historyLastName;
		this.historyOccurance= historyOccurance;
	
	}
	public Integer getrunVar() {
		return runVar;
	}
	public void setrunVar(Integer runVar) {
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
		return "HistoryModel [historyId=" + runVar + ", historyFirstName=" + historyFirstName + ", historyLastName="
				+ historyLastName + ", historyOccurance=" + historyOccurance + "]";
	}
}