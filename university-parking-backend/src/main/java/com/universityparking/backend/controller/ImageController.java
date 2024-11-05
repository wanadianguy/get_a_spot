package com.universityparking.backend.controller;

import com.universityparking.backend.dto.ImageConverters;
import com.universityparking.backend.dto.ImageInformationOutDTO;
import com.universityparking.backend.model.Image;
import com.universityparking.backend.service.ImageService;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/image")
public record ImageController(
        ImageService imageService,
        ImageConverters imageConverters
) {

    @PostMapping("")
    public ResponseEntity<ImageInformationOutDTO> uploadImage(
            @RequestPart("file") MultipartFile file
    ) throws IOException {
        if (!imageService.isValidImageFormat(file)) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
        Image createdImage = imageService.uploadImage(file);
        ImageInformationOutDTO createdImageInformationOutDto = imageConverters.convertObjectToInformationOutDto(createdImage);
        return new ResponseEntity<>(createdImageInformationOutDto, HttpStatus.CREATED);
    }

    @GetMapping(value = "/{id}", produces = "image/jpeg")
    public ResponseEntity<ByteArrayResource> getImage(
            @PathVariable String id
    ) {
        byte[] imageData = imageService.getImageData(id);
        if (imageData != null) {
            return ResponseEntity.ok()
                    .header("Content-Type", "image/jpeg")
                    .body(new ByteArrayResource(imageData));
        } else {
            return ResponseEntity.notFound().build();
        }
    }
}
