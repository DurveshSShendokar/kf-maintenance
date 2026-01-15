package com.kfMaintenancce.repo;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kfMaintenancce.model.CompanyProfile;

import java.util.List;
import java.util.Optional;

public interface CompanyProfileRepo extends JpaRepository<CompanyProfile,Integer>, CompanyProfileRepoCustom {


    Optional<CompanyProfile> findTopByOrderByIdAsc();

    @Query("SELECT c FROM CompanyProfile c ORDER BY c.id ASC")
    CompanyProfile getCompanyProfile();


}
