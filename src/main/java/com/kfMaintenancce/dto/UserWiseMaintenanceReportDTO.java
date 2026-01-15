package com.kfMaintenancce.dto;

import java.util.ArrayList;
import java.util.List;

import com.kfMaintenancce.model.Checklist;
import com.kfMaintenancce.model.Maint;

public class UserWiseMaintenanceReportDTO {
    private String userName;
    private List<MaintenanceReport> maintenanceReports;

    public UserWiseMaintenanceReportDTO(String userName) {
        this.userName = userName;
        this.maintenanceReports = new ArrayList<>();
    }

    
    public void addMaintenance(MaintenanceReport report) {
        this.maintenanceReports.add(report);
    }

   

    public UserWiseMaintenanceReportDTO() {
		
	}


	public String getUserName() {
		return userName;
	}


	public void setUserName(String userName) {
		this.userName = userName;
	}


	public List<MaintenanceReport> getMaintenanceReports() {
		return maintenanceReports;
	}


	public void setMaintenanceReports(List<MaintenanceReport> maintenanceReports) {
		this.maintenanceReports = maintenanceReports;
	}



	public static class MaintenanceReport {

        private Maint maint;
        private String checklist;
        private String frequency;
        private String acceptableRange;

        
        public MaintenanceReport(Maint maint, String checklist, String frequency, String acceptableRange) {
			super();
			this.maint = maint;
			this.checklist = checklist;
			this.frequency = frequency;
			this.acceptableRange = acceptableRange;
		}

		public String getFrequency() {
			return frequency;
		}

		public void setFrequency(String frequency) {
			this.frequency = frequency;
		}

		public String getAcceptableRange() {
			return acceptableRange;
		}

		public void setAcceptableRange(String acceptableRange) {
			this.acceptableRange = acceptableRange;
		}

		public MaintenanceReport() {
			super();
			// TODO Auto-generated constructor stub
		}

		public Maint getMaint() {
            return maint;
        }

        public void setMaint(Maint maint) {
            this.maint = maint;
        }

        public String getChecklist() {
            return checklist;
        }

        public void setChecklist(String checklist) {
            this.checklist = checklist;
        }
    }
}

