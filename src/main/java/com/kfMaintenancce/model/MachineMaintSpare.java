package com.kfMaintenancce.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "Machine_spare_assign")  
public class MachineMaintSpare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

   
    @ManyToOne
    @JoinColumn(name = "machine_id", nullable = false)
    private Machine machine;
    
    @ManyToOne
    @JoinColumn(name = "maintspare_id", nullable = false)
    private MaintSpare maintSpare;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Machine getMachine() {
		return machine;
	}

	public void setMachine(Machine machine) {
		this.machine = machine;
	}

	public MaintSpare getMaintSpare() {
		return maintSpare;
	}

	public void setMaintSpare(MaintSpare maintSpare) {
		this.maintSpare = maintSpare;
	}
    


   
}
