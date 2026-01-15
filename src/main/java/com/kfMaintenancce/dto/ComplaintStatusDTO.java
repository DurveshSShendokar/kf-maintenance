package com.kfMaintenancce.dto;

import java.util.List;

import com.kfMaintenancce.model.Complaint;

public class ComplaintStatusDTO {
	 private long count;
	    private List<Complaint> records;
		public long getCount() {
			return count;
		}
		public void setCount(long count) {
			this.count = count;
		}
		public List<Complaint> getRecords() {
			return records;
		}
		public void setRecords(List<Complaint> records) {
			this.records = records;
		}
		public ComplaintStatusDTO(long count, List<Complaint> records) {
			super();
			this.count = count;
			this.records = records;
		}
		public ComplaintStatusDTO() {
			
		}
	    
	    
	    
	    

}
