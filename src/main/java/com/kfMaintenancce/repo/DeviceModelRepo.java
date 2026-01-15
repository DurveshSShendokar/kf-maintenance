package com.kfMaintenancce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.DeviceModel;

public interface DeviceModelRepo extends JpaRepository<DeviceModel, Integer>{
	@Query("From DeviceModel d  where d.modelName=?1")
	Optional<DeviceModel> getDeviceModelByDevice(String deviceModelName);

}
