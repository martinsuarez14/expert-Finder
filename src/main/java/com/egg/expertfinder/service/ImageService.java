package com.egg.expertfinder.service;

import com.egg.expertfinder.entity.Image;
import com.egg.expertfinder.exception.MyException;
import com.egg.expertfinder.repository.ImageRepository;
import java.io.IOException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

//  Creamos una Image
    @Transactional
    public Image createImage(MultipartFile file) {
        if (file != null) {
            try {
                Image image = new Image(file);
                return imageRepository.save(image);
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

//  Actualizamos una Image
    @Transactional
    public Image updateImage(Long idImage, MultipartFile file) {
        if (file != null) {
            try {
                Image image = new Image();

//              Corroboramos que exista la Image cuyo Id llega
                if (idImage != null) {
                    Optional<Image> response = imageRepository.findById(idImage);
                    if (response.isPresent()) {
                        image = response.get();
                        image.setMime(file.getContentType());
                        image.setName(file.getName());
                        image.setContent(file.getBytes());
                        return imageRepository.save(image);
                    }
                }
//              Si no existe no hacemos nada
            } catch (IOException e) {
                System.err.println(e.getMessage());
            }
        }
        return null;
    }

//  Retornamos una Image
    public Image getImage(Long idImage) throws MyException {
        Optional<Image> response = imageRepository.findById(idImage);
        if (response.isPresent()) {
            return response.get();
        } else {
            throw new MyException("No se encontró una imagen.");
        }
    }
}
