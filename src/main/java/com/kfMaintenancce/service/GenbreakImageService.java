package com.kfMaintenancce.service;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.kfMaintenancce.model.genbreak_Image;

public interface GenbreakImageService {

	// public genbreak_Image saveImage(MultipartFile images, int genbreakId) throws IOException;
	  public genbreak_Image saveImages(MultipartFile image, int genbreakId) throws IOException;
	
}
