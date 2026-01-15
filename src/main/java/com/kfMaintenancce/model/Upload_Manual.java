package com.kfMaintenancce.model;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="upload_mst")
public class Upload_Manual {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int upload_id;
	
    @ManyToOne
	    @JoinColumn(name = "machine_id")
	    private Machine machine;
	 
	 @Column(name = "File_Name")
	 private String UserManualName ;
	 
	 
	 @Column(name = "File_Path")
	 private String UserManualPath ;
	 
	 
	 @Column(name = "File_Type")
	 private String FileType ;
	 
	 @Column(name="Upload_date")
		private Date uploadDate;
	 
	 @Column(name = "active")
	    private Integer active = 1;

	 @Column(name = "uploaded_by_username")
	    private String uploadedByUsername;

	public int getUpload_id() {
		return upload_id;
	}


	public void setUpload_id(int upload_id) {
		this.upload_id = upload_id;
	}


	public Machine getMachine() {
		return machine;
	}


	public void setMachine(Machine machine) {
		this.machine = machine;
	}


	public String getUserManualName() {
		return UserManualName;
	}


	public void setUserManualName(String userManualName) {
		UserManualName = userManualName;
	}


	public String getUserManualPath() {
		return UserManualPath;
	}


	public void setUserManualPath(String userManualPath) {
		UserManualPath = userManualPath;
	}


	public String getFileType() {
		return FileType;
	}


	public void setFileType(String fileType) {
		FileType = fileType;
	}


	public Date getUploadDate() {
		return uploadDate;
	}


	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}


	public Integer getActive() {
		return active;
	}


	public void setActive(Integer active) {
		this.active = active;
	}


	public String getUploadedByUsername() {
		return uploadedByUsername;
	}


	public void setUploadedByUsername(String uploadedByUsername) {
		this.uploadedByUsername = uploadedByUsername;
	}
	

}
