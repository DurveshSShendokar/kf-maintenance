package com.kfMaintenancce.dto;

import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.MachineMaintSpare;
import java.util.List;

public class Break_DashboardDTO {
	
	private Breakdown breakdown;
	private List<MachineMaintSpare> machineSpareAssignments;

	public Breakdown getBreakdown() {
		return breakdown;
	}

	public void setBreakdown(Breakdown breakdown) {
		this.breakdown = breakdown;
	}

	

	public Break_DashboardDTO(Breakdown breakdown, List<MachineMaintSpare> machineSpareAssignments) {
		super();
		this.breakdown = breakdown;
		this.machineSpareAssignments = machineSpareAssignments;
	}

	public Break_DashboardDTO() {
		
		// TODO Auto-generated constructor stub
	}

	public List<MachineMaintSpare> getMachineSpareAssignments() {
		return machineSpareAssignments;
	}

	public void setMachineSpareAssignments(List<MachineMaintSpare> machineSpareAssignments) {
		this.machineSpareAssignments = machineSpareAssignments;
	}
	
	
	
	

}
