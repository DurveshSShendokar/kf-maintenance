package com.kfMaintenancce.service;

import java.util.List;

import com.kfMaintenancce.model.Breakdownupdate;
import com.kfMaintenancce.model.Spares;

public interface BreakdownupdateServices {

	void addBreakdownupdate(Breakdownupdate breakdownupdate);

	List<Breakdownupdate> getBreakdownupdateList();

	void deleteBreakdownupdate(int breakdown_update_id);

	List<Spares> getSpares(int breakdownUpdateId);

}
