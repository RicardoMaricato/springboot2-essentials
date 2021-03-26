package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.domain.dto.AnimeDto;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.requests.AnimePutRequestBody;
import academy.devdojo.springboot2.service.AnimeService;
import academy.devdojo.springboot2.util.AnimeCreator;
import academy.devdojo.springboot2.util.AnimeCreatorDto;
import academy.devdojo.springboot2.util.AnimePostRequestBodyCreator;
import academy.devdojo.springboot2.util.AnimePutRequestBodyCreator;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks
    AnimeController animeController;

    @Mock
    AnimeService animeServiceMock;

    @BeforeEach
    void setUp() {
        PageImpl<AnimeDto> animePage = new PageImpl<>(List.of(AnimeCreatorDto.createValidAnimeDto()));
        BDDMockito.when(animeServiceMock.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);

        BDDMockito.when(animeServiceMock.listAllNonPageable())
                .thenReturn(List.of(AnimeCreatorDto.createValidAnimeDto()));

        BDDMockito.when(animeServiceMock.findById(ArgumentMatchers.anyLong()))
                .thenReturn(AnimeCreatorDto.createValidAnimeDto());

        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(List.of(AnimeCreatorDto.createValidAnimeDto()));

        BDDMockito.when(animeServiceMock.save(ArgumentMatchers.any(AnimePostRequestBody.class)))
                .thenReturn(AnimeCreator.createValidAnime());

        BDDMockito.doNothing().when(animeServiceMock).replace(ArgumentMatchers.any(AnimePutRequestBody.class));

        BDDMockito.doNothing().when(animeServiceMock).delete(ArgumentMatchers.anyLong());
    }

    @Test
    @DisplayName("list returns list of anime inside page object when successful")
    void list_ReturnsListOfAnimesInsidePageObject_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();

        ResponseEntity<Page<AnimeDto>> animePage = animeController.list(null);

        Assertions.assertThat(animePage).isNotNull();

        Assertions.assertThat(animePage.getBody().toList())
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animePage.getBody().toList().get(0).getName()).isEqualTo(expectedName);

        Assertions.assertThat(animePage.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("listAll returns list of anime when successful")
    void listAll_ReturnsListOfAnimes_WhenSuccessful(){
        String expectedName = AnimeCreator.createValidAnime().getName();

        ResponseEntity<List<AnimeDto>> animeDto = animeController.listAll();

        Assertions.assertThat(animeDto.getBody())
                .isNotNull()
                .isNotEmpty()
                .hasSize(1);

        Assertions.assertThat(animeDto.getBody().get(0).getName()).isEqualTo(expectedName);

        Assertions.assertThat(animeDto.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("findById returns anime when successful")
    void findById_ReturnsAnimes_WhenSuccessful(){
        Long expectedId = AnimeCreator.createValidAnime().getId();

        ResponseEntity<AnimeDto> animeDto = animeController.findById(1);

        Assertions.assertThat(animeDto.getBody()).isNotNull();

        Assertions.assertThat(animeDto.getBody().getId()).isEqualTo(expectedId);

        Assertions.assertThat(animeDto.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("findByName returns an empty list of anime when anime is not found")
    void findByName_ReturnsEmptyListOfAnime_WhenAnimeIsNotFound(){
        BDDMockito.when(animeServiceMock.findByName(ArgumentMatchers.anyString()))
                .thenReturn(Collections.emptyList());

        ResponseEntity<List<AnimeDto>> animeDto = animeController.findByName("anime");

        Assertions.assertThat(animeDto.getBody())
                .isNotNull()
                .isEmpty();

        Assertions.assertThat(animeDto.getStatusCode()).isEqualTo(HttpStatus.OK);
    }

    @Test
    @DisplayName("save returns anime when successful")
    void save_ReturnsAnimes_WhenSuccessful(){
        ResponseEntity<Anime> anime = animeController.save(AnimePostRequestBodyCreator.createAnimePostRequestBody());

        Assertions.assertThat(anime.getBody()).isNotNull().isEqualTo(AnimeCreator.createValidAnime());

        Assertions.assertThat(anime.getStatusCode()).isEqualTo(HttpStatus.CREATED);
    }

    @Test
    @DisplayName("replace returns anime when successful")
    void replace_UpdatesAnimes_WhenSuccessful(){
        Assertions.assertThatCode(() -> animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody()))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = animeController.replace(AnimePutRequestBodyCreator.createAnimePutRequestBody());

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Test
    @DisplayName("delete returns anime when successful")
    void delete_RemovesAnimes_WhenSuccessful(){
        Assertions.assertThatCode(() -> animeController.delete(1))
                .doesNotThrowAnyException();

        ResponseEntity<Void> entity = animeController.delete(1);

        Assertions.assertThat(entity).isNotNull();

        Assertions.assertThat(entity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }
}