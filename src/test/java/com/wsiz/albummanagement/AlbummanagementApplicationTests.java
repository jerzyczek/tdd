package com.wsiz.albummanagement;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
class AlbummanagementApplicationTests {

	@Autowired
	private AlbumManagementController albumManagementController;

	@Test
	public void saveAlbumWithValidDataShouldReturn200StatusCode() {
		final AlbumRequest albumRequest = new AlbumRequest();
		albumRequest.setName("Name1");
		albumRequest.setAuthor("Author1");
		ResponseEntity<?> response = this.albumManagementController.saveAlbum(albumRequest);
		assertThat(response.getStatusCode(), Is.is(HttpStatus.OK));
	}

	@Test
	public void saveAlbumWithNullNameShouldThrowNameException() {
		final AlbumRequest albumRequest = new AlbumRequest();
		albumRequest.setName(null);
		albumRequest.setAuthor("Author1");
		Assertions.assertThrows(NameException.class, () -> {
			ResponseEntity<?> response = this.albumManagementController.saveAlbum(albumRequest);
		});
	}

	@Test
	public void saveAlbumWithNullAuthorShouldThrowNameException() {
		final AlbumRequest albumRequest = new AlbumRequest();
		albumRequest.setName("Name1");
		albumRequest.setAuthor(null);
		Assertions.assertThrows(NameException.class, () -> {
			ResponseEntity<?> response = this.albumManagementController.saveAlbum(albumRequest);
		});
	}


}
