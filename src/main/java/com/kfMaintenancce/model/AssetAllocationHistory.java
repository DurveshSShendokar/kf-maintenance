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
@Table(name = "asset_allocation_history")
public class AssetAllocationHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int history_id;

    @Column(name="allocation_by")
    private String allocationBy;

    @Column(name="allocation_date")
    private Date allocationDate;

    @ManyToOne
    @JoinColumn(name="allocate_to")
    private UserDetails allocateTo;

    @ManyToOne
    @JoinColumn(name="asset_inventory_id")
    private AssetInventory assetInventory;

    @Column(name="deallocation_date")
    private Date deallocationDate;

    @Column(name="deallocation_by")
    private String deallocationBy;

    @Column(name="status")
    private String status; // always DEALLOCATED for history

	public int getHistory_id() {
		return history_id;
	}

	public void setHistory_id(int history_id) {
		this.history_id = history_id;
	}

	public String getAllocationBy() {
		return allocationBy;
	}

	public void setAllocationBy(String allocationBy) {
		this.allocationBy = allocationBy;
	}

	public Date getAllocationDate() {
		return allocationDate;
	}

	public void setAllocationDate(Date allocationDate) {
		this.allocationDate = allocationDate;
	}

	public UserDetails getAllocateTo() {
		return allocateTo;
	}

	public void setAllocateTo(UserDetails allocateTo) {
		this.allocateTo = allocateTo;
	}

	public AssetInventory getAssetInventory() {
		return assetInventory;
	}

	public void setAssetInventory(AssetInventory assetInventory) {
		this.assetInventory = assetInventory;
	}

	public Date getDeallocationDate() {
		return deallocationDate;
	}

	public void setDeallocationDate(Date deallocationDate) {
		this.deallocationDate = deallocationDate;
	}

	public String getDeallocationBy() {
		return deallocationBy;
	}

	public void setDeallocationBy(String deallocationBy) {
		this.deallocationBy = deallocationBy;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
    
    
    
}

	
	


