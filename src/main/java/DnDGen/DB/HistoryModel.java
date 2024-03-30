/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Alignment Datenbank zu erstellen. 
 * Aktuell noch WIP da die Atrribute der DB noch nicht feststehen.
 * 
 */

package DnDGen.DB;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class HistoryModel {
	@Id
	private Integer historyId;
	private Integer runVar;
	
	public HistoryModel() {
		
	}
	
	public HistoryModel(Integer historyId, Integer runVar) {
		this.historyId = historyId;
		this.runVar = runVar;
	}
	
	public Integer getHistoryId() {
		return historyId;
	}
	public void setHistoryId(Integer historyId) {
		this.historyId = historyId;
	}
	public Integer getRunVar() {
		return runVar;
	}
	public void setRunVar(Integer runVar) {
		this.runVar = runVar;
	}
}
