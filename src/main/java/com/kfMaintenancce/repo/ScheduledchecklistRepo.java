package com.kfMaintenancce.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.Scheduledchecklist;

public interface ScheduledchecklistRepo  extends JpaRepository<Scheduledchecklist, Integer>{
	@Query("from Scheduledchecklist s where s.maint.maint_id=?1")
	List<Scheduledchecklist> getChecklistList(int maint_id);

}
