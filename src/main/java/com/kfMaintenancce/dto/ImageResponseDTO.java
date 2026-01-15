package com.kfMaintenancce.dto;

import com.kfMaintenancce.model.General_breakdown;

public class ImageResponseDTO {
	
	  private int id;
	    private String imageUrl;
	    private String imageName;
	    private General_breakdown general_breakdown;
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
		public String getImageName() {
			return imageName;
		}
		public void setImageName(String imageName) {
			this.imageName = imageName;
		}
		public General_breakdown getGeneral_breakdown() {
			return general_breakdown;
		}
		public void setGeneral_breakdown(General_breakdown general_breakdown) {
			this.general_breakdown = general_breakdown;
		}
		 // Constructor
	    public ImageResponseDTO(int id, String imageUrl, General_breakdown general_breakdown) {
	        this.id = id;
	        this.imageUrl = imageUrl;
	        this.imageName = extractImageName(imageUrl); // Extract image name from imageUrl
	        this.general_breakdown = general_breakdown;
	    }

	    // Method to extract the image name from the image URL
	    private String extractImageName(String imageUrl) {
	        if (imageUrl != null) {
	            return imageUrl.substring(imageUrl.lastIndexOf('\\') + 1); // Use '\\' for Windows paths
	        }
	        return null; // Handle case where imageUrl is null
	    }
	    
	    

}
