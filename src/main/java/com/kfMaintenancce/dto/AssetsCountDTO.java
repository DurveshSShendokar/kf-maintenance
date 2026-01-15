package com.kfMaintenancce.dto;

public class AssetsCountDTO {
    private long countWithAllocate;
    private long countWithoutAllocate;

    public AssetsCountDTO(long countWithAllocate, long countWithoutAllocate) {
        this.countWithAllocate = countWithAllocate;
        this.countWithoutAllocate = countWithoutAllocate;
    }

	

	public long getCountWithAllocate() {
		return countWithAllocate;
	}

	public void setCountWithAllocate(long countWithAllocate) {
		this.countWithAllocate = countWithAllocate;
	}

	public long getCountWithoutAllocate() {
		return countWithoutAllocate;
	}

	public void setCountWithoutAllocate(long countWithoutAllocate) {
		this.countWithoutAllocate = countWithoutAllocate;
	}

	

	public AssetsCountDTO() {
		
		// TODO Auto-generated constructor stub
	}
    
    
}