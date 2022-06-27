package com.wsiz.albummanagement;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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

    @PutMapping("/album")
    public ResponseEntity<AlbumRequest> editAlbumById(@RequestBody AlbumRequest albumRequest) {
        Optional<AlbumRequest> albumRequestOptional = this.albumManagementRepository.findById(albumRequest.getId());

        AlbumRequest albumRequestToSave = albumRequestOptional.get();

        albumRequestToSave.setName(albumRequest.getName());
        albumRequestToSave.setAuthor(albumRequest.getAuthor());

        this.albumManagementRepository.save(albumRequestToSave);
        Optional<AlbumRequest> albumRequestEdited = this.albumManagementRepository.findById(albumRequestToSave.getId());

        return ResponseEntity.ok(albumRequestEdited.get());
    }

    private void validateAlbumData(final AlbumRequest albumRequest) {

        if (Objects.isNull(albumRequest)) {
            throw new NameException();
        }

        if (Objects.isNull(albumRequest.getName())) {
            throw new NameException();
        }

        if (Objects.isNull(albumRequest.getAuthor())) {
            throw new NameException();
        }
    }

}