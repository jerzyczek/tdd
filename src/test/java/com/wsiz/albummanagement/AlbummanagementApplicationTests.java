package com.wsiz.albummanagement;

import org.hamcrest.core.Is;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.hamcrest.MatcherAssert.assertThat;

@SpringBootTest
@AutoConfigureTestDatabase
class AlbummanagementApplicationTests {

	@Autowired
	private AlbumManagementController albumManagementController;

	@Autowired
	private AlbumManagementRepository albumManagementRepository;

	@Autowired
	private JdbcTemplate jdbcTemplate;

	@BeforeEach
	public void setup() {
		final AlbumRequest albumRequest = new AlbumRequest();
		albumRequest.setId(1L);
		albumRequest.setName("Name1");
		albumRequest.setAuthor("Author1");
		this.albumManagementRepository.save(albumRequest);
	}

	@AfterEach
	public void execute() {
		this.jdbcTemplate.execute("TRUNCATE TABLE album" );
	}

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

	@Test
	public void saveAlbumWithNullShouldThrowNameException() {
		final AlbumRequest albumRequest = null;
		Assertions.assertThrows(NameException.class, () -> {
			ResponseEntity<?> response = this.albumManagementController.saveAlbum(albumRequest);
		});
	}

	@Test
	public void editAlbumWithValidDataShouldEditAlbum() {
		final AlbumRequest albumRequest = new AlbumRequest();
		albumRequest.setId(1L);
		albumRequest.setName("Name44");
		albumRequest.setAuthor("Author44");

		ResponseEntity<AlbumRequest> response = this.albumManagementController.editAlbumById(albumRequest);
		assertThat(response.getBody().getAuthor(), Is.is("Author44"));
		assertThat(response.getBody().getName(), Is.is("Name44"));
	}

	@Test
	public void editAlbumNameNullShouldThrowNameException() {
		final AlbumRequest albumRequest = new AlbumRequest();
		albumRequest.setId(1L);
		albumRequest.setName(null);
		albumRequest.setAuthor("Author44");

		Assertions.assertThrows(NameException.class, () -> {
			ResponseEntity<?> response = this.albumManagementController.editAlbumById(albumRequest);
		});
	}

	@Test
	public void editAuthorNullShouldThrowNameException() {
		final AlbumRequest albumRequest = new AlbumRequest();
		albumRequest.setId(1L);
		albumRequest.setName("Name22");
		albumRequest.setAuthor(null);

		Assertions.assertThrows(NameException.class, () -> {
			ResponseEntity<?> response = this.albumManagementController.editAlbumById(albumRequest);
		});
	}

	@Test
	public void deleteAlbumByName() {
		ResponseEntity<?> response = this.albumManagementController.deleteAlbum("Name1");
		assertThat(response.getStatusCode(), Is.is(HttpStatus.OK));
	}

	@Test
	public void deleteAlbumByNameAlbumNotFoundShouldThrowAlbumNotFoundException() {
		Assertions.assertThrows(AlbumNotFoundException.class, () -> {
			ResponseEntity<?> response = this.albumManagementController.deleteAlbum("Name166");
		});
	}

	@Test
	public void findAlbumShouldReturnAlbumEntity() {
		ResponseEntity<AlbumRequest> response = this.albumManagementController.findAlbum("Name1");
		assertThat(response.getBody().getName(), Is.is("Name1"));
		assertThat(response.getBody().getAuthor(), Is.is("Author1"));
	}

	@Test
	public void saveCategoryWithValidDataShouldReturn200StatusCode() {
		final Category category = new Category();
		category.setName("Name1");
		ResponseEntity<?> response = this.albumManagementController.saveCategory(category);
		assertThat(response.getStatusCode(), Is.is(HttpStatus.OK));
	}

	@Test
	public void saveCategoryWithInValidDataShouldThrowCategoryException() {
		final Category category = new Category();
		category.setName(null);
		Assertions.assertThrows(CategoryException.class, () -> {
			ResponseEntity<?> response = this.albumManagementController.saveCategory(category);
		});
	}

}
