package com.kfMaintenancce.dto;

import com.kfMaintenancce.model.UserDetails;
import com.kfMaintenancce.repo.UserDetailsRepo;


public class GraphResponseDTO2 {
    private UserDetails user;
    private int open;
    private int closed;

    public UserDetails getUser() {
        return user;
    }

    public void setUser(UserDetails userDetails) {
        this.user = userDetails;
    }

    public int getOpen() {
        return open;
    }

    public void setOpen(int open) {
        this.open = open;
    }

    public int getClosed() {
        return closed;
    }

    public void setClosed(int closed) {
        this.closed = closed;
    }
}
