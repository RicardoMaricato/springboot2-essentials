package academy.devdojo.springboot2.mapper;

import academy.devdojo.springboot2.domain.Anime;
import academy.devdojo.springboot2.domain.dto.AnimeDto;
import org.springframework.stereotype.Component;

@Component
public class AnimeMapperDto {

    public AnimeDto ormToDto(Anime anime) {
        return AnimeDto.builder()
                .id(anime.getId())
                .name(anime.getName())
                .build();
    }
}
