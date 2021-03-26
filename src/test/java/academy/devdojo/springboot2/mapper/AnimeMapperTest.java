package academy.devdojo.springboot2.mapper;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.requests.AnimePostRequestBody;
import academy.devdojo.springboot2.util.AnimePostRequestBodyCreator;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;

class AnimeMapperTest {

    @InjectMocks
    AnimeMapper animeMapper;

    @Test
    void toAnime_mustConvert() {
        AnimePostRequestBody animePostRequestBody = AnimePostRequestBodyCreator.createAnimePostRequestBody();

        Anime anime = AnimeMapper.INSTANCE.toAnime(animePostRequestBody);
    }
}