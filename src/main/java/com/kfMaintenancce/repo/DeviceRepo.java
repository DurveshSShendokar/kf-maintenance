package com.kfMaintenancce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.Device;

public interface DeviceRepo extends JpaRepository<Device, Integer> {
	@Query("from Device d where d.deviceId=?1")
	Optional<Device> getDeviceByDeviceId(String deviceId);

}
