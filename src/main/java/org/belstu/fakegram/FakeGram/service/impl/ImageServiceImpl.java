package org.belstu.fakegram.FakeGram.service.impl;

import lombok.NoArgsConstructor;
import org.belstu.fakegram.FakeGram.service.ImageService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

//"D:\\5sem\\NC\\fakegram\\backend\\src\\main\\resources\\images\\"
@NoArgsConstructor
@Service
public class ImageServiceImpl implements ImageService {
    @Value("${upload.path}")
    private String filePath;
    @Override
    public ResponseEntity<byte[]> getPhoto(String name) throws IOException {
        File imgPath = new File(filePath+"/"+name);

        byte[] image = Files.readAllBytes(imgPath.toPath());
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.IMAGE_JPEG);
        headers.setContentLength(image.length);
        return new ResponseEntity<>(image, headers, HttpStatus.OK);
    }
}
