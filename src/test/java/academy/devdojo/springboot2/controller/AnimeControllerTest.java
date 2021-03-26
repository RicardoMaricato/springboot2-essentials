package academy.devdojo.springboot2.controller;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.domain.dto.AnimeDto;
import academy.devdojo.springboot2.service.AnimeService;
import academy.devdojo.springboot2.util.AnimeCreator;
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

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class AnimeControllerTest {

    @InjectMocks
    AnimeController animeController;

    @Mock
    AnimeService animeService;

    @BeforeEach
    void setUp() {
        PageImpl<AnimeDto> animePage = new PageImpl<>(List.of(AnimeCreator.createValidAnimeDto()));
        BDDMockito.when(animeService.listAll(ArgumentMatchers.any()))
                .thenReturn(animePage);
    }

    @Test
    @DisplayName("List returns list of anime inside page object when successful")
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
}