package com.kfMaintenancce.dto;
import com.kfMaintenancce.model.Machine;
import com.kfMaintenancce.model.Upload_Manual;
import java.util.Date;
import java.util.List;

public class MachineDetailsDTO {
	 private Machine machine;
	    private List<Upload_Manual> userManuals;

	    public Machine getMachine() {
	        return machine;
	    }

	    public void setMachine(Machine machine) {
	        this.machine = machine;
	    }

	    public List<Upload_Manual> getUserManuals() {
	        return userManuals;
	    }

	    public void setUserManuals(List<Upload_Manual> userManuals) {
	        this.userManuals = userManuals;
	    }
    
}
