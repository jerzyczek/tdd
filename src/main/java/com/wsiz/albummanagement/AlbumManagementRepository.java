package com.wsiz.albummanagement;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumManagementRepository extends CrudRepository<AlbumRequest, Long> {
    Optional<AlbumRequest> findByName(String name);
}
