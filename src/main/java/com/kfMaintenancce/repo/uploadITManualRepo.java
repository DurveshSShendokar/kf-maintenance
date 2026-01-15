package com.kfMaintenancce.repo;



import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.kfMaintenancce.model.AssetSpareAssign;
import com.kfMaintenancce.model.Upload_IT_Manual;

public interface uploadITManualRepo extends JpaRepository<Upload_IT_Manual, Integer> {
	
	
	
	 @Query("FROM Upload_IT_Manual a WHERE a.assetInventory.asset_inventory_id = ?1 ")
		List<Upload_IT_Manual> findByassetInventoryID(int asset_inventory_id);
		
		 @Query("SELECT u FROM Upload_IT_Manual u WHERE u.assetInventory.asset_inventory_id = :assetInventoryId AND u.active = 1")
		    List<Upload_IT_Manual> findActiveManualsByAssetInventoryId(@Param("assetInventoryId") int assetInventoryId);
		    
		    @Query("SELECT u FROM Upload_IT_Manual u WHERE u.assetInventory.asset_inventory_id = :assetInventoryId")
		    List<Upload_IT_Manual> findByAssetInventoryId(@Param("assetInventoryId") int assetInventoryId);		    
		    
		    @Query("SELECT u FROM Upload_IT_Manual u WHERE u.assetInventory.asset_inventory_id = :assetInventoryId AND u.active = :active")
		    List<Upload_IT_Manual> findByAssetInventoryIdAndActive(@Param("assetInventoryId") int assetInventoryId, @Param("active") int active);
		    
		    
		    
		    // ✅ 1️⃣ Pagination
		    @Query("SELECT u FROM Upload_IT_Manual u WHERE u.assetInventory.asset_inventory_id = :asset_inventory_id")
		    Page<Upload_IT_Manual> findByAssetInventoryIdPage(@Param("asset_inventory_id") int asset_inventory_id, Pageable pageable);


		    // ✅ 2️⃣ Search + Pagination
		    @Query("SELECT u FROM Upload_IT_Manual u " +
		           "WHERE u.assetInventory.asset_inventory_id = :asset_inventory_id " +
		           "AND (" +
		           "LOWER(u.UserManualName) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		           "LOWER(u.FileType) LIKE LOWER(CONCAT('%', :keyword, '%')) OR " +
		           "LOWER(u.assetInventory.lab.labName) LIKE LOWER(CONCAT('%', :keyword, '%'))" +
		           ")")
		    Page<Upload_IT_Manual> findByAssetInventoryIdAndSearch(@Param("asset_inventory_id") int asset_inventory_id,
		                                                           @Param("keyword") String keyword,
		                                                           Pageable pageable);

		    // ✅ 3️⃣ Count
		    @Query("SELECT COUNT(u) FROM Upload_IT_Manual u WHERE u.assetInventory.asset_inventory_id = :asset_inventory_id")
		    long countByAssetInventoryId(@Param("asset_inventory_id") int asset_inventory_id);


}
