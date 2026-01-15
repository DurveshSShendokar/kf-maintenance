package com.kfMaintenancce.dto;

import java.util.List;

import com.kfMaintenancce.model.Complaint;

public class EngineerComplaintRecordDTO {
    private String engineerName;
    private List<Complaint> allComplaints;

    public EngineerComplaintRecordDTO(String engineerName, List<Complaint> allComplaints) {
        this.engineerName = engineerName;
        this.allComplaints = allComplaints;
    }

    public String getEngineerName() {
        return engineerName;
    }

    public void setEngineerName(String engineerName) {
        this.engineerName = engineerName;
    }

    public List<Complaint> getAllComplaints() {
        return allComplaints;
    }

    public void setAllComplaints(List<Complaint> allComplaints) {
        this.allComplaints = allComplaints;
    }
}
