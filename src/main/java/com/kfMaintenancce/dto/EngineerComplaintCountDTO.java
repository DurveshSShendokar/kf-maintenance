package com.kfMaintenancce.dto;


public class EngineerComplaintCountDTO {
    private String name;
    private int openComplaints;
    private int allocateComplaints;
    private int inprocessComplaints; 
    private int closedComplaints;
    private int totalComplaints;
   

    public EngineerComplaintCountDTO(String name, int openComplaints, int allocateComplaints, int inprocessComplaints, int closedComplaints, int totalComplaints) {
        this.name = name;
        this.openComplaints = openComplaints;
        this.allocateComplaints = allocateComplaints;
        this.inprocessComplaints = inprocessComplaints;
        this.closedComplaints = closedComplaints;
        this.totalComplaints = totalComplaints;
    }

    // Getters and setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getInprocessComplaints() {
        return inprocessComplaints;
    }

    public void setInprocessComplaints(int inprocessComplaints) {
        this.inprocessComplaints = inprocessComplaints;
    }

    public int getOpenComplaints() {
        return openComplaints;
    }

    public void setOpenComplaints(int openComplaints) {
        this.openComplaints = openComplaints;
    }

    public int getClosedComplaints() {
        return closedComplaints;
    }

    public void setClosedComplaints(int closedComplaints) {
        this.closedComplaints = closedComplaints;
    }

	public int getAllocateComplaints() {
		return allocateComplaints;
	}

	public void setAllocateComplaints(int allocateComplaints) {
		this.allocateComplaints = allocateComplaints;
	}

	public int getTotalComplaints() {
		return totalComplaints;
	}

	public void setTotalComplaints(int totalComplaints) {
		this.totalComplaints = totalComplaints;
	}
    
    
}
