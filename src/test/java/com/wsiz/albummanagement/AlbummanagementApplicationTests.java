package com.wsiz.albummanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;

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
		this.albumManagementController.saveAlbum(albumRequest);
	}

}
