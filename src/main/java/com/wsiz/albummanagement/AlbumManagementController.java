package com.wsiz.albummanagement;

import java.util.Objects;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AlbumManagementController {

    @Autowired
    private AlbumManagementRepository albumManagementRepository;

    @PostMapping("/album")
    public ResponseEntity<?> saveAlbum(@RequestBody final AlbumRequest albumRequest) {

        validateAlbumData(albumRequest);

        this.albumManagementRepository.save(albumRequest);
        return ResponseEntity.ok().build();
    }

    private void validateAlbumData(final AlbumRequest albumRequest) {
        if (Objects.isNull(albumRequest.getName())) {
            throw new NameException();
        }

        if (Objects.isNull(albumRequest.getAuthor())) {
            throw new NameException();
        }
    }

}
