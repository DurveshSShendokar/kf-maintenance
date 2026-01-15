package com.kfMaintenancce.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.kfMaintenancce.model.AssetSpare;
import com.kfMaintenancce.model.Spare;

import java.util.List;

@Repository
public interface AssetSpareRepository extends JpaRepository<AssetSpare, Integer> {

//    @Query("SELECT aspare.spare FROM AssetSpare aspare WHERE aspare.assetInventory.equipmentId = :equipmentId AND aspare.spare.active = 1")
//    List<Spare> findActiveSparesByEquipmentId(@Param("equipmentId") String equipmentId);
	 @Query("SELECT aspare.spare FROM AssetSpare aspare WHERE aspare.assetInventory.asset_inventory_id = :assetInventoryId AND aspare.spare.active = 1")
	    List<Spare> findActiveSparesByAssetInventoryId(@Param("assetInventoryId") int assetInventoryId);
}

