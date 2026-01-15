package com.kfMaintenancce.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardCopyOption;
import java.util.Optional;
import java.util.UUID;

@Component
@ConfigurationProperties(prefix = "project")
public class FileConfig {

    private String files;

    public String getUploadDir() {
        if (files == null || files.isEmpty()) {
            throw new NullPointerException("Project files directory is not configured properly.");
        }
        File directory = new File(files);
        if (!directory.isAbsolute()) {
            directory = new File(System.getProperty("user.dir"), files);
        }
        if (!directory.exists()) {
            directory.mkdirs();
        }
        return directory.getAbsolutePath();
    }

    public void setFiles(String files) {
        this.files = files;
    }

    public String saveFile(MultipartFile file, String folder, String fileName) throws IOException {
        // Base root folder from config
        Path rootPath = Path.of(getUploadDir());

        // Subfolder path
        Path folderPath = rootPath.resolve(folder);

        // Ensure directories exist
        Files.createDirectories(folderPath);

        // Extract extension if available
        String originalFilename = file.getOriginalFilename();
        String extension = "";
        if (originalFilename != null && originalFilename.contains(".")) {
            extension = originalFilename.substring(originalFilename.lastIndexOf("."));
        }

        // Construct new file name with UUID
        String safeFileName = fileName + "_" + UUID.randomUUID() + extension;

        // Final file path
        Path filePath = folderPath.resolve(safeFileName);

        // Save the file
        Files.copy(file.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

        // Return absolute path
        return filePath.toAbsolutePath().toString();
    }

}
