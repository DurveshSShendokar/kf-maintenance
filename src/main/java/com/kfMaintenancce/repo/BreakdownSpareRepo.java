package com.kfMaintenancce.repo;

import com.kfMaintenancce.dto.SpareConsumptionReportDTO;
import com.kfMaintenancce.model.Breakdown;
import com.kfMaintenancce.model.BreakdownSpare;
import com.kfMaintenancce.model.SpareConsumption;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface BreakdownSpareRepo extends JpaRepository<BreakdownSpare, Integer> {

	// List<BreakdownSpare> findByBreakdown(Breakdown breakdown);
	 
	 @Query("SELECT b FROM BreakdownSpare b WHERE b.breakdown.breakdown_id = :breakdownId")
	    List<BreakdownSpare> getBreakdownSpareByBreakdownId(@Param("breakdownId") int breakdownId);
	 
	 @Query("From BreakdownSpare b where b.breakdown.breakdown_id=?1 and b.maintspare.maintspare_id=?2")
	 Optional<BreakdownSpare> getBreakdownSpareByBreakdownIDAndMaintSpareId(int breakdown_id, int maintspare_id);
	 
	 @Query("SELECT new com.kfMaintenancce.dto.SpareConsumptionReportDTO(" +
		       "b.maintspare.spare_name, " +
		       "b.breakdown.bd_slip, " +
		       "b.breakdown.machine.machine_name, " +
		       "b.breakdown.machine.eqid, " +
		       "b.breakdown.ticket_raised_time, " +
		       "b.consumptionDate, " + 
		       "b.breakdown.shift.shift_name, " +
		       "b.breakdown.root_cause, " +
		       "b.breakdown.action_taken, " +
		       "b.Consumedquantity, " +
		       "ms.availableQuantity) " +
		       "FROM BreakdownSpare b " +
		       "LEFT JOIN MaintSpareStock ms ON b.maintspare.maintspare_id = ms.maintSpare.maintspare_id")
		List<SpareConsumptionReportDTO> getAllBreakdownSpareDetails();

	 
	 
	 @Query("SELECT new com.kfMaintenancce.dto.SpareConsumptionReportDTO(" +
		       "b.maintspare.spare_name, " +
		       "b.breakdown.bd_slip, " +
		       "b.breakdown.machine.machine_name, " +
		       "b.breakdown.machine.eqid, " +
		       "b.breakdown.ticket_raised_time, " +
		       "b.consumptionDate, " + 
		       "b.breakdown.shift.shift_name, " +
		       "b.breakdown.root_cause, " +
		       "b.breakdown.action_taken, " +
		       "b.Consumedquantity, " +
		       "ms.availableQuantity) " +  
		       "FROM BreakdownSpare b " +
		       "LEFT JOIN MaintSpareStock ms ON b.maintspare.maintspare_id = ms.maintSpare.maintspare_id " +  
		       "WHERE b.maintspare.maintspare_id = :maintspareId")
		List<SpareConsumptionReportDTO> getBreakdownSpareDetailsByMaintSpareId(@Param("maintspareId") int maintspareId);




	 
	

}
