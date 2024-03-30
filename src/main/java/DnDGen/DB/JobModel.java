/* @author Lucas
 * 
 * Zuständig dafür das Gerüst der Job Datenbank zu erstellen. 
 *
 */

package DnDGen.DB;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class JobModel {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer jobId;
	private String jobName;
	private String jobSkills;
	private String jobLanguage;
	
	public JobModel() {
		
	}
	public JobModel(String jobName, String jobSkills, String jobLanguage) {
		this.jobName = jobName;
		this.jobSkills = jobSkills;
		this.jobLanguage = jobLanguage;
	}
	public Integer getJobId() {
		return jobId;
	}
	public void setJobId(Integer jobId) {
		this.jobId = jobId;
	}
	public String getJobName() {
		return jobName;
	}
	public void setJobName(String jobName) {
		this.jobName = jobName;
	}
	public String getJobSkills() {
		return jobSkills;
	}
	public void setJobSkills(String jobSkills) {
		this.jobSkills = jobSkills;
	}
	public String getJobLanguage() {
		return jobLanguage;
	}
	public void setJobLanguage(String jobLanguage) {
		this.jobLanguage = jobLanguage;
	}
	@Override
	public String toString() {
		return "JobModel [jobId=" + jobId + ", jobName=" + jobName + ", jobSkills=" + jobSkills + ", jobLanguage="
				+ jobLanguage + "]";
	}
}
