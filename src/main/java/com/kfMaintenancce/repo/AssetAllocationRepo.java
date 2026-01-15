package com.kfMaintenancce.repo;

import java.util.Date;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kfMaintenancce.model.AssetAllocation;
import com.kfMaintenancce.model.AssetInventory;


@Repository
public interface AssetAllocationRepo extends JpaRepository<AssetAllocation, Integer>, AssetAllocationRepoCustom{
	  
	
	  // Count assets with allocate
    @Query("SELECT COUNT(a) FROM AssetAllocation a WHERE a.allocateTo IS NOT NULL")
    long countAssetssWithAllocate();

    // Count assets without allocate
    @Query("SELECT COUNT(a) FROM AssetAllocation a WHERE a.allocateTo IS NULL")
    long countAssetssWithoutAllocate();
    
    List<AssetAllocation> findByAssetInventory(AssetInventory assetInventory);
    
    @Query("SELECT a FROM AssetAllocation a WHERE a.status = :status")
    List<AssetAllocation> findByStatus(@Param("status") String status);
    
    @Query("SELECT COUNT(a) FROM AssetAllocation a WHERE a.status = 'DEALLOCATED'")
    long countDeAllocatedAssets();

    
    @Query("SELECT a FROM AssetAllocation a WHERE a.assetInventory.asset_inventory_id = :assetId")
    List<AssetAllocation> findByAssetInventoryId(@Param("assetId") int assetId);

    
    @Query("SELECT a FROM AssetAllocation a WHERE a.assetInventory.asset_inventory_id = :assetId")
    List<AssetAllocation> findActiveByAsset(@Param("assetId") int assetId);
    
    @Query("SELECT a FROM AssetAllocation a " +
    	       "WHERE a.assetInventory.asset_inventory_id = :assetId " +
    	       "AND a.allocationDate BETWEEN :startDate AND :endDate")
    	List<AssetAllocation> findActiveByAssetAndDateRange(@Param("assetId") int assetId,
    	                                                    @Param("startDate") Date startDate,
    	                                                    @Param("endDate") Date endDate);

    @Query("SELECT a FROM AssetAllocation a " +
    	       "WHERE a.allocationDate BETWEEN :startDate AND :endDate")
    	List<AssetAllocation> findActiveByDateRange(@Param("startDate") Date startDate,
    	                                            @Param("endDate") Date endDate);


}