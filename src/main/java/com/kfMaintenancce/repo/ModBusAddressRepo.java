package com.kfMaintenancce.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kfMaintenancce.model.ModBusAddress;

@Repository
public interface ModBusAddressRepo extends JpaRepository<ModBusAddress, Integer> {
	@Query("from ModBusAddress m where m.name=?1 and m.modAddress=?2")
	Optional<ModBusAddress> getModBusAddByNameAndAdd(String name, String modAddress);
	@Query("from ModBusAddress m where m.modAddress=?2")

	Optional<ModBusAddress> getModBusAddByAdd(String address);

}
