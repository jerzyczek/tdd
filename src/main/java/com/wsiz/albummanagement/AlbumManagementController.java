package com.wsiz.albummanagement;

import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class AlbumManagementController {

    @Autowired
    private AlbumManagementRepository albumManagementRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @PostMapping("/album")
    public ResponseEntity<?> saveAlbum(@RequestBody final AlbumRequest albumRequest) {

        validateAlbumData(albumRequest);

        this.albumManagementRepository.save(albumRequest);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/album")
    public ResponseEntity<AlbumRequest> editAlbumById(@RequestBody AlbumRequest albumRequest) {
        validateAlbumData(albumRequest);
        Optional<AlbumRequest> albumRequestOptional = this.albumManagementRepository.findById(albumRequest.getId());

        AlbumRequest albumRequestToSave = albumRequestOptional.get();

        albumRequestToSave.setName(albumRequest.getName());
        albumRequestToSave.setAuthor(albumRequest.getAuthor());

        this.albumManagementRepository.save(albumRequestToSave);
        Optional<AlbumRequest> albumRequestEdited = this.albumManagementRepository.findById(albumRequestToSave.getId());

        return ResponseEntity.ok(albumRequestEdited.get());
    }

    @DeleteMapping("/album/{name}")
    public ResponseEntity<?> deleteAlbum(@PathVariable String name) {
        Optional<AlbumRequest> albumRequest = this.albumManagementRepository.findByName(name);

        if (!albumRequest.isPresent()) {
            throw new AlbumNotFoundException();
        }

        this.albumManagementRepository.delete(albumRequest.get());
        return ResponseEntity.ok().build();
    }

    @GetMapping("/album/{name}")
    public ResponseEntity<AlbumRequest> findAlbum(@PathVariable String name) {
        Optional<AlbumRequest> albumRequest = this.albumManagementRepository.findByName(name);

        if (albumRequest.isPresent()) {
            return ResponseEntity.ok(albumRequest.get());
        }

        throw new AlbumNotFoundException();
    }

    @PostMapping("/category")
    public ResponseEntity<?> saveCategory(@RequestBody Category category) {
        validateCategory(category);
        this.categoryRepository.save(category);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/category")
    public ResponseEntity<Category> editCategory(@RequestBody Category category) {
        Optional<Category> categoryFromDatabase = this.categoryRepository.findById(category.getId());

        if (!categoryFromDatabase.isPresent()) {
            throw new CategoryException();
        }

        Category categoryToEdit = categoryFromDatabase.get();
        categoryToEdit.setName(category.getName());
        this.categoryRepository.save(categoryToEdit);

        Optional<Category> categorySaved = this.categoryRepository.findByName(category.getName());
        if (!categorySaved.isPresent()) {
            throw new CategoryException();
        }

        return ResponseEntity.ok(categorySaved.get());
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

    private void validateCategory(final Category category) {
        if (Objects.isNull(category)) {
            throw new CategoryException();
        }

        if (Objects.isNull(category.getName())) {
            throw new CategoryException();
        }
    }

}
