package com.kfMaintenancce.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "complaint_images")
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "image_url")
    private String imageUrl; 

    @Lob
    @Column(name = "image_data")
    private byte[] imageData; // image data as bytes
    
    @ManyToOne
    @JoinColumn(name = "complaint_id")
    private Complaint complaint;

    @Column(name="Upload_date")
	private Date uploadDate;
   
    @Column(name = "uploaded_by_username")
    private String uploadedByUsername;


    public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Complaint getComplaint() {
        return complaint;
    }

    public void setComplaint(Complaint complaint) {
        this.complaint = complaint;
    }

	public byte[] getImageData() {
		return imageData;
	}

	public void setImageData(byte[] imageData) {
		this.imageData = imageData;
	}

	public Date getUploadDate() {
		return uploadDate;
	}

	public void setUploadDate(Date uploadDate) {
		this.uploadDate = uploadDate;
	}

	public String getUploadedByUsername() {
		return uploadedByUsername;
	}

	public void setUploadedByUsername(String uploadedByUsername) {
		this.uploadedByUsername = uploadedByUsername;
	}
	
    
}
