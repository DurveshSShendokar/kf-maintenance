package com.kfMaintenancce.repo;


import com.kfMaintenancce.model.AssetInventory;
import com.kfMaintenancce.model.AssetSpareAssign;
import com.kfMaintenancce.model.Spare;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface AssetSpareAssignRepository extends JpaRepository<AssetSpareAssign, Integer> {

	 @Query("SELECT a FROM AssetSpareAssign a WHERE a.assetinventory.asset_inventory_id = :assetInventoryId AND a.spare.id = :spareId")
	    Optional<AssetSpareAssign> findByAssetInventoryIdAndSpareId(@Param("assetInventoryId") int assetInventoryId, @Param("spareId") int spareId);

	 @Query("FROM AssetSpareAssign a WHERE a.assetinventory.asset_inventory_id = ?1 ")
	List<AssetSpareAssign> findByassetInventoryID(int asset_inventory_id);
	 
	 @Query("SELECT a FROM AssetSpareAssign a WHERE a.assetinventory.id = :asset_inventory_id AND a.spare.active = 1")
	    List<AssetSpareAssign> findActiveSparesByAssetInventoryId(int asset_inventory_id);
	 	
	
	 @Query("SELECT a FROM AssetSpareAssign a WHERE a.assetinventory = ?1 AND a.spare = ?2")
	 Optional<AssetSpareAssign> findByAssetinventoryAndSpare(AssetInventory assetinventory, Spare spare);
	 
	 
	  // ✅ 1️⃣ Pagination only
	    @Query("SELECT a FROM AssetSpareAssign a WHERE a.assetinventory.asset_inventory_id = :asset_inventory_id")
	    Page<AssetSpareAssign> findByAssetInventoryIdPage(
	            @Param("asset_inventory_id") int asset_inventory_id,
	            Pageable pageable);

	    // ✅ 2️⃣ Search + Pagination
	    @Query("SELECT a FROM AssetSpareAssign a " +
	           "WHERE a.assetinventory.asset_inventory_id = :asset_inventory_id " +
	           "AND (" +
	           "LOWER(a.spare.spare_name) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
	           "LOWER(a.assetinventory.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
	           ")")
	    Page<AssetSpareAssign> findByAssetInventoryIdAndSearch(
	            @Param("asset_inventory_id") int asset_inventory_id,
	            @Param("keyword") String keyword,
	            Pageable pageable);

	    // ✅ 3️⃣ Count total spares assigned to an asset
	    @Query("SELECT COUNT(a) FROM AssetSpareAssign a WHERE a.assetinventory.asset_inventory_id = :asset_inventory_id")
	    long countByAssetInventoryId(@Param("asset_inventory_id") int asset_inventory_id);
	 



	}
