package com.wsiz.albummanagement;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlbumManagementRepository extends CrudRepository<AlbumRequest, Long> {
}
