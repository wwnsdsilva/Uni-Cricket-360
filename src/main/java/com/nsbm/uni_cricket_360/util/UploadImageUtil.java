package com.nsbm.uni_cricket_360.util;

import com.nsbm.uni_cricket_360.exception.ImageFileException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.UUID;

@Component
public class UploadImageUtil {

    public String saveImage(String uploadDir, String subFolder, MultipartFile imageFile) {
        try {
            Path uploadPath = Paths.get(uploadDir);

            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            String fileName = UUID.randomUUID() + "_" + StringUtils.cleanPath(imageFile.getOriginalFilename());
            Path filePath = uploadPath.resolve(fileName);

            imageFile.transferTo(filePath.toFile());


            System.out.println(filePath.toString());
            System.out.println("/uploads/" + subFolder + "/" + fileName);

            // return filePath.toString();
            return "/uploads/" + subFolder + "/" + fileName;

        } catch (IOException e) {
            throw new ImageFileException("Failed to store image file: " + e.getMessage());
        }
    }
}
