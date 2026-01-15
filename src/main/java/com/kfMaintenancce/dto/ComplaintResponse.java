package com.kfMaintenancce.dto;

public class ComplaintResponse {
    
    private int activeComplaintCount;
    private int inactiveComplaintCount;
    private int allocateComplaintCount;
    private int InprocessComplaintCount;
    private int NonallocateComplaintCount;
    private long totalComplaints;
    private long totalAssets;
   
	public int getActiveComplaintCount() {
		return activeComplaintCount;
	}
	public void setActiveComplaintCount(int activeComplaintCount) {
		this.activeComplaintCount = activeComplaintCount;
	}
	
	
	
	public long getTotalComplaints() {
		return totalComplaints;
	}
	public void setTotalComplaints(long totalComplaints) {
		this.totalComplaints = totalComplaints;
	}
	public long getTotalAssets() {
		return totalAssets;
	}
	public void setTotalAssets(long totalAssets) {
		this.totalAssets = totalAssets;
	}
	public int getInactiveComplaintCount() {
		return inactiveComplaintCount;
	}
	public void setInactiveComplaintCount(int inactiveComplaintCount) {
		this.inactiveComplaintCount = inactiveComplaintCount;
	}
	
	
	
	public int getAllocateComplaintCount() {
		return allocateComplaintCount;
	}
	public void setAllocateComplaintCount(int allocateComplaintCount) {
		this.allocateComplaintCount = allocateComplaintCount;
	}
	
	

	public int getInprocessComplaintCount() {
		return InprocessComplaintCount;
	}
	public void setInprocessComplaintCount(int inprocessComplaintCount) {
		this.InprocessComplaintCount = inprocessComplaintCount;
	}
	
	
	public int getNonallocateComplaintCount() {
		return NonallocateComplaintCount;
	}
	public void setNonallocateComplaintCount(int nonallocateComplaintCount) {
		NonallocateComplaintCount = nonallocateComplaintCount;
	}
	
	
	

	
	public ComplaintResponse(int activeComplaintCount, int inactiveComplaintCount, int allocateComplaintCount,
			int inprocessComplaintCount, int nonallocateComplaintCount, long totalComplaints, long totalAssets) {
		super();
		this.activeComplaintCount = activeComplaintCount;
		this.inactiveComplaintCount = inactiveComplaintCount;
		this.allocateComplaintCount = allocateComplaintCount;
		InprocessComplaintCount = inprocessComplaintCount;
		NonallocateComplaintCount = nonallocateComplaintCount;
		this.totalComplaints = totalComplaints;
		this.totalAssets = totalAssets;
	}
	public ComplaintResponse() {
		
		
	}

    
   
}
