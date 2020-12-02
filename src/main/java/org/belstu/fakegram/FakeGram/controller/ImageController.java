package org.belstu.fakegram.FakeGram.controller;

import org.belstu.fakegram.FakeGram.service.ImageService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
@RequestMapping("api/photo")
public class ImageController {
    private ImageService imageService;

    @GetMapping(value = "/{name}")
    @ResponseBody
    public ResponseEntity<byte[]> getPhoto(@PathVariable() String name) throws IOException {
        return imageService.getPhoto(name);
    }
}
