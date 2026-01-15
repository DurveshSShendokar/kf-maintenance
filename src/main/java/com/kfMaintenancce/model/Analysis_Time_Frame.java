package com.kfMaintenancce.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "AnalysisTimeFrame")
public class Analysis_Time_Frame 
	{
	
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int ATF_id;

	
	 @Column(name = "status")
	    private Integer status = 1;
	 
	 @Column(name="analysisFor")
		private String analysis_for;
	 
	 
	 @Column(name="No_Of_Days")
		private int No_Of_Days;



	public int getATF_id() {
		return ATF_id;
	}


	public void setATF_id(int aTF_id) {
		ATF_id = aTF_id;
	}


	public Integer getStatus() {
		return status;
	}


	public void setStatus(Integer status) {
		this.status = status;
	}


	public String getAnalysis_for() {
		return analysis_for;
	}


	public void setAnalysis_for(String analysis_for) {
		this.analysis_for = analysis_for;
	}


	public int getNo_Of_Days() {
		return No_Of_Days;
	}


	public void setNo_Of_Days(int no_Of_Days) {
		No_Of_Days = no_Of_Days;
	}


	 
		

}
