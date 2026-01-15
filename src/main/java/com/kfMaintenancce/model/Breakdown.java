package com.kfMaintenancce.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;




@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
@Table(name="breakdown_tx")

public class Breakdown implements Serializable {

	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int breakdown_id;
	
	@ManyToOne
	@JoinColumn(name="lab_id", nullable = true)
	private Lab lab;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="trial_By")
	private UserDetails trialBy;
	
	@Transient
	List<Spares>  spares;

	@Column(name="status")
	private int status;
	
	@Column(name="spares_used", length = 2000)
	private String spares_used;
	
	public String getSpares_used() {
		return spares_used;
	}

	@Column(name="ticket_closed_flag")
	private int ticket_closed_flag;
	
	
	@Column(name="tc_tr_hr")
	private double tc_tr_hr;
	
	
	@Column(name="total_trial_hr")
	private double total_trial_hr;
	
	
	@ManyToOne
	@JoinColumn(name="department_id", nullable = true)
	private Department department;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="added_by")
	private UserDetails addedBy;
	
	
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name="update_By")
	private UserDetails updateBy;
	
	
	
	@Transient
	private String createDate;
	@Transient
	private String createTime;
	@Transient
	private String closedDate;
	@Transient
	private String closedTime;
	@Transient
	private String TrialResult;
	
	

	@Transient
	private int week;
	@Transient
	private String raisedName;
	@Transient
	private String spareData;
	@Transient
	private String statusStr;
	
	

	@Column(name="bd_slip", length = 200)
	private String bd_slip;
	
	
	@Column(name="observation", length = 2000)
	private String observation;
	
	@Column(name="detected_by", length = 200)
	private String detected_by;
	
	
	
	@Column(name="deletes", length = 2)
	private int deletes;

	@Column(name="diff_days", length =11, nullable = false, columnDefinition = "int default 0")
	private long diff_days;

	
	@Transient
	private int srNo;
	
	@Transient
	private double ttr;
	
	@Column(name = "show_bit")
	private int showBit = 1;
	
	@Transient
	private String extingRootC;
	
	
	@Transient
	private String extingAction;

	
	@Column(name="action_taken", length = 2000)
	private String action_taken;
	
	@Column(name="spare_used", length = 2000)
	private String spare_used;
	

	@Column(name="engg_finding", length = 1000)
	private String engg_finding;
	
	@Column(name="root_cause", length = 2000)
	private String root_cause;
	
	@Column(name="prev_action_plan", length = 2000)
	private String prev_action_plan;
	

	@Transient
	private int machine_downtime_id;
	
	
	
	@Column(name="breakdown_update_time")
	private Date breakDownUpdateTime;
	
	@Column(name="ticket_raised_time")
	private Date ticket_raised_time;
	
	@Column(name="ticket_closed_time")
	private Date ticket_closed_time;
	
	@Column(name="ticket_trial_time")
	private Date ticket_trial_time;
	
	

	@ManyToOne
	@JoinColumn(name = "shift_id", nullable = true)
	private Shift shift;

	
	
	@ManyToOne
	@JoinColumn(name="machine_id")
	private Machine machine;
	
	@Column(name = "duplicate_flag", columnDefinition = "int default 0")
	private int duplicateFlag; 


	
	@Column(name="tr_month")
	private String tr_month;
	
	 @JsonProperty("maintspares")
		@Transient
		private List<MaintSpare> maintspare;

	@OneToMany(mappedBy = "breakdown", fetch = FetchType.LAZY)
	 @JsonManagedReference
	    private List<BreakdownSpare> breakdownSpares;
	 

	 @Column(name="attended_users", length = 2000)
	    private String attendedUsers;

	    // Actual working start 
	    @Column(name="actual_working_start_time")
	    private Date actualWorkingStartTime;
	
	    
	 // List of attended users
	    @OneToMany(mappedBy = "breakdown", cascade = CascadeType.ALL, orphanRemoval = true)
		 
	    private List<BreakdownAttendee> attendees = new ArrayList<>();
	    
	    
	
	public List<BreakdownAttendee> getAttendees() {
			return attendees;
		}

		public void setAttendees(List<BreakdownAttendee> attendees) {
			this.attendees = attendees;
		}

	public String getAttendedUsers() {
			return attendedUsers;
		}

		public void setAttendedUsers(String attendedUsers) {
			this.attendedUsers = attendedUsers;
		}

		public Date getActualWorkingStartTime() {
			return actualWorkingStartTime;
		}

		public void setActualWorkingStartTime(Date actualWorkingStartTime) {
			this.actualWorkingStartTime = actualWorkingStartTime;
		}

	public int getShowBit() {
		return showBit;
	}

	public void setShowBit(int showBit) {
		this.showBit = showBit;
	}

	public UserDetails getTrialBy() {
		return trialBy;
	}

	public void setTrialBy(UserDetails trialBy) {
		this.trialBy = trialBy;
	}

	public String getTrialResult() {
		return TrialResult;
	}

	public void setTrialResult(String trialResult) {
		TrialResult = trialResult;
	}

	
	
	public UserDetails getUpdateBy() {
		return updateBy;
	}

	public void setUpdateBy(UserDetails updateBy) {
		this.updateBy = updateBy;
	}

	public Lab getLab() {
		return lab;
	}

	public void setLab(Lab lab) {
		this.lab = lab;
	}

	public String getExtingRootC() {
		return extingRootC;
	}

	public void setExtingRootC(String extingRootC) {
		this.extingRootC = extingRootC;
	}

	public String getExtingAction() {
		return extingAction;
	}

	public void setExtingAction(String extingAction) {
		this.extingAction = extingAction;
	}

	public List<MaintSpare> getMaintspare() {
		return maintspare;
	}

	public void setMaintspare(List<MaintSpare> maintspare) {
		this.maintspare = maintspare;
	}

	
	
	public List<BreakdownSpare> getBreakdownSpares() {
		return breakdownSpares;
	}

	public void setBreakdownSpares(List<BreakdownSpare> breakdownSpares) {
		this.breakdownSpares = breakdownSpares;
	}

	
//	
//	 @ManyToOne
//	 @JoinColumn(name="updated_by")
//	 private UserDetails updatedBy;
//
//	 
//
//	public UserDetails getUpdatedBy() {
//		return updatedBy;
//	}
//
//	public void setUpdatedBy(UserDetails updatedBy) {
//		this.updatedBy = updatedBy;
//	}

	public List<MaintSpare> getSpare() {
		return maintspare;
	}

	public void setSpare(List<MaintSpare> maintspare) {
		this.maintspare = maintspare;
	}

	public int getSrNo() {
		return srNo;
	}
	
	public String getCreateDate() {
		return createDate;
	}

	public void setCreateDate(String createDate) {
		this.createDate = createDate;
	}

	public String getCreateTime() {
		return createTime;
	}

	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}

	public String getClosedDate() {
		return closedDate;
	}

	public void setClosedDate(String closedDate) {
		this.closedDate = closedDate;
	}

	public String getClosedTime() {
		return closedTime;
	}

	public void setClosedTime(String closedTime) {
		this.closedTime = closedTime;
	}



	public void setSrNo(int srNo) {
		this.srNo = srNo;
	}

	public String getSpareData() {
		return spareData;
	}


	public void setSpareData(String spareData) {
		this.spareData = spareData;
	}

	
	
	
	public String getRaisedName() {
		return raisedName;
	}


	public void setRaisedName(String raisedName) {
		this.raisedName = raisedName;
	}


	public String getStatusStr() {
		return statusStr;
	}


	public double getTtr() {
		return ttr;
	}


	public void setTtr(double ttr) {
		this.ttr = ttr;
	}


	public void setStatusStr(String statusStr) {
		this.statusStr = statusStr;
	}


	public int getWeek() {
		return week;
	}


	public void setWeek(int week) {
		this.week = week;
	}


	public void setSpares_used(String spares_used) {
		this.spares_used = spares_used;
	}
	
	
	
	
	
	public int getTicket_closed_flag() {
		return ticket_closed_flag;
	}


	public void setTicket_closed_flag(int ticket_closed_flag) {
		this.ticket_closed_flag = ticket_closed_flag;
	}
	

	public double getTc_tr_hr() {
		return tc_tr_hr;
	}


	public UserDetails getAddedBy() {
		return addedBy;
	}


	public void setAddedBy(UserDetails addedBy) {
		this.addedBy = addedBy;
	}


	public void setTc_tr_hr(double tc_tr_hr) {
		this.tc_tr_hr = tc_tr_hr;
	}


	public double getTotal_trial_hr() {
		return total_trial_hr;
	}


	public void setTotal_trial_hr(double total_trial_hr) {
		this.total_trial_hr = total_trial_hr;
	}



	
	
	public String getRoot_cause() {
		return root_cause;
	}


	public void setRoot_cause(String root_cause) {
		this.root_cause = root_cause;
	}


	public String getPrev_action_plan() {
		return prev_action_plan;
	}


	public void setPrev_action_plan(String prev_action_plan) {
		this.prev_action_plan = prev_action_plan;
	}


	public String getEngg_finding() {
		return engg_finding;
	}


	public void setEngg_finding(String engg_finding) {
		this.engg_finding = engg_finding;
	}


//	public String getDone_by() {
//		return done_by;
//	}
//
//
//	public void setDone_by(String done_by) {
//		this.done_by = done_by;
//	}


	public String getAction_taken() {
		return action_taken;
	}


	public void setAction_taken(String action_taken) {
		this.action_taken = action_taken;
	}


	

	
	
	public Date getTicket_trial_time() {
		return ticket_trial_time;
	}

	public void setTicket_trial_time(Date ticket_trial_time) {
		this.ticket_trial_time = ticket_trial_time;
	}

	/**
	 * @return the ticket_closed_time
	 */
	public Date getTicket_closed_time() {
		return ticket_closed_time;
	}


	/**
	 * @param ticket_closed_time the ticket_closed_time to set
	 */
	public void setTicket_closed_time(Date ticket_closed_time) {
		this.ticket_closed_time = ticket_closed_time;
	}


	public Date getTicket_raised_time() {
		return ticket_raised_time;
	}


	public void setTicket_raised_time(Date ticket_raised_time) {
		this.ticket_raised_time = ticket_raised_time;
	}


	public Date getBreakDownUpdateTime() {
		return breakDownUpdateTime;
	}

	public void setBreakDownUpdateTime(Date breakDownUpdateTime) {
		this.breakDownUpdateTime = breakDownUpdateTime;
	}

	public int getMachine_downtime_id() {
		return machine_downtime_id;
	}


	public void setMachine_downtime_id(int machine_downtime_id) {
		this.machine_downtime_id = machine_downtime_id;
	}


	public int getStatus() {
		return status;
	}


	public void setStatus(int status) {
		this.status = status;
	}


	
	
	public long getDiff_days() {
		return diff_days;
	}


	public void setDiff_days(long diff_days) {
		this.diff_days = diff_days;
	}


	public String getTr_month() {
		return tr_month;
	}

	public void setTr_month(String tr_month) {
		this.tr_month = tr_month;
	}

	
	

	public int getBreakdown_id() {
		return breakdown_id;
	}


	public void setBreakdown_id(int breakdown_id) {
		this.breakdown_id = breakdown_id;
	}


	public String getBd_slip() {
		return bd_slip;
	}


	public void setBd_slip(String bd_slip) {
		this.bd_slip = bd_slip;
	}


	public String getObservation() {
		return observation;
	}


	public void setObservation(String observation) {
		this.observation = observation;
	}


	public String getDetected_by() {
		return detected_by;
	}


	public void setDetected_by(String detected_by) {
		this.detected_by = detected_by;
	}


	public int getDeletes() {
		return deletes;
	}


	public void setDeletes(int deletes) {
		this.deletes = deletes;
	}


	public Shift getShift() {
		return shift;
	}


	public void setShift(Shift shift) {
		this.shift = shift;
	}


	public Machine getMachine() {
		return machine;
	}


	public void setMachine(Machine machine) {
		this.machine = machine;
	}


	public Department getDepartment() {
		return department;
	}


	public void setDepartment(Department department) {
		this.department = department;
	}





	
	
	public List<Spares> getSpares() {
		return spares;
	}


	public void setSpares(List<Spares> spares) {
		this.spares = spares;
	}

	

	

	public int getDuplicateFlag() {
		return duplicateFlag;
	}



	public void setDuplicateFlag(int duplicateFlag) {
		this.duplicateFlag = duplicateFlag;
	}

	public String getSpare_used() {
		return spare_used;
	}

	public void setSpare_used(String spare_used) {
		this.spare_used = spare_used;
	}

	@Override
	public String toString() {
		return "Breakdown [breakdown_id=" + breakdown_id + ", lab=" + lab + ", trialBy=" + trialBy + ", spares="
				+ spares + ", status=" + status + ", spares_used=" + spares_used + ", ticket_closed_flag="
				+ ticket_closed_flag + ", tc_tr_hr=" + tc_tr_hr + ", total_trial_hr=" + total_trial_hr + ", department="
				+ department + ", addedBy=" + addedBy + ", updateBy=" + updateBy + ", createDate=" + createDate
				+ ", createTime=" + createTime + ", closedDate=" + closedDate + ", closedTime=" + closedTime
				+ ", TrialResult=" + TrialResult + ", week=" + week + ", raisedName=" + raisedName + ", spareData="
				+ spareData + ", statusStr=" + statusStr + ", bd_slip=" + bd_slip + ", observation=" + observation
				+ ", detected_by=" + detected_by + ", deletes=" + deletes + ", diff_days=" + diff_days + ", srNo="
				+ srNo + ", ttr=" + ttr + ", showBit=" + showBit + ", extingRootC=" + extingRootC + ", extingAction="
				+ extingAction + ", action_taken=" + action_taken + ", spare_used=" + spare_used + ", engg_finding="
				+ engg_finding + ", root_cause=" + root_cause + ", prev_action_plan=" + prev_action_plan
				+ ", machine_downtime_id=" + machine_downtime_id + ", breakDownUpdateTime=" + breakDownUpdateTime
				+ ", ticket_raised_time=" + ticket_raised_time + ", ticket_closed_time=" + ticket_closed_time
				+ ", ticket_trial_time=" + ticket_trial_time + ", shift=" + shift + ", machine=" + machine
				+ ", duplicateFlag=" + duplicateFlag + ", tr_month=" + tr_month + ", maintspare=" + maintspare
				+ ", breakdownSpares=" + breakdownSpares + "]";
	}


	
	@Transient
	private String totalBreakdownTime;  

	@Transient
	private String repairingTime;      

	public String getTotalBreakdownTime() {
	    if (ticket_closed_time != null && ticket_raised_time != null) {
	        long diffMillis = ticket_closed_time.getTime() - ticket_raised_time.getTime();

	        long totalMinutes = diffMillis / (1000 * 60);
	        long hours = totalMinutes / 60;
	        long minutes = totalMinutes % 60;

	        return hours + "h " + minutes + "m";
	    }
	    return "0h 0m";
	}

	public String getRepairingTime() {
	    if (ticket_trial_time != null && actualWorkingStartTime != null) {
	        long diffMillis = ticket_trial_time.getTime() - actualWorkingStartTime.getTime();

	        long totalMinutes = diffMillis / (1000 * 60);
	        long hours = totalMinutes / 60;
	        long minutes = totalMinutes % 60;

	        return hours + "h " + minutes + "m";
	    }
	    return "0h 0m";
	}

	

	
}
