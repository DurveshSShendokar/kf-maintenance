package com.kfMaintenancce.model;


import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;



@Entity
@Table(name="company_profile")

public class CompanyProfile {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String companyName;
    private String companyCodePrefix;
    private String address;
    private String logoPath;
    private String city;
    private String state;
    private String country;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;


    // -------- Auto-generate CompanyName prefix -----------
    @PrePersist
    @PreUpdate
    public void generatePrefix() {

        if (this.companyName != null &&
                (this.companyCodePrefix == null || this.companyCodePrefix.isEmpty())) {

            // Remove useless words
            String cleaned = companyName
                    .replaceAll("(?i)pvt|private|ltd|limited|llp|solutions|solution|technologies|technology|tech", "")
                    .trim();

            // Words split
            String[] words = cleaned.split("\\s+");

            // Take the first valid word
            String firstWord = words[0];

            // Convert to uppercase
            firstWord = firstWord.toUpperCase();

            // Ensure at least 3 characters (pad if needed)
            if (firstWord.length() >= 3) {
                this.companyCodePrefix = firstWord.substring(0, 3);
            } else {
                // Pad with X if short
                this.companyCodePrefix = String.format("%-3s", firstWord).replace(' ', 'X');
            }
        }
    }


	


	public int getId() {
		return id;
	}





	public void setId(int id) {
		this.id = id;
	}





	public String getCompanyName() {
		return companyName;
	}


	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}


	public String getCompanyCodePrefix() {
		return companyCodePrefix;
	}


	public void setCompanyCodePrefix(String companyCodePrefix) {
		this.companyCodePrefix = companyCodePrefix;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getLogoPath() {
		return logoPath;
	}


	public void setLogoPath(String logoPath) {
		this.logoPath = logoPath;
	}


	public String getCity() {
		return city;
	}


	public void setCity(String city) {
		this.city = city;
	}


	public String getState() {
		return state;
	}


	public void setState(String state) {
		this.state = state;
	}


	public String getCountry() {
		return country;
	}


	public void setCountry(String country) {
		this.country = country;
	}


	public LocalDateTime getCreatedAt() {
		return createdAt;
	}


	public void setCreatedAt(LocalDateTime createdAt) {
		this.createdAt = createdAt;
	}


	public LocalDateTime getUpdatedAt() {
		return updatedAt;
	}


	public void setUpdatedAt(LocalDateTime updatedAt) {
		this.updatedAt = updatedAt;
	}
    
    
    
    
    

}
