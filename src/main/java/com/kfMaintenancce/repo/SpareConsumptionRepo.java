package com.kfMaintenancce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.dto.SpareUtilizationReportDTO;
import com.kfMaintenancce.model.SpareConsumption;

public interface SpareConsumptionRepo  extends JpaRepository<SpareConsumption, Integer> {
	@Query("From SpareConsumption s where s.complaint.comp_id=?1")
	List<SpareConsumption> getSpareConsumptionByComplaintID(int comp_id);
	@Query("From SpareConsumption s where s.complaint.comp_id=?1 and s.spare.spare_id=?2")

	Optional<SpareConsumption> getSpareConsumptionByComplaintIDAndSpareId(int comp_id, int spare_id);
	
	@Query("SELECT sc FROM SpareConsumption sc WHERE sc.complaint.comp_id = :complaintId")
	List<SpareConsumption> findSparesByComplaintId(@Param("complaintId") int complaintId);
	
	 // To get the total utilized quantity for each spare from the SpareConsumption
    @Query("SELECT SUM(sc.quantity) FROM SpareConsumption sc WHERE sc.spare.spare_id = :spareId")
    Double getTotalUtilizedBySpareId(@Param("spareId") int spareId);

    @Query("SELECT sc FROM SpareConsumption sc WHERE sc.spare.spare_id = :spareId")
    List<SpareConsumption> findSparesBySpareId(@Param("spareId") int spareId);
    
 // In SpareConsumptionRepository
    @Query("SELECT sc FROM SpareConsumption sc JOIN sc.complaint c WHERE c.ticketNo = :ticketNumber")
    List<SpareConsumption> findByTicketNumber(@Param("ticketNumber") String ticketNumber);

    @Query("SELECT COUNT(sc) FROM SpareConsumption sc " +
            "WHERE (:spareId IS NULL OR sc.spare.id = :spareId)")
    long countBySpareId(@Param("spareId") Integer spareId);

    @Query("SELECT sc FROM SpareConsumption sc WHERE sc.complaint.comp_id = :compId")
    List<SpareConsumption> findByComplaintCompId(@Param("compId") int compId);

    @Query("SELECT sc FROM SpareConsumption sc " +
            "WHERE (:spareId IS NULL OR sc.spare.spare_id = :spareId)")
    Page<SpareConsumption> searchWithPagination(@Param("spareId") Integer spareId, Pageable pageable);

    @Query("SELECT sc FROM SpareConsumption sc " +
            "WHERE (:spareId IS NULL OR sc.spare.spare_id = :spareId) " +
            "AND (" +
            " :keyword IS NULL OR :keyword = '' OR " +
            " LOWER(sc.spare.spare_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            " LOWER(sc.complaint.ticketNo) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
            " LOWER(sc.complaint.assetInventory.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
            ")")
    Page<SpareConsumption> searchWithPaginationAndKeyword(
            @Param("spareId") Integer spareId,
            @Param("keyword") String keyword,
            Pageable pageable);

}
