package org.belstu.fakegram.FakeGram.service;

import org.springframework.http.ResponseEntity;

import java.io.IOException;

public interface ImageService {
    ResponseEntity<byte[]> getPhoto(String name) throws IOException;
}
