package com.kfMaintenancce.dto;

import java.time.LocalDateTime;

public class BreakdownSummaryDTO {
    private LocalDateTime fromDate;
    private LocalDateTime toDate;
    private int numberOfBreakdowns;

    public BreakdownSummaryDTO(LocalDateTime fromDate, LocalDateTime toDate, int numberOfBreakdowns) {
        this.fromDate = fromDate;
        this.toDate = toDate;
        this.numberOfBreakdowns = numberOfBreakdowns;
    }

  
    public LocalDateTime getFromDate() {
        return fromDate;
    }

    public void setFromDate(LocalDateTime fromDate) {
        this.fromDate = fromDate;
    }

    public LocalDateTime getToDate() {
        return toDate;
    }

    public void setToDate(LocalDateTime toDate) {
        this.toDate = toDate;
    }

    public int getNumberOfBreakdowns() {
        return numberOfBreakdowns;
    }

    public void setNumberOfBreakdowns(int numberOfBreakdowns) {
        this.numberOfBreakdowns = numberOfBreakdowns;
    }
}
