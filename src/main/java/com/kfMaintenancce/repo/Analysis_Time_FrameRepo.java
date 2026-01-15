package com.kfMaintenancce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.Analysis_Time_Frame;

public interface Analysis_Time_FrameRepo extends JpaRepository<Analysis_Time_Frame, Integer> {

	
	@Query("From Analysis_Time_Frame b where b.analysis_for =?1" )
	List<Analysis_Time_Frame> getByFor(String analysis_for);

	@Query("From Analysis_Time_Frame b where b.analysis_for =?1 and status=1" )
	Optional<Analysis_Time_Frame> gettAciveByFor(String string);

}
