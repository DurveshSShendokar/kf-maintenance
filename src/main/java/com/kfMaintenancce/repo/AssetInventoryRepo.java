package com.kfMaintenancce.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.dto.GroupSearchDTO;
import com.kfMaintenancce.model.AssetInventory;

public interface AssetInventoryRepo extends JpaRepository<AssetInventory, Integer> ,AssetInventoryCustomeRepo {

    @Query("SELECT a FROM AssetInventory a WHERE " +
            "(:search IS NULL OR LOWER(a.equipmentId) LIKE LOWER(CONCAT('%', :search, '%')) " +
            "OR LOWER(a.lab.labName) LIKE LOWER(CONCAT('%', :search, '%'))) ")
    Page<AssetInventory> findAllAssetsWithSearch(String search, Pageable pageable);


    boolean existsByEquipmentId(String equipmentId);
	 @Query("from AssetInventory a where a.equipmentId=?1")
	Optional<AssetInventory> getAssetInventoryByEquipmentId(String qrcode);
	 
	 @Query("SELECT ai FROM AssetInventory ai WHERE ai.asset_inventory_id NOT IN (SELECT aa.assetInventory.asset_inventory_id FROM AssetAllocation aa)")
	    List<AssetInventory> findNonAllocatedAssets();
	 
	 @Query("SELECT ai FROM AssetInventory ai WHERE ai.asset_inventory_id  IN (SELECT aa.assetInventory.asset_inventory_id FROM AssetAllocation aa)")
	    List<AssetInventory> findAllocatedAssets();
	 
	 @Query("SELECT ai FROM AssetInventory ai WHERE ai.model = ?1")
	    List<AssetInventory> getAssetsByModel(String model);

	 
	 AssetInventory findByEquipmentId(String equipmentId);
}
