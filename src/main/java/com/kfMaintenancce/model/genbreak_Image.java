package com.kfMaintenancce.model;

import java.util.Date;

import javax.persistence.*;

@Entity
@Table(name = "generalbreakdown_Images")
public class genbreak_Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name = "image_url")
    private String imageUrl; 

//    @Lob
//    @Column(name = "image_data")
//    private byte[] imageData; // image data as bytes
    
    @ManyToOne
    @JoinColumn(name = "genbreak_id")
    private General_breakdown General_breakdown;

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

//	public byte[] getImageData() {
//		return imageData;
//	}
//
//	public void setImageData(byte[] imageData) {
//		this.imageData = imageData;
//	}

	public General_breakdown getGeneral_breakdown() {
		return General_breakdown;
	}

	public void setGeneral_breakdown(General_breakdown general_breakdown) {
		General_breakdown = general_breakdown;
	}


}
