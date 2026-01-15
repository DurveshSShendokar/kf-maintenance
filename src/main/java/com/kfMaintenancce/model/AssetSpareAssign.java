package com.kfMaintenancce.model;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.*;

@Entity
@Table(name = "asset_spare_assign")  
public class AssetSpareAssign {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

   
    @ManyToOne
    @JoinColumn(name = "assetinventory_id", nullable = false)
    private AssetInventory assetinventory;
    
    
    

    @ManyToOne
    @JoinColumn(name = "spare_id", nullable = false) 
    private Spare spare;
    
    
   

    
    // Getters and Setters
    
    
    
    public int getId() {
        return id;
    
	}

	public void setId(int id) {
        this.id = id;
    }

    public AssetInventory getAssetinventory() {
        return assetinventory;
    }

    public void setAssetinventory(AssetInventory assetinventory) {
        this.assetinventory = assetinventory;
    }

    public Spare getSpare() {
		return spare;
	}

	public void setSpare(Spare spare) {
		this.spare = spare;
	}

	

   
}
