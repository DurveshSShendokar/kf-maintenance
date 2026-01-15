package com.kfMaintenancce.model;

import javax.persistence.*;

@Entity
@Table(name = "asset_spare")
public class AssetSpare {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "asset_inventory_id", nullable = false)
    private AssetInventory assetInventory;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "spare_id", nullable = false)
    private Spare spare;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public AssetInventory getAssetInventory() {
        return assetInventory;
    }

    public void setAssetInventory(AssetInventory assetInventory) {
        this.assetInventory = assetInventory;
    }

    public Spare getSpare() {
        return spare;
    }

    public void setSpare(Spare spare) {
        this.spare = spare;
    }

    @Override
    public String toString() {
        return "AssetSpare [id=" + id + ", assetInventory=" + assetInventory + ", spare=" + spare + "]";
    }
}
