package com.kfMaintenancce.dto;

import java.util.List;
import java.util.Map;

import com.kfMaintenancce.model.Maint;

public class MaintReportResponse {
    private List<Maint> maintList;
    private Map<String, List<StatusCount>> machineWiseStatusSummary;

  
    public List<Maint> getMaintList() {
        return maintList;
    }

    public void setMaintList(List<Maint> maintList) {
        this.maintList = maintList;
    }

    public Map<String, List<StatusCount>> getMachineWiseStatusSummary() {
        return machineWiseStatusSummary;
    }

    public void setMachineWiseStatusSummary(Map<String, List<StatusCount>> machineWiseStatusSummary) {
        this.machineWiseStatusSummary = machineWiseStatusSummary;
    }
}

