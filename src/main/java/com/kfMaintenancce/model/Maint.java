package com.kfMaintenancce.model;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.kfMaintenancce.dto.MainCheckPointDTO;



@Entity
@Table(name="machine_maint")
@JsonIgnoreProperties(ignoreUnknown = true)
public class Maint implements Serializable {

	@Column(name="checkIssue_bit", columnDefinition = "int default 0")
	private int checkIssueBit;
	
	@ManyToOne
	@JoinColumn(name="lab_id")
	private Lab lab;

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="maint_id")
	private int maint_id;
	
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
	@Column(name="schedule_date")
	Date schedule_date;
	 
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
	@Column(name="closed_date")
	Date closedDate;
	
	  @Transient 
	  @JsonManagedReference 
	  private List<MaintenenaceCheckPoint> checkpointlist;
	
	@Transient
	private List<MainCheckPointDTO> checkpointMaaint;
	
	

	@Column(name="mode")
	private String 	mode;
	

	@Column(name="status_code")
	private int statusCode;
	
	
	
	@Column(name="frequency")
	private String 	frequency;
	
	@Column(name="overall_status")
	private String 	overall_status;
	
	@Column(name="spare_used")
	private String 	spare_used;
	
	@Column(name = "Tech_Mark", nullable = true)
	private String techmark;
	
	@ManyToOne
	@JoinColumn(name="done_by")
	private UserDetails 	done_by;
	

	
	
	@ManyToOne
	@JoinColumn(name="raised_by")
	private UserDetails raisedBy;
	

	

	@ManyToOne
	@JoinColumn(name="machine_id")
	private Machine machine;
	
	@Transient
	private String status;
	
	
	@Transient
	private String overdues;
	
	
	@Transient
	private String CheckListStr;
	
	@Transient
	private String raisedName;
	@Transient
	private String doneName;
	
	@Transient
	private String scheduleDateStr;
	
	
	@Transient
	private String closedDateStr;
	
	@Transient
	private int type;
	
	@Transient
	private String startDate;
	
	@Transient
	private String endDate;
	
	

	@Transient
	private int  srNo;
	

	@Transient
	private int week;
	
	

	@Transient
	@ManyToOne
	@JoinColumn(name="checklist_id")
	private Checklist checkpoint;
	
	

	@Transient
	private String action;
	
	@Transient
	private String arr[];
	

	@Transient
	private int s_id;
	
	@Transient
	private List<Maint> checklist;
	
	@Column(name="approval_bit", columnDefinition = "int default 0")
	private int approvalBit;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="approval_by")
	private UserDetails approvalBy;
	
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
		@Column(name="closed_Approval_date")
	private Date closedApprovalDate;
	 
	 
	 @Column(name="executive_remark")
		private String 	executiveRemark;

	
	 @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss", timezone = "Asia/Kolkata")
		@Column(name="UnApproval_date")
	private Date unApprovalDate;
	
	 
	 
	
	public String getSpare_used() {
		return spare_used;
	}
	public void setSpare_used(String spare_used) {
		this.spare_used = spare_used;
	}
	public String getTechmark() {
		return techmark;
	}
	public void setTechmark(String techmark) {
		this.techmark = techmark;
	}
	public int getCheckIssueBit() {
		return checkIssueBit;
	}
	public void setCheckIssueBit(int checkIssueBit) {
		this.checkIssueBit = checkIssueBit;
	}
	public Date getUnApprovalDate() {
		return unApprovalDate;
	}
	public void setUnApprovalDate(Date unApprovalDate) {
		this.unApprovalDate = unApprovalDate;
	}
	
	public String getExecutiveRemark() {
		return executiveRemark;
	}
	public void setExecutiveRemark(String executiveRemark) {
		this.executiveRemark = executiveRemark;
	}
	public Date getClosedApprovalDate() {
		return closedApprovalDate;
	}
	public void setClosedApprovalDate(Date closedApprovalDate) {
		this.closedApprovalDate = closedApprovalDate;
	}
	public int getApprovalBit() {
		return approvalBit;
	}
	public void setApprovalBit(int approvalBit) {
		this.approvalBit = approvalBit;
	}
	public UserDetails getApprovalBy() {
		return approvalBy;
	}
	public void setApprovalBy(UserDetails approvalBy) {
		this.approvalBy = approvalBy;
	}
	public UserDetails getDone_by() {
		return done_by;
	}
	public void setDone_by(UserDetails done_by) {
		this.done_by = done_by;
	}

	
	public UserDetails getRaisedBy() {
		return raisedBy;
	}

	
	
	public void setRaisedBy(UserDetails raisedBy) {
		this.raisedBy = raisedBy;
	}
	
	public List<MainCheckPointDTO> getCheckpointMaaint() {
		return checkpointMaaint;
	}


	public void setCheckpointMaaint(List<MainCheckPointDTO> checkpointMaaint) {
		this.checkpointMaaint = checkpointMaaint;
	}


	


	
	
	
	@Override
	public String toString() {
		return "Maint [checkIssueBit=" + checkIssueBit + ", lab=" + lab + ", maint_id=" + maint_id + ", schedule_date="
				+ schedule_date + ", closedDate=" + closedDate + ", checkpointlist=" + checkpointlist
				+ ", checkpointMaaint=" + checkpointMaaint + ", mode=" + mode + ", statusCode=" + statusCode
				+ ", frequency=" + frequency + ", overall_status=" + overall_status + ", spare_used=" + spare_used
				+ ", techmark=" + techmark + ", done_by=" + done_by + ", raisedBy=" + raisedBy + ", machine=" + machine
				+ ", status=" + status + ", overdues=" + overdues + ", CheckListStr=" + CheckListStr + ", raisedName="
				+ raisedName + ", doneName=" + doneName + ", scheduleDateStr=" + scheduleDateStr + ", closedDateStr="
				+ closedDateStr + ", type=" + type + ", startDate=" + startDate + ", endDate=" + endDate + ", srNo="
				+ srNo + ", week=" + week + ", checkpoint=" + checkpoint + ", action=" + action + ", arr="
				+ Arrays.toString(arr) + ", s_id=" + s_id + ", checklist=" + checklist + ", approvalBit=" + approvalBit
				+ ", approvalBy=" + approvalBy + ", closedApprovalDate=" + closedApprovalDate + ", executiveRemark="
				+ executiveRemark + ", unApprovalDate=" + unApprovalDate + "]";
	}
	public String getCheckListStr() {
		return CheckListStr;
	}


	public void setCheckListStr(String checkListStr) {
		CheckListStr = checkListStr;
	}


	public String getOverdues() {
		return overdues;
	}


	public Date getClosedDate() {
		return closedDate;
	}


	public void setClosedDate(Date closedDate) {
		this.closedDate = closedDate;
	}


	public void setOverdues(String overdues) {
		this.overdues = overdues;
	}

	
	public int getSrNo() {
		return srNo;
	}


	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	
	
	
	
	
	
	public String getScheduleDateStr() {
		return scheduleDateStr;
	}


	public void setScheduleDateStr(String scheduleDateStr) {
		this.scheduleDateStr = scheduleDateStr;
	}


	public String getClosedDateStr() {
		return closedDateStr;
	}


	public void setClosedDateStr(String closedDateStr) {
		this.closedDateStr = closedDateStr;
	}


	public String getRaisedName() {
		return raisedName;
	}


	public String getDoneName() {
		return doneName;
	}


	public void setDoneName(String doneName) {
		this.doneName = doneName;
	}


	public void setRaisedName(String raisedName) {
		this.raisedName = raisedName;
	}

	
	public int getWeek() {
		return week;
	}


	public void setWeek(int week) {
	    this.week = week;
	}

	
	
	

	public int getType() {
		return type;
	}


	public void setType(int type) {
		this.type = type;
	}


	public String getStartDate() {
		return startDate;
	}


	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}


	public String getEndDate() {
		return endDate;
	}


	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getStatus() {
		return status;
	}


	public void setStatus(String status) {
		this.status = status;
	}

	
	public Checklist getCheckpoint() {
		return checkpoint;
	}


	public void setCheckpoint(Checklist checkpoint) {
		this.checkpoint = checkpoint;
	}


	public Date getSchedule_date() {
		return schedule_date;
	}


	public void setSchedule_date(Date schedule_date) {
		this.schedule_date = schedule_date;
	}
	
	
	

	public String getAction() {
		return action;
	}


	public void setAction(String action) {
		this.action = action;
	}

	
//	@Transient
//	private List<Object> checklist;
	

	public List<Maint> getChecklist() {
		return checklist;
	}


	public void setChecklist(List<Maint> checklist) {
		this.checklist = checklist;
	}



	
	
	public String[] getArr() {
		return arr;
	}


	public void setArr(String[] arr) {
		this.arr = arr;
	}



	public Lab getLab() {
		return lab;
	}


	public void setLab(Lab lab) {
		this.lab = lab;
	}

	
	public int getS_id() {
		return s_id;
	}


	public void setS_id(int s_id) {
		this.s_id = s_id;
	}


	public int getMaint_id() {
		return maint_id;
	}


	public void setMaint_id(int maint_id) {
		this.maint_id = maint_id;
	}


	

	public String getMode() {
		return mode;
	}


	public void setMode(String mode) {
		this.mode = mode;
	}


	public String getFrequency() {
		return frequency;
	}


	public void setFrequency(String frequency) {
		this.frequency = frequency;
	}


	public String getOverall_status() {
		return overall_status;
	}


	public void setOverall_status(String overall_status) {
		this.overall_status = overall_status;
	}






	public Machine getMachine() {
		return machine;
	}


	public void setMachine(Machine machine) {
		this.machine = machine;
	}


	public int getStatusCode() {
		return statusCode;
	}


	public void setStatusCode(int statusCode) {
		this.statusCode = statusCode;
	}


	public List<MaintenenaceCheckPoint> getCheckpointlist() {
		return checkpointlist;
	}


	public void setCheckpointlist(List<MaintenenaceCheckPoint> checkpointlist) {
		this.checkpointlist = checkpointlist;
	}




	

	
}
