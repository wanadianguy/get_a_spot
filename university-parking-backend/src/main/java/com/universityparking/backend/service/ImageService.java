package com.universityparking.backend.service;

import com.universityparking.backend.exception.EntityNotFoundException;
import com.universityparking.backend.model.Image;
import com.universityparking.backend.repository.ImageRepository;
import net.coobird.thumbnailator.Thumbnails;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

@Service
public record ImageService(
        ImageRepository imageRepository
) {

    public Image uploadImage(MultipartFile file) throws IOException {
        // Load image into BufferedImage object
        BufferedImage originalImage = ImageIO.read(file.getInputStream());

        // Resize image to a specific size
        BufferedImage resizedImage = Thumbnails.of(originalImage)
                .size(1000, 1000)
                .asBufferedImage();

        // Convert resized image to byte array
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(resizedImage, "jpg", baos);
        byte[] resizedImageData = baos.toByteArray();

        // Save resized image in database
        Image newImage = new Image();
        newImage.setData(resizedImageData);
       return imageRepository.save(newImage);
    }

    public byte[] getImageData(String id) {
        Image image = imageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Image.class, id));
        return (image != null) ? image.getData() : null;
    }

    public Image getImageById(String id) {
        return imageRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(Image.class, id));
    }

    public boolean isValidImageFormat(MultipartFile file) {
        String fileName = file.getOriginalFilename();
        return fileName != null && (fileName.endsWith(".jpg") || fileName.endsWith(".jpeg"));
    }
}
