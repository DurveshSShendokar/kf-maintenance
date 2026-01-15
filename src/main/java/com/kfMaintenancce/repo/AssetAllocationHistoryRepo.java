package com.kfMaintenancce.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kfMaintenancce.model.AssetAllocation;
import com.kfMaintenancce.model.AssetAllocationHistory;
import com.kfMaintenancce.model.AssetInventory;


@Repository
public interface AssetAllocationHistoryRepo extends JpaRepository<AssetAllocationHistory, Integer>, AssetAllocationHistoryRepoCustom{
	  
	
	@Query("SELECT h FROM AssetAllocationHistory h WHERE h.assetInventory.asset_inventory_id = :assetId")
	List<AssetAllocationHistory> findHistoryByAsset(@Param("assetId") int assetId);

	@Query("SELECT h FROM AssetAllocationHistory h " +
		       "WHERE h.assetInventory.asset_inventory_id = :assetId " +
		       "AND h.allocationDate BETWEEN :startDate AND :endDate")
		List<AssetAllocationHistory> findHistoryByAssetAndDateRange(@Param("assetId") int assetId,
		                                                            @Param("startDate") Date startDate,
		                                                            @Param("endDate") Date endDate);
	
	
	///////////////////////////////
	 @Query("SELECT a FROM AssetAllocationHistory a WHERE a.status = 'DEALLOCATED'")
	    List<AssetAllocationHistory> findAllDeallocated();
	 
	  // ✅ Basic deallocated fetch with pagination
	    @Query("SELECT a FROM AssetAllocationHistory a WHERE a.status = 'DEALLOCATED'")
	    Page<AssetAllocationHistory> findAllDeallocated(Pageable pageable);

	    // ✅ Deallocated fetch with keyword search (search in asset name, user name, or any field)
	    @Query("SELECT a FROM AssetAllocationHistory a " +
	           "WHERE a.status = 'DEALLOCATED' AND " +
	           "(LOWER(a.allocateTo.userName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	           "OR LOWER(a.assetInventory.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
	           "OR LOWER(a.status) LIKE LOWER(CONCAT('%', :keyword, '%')) )")
	    Page<AssetAllocationHistory> searchDeallocated(@Param("keyword") String keyword, Pageable pageable);

	    // ✅ Count of all deallocated records
	    @Query("SELECT COUNT(a) FROM AssetAllocationHistory a WHERE a.status = 'DEALLOCATED'")
	    long countAllDeallocated();
	    ///////////////////////////////////////
	    

	 @Query("SELECT h FROM AssetAllocationHistory h " +
		       "WHERE h.allocationDate BETWEEN :startDate AND :endDate")
		List<AssetAllocationHistory> findHistoryByDateRange(@Param("startDate") Date startDate,
		                                                    @Param("endDate") Date endDate);
	 
	 
	 
	 
	 
	 
	 
	 @Query("SELECT a FROM AssetAllocation a WHERE a.assetInventory.asset_inventory_id = :assetId " +
		       "AND a.allocationDate BETWEEN :startDate AND :endDate")
		Page<AssetAllocation> findActiveByAssetAndDateRange(@Param("assetId") int assetId,
		                                                    @Param("startDate") Date startDate,
		                                                    @Param("endDate") Date endDate,
		                                                    Pageable pageable);

		@Query("SELECT h FROM AssetAllocationHistory h WHERE h.assetInventory.asset_inventory_id = :assetId " +
		       "AND h.allocationDate BETWEEN :startDate AND :endDate")
		Page<AssetAllocationHistory> findHistoryByAssetAndDateRange(@Param("assetId") int assetId,
		                                                            @Param("startDate") Date startDate,
		                                                            @Param("endDate") Date endDate,
		                                                            Pageable pageable);

		@Query("SELECT a FROM AssetAllocation a WHERE a.allocationDate BETWEEN :startDate AND :endDate")
		Page<AssetAllocation> findActiveByDateRange(@Param("startDate") Date startDate,
		                                            @Param("endDate") Date endDate,
		                                            Pageable pageable);

		@Query("SELECT h FROM AssetAllocationHistory h WHERE h.allocationDate BETWEEN :startDate AND :endDate")
		Page<AssetAllocationHistory> findHistoryByDateRange(@Param("startDate") Date startDate,
		                                                    @Param("endDate") Date endDate,
		                                                    Pageable pageable);

	 
	 
		@Query("SELECT a FROM AssetAllocation a WHERE " +
			       "a.allocationDate BETWEEN :startDate AND :endDate AND " +
			       "(LOWER(a.assetInventory.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
			       "OR LOWER(a.allocateTo.userName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
			Page<AssetAllocation> searchActiveByDateRange(@Param("startDate") Date startDate,
			                                              @Param("endDate") Date endDate,
			                                              @Param("keyword") String keyword,
			                                              Pageable pageable);

			@Query("SELECT h FROM AssetAllocationHistory h WHERE " +
			       "h.allocationDate BETWEEN :startDate AND :endDate AND " +
			       "(LOWER(h.assetInventory.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
			       "OR LOWER(h.allocateTo.userName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
			Page<AssetAllocationHistory> searchHistoryByDateRange(@Param("startDate") Date startDate,
			                                                      @Param("endDate") Date endDate,
			                                                      @Param("keyword") String keyword,
			                                                      Pageable pageable);

			@Query("SELECT a FROM AssetAllocation a WHERE a.assetInventory.asset_inventory_id = :assetId " +
			       "AND a.allocationDate BETWEEN :startDate AND :endDate AND " +
			       "(LOWER(a.assetInventory.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
			       "OR LOWER(a.allocateTo.userName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
			Page<AssetAllocation> searchActiveByAssetAndDateRange(@Param("assetId") int assetId,
			                                                      @Param("startDate") Date startDate,
			                                                      @Param("endDate") Date endDate,
			                                                      @Param("keyword") String keyword,
			                                                      Pageable pageable);

			@Query("SELECT h FROM AssetAllocationHistory h WHERE h.assetInventory.asset_inventory_id = :assetId " +
			       "AND h.allocationDate BETWEEN :startDate AND :endDate AND " +
			       "(LOWER(h.assetInventory.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%')) " +
			       "OR LOWER(h.allocateTo.userName) LIKE LOWER(CONCAT('%', :keyword, '%')))")
			Page<AssetAllocationHistory> searchHistoryByAssetAndDateRange(@Param("assetId") int assetId,
			                                                              @Param("startDate") Date startDate,
			                                                              @Param("endDate") Date endDate,
			                                                              @Param("keyword") String keyword,
			                                                              Pageable pageable);

	 
	 
	 
	 
			@Query("SELECT COUNT(a) FROM AssetAllocation a WHERE a.assetInventory.asset_inventory_id = :assetId " +
				       "AND a.allocationDate BETWEEN :startDate AND :endDate")
				long countActiveByAssetAndDateRange(@Param("assetId") int assetId,
				                                    @Param("startDate") Date startDate,
				                                    @Param("endDate") Date endDate);

				@Query("SELECT COUNT(h) FROM AssetAllocationHistory h WHERE h.assetInventory.asset_inventory_id = :assetId " +
				       "AND h.allocationDate BETWEEN :startDate AND :endDate")
				long countHistoryByAssetAndDateRange(@Param("assetId") int assetId,
				                                     @Param("startDate") Date startDate,
				                                     @Param("endDate") Date endDate);

				@Query("SELECT COUNT(a) FROM AssetAllocation a WHERE a.allocationDate BETWEEN :startDate AND :endDate")
				long countActiveByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

				@Query("SELECT COUNT(h) FROM AssetAllocationHistory h WHERE h.allocationDate BETWEEN :startDate AND :endDate")
				long countHistoryByDateRange(@Param("startDate") Date startDate, @Param("endDate") Date endDate);

	 
	 
	 
	 
	 
	 
}