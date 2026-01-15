package com.kfMaintenancce.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kfMaintenancce.model.Shift;


public interface ShiftRepo  extends JpaRepository<Shift, Integer>{

}
